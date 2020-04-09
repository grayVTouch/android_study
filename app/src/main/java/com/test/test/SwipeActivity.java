package com.test.test;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.test.lib.Tool;

import androidx.appcompat.app.AppCompatActivity;

public class SwipeActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.swipe);
        this.run();
    }

    // 初始化
    public void run()
    {
        LinearLayout linearViewPager = this.findViewById(R.id.linear_view_pager);
        LinearLayout linearViewPagerInner = this.findViewById(R.id.linear_view_pager_inner);

        /**
         * 子视图初始化
         */
        linearViewPager.post(() -> {
            // 获取视图的宽度
            int linearViewPagerW = linearViewPager.getWidth();
            // 获取子元素的数量
            int childCount = linearViewPagerInner.getChildCount();
            for (int i = 0; i < childCount; ++i)
            {
                View view = linearViewPagerInner.getChildAt(i);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                // 设置宽度
                params.width = linearViewPagerW;
                view.setLayoutParams(params);
            }
        });

        /**
         * 相关的动画效果
         */
        linearViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view , MotionEvent event)
            {
                int action = event.getAction();
                String actionName = Tool.getEventName(action);
                Tool.log("当前触发的事件：" + actionName);
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        this.onTouchStart(event);
                        // 手动触发所有子视图的事件
//                        this.triggerChildrenClickEvent(linearViewPager);
//                        return true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        this.onTouchMove(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        this.onTouchUp(event);
                        break;
                }

                return false;
            }

            // 触发所有子类的点击事件
            public void triggerChildrenClickEvent(ViewGroup view)
            {
                int count = view.getChildCount();
                for (int i = 0; i < count; ++i)
                {
                    View v = view.getChildAt(i);
                    // 如何判断当前的视图是否是 布局
                }
            }

            public void onTouchStart(MotionEvent event)
            {
                Tool.log("当前执行的动作：" + Tool.getEventName(event.getAction()));
            }

            public void onTouchMove(MotionEvent event)
            {
                Tool.log("当前执行的动作：" + Tool.getEventName(event.getAction()));
            }

            public void onTouchUp(MotionEvent event)
            {
                Tool.log("当前执行的动作：" + Tool.getEventName(event.getAction()));
            }
        });


//        linearViewPager.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view)
//            {
//                Tool.log("linearlayout 上注册的点击事件");
//            }
//        });

        Button btn = this.findViewById(R.id.test_btn);
        btn.setOnClickListener((View view) -> {
            Tool.log("test_btn 点击事件触发成功");
        });

        ImageView imageView = this.findViewById(R.id.image);
        imageView.setOnClickListener((View view) -> {
            Tool.log("image 点击事件触发");
        });



    }
}
