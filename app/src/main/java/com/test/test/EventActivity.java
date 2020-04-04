package com.test.test;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.test.test.lib.Tool;
import com.test.test.view.OneFloor;

import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.event);
        this.run();
    }

    public void run()
    {
        /**
         * **************
         * 事件分发
         * **************
         */
        Button testBtn = this.findViewById(R.id.event_btn);
        OneFloor testLinearLayout = this.findViewById(R.id.test_linear_layout);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // event btn click
                Tool.log("event btn click!!");
            }
        });

        // 查找子元素（这个很明显就是 直接子元素的数量）
//        Tool.log("子视图数量：" + testLinearLayout.getChildCount());



    }

    public boolean onTouchEvent(MotionEvent event)
    {
        // true  -> 表示当前 activity 消费事件
        // false -> 如果返回 false，那么事件消费会丢给下一级
        // 事件传递都是一级一级进行传递的，
        // super 也是可以的
        super.onTouchEvent(event);
        Tool.log("activity on touch event handler: " + Tool.getEventName(event.getAction()));
        return true;
    }


}
