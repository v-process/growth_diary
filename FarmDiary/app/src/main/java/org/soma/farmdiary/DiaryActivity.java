package org.soma.farmdiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DiaryActivity extends ListViewActivity {
    DiaryDB db = new DiaryDB(this, "diary.db", null, 1);

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        {
            final Activity activity = this;

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.cam);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Not Tested!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    new PhotoDialog(activity).show();
                }
            });

        }
        {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.write);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Write Farmming Story", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    startActivity(new Intent(getApplicationContext(), WriteActivity.class));
                }
            });
        }

        update();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        update();
    }

    public void update() {
        ((TextView) findViewById(R.id.type)).setText(type);

        List<Map<String, String>> lst = new ArrayList();

        db.Select(lst);

        if (type == "") {
            ((FloatingActionButton) findViewById(R.id.cam)).hide();

            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv = (TextView) view;
                    type = tv.getText().toString();
                    update();
                }
            });

            Set<String> type_set = new HashSet();

            for (int i = 0; i < lst.size(); i++)
                type_set.add(lst.get(i).get(DiaryDB.TAG_TYPE));

            lst.clear();

            Iterator iter = type_set.iterator();
            while (iter.hasNext()) {
                Map<String, String> m = new HashMap();
                m.put(DiaryDB.TAG_DATA, (String) iter.next());
                lst.add(m);
            }

            Collections.reverse(lst);
        } else {
            ((FloatingActionButton) findViewById(R.id.cam)).show();

            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv = (TextView) view;
                    if (tv.getText().toString().equals("..")) {
                        type = "";
                        update();
                    }
                }
            });

            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    View v = getLayoutInflater().inflate(R.layout.item_scrollview_h, null);
                    final AlertDialog dialog = new AlertDialog.Builder(DiaryActivity.this)
                            .setView(v)
                            .create();

                    if (!((TextView) view).getText().toString().equals("..")) {
                        {
                            Button btn = (Button) ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_btn, null);
                            btn.setText("수정");
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(v, "Can't do it.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                            });

                            ((LinearLayout) v.findViewById(android.R.id.list)).addView(btn);
                        }

                        {
                            Button btn = (Button) ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_btn, null);
                            btn.setText("제거");
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(v, "Can't do it.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                            });

                            ((LinearLayout) v.findViewById(android.R.id.list)).addView(btn);
                        }
                    }

                    dialog.show();

                    return true;
                }
            });

            for (int i = 0; i < lst.size(); i++) {
                if (!lst.get(i).get(DiaryDB.TAG_TYPE).equals(type)) {
                    lst.remove(i--);
                }
            }

            Map<String, String> m = new HashMap();
            m.put(DiaryDB.TAG_DATA, "..");
            lst.add(m);
        }

        Collections.reverse(lst);

        mListAdapter = new HTMLAdapter(this, lst);
        super.update();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        try {
            Bitmap img = null;
            switch (requestCode) {
                case PhotoDialog.FROM_CAMERA:
                    File file = new File(Environment.getExternalStorageDirectory(), "img.png");
                    img = BitmapFactory.decodeFile(file.getAbsolutePath());
                    file.deleteOnExit();
                    break;
                case PhotoDialog.FROM_ALBUM:
                    img = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    break;
            }
            if (img != null) {
                String path = UUID.randomUUID().toString();
                FileOutputStream fos = openFileOutput(path, Context.MODE_PRIVATE);
                int nh = (int) (img.getHeight() * (1024. / img.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(img, 1024, nh, true);
                scaled.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                db.Insert(type, "<img src=\"file:///" + getFilesDir() + '/' + path + "\" />");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class PhotoDialog extends Dialog {
        public static final int FROM_CAMERA = 1;
        public static final int FROM_ALBUM = 2;

        Activity activity;

        public PhotoDialog(Activity activity) {
            super(activity);

            this.activity = activity;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.dialog_photo);

            Button btn_takephoto = (Button) findViewById(R.id.takephoto);
            btn_takephoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhoto();
                    dismiss();
                }
            });
            Button btn_getalbum = (Button) findViewById(R.id.getalbum);
            btn_getalbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAlbum();
                    dismiss();
                }
            });
        }

        public void takePhoto() {
            activity.startActivityForResult(
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            .putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(
                                            new File(
                                                    Environment.getExternalStorageDirectory(),
                                                    "img.png"))),
                    FROM_CAMERA);
        }

        public void getAlbum() {
            activity.startActivityForResult(
                    new Intent(Intent.ACTION_PICK)
                            .setType(MediaStore.Images.Media.CONTENT_TYPE)
                            .setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                    FROM_ALBUM);
        }
    }
}
