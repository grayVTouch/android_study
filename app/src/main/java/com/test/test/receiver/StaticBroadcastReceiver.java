package com.test.test.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.test.test.lib.Tool;

public class StaticBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context , Intent intent)
    {
        Tool.log("静态广播触发");
        Tool.tip(context , "静态广播触发");
    }
}
