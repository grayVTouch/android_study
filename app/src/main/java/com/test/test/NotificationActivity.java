package com.test.test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.app.TaskStackBuilder;

import com.test.test.lib.Tool;

public class NotificationActivity extends AppCompatActivity
{
    private final String channelId = "test";

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.notification);
    }

    private void createChannel(String importance)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 如果 android 系统版本 >= 8.0
            // 用来设置在系统设置中针对当前应用通知的描述
            CharSequence name = "新消息提醒";
            String description = "新消息提醒通知";
            int importantce = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(this.channelId , name , importantce);
            channel.setDescription(description);
            switch (importance)
            {
                case "high":
                    channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
                    break;
                case "default":
                    channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
                    break;
                case "low":
                    channel.setImportance(NotificationManager.IMPORTANCE_LOW);
                    break;
                case "min":
                    channel.setImportance(NotificationManager.IMPORTANCE_MIN);
                    break;

            }
            channel.setShowBadge(true);
            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void createChannel()
    {
        this.createChannel("default");
    }

    public void showNotification(View v)
    {
        // 兼容 >= android 8
        this.createChannel();
        Intent intent = new Intent(this , NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setAction("test");

        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , 0);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this , 0 , intent , 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("app陈学龙：自定义通知标题")
                // 单行通知内容
                .setContentText("app陈学龙：自定义通知内容1111111111111")
                // 可展开的多行通知内容
                .setStyle(
                        new NotificationCompat
                                .BigTextStyle()
                                .bigText("这里是展开后看到的内容正文！11111111111111111111111")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
//                .addAction(R.drawable.logo , "action string" , pendingIntent);

        Notification notification = builder.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        // 唯一id（否则，多条通知仅会显示最后一条，实际上就是更新的意思）
        notificationManagerCompat.notify(Tool.random() , notification);
    }

    public void showNotificationWithGoToActivity(View v)
    {
        this.createChannel();
        Intent intent = new Intent(this , NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("点击推送可以返回到给定的 Activity")
                .setContentText("单行正文")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(Tool.random() , notification);
    }

    public void showNotificationWithAction(View v)
    {
        this.createChannel();
        Intent intent = new Intent(this , NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("点击推送可以返回到给定的 Activity")
                .setContentText("单行正文")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(Tool.random() , notification);
    }

    // 5s 之后触发的通知
    public void showNotificationStay5Second(View v)
    {
        final NotificationActivity self = this;
        (new Thread(new Runnable() {
            public void run()
            {
                try {
                    Thread.sleep(5 * 1000);

                    NotificationActivity.this.createChannel();
                    Intent intent = new Intent(NotificationActivity.this , NotificationActivity.class);
                    intent.setAction("同意");
                    intent.putExtra("decision" , 1);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this , 0 , intent , 0);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this , NotificationActivity.this.channelId)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("点击推送可以返回到给定的 Activity")
                            .setContentText("单行正文")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .addAction(R.drawable.logo , "动作名称" , pendingIntent);

                    Notification notification = builder.build();
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationActivity.this);
                    managerCompat.notify(Tool.random() , notification);
                } catch(Exception e) {
                    System.out.println("异常信息：" + e.getMessage());
                }
            }
        })).start();
    }

    // 显示通知并且能够直接在通知栏中进行回复
    public void showNotificationWithInput(View v)
    {
        this.createChannel();

        String label = "输入框";
        RemoteInput remoteInput = new RemoteInput.Builder("test")
                .setLabel(label)
                .build();

        Intent intent = new Intent(this , NotificationActivity.class);
        intent.setAction("同意");
        intent.putExtra("decision" , 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext() , 0 , intent , PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.logo , label , pendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("点击推送可以返回到给定的 Activity")
                .setContentText("单行正文")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(action);

        Notification notification = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(Tool.random() , notification);
    }

    // 添加进度条
    public void showNotificationWithConfirmProgress(View v)
    {
        this.createChannel("min");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , this.channelId);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle("进度条展示")
                .setContentText("下载中...")
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setOnlyAlertOnce(true);

        int max = 100;
        int cur = 0;
        builder.setProgress(max , cur , false);
        int notificationId = Tool.random();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        // 间隔1s更新一次
        (new Thread(new Runnable() {
            public void run()
            {
                int curVal = cur;
                try {
                    while (curVal < max)
                    {
                        Thread.sleep(1 * 1000);
                        System.out.println("当前的进度: " + curVal);
                        // 下载完成的时候，最大进度 应该等于当前进度
                        builder.setProgress(max , Math.min(curVal , max) , false);
                        managerCompat.notify(notificationId , builder.build());
                        curVal+=20;
                    }
                    builder.setContentText("download completed")
                            .setProgress(0 , 0 , false);
                    managerCompat.notify(notificationId , builder.build());
                } catch(Exception e) {
                    System.out.println("发生错误：" + e.getMessage());
                }
            }
        })).start();
    }

    // 添加进度条
    public void showNotificationWithProgress(View v)
    {
        this.createChannel("min");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , this.channelId);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle("进度条展示")
                .setContentText("下载中...")
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setOnlyAlertOnce(true);

        int max = 100;
        int cur = 0;
        builder.setProgress(max , cur , true);
        int notificationId = Tool.random();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(notificationId , builder.build());

        // 间隔1s更新一次
        (new Thread(new Runnable() {
            public void run()
            {
                int curVal = cur;
                try {
                    while (curVal < max)
                    {
                        Thread.sleep(1 * 1000);
                        curVal+=20;
                    }
                    builder.setContentText("download completed")
                            .setProgress(0 , 0 , false);
                    managerCompat.notify(notificationId , builder.build());
                } catch(Exception e) {
                    System.out.println("发生错误：" + e.getMessage());
                }
            }
        })).start();
    }

    public void showNotificationWithSetting(View v)
    {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE , this.getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID , this.channelId);
        this.startActivity(intent);
    }

    public void createNotificationAndGroup(View v)
    {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            // 仅支持在 >= android 8 的系统上运行
            String groupId = "my_group_01";
            String groupName = "分组名称";
            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannelGroup(new NotificationChannelGroup(groupId , groupName));
        }
    }

    public void showCategoryNotification(View v)
    {
        this.createChannel();
        Intent intent = new Intent(this , NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , this.channelId);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle("限定范围通知标题")
                .setContentText("限定范围通知内容")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_CALL);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(Tool.random() , builder.build());
    }

    public void showImportanceNotification(View v)
    {
        this.createChannel();
        Intent intent = new Intent(this , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , this.channelId);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle("紧急消息")
                .setContentText("紧急消息对应的内容")
                .setFullScreenIntent(pendingIntent , true)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        (new Thread(() -> {
            try {
                Thread.sleep(5 * 1000);
                notificationManagerCompat.notify(Tool.random() , builder.build());
            } catch(Exception e) {
                System.out.println("数据测试：" + e.getMessage());
            }
        })).start();
    }

    public void showSpreadNotification(View v)
    {
        this.createChannel();
        Drawable drawable = this.getDrawable(R.drawable.wzt);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Notification notification = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("展开式通知标题")
                .setContentText("展开式通知正文")
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null)
                )
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(Tool.random() , notification);
    }

    public void showNotificationWithTime(View v)
    {
        Tool.snackbar(v , "待完善的功能");
        System.exit(0);
        this.createChannel();
        Intent intent = new Intent(this , NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("时效性通知标题")
                .setContentText("时效性通知内容")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(pendingIntent , true)
                .setAutoCancel(true)
//                .setOnlyAlertOnce(true)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        int notificationId = Tool.random();
    }

    public void showNotificationWithReturn(View v)
    {
        this.createChannel();
        Intent intent = new Intent(this , PickerActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,  PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("从通知中返回的 Activity 标题")
                .setContentText("从通知中返回的 Activity 内容")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
//                .setOnlyAlertOnce(true)
//                .setCategory(NotificationCompat.CATEGORY_CALL)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        int notificationId = Tool.random();
        notificationManagerCompat.notify(notificationId , notification);
    }

    public void showNotificationNoStack(View v)
    {
        this.createChannel();
        Intent intent = new Intent(this , ResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("从通知中返回的 Activity 标题")
                .setContentText("从通知中返回的 Activity 内容")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        int notificationId = Tool.random();
        notificationManagerCompat.notify(notificationId , notification);
    }

    public void createNotificationGroup(View v)
    {
        this.createChannel();
        Intent intent = new Intent(this , ResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_UPDATE_CURRENT);
        String groupName = "通知组的名称";
        Notification notification = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("分组的通知标题")
                .setContentText("分组的通知内容")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setGroup(groupName)
                .setGroupSummary(true)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        int notificationId = Tool.random();
        notificationManagerCompat.notify(notificationId , notification);
    }

    public void showFlag(View v)
    {
        this.createChannel();
        Notification notification = new NotificationCompat.Builder(this , this.channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("这个通知会使app显示标志 标题")
                .setContentText("这个通知会使app显示标志 内容")
                .setNumber(10)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(Tool.random() , notification);
    }
}
