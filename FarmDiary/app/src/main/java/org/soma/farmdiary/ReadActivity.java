package org.soma.farmdiary;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends ExplorerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        update("/해바라기 반/짱구");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Write Farming Story~", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(getApplicationContext(), WriteActivity.class));
            }
        });
    }

    public void plantInfo(View view) {
        startActivity(new Intent(this, PlantInfoActivity.class));
    }

    public void siteInfo(View view) {
        startActivity(new Intent(this,SiteInfoActivity.class));
    }
}
