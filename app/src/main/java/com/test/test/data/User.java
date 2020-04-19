package com.test.test.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

// 合同类
public class User
{

    public static class UserEntry implements BaseColumns
    {
        public static final String tableName = "user";

        public static final String name = "name";

        public static final String sex  = "sex";
    }

    // 建表语句
    private static final String createSql =
            "create table " + UserEntry.tableName + "( " +
                    UserEntry._ID + " integer primary key ," +
                    UserEntry.name + " text , " +
                    UserEntry.sex + " text)";

    // 删除表
    private static final String dropSql = "drop table if exists " + UserEntry.tableName;

    /**
     * SQLiteOpenHelper 类包含一组用于管理数据库的使用 API
     */
    public static class DatabaseHelper extends SQLiteOpenHelper
    {
        public static final int version = 1;

        public static final String name = "test.db";

        public DatabaseHelper(Context context)
        {
            super(context , name , null , version);
        }

        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(createSql);
        }

        // 升级
        public void onUpgrade(SQLiteDatabase db , int oldVersion , int newVersion)
        {
            db.execSQL(dropSql);
            // 更新就删除表重建
            this.onCreate(db);
        }

//        // 降级
//        public void onDowngrade(SQLiteDatabase db , int oldVersion , int newVersion)
//        {
//            this.onUpgrade(db , oldVersion , newVersion);
//        }
    }
}
