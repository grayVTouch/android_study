package com.test.test.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.test.test.lib.Tool;

public class DynamicBroadcastReceiver extends BroadcastReceiver
{
    public static int count = 0;
    @Override
    public void onReceive(Context context , Intent intent)
    {
        Tool.log("动态广播触发" + ++DynamicBroadcastReceiver.count);
        Tool.tip(context , "动态广播触发");
    }
}
