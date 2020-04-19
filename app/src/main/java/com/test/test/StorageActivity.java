package com.test.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.test.test.data.User;
import com.test.test.lib.Tool;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class StorageActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.run();
    }

    public void run()
    {
        this.innerDir();
        this.outerDir();
        this.privateDir();
        this.saveDatabase();
    }

    // 应用内部文件存储
    public void innerDir()
    {
        // 返回的是应用程序安装文件所在的存储路径
        // 用户不应该使用这个路径
        File dataDir = this.getDataDir();
        File filesDir = this.getFilesDir();
        File cacheDir = this.getCacheDir();
        File codeCacheDir = this.getCodeCacheDir();

        // 获取文件的绝对路径
        Tool.log("dataDir 应用的默认文件存储：" + dataDir.getAbsolutePath());
        Tool.log("filesDir 文件的绝对路径：" + filesDir.getAbsolutePath());
        Tool.log("cacheDir 应用临时文件缓存目录 的绝对路径：" + cacheDir.getAbsolutePath());
        Tool.log("codeCacheDir 文件的绝对路径：" + codeCacheDir.getAbsolutePath());
    }


    public void outerDir()
    {
        // 访问其他磁盘文件
    }

    public void privateDir()
    {
        // 私有文件存储
        // 这些文件在用户卸载应用时会被清除
        File obbDir = this.getObbDir();
        File cacheDir = this.getExternalCacheDir();
        File fileDir = this.getExternalFilesDir(null);

        Tool.log("obbDir: " + obbDir.getAbsolutePath());
        Tool.log("cacheDir: " + cacheDir.getAbsolutePath());
        Tool.log("obbDir: " + fileDir.getAbsolutePath());
    }

    // 保存到数据库
    public void saveDatabase()
    {
        User user = new User();
        User.DatabaseHelper dbHelper = new User.DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /**
         * 将数据插入到数据库
         */
//        ContentValues values = new ContentValues();
//        values.put(User.UserEntry.name , "running");
//        values.put(User.UserEntry.sex , "male");
//        long lastId = db.insert(User.UserEntry.tableName , null , values);
//        Tool.log("当前插入的数据 id：" + lastId);
//
//        /**
//         * 从数据库获取信息
//         */
        String selection = User.UserEntry.name + " = ?";
        String[] selectionArgs = {"running"};
        String order = User.UserEntry._ID + " desc";

        Cursor cursor = db.query(
                User.UserEntry.tableName ,
                // 要返回的字段，请提供一个数组，如果返回 null，则返回所有的列
//                projection ,
                null ,
                // where 条件判断
                selection ,
                selectionArgs ,
                null ,
                null ,
                order
        );

        // 相关方法：query | insert | delete | update
        // 事务相关： beginTransaction | endTransaction

        // 获取其中的数据
        while (cursor.moveToNext())
        {
            int nameIndex = cursor.getColumnIndex(User.UserEntry.name);
            int sexIndex = cursor.getColumnIndex(User.UserEntry.sex);
            String name = cursor.getString(nameIndex);
            String sex = cursor.getString(sexIndex);
            Tool.log("my name is " + name + "; sex is " + sex);
        }

    }

}
