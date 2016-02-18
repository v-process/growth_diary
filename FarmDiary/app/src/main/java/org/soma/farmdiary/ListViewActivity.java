package org.soma.farmdiary;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

public class ListViewActivity extends ListActivity {

    protected ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void update() {
        setListAdapter(mListAdapter);
    }
}
