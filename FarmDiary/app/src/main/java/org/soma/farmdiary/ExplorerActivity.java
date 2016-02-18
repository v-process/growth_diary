package org.soma.farmdiary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExplorerActivity extends ListViewActivity {

    List<String> pathList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void update(String path) {
        Log.i("Update", path);

        LinearLayout ll = (LinearLayout) findViewById(R.id.path);

        ll.removeAllViews();

        {
            String[] tmp = path.split("/");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < tmp.length; i++) {
                builder.append("/" + tmp[i]);

                final String TP = builder.toString();

                TextView tv = (TextView) ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_path, null);
                tv.setText(tmp[i]);
                tv.setOnClickListener(new View.OnClickListener() {
                    private String path = TP;
                    @Override
                    public void onClick(View v) {
                        update(path);
                    }
                });
                ll.addView(tv);
            }
        }
        ((HorizontalScrollView) findViewById(R.id.scroll)).fullScroll(HorizontalScrollView.FOCUS_RIGHT);

    }
}
