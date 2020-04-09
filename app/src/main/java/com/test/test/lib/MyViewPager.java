package com.test.test.lib;


import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.LinearLayout;

public class MyViewPager extends LinearLayout
{
    // 必须
    public MyViewPager(Context context)
    {
        super(context);
    }

    // 必须
    public MyViewPager(Context context , AttributeSet attrs)
    {
        super(context , attrs);
    }

//    public boolean dispatchTouchEvent(MotionEvent event)
//    {
//
//    }



}
