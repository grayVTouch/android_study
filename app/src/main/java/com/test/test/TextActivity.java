package com.test.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.test.test.lib.Tool;

public class TextActivity extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.text);

        if (bundle != null) {
            Tool.log("intent 中传递的值 name：" + bundle.getCharSequence("name"));
            Tool.log("intent 中传递的值 sex：" + bundle.getCharSequence("sex"));
        } else {
            Tool.log("bundle is null");
        }
    }
}
