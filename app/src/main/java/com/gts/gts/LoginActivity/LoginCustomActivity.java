package com.gts.gts.LoginActivity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gts.gts.R;



public class LoginCustomActivity extends AppCompatActivity implements View.OnClickListener {

private Toolbar toolbar;

    private ImageButton estatebtn;
    private ImageButton userbtn;
    private ViewPager customPager;
    private ViewPagerAdapter pagerAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_custom);

        toolbar = (Toolbar) findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);
        int resId=LoginCustomActivity.this.getResources().getIdentifier("houselogo", "drawable",LoginCustomActivity.this.getPackageName());
        toolbar.setLogo(resId);
        toolbar.setTitle("  GTS");

       /* firstpage = (Button) findViewById(R.id.firstpagerBtn);
        secondpage = (Button) findViewById(R.id.secondpagerBtn);*/
       estatebtn = (ImageButton) findViewById(R.id.estateBtn2);
       userbtn = (ImageButton) findViewById(R.id.userBtn);

        customPager = (ViewPager) findViewById(R.id.customPagerContainer);


        estatebtn.setOnClickListener(this);
        userbtn.setOnClickListener(this);


        pagerAdapter = new ViewPagerAdapter();

        customPager.setAdapter(pagerAdapter);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.estateBtn2:
               customPager.setCurrentItem(2);
                break;

            case R.id.secondpagerBtn:
                /*verifyOtp();*/
                break;

        }// end switch

    }//end onclick




    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.firstpager;
                    break;
                case 1:
                    resId = R.id.secondpager;
                    break;
            }
            return findViewById(resId);
        }// end instantiate

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }


    }// end page adapter
}
