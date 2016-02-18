package org.soma.farmdiary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class InfoPlantActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {
    final static String ADDR = "http://www.nihhs.go.kr/personal/plow_view.asp?nongsaroMenuId=PS03172&PageIndex=1&cntntsNo=";

    ArrayList<String> plantInfoList = new ArrayList<String>();
    ArrayList<String> plantNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_plant);

        ListView plantInfoListView = (ListView) findViewById(R.id.plantInfoListview);
        plantInfoListView.setOnItemClickListener(this);

        plantInfoList.add(ADDR + "101643");
        plantNameList.add("양파");

        plantInfoList.add(ADDR + "101641");
        plantNameList.add("상추");

        plantInfoList.add(ADDR + "101627");
        plantNameList.add("배추");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, plantNameList);

        plantInfoListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = plantInfoList.get(position);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
