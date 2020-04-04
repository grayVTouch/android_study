package com.test.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.view.MotionEvent;

import com.test.test.lib.Tool;

public class TwoFloor extends LinearLayout
{
    public TwoFloor(Context context)
    {
        super(context);
    }

    public TwoFloor(Context context , AttributeSet attrs)
    {
        super(context , attrs);
    }

    public boolean dispatchTouchEvent(MotionEvent event)
    {
        super.dispatchTouchEvent(event);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            // 只对点击事件进行判断
            String name = Tool.getEventName(action);
            Tool.log("two_floor: 当前拦截的事件：" + name + "； 已拦截");
            return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        String name = Tool.getEventName(action);

        Tool.log("two_floor: 当前事件： " + name + "； 消费状态未知");
        return super.onTouchEvent(event);
    }
}
