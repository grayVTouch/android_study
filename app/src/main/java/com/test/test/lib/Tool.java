package com.test.test.lib;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Tool
{
    public static boolean snackbar(View v , String msg , String time)
    {
        if (time.equals("short")) {
            Tool.snackbarForDefault(v , msg , Snackbar.LENGTH_SHORT);
            return true;
        }
        if (time.equals("long")) {
            Tool.snackbarForDefault(v , msg , Snackbar.LENGTH_LONG);
            return true;
        }
        return false;
    }

    public static boolean snackbar(View v , String msg)
    {
        return Tool.snackbar(v , msg , "long");
    }

    public static void snackbarForDefault(View v , String msg , int time)
    {
        Snackbar.make(v , msg , time)
                .setAction("Action" , null)
                .show();
    }

    public static int random(int len)
    {
        return (int) (Math.random() * 10 * len);
    }

    public static int random()
    {
        return Tool.random(6);
    }

    public static void log(String log)
    {
        Log.d("mylog" , log);
    }

    public static void tip(Context context , String msg)
    {
        Toast.makeText(context , msg , Toast.LENGTH_SHORT).show();
    }

    /**
     * dp 转 px
     * @param context
     * @param dp
     * @return double
     */
    public static int dpToPx(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        final float res = dp * scale;
        return (int) res;
    }

    /**
     * px 转 dp
     * @param context
     * @param px
     * @return double
     */
    public static int pxToDp(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        final float res = px / scale;
        return (int) res;
    }

    /**
     * 获取事件名称
     */
    public static String getEventName(int action)
    {
        switch (action)
        {
            case MotionEvent.ACTION_BUTTON_PRESS:
                return "ACTION_BUTTON_PRESS";
            case MotionEvent.ACTION_BUTTON_RELEASE:
                return "ACTION_BUTTON_RELEASE";
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            case MotionEvent.ACTION_HOVER_ENTER:
                return "ACTION_HOVER_ENTER";
            case MotionEvent.ACTION_HOVER_EXIT:
                return "ACTION_HOVER_EXIT";
            case MotionEvent.ACTION_OUTSIDE:
                return "ACTION_OUTSIDE";
            case MotionEvent.ACTION_POINTER_DOWN:
                return "ACTION_POINTER_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            default:
                return "unknow";
        }
    }

    public static void fullscreen(AppCompatActivity activity) throws Exception
    {
        Tool.fullscreen(activity , "auto");
    }

    /**
     * 全屏-完美适配刘海屏 和 非刘海屏
     */
    public static void fullscreen(AppCompatActivity activity , String type) throws Exception
    {
        String[] typeRange = {"sticky" , "auto"};
        if (!Arrays.asList(typeRange).contains(type)) {
            throw new Exception("不支持的类型，当前受支持的类型有：" + Arrays.toString(typeRange));
        }
        Window win = activity.getWindow();
        View decorView = win.getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        int flags = View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                // 避免出现拖影，所以请设置
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        switch (type)
        {
            case "sticky":
                // 沉浸式-粘性-全屏
                flags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                break;
        }
        systemUiVisibility |= flags;
        decorView.setSystemUiVisibility(systemUiVisibility);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            // 如果 api level < 28 （即 android 9 之前的操作系统）
            // 那么直接返回
            return ;
        }
        // 请求没有标题
//        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 以下代码需要 android 9.0 或 api level >= 28
        WindowManager.LayoutParams params = win.getAttributes();
        // 窗口总是允许扩展到 刘海屏 区域d
        params.layoutInDisplayCutoutMode  = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        win.setAttributes(params);
    }
}
