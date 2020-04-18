package com.test.test.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.core.app.NotificationCompat;

import com.test.test.MainActivity;
import com.test.test.R;
import com.test.test.lib.Tool;

/**
 * Service He  IntentService 的区别就是
 *
 * Service 通常用于处理一些复杂的任务
 * IntentService 通常用于处理一些简单的任务
 */

public class TestService extends Service
{
    public int onStartCommand(Intent intent , int flags , int startId)
    {
        Tool.log("service onStartCommand");
        // 如果用户通过 startService 启动了当前服务
        // 那么服务会一直运行下去，除非自己调用了
        // this.stopSelf() 方法终止服务
        // 否则服务会一直运行
        // 或者 通过其他组件（比如 activity | broadcast receiver）
        // 调用 stopService 终止
        (new Thread(() -> {
            try {
                Thread.sleep(10 * 1000);
                this.stopSelf();
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        })).start();
        String type = intent.getStringExtra("type");
        if (type.equals("foreground")) {
            // 您应该是中使用显式的 intent 来启动服务
            Intent otherIntent = new Intent(this , MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , otherIntent , 0);
            String channelId = "test";
            Notification notification = new NotificationCompat.Builder(this , channelId)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("前台服务标题")
                    .setContentText("前台服务内容")
                    .setContentIntent(pendingIntent)
                    .setTicker("ticker")
                    .build();
            this.startForeground(Tool.random() , notification);
            Tool.log("启动 foreground 服务");
        } else {
            Tool.log("启动 image_02 服务");
        }
        return Service.START_STICKY;
    }

    public IBinder onBind(Intent intent)
    {
        Tool.log("service bind");
        // 如果组件通过调用 bindService 来创建服务
        // 且未调用 onStartCommand
        // 则服务只会在该组件绑定时运行，当该服务与
        // 其所有组件取消绑定后，系统便会将其销毁

        // 长时间运行的服务，在运行超过 30min 后
        // 系统会调低他的重要程度，应该会被列入
        // 缓存进程，也就是所谓的 待回收进程
        // 当系统的内存不足的时候，第一个回收的进程
        // 就是在分类为 缓存进程的 类型中进行回收
        return null;
    }

    public void onCreate()
    {
        Tool.log("service oncreate");
    }

    public void onDestroy()
    {
        Tool.log("service done");
    }
}
