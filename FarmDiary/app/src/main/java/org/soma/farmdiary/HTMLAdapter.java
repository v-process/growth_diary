package org.soma.farmdiary;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by lminjae on 2016. 2. 18..
 */
public class HTMLAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> list;

    public HTMLAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText(Html.fromHtml(list.get(position).get(DiaryDB.TAG_DATA), new HTMLImageGetter(context, tv), null));

        return tv;
    }
}
