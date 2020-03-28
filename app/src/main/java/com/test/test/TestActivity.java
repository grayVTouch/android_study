package com.test.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.test.test.lib.Tool;

public class TestActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.test);

        this.run(bundle);
        Tool.log("create");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Tool.log("on start");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Tool.log("on resume");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Tool.log("on restart");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Tool.log("on pause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Tool.log("on stop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Tool.log("on destroy");
    }

    // 当 app 发生旋转等操作的时候会触发一些相关操作
    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putString("username" , "running");
        bundle.putString("sex" , "male");
        Tool.log("on save instance state ...");
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        Tool.log("回复 bundle 中的数据：" + JSON.toJSONString(bundle));
        Tool.log("restore instance state: username: " + bundle.getString("username") + "; sex: " + bundle.getString("sex"));
    }

    public void run(Bundle bundle)
    {
        // 转成 json 字符串
        Tool.log(JSON.toJSONString(bundle));

        //
    }
}
