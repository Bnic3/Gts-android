package com.gts.gts.Utility;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by john.nana on 8/3/2017.
 */

public class CustomPager extends ViewPager {
    public CustomPager(Context context) {
        super(context);
    }

    public CustomPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Never allow swiping to switch between pages
        return false;
    }
}
