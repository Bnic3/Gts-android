package com.gts.gts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);
        int resId=MainActivity.this.getResources().getIdentifier("houselogo", "drawable", MainActivity.this.getPackageName());
        toolbar.setLogo(resId);
        toolbar.setTitle("  GTS");
    }
}
