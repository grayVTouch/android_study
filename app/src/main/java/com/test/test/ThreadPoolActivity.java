package com.test.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.test.test.lib.Tool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class ThreadPoolActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        // 与界面线程通信
        // 在 handler 中可以安全的访问 视图
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg)
            {
                // 由任务移动到界面线程
            }
        };


        // 初始线程池的大小（四核八线程）
        int min = Runtime.getRuntime().availableProcessors();
        int max = min;

        Tool.log("当前处理器的线程数量：" + min);

        // 这是一个阻塞的线程安全的队列
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                // 最小线程数量
                min ,
                // 最大线程数量
                max ,
                // 设置一个空闲线程在结束钱等待的时间
                1 ,
                // 时间单位
                TimeUnit.SECONDS ,
                // 任务队列
                queue
        );

        // 执行代码，并非立即执行
        // 如果队列空闲，那么会立即执行
        // 否则会先添加到队列，然后按顺序消费队列
        // 队列消费是先进先出的消费模式
        executor.execute(new Runnable() {
            @Override
            public void run()
            {
                // 在线程队列中的运行的代码
                Tool.log("线程池中的线程上运行的代码");
            }
        });

        // 创建后台服务
    }





}
