package com.gts.gts.LoginActivity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gts.gts.R;

public class LoginCustomActivity extends AppCompatActivity implements View.OnClickListener {


 private Button firstpage;
 private Button secondpage;
    private ViewPager customPager;
    private ViewPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_custom);

        firstpage = (Button) findViewById(R.id.firstpagerBtn);
        secondpage = (Button) findViewById(R.id.secondpagerBtn);
        customPager = (ViewPager) findViewById(R.id.customPagerContainer);

        firstpage.setOnClickListener(this);
        secondpage.setOnClickListener(this);


        pagerAdapter = new ViewPagerAdapter();

        customPager.setAdapter(pagerAdapter);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firstpagerBtn:
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
