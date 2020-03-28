package com.test.test;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.test.test.lib.Tool;
import com.test.test.view.MyLinearLayout;
import com.test.test.view.TopLinearLayout;

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
        Button testBtn = this.findViewById(R.id.test_btn);
        Button clickBtn = this.findViewById(R.id.click_btn);
        Button longClickBtn = this.findViewById(R.id.long_click_btn);
        Button focusBtn = this.findViewById(R.id.focus_btn);
        ScrollView scrollView = this.findViewById(R.id.scroll_view);
        EditText editText = this.findViewById(R.id.edit_text);
        MyLinearLayout myLinearLayout = this.findViewById(R.id.my_linear_layout);
        EditText editTextForMyLinearLayout = this.findViewById(R.id.input);
        TopLinearLayout topLinearLayout = this.findViewById(R.id.top_linear_layout);

        // 点击事件
        clickBtn.setOnClickListener((View view) -> {
            Tool.log("click event");
        });

        testBtn.setOnClickListener((View view) -> {
            Tool.log("测试事件拦截的按钮事件，如果你看到我，说明事件拦截失败");
        });

        editTextForMyLinearLayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view , int keyCode , KeyEvent event)
            {
                Tool.log("my linear layout event handle key event: " + keyCode);
                return true;
            }
        });

        myLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Tool.log("my linear layout click event");
            }
        });

        topLinearLayout.setOnClickListener((View view) -> {
            Tool.log("top linear layout click event");
        });

        // 长按事件
        longClickBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view)
            {
                Tool.log("long click event");
                return true;
            }
        });

        // 滚动事件
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view , int scrollX , int scrollY , int oldScrollX , int oldScrollY)
            {
                Tool.log("视图正在滚动：scrollX: " + scrollX + "; scrollY: " + scrollY + "; oldScrollX: " + oldScrollX + "; oldScrollY: " + oldScrollY);
            }
        });

        // 设置焦点
        focusBtn.setOnClickListener((View view) -> {
            // 设置输入框获取焦点
            editText.setFocusable(true);
        });

        // 设置焦点变化事件
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view , boolean hasFocus)
            {
                Tool.log("是否获取焦点：" + hasFocus);
            }
        });


        // 键盘输入事件
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view , int keyCode , KeyEvent keyEvent)
            {
                Tool.log("on key event keyCode: " + keyCode);
                return false;
            }
        });

        // 测试按钮新增触摸事件
        testBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view , MotionEvent event)
            {
                // 这个是用来判断是否侦听器消费了事件，如果没有消费的话
                // 那么可以返回 false，如果返回 false 那么会继续触发
                // 父视图上的
                Tool.log("test btn on touch event handler");
                return false;
            }
        });

    }


}
