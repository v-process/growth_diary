package org.soma.farmdiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
    }

    public void plantInfo(View view) {
        startActivity(new Intent(this, InfoPlantActivity.class));
    }

    public void siteInfo(View view) {
        startActivity(new Intent(this, InfoSiteActivity.class));
    }
}
