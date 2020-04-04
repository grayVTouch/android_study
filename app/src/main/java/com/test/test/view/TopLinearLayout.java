package com.test.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.test.test.lib.Tool;

public class TopLinearLayout extends LinearLayout
{

    public TopLinearLayout(Context context)
    {
        super(context);
    }

    public TopLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    // 事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
//        Tool.log("event: dispatch touch event");

        // 一定要调用该方法，否则 事件分发 无法继续 执行
        super.dispatchTouchEvent(event);
        return true;
    }

    // 事件拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
//        Tool.log("event: on intercept touch event");
        super.onInterceptTouchEvent(event);
        return false;
    }

    // 相关事件
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Tool.log("event: on touch event");
        return false;
    }
}
