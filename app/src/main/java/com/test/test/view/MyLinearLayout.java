package com.test.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.test.test.lib.Tool;

public class MyLinearLayout extends LinearLayout
{

    public MyLinearLayout(Context context)
    {
        // 要求必须有这个
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    // 事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
//        Tool.log("my dispatch touch event");
        super.dispatchTouchEvent(event);
        return true;
    }

    // 事件拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
//        Tool.log("my on intercept touch event");
        super.onInterceptTouchEvent(event);
//        return false;
        return false;
    }

    // 相关事件
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
//        Tool.log("my on touch event");
        super.onTouchEvent(event);
        return true;
    }
}
