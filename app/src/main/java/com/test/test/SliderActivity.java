package com.test.test;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.test.test.lib.Tool;
import com.test.test.view.MyLinearLayout;
import com.test.test.view.TopLinearLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class SliderActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.slider);
        this.run();
    }

    public void run()
    {
        // 尺寸学习
        this.measure();

        //
        ArrayList<ImageView> imageList = new ArrayList<>();
        int min = 1;
        int max = 5;
        for (int i = min; i <= max; ++i)
        {
            int resId = this.getResources().getIdentifier("image_0" + i , "id" , this.getPackageName());
            ImageView imageView = this.findViewById(resId);
            imageList.add(imageView);
        }


        //
    }

    public void measure()
    {
        LinearLayout linearLayout = this.findViewById(R.id.image_list);

        /**
         * 貌似这些都是针对子布局进行设置的，不太清楚这边子视图进行的设置究竟是什么含义
         *
         * 1. UNSPECIFIED 表示子布局的尺寸想要多大就能多大
         * 2. AT_MOST 表示子布局被限制在一个最大值内，当 childView 或 view 被设置成 wrap_content 的时候设置
         * 3. EXACTLY 表示设置精确的值，当 view 或 childView 的宽 / 高 被设置为 match_parent 的时候请设置
         *
         * 结论就是，通过以上设置获取的相关测量尺寸的方式，通常都是不准确的
         */
        int width = View.MeasureSpec.makeMeasureSpec(0 , View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0 , View.MeasureSpec.UNSPECIFIED);

        linearLayout.measure(width , height);

        // 获取容器宽度
        float conWidth = linearLayout.getWidth();

        Tool.log("LinearLayout width：" + linearLayout.getWidth());
        Tool.log("LinearLayout height：" + linearLayout.getHeight());

        // 方法1
        // 注意通过 getWidth | getHeight | getMeasuredWidth | getMeasuredHeight 等方法获取到的
        // 相关参数值，单位都是 px
        Tool.log("LinearLayout measured width：" + Tool.pxToDp(this , linearLayout.getMeasuredWidth()));
        Tool.log("LinearLayout measured height：" + Tool.pxToDp(this , linearLayout.getMeasuredHeight()));

        final SliderActivity self = this;

        // 方法二： 通过添加 视图树 的观察器 来设置
        linearLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw()
            {
                // 移除视图监听器
                linearLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                Tool.log("Observer 中获取到的视图 宽度： " + linearLayout.getWidth() + "；转换为 dp：" + Tool.pxToDp(self , linearLayout.getWidth()));
                Tool.log("Observer 中获取到的视图 高度：" + linearLayout.getHeight() + "；转换为 dp：" + Tool.pxToDp(self , linearLayout.getHeight()));
                return true;
            }
        });


        // 方法三： 添加全局的监听器
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout()
            {
                // 移除事件监听
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Tool.log("linear layout width: px=" + linearLayout.getWidth() + "; dp= " + Tool.pxToDp(self , linearLayout.getWidth()));
            }
        });

        // 方法四： 重写 view 的 onSizeChanged 方法
        // 方法五：重写 view 的 onLayout 方法
        // 方法六： 注册 view 的 addOnLayoutChangeListener 方法，实现 onLayoutChange 方法

    }



}
