package com.example.kimseungchul.growth_diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class InformationMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_menu);
    }

    public void siteInfo(View view) {
        Intent intent = new Intent(this, SiteInfoActivity.class);
        startActivity(intent);

    }

    public void plantInfo(View view) {
        Intent intet =  new Intent(this,PlantInfoActivity.class);
    }
}
