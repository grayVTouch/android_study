package com.test.test;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UiActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        this.setContentView(R.layout.ui);

        Toolbar toolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        View decorView = this.getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility)
            {
                System.out.println("系统界面可见度发生了变化，当前可见度：" + visibility);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.one , menu);
        return true;
    }

    public void downBrightness(View v)
    {
        // 隐藏一些非系统图标
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    public void originBrightness(View v)
    {
        // 还原非系统图标
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }

    public void hideActionBar(View v)
    {
        // 影藏状态栏（刘海屏 有缺点）
        View decorView = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.hide();
    }

    public void originActionBar(View v)
    {
        // 还原状态栏
        this.originBrightness(v);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.show();
    }

    public void originNavigator(View v)
    {
        // 隐藏导航栏（底部的 三个按键 后台运行任务 | 首页 | 返回键）
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.show();
    }

    public void hideNavigator(View v)
    {
        // 还原导航栏
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public void fullscreen_1(View v)
    {
        // 全屏-向后倾斜
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public void fullscreen_2(View v)
    {
        // 全屏-沉浸式
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    public void fullscreen_3(View v)
    {
        // 全屏-粘性沉浸式
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    public void originScreen(View v)
    {
        // 还原导航栏
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }

    public void fullscreen_liuhai(View view)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            this.fullscreen_3(view);
            return ;
        }
        Window win = this.getWindow();
        // 以下代码需要 android 9.0 或 api level >= 28
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams params = win.getAttributes();
        // 窗口总是允许扩展到 刘海屏 区域d
        params.layoutInDisplayCutoutMode  = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        win.setAttributes(params);
        View decorView = win.getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        int flags = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                // 避免出现拖影，所以请设置
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        systemUiVisibility |= flags;
        decorView.setSystemUiVisibility(systemUiVisibility);
    }

}
