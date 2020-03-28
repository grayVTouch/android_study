package com.test.test;

import android.app.Activity;
import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.test.lib.Tool;

import java.io.File;

public class ConstraintLayoutActivity extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.constraint_layout);
        this.run();
    }

    // WebView 的具体使用方式
    private void loadBaidu()
    {
        WebView webView = this.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();

        // 是否启用 javascript
        webSettings.setJavaScriptEnabled(true);
        // 是否支持 js 打开新的窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 是否支持缩放
        webSettings.setSupportZoom(true);

        // 是否显示缩放按钮
        webSettings.setBuiltInZoomControls(true);

        // 以下两个方法一起使用，解决网页自适应问题
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // 出现如下错误：
        // net::ERR_CACHE_MISS
        // 请请求联网权限
        webSettings.setAppCacheEnabled(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView , String url)
            {
                // 在 app 内打开链接，而不是通过打开手机内置的浏览器等方式
                // 打开链接
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView webView , SslErrorHandler sslErrorHandler , SslError sslError)
            {
                // 解决 webview 加载 https 的时候出现没内容的情况
                // 遇到 ssl 问题，继续进行
                sslErrorHandler.proceed();
            }
        });
        Button btn = this.findViewById(R.id.load_baidu);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                // 载入 百度
                webView.loadUrl("https://www.baidu.com");
            }
        });
    }

    private void playVideo()
    {
        Button playVideo = this.findViewById(R.id.playVideo);
        VideoView videoView = this.findViewById(R.id.videoView);

//        Tool.log("cache_dir: " + this.getExternalCacheDir().getAbsolutePath());

//        File file = new File(this.getExternalCacheDir() , "sister.mkv");
        Tool.log("包内缓存地址：" + this.getExternalCacheDir());
        Tool.log("包外部地址：" + Environment.getExternalStorageDirectory());
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp4");
        Tool.log("完整地址：" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp4");
        if (!file.exists()) {
            Toast.makeText(this , "文件不存在" , Toast.LENGTH_SHORT).show();
            return ;
        }
        videoView.setVideoPath(file.getPath());
        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                videoView.start();
            }
        });
    }

    private void run()
    {
        this.loadBaidu();
        this.playVideo();
    }
}
