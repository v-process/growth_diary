package org.soma.farmdiary;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SiteInfoActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener {

    ListView siteListView;
    ArrayList<String> siteList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_info);

        siteListView = (ListView) findViewById(R.id.siteListView);
        siteListView.setOnItemClickListener(this);
        siteList.add("http://www.rda.go.kr/");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, siteList);

        siteListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = siteList.get(position);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
