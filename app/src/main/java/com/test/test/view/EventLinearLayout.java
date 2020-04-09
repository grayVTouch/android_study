package com.test.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.test.test.lib.Tool;

public class EventLinearLayout extends LinearLayout
{

    public EventLinearLayout(Context context)
    {
        super(context);
    }

    public EventLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    // 事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        super.dispatchTouchEvent(event);

//        int action = event.getAction();
////        Tool.log("当前执行的事件：" + Tool.getEventName(action));
//        switch (action)
//        {
//            case MotionEvent.ACTION_DOWN:
//                this.onTouchStart();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                this.onTouchMove();
//                break;
//            case MotionEvent.ACTION_UP:
//                this.onTouchUp();
//                break;
//        }
//        super.dispatchTouchEvent(event);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return false;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        return false;
    }

    public void onTouchStart()
    {
        Tool.log("触摸点击事件触发");
    }

    public void onTouchMove()
    {
        Tool.log("触摸移动事件触发");
    }

    public void onTouchUp()
    {
        Tool.log("触摸松开事件触发");
    }
}
