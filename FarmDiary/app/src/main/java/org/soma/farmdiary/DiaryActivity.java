package org.soma.farmdiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiaryActivity extends ListViewActivity {
    DiaryDB db = new DiaryDB(this, "diary.db", null, 1);

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Write Farmming Story", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(getApplicationContext(), WriteActivity.class));
            }
        });

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
}
