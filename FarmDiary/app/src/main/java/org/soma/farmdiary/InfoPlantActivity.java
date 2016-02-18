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

    ArrayList<String> plantInfoList = new ArrayList<String>();
    ArrayList<String> plantNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_plant);

        ListView plantInfoListView = (ListView) findViewById(R.id.plantInfoListview);
        plantInfoListView.setOnItemClickListener(this);

        plantInfoList.add("http://www.nihhs.go.kr/personal/plow_view.asp?PageIndex=1&cntntsNo=101643&nongsaroMenuId=PS03172&sSeCode=335001&stype=1&searchword=");
        plantNameList.add("양파");

        plantInfoList.add("http://www.nihhs.go.kr/personal/plow_view.asp?PageIndex=1&cntntsNo=101641&nongsaroMenuId=PS03172&sSeCode=335001&stype=1&searchword=");
        plantNameList.add("상추");

        plantInfoList.add("http://www.nihhs.go.kr/personal/plow_view.asp?PageIndex=3&cntntsNo=101627&nongsaroMenuId=PS03172&sSeCode=335001&stype=1&searchword=");
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
