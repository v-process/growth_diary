package org.soma.farmdiary;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends ListActivity {

    protected ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(mListAdapter);
    }
}
