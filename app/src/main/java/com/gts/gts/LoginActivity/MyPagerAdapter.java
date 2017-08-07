package com.gts.gts.LoginActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by john.nana on 8/4/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return  new FragmentThree();
            case 3:
                return new FragmentFour();
            default:
                break;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
