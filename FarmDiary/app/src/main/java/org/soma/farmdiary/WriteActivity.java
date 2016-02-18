package org.soma.farmdiary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class WriteActivity extends AppCompatActivity {
    DiaryDB db = new DiaryDB(this, "diary.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((EditText) findViewById(R.id.type)).getText().length() < 1) {
                    Snackbar.make(view, "Please Input Type!!!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Post", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    db.Insert(((EditText) findViewById(R.id.type)).getText().toString(), ((EditText) findViewById(R.id.text)).getText().toString());
                    finish();
                }
            }
        });
    }
}
