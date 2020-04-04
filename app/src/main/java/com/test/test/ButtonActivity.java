package com.test.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.test.test.lib.Tool;

import androidx.appcompat.app.AppCompatActivity;


public class ButtonActivity extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.button);
        this.run();
    }

    public void run()
    {
        Button btn = this.findViewById(R.id.touch_btn);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view , MotionEvent event)
            {
                Tool.log("on touch listener handler");
                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Tool.log("on click listener handler");
            }
        });
    }
}
