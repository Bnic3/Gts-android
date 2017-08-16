package com.gts.gts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public Button bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.customToolbar);
        bt3 = (Button) findViewById(R.id.button3);


        setSupportActionBar(toolbar);
        int resId=MainActivity.this.getResources().getIdentifier("houselogo", "drawable", MainActivity.this.getPackageName());

        toolbar.setLogo(resId);
        toolbar.setTitle("  GTS");
    }
}
