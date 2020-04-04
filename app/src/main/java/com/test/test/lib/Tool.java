package com.test.test.lib;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

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
}
