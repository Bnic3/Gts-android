package com.gts.gts.LoginActivity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gts.gts.R;
import com.vstechlab.easyfonts.EasyFonts;

public class LoginCustomActivity2 extends FragmentActivity implements FragmentOne.OnFragmentInteractionListener, FragmentTwo.OnFragmentInteractionListener, FragmentThree.OnFragmentInteractionListener, FragmentFour.OnFragmentInteractionListener {

    ViewPager vp;
    private static String TAG = LoginCustomActivity2.class.getSimpleName();
    private Toolbar toolbar;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_custom2);



        vp = (ViewPager) findViewById(R.id.pager2);
        title = (TextView) findViewById(R.id.title);
        title.setTypeface(EasyFonts.androidNation(this));

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

    }

    @Override
    public void onFragmentInteraction(int value) {
        Log.i("JOHNNY", String.valueOf(value));
        vp.setCurrentItem(value);

    }



    @Override
    public void onFragmentInteraction2(Uri uri) {

    }

    @Override
    public void onFragmentInteraction3(Uri uri) {

    }
    @Override
    public void onFragmentInteraction4(int value) {

    }
}
