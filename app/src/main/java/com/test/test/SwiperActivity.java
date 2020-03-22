package com.test.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.test.test.lib.Tool;

public class SwiperActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        Tool.log("onCreate");
        super.onCreate(bundle);
        this.setContentView(R.layout.swiper);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle)
    {
        Tool.log("onRestoreInstanceState");
        System.out.println("bundle 中获取到的值：name: " + bundle.getString("name"));
        System.out.println("bundle 中获取到的值：sex: " + bundle.getString("sex"));
        System.out.println("onRestoreInstanceState trigger");
    }

    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        bundle.putString("name" , "running");
        bundle.putString("sex" , "male");
        super.onSaveInstanceState(bundle);
        Tool.log("onSaveInstanceState");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Tool.log("onStart");
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        Tool.log("onRestart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Tool.log("onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Tool.log("onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Tool.log("onStop");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Tool.log("onDestroy");
    }
}
