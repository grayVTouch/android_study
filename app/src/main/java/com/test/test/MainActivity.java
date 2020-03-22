package com.test.test;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.test.bubbles.BubbleActivity;

import com.test.test.lib.Tool;
import com.test.test.receiver.DynamicBroadcastReceiver;
import com.test.test.receiver.StaticBroadcastReceiver;
import com.test.test.service.TestService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.main);

        FloatingActionButton fab = this.findViewById(R.id.fab);
        fab.setOnClickListener((v) -> {
            // 点击后底部弹窗提示
            Tool.snackbar(v , "你点击了我 FloatActionButton");
            // 点击后普通提示
        });


    }

    public void openTextActivity(View v)
    {
        Intent intent = new Intent(this , TextActivity.class);
        intent.putExtra("name" , "running");
        intent.putExtra("sex" , "male");
        this.startActivity(intent);
    }

    public void openButtonActivity(View v)
    {
        Intent intent = new Intent(this , ButtonActivity.class);
        this.startActivity(intent);
    }

    public void openCheckBoxAndRadioActivity(View v)
    {
        Intent intent = new Intent(this , CheckBoxAndRadioActivity.class);
        this.startActivity(intent);
    }

    public void openSpinnerActivity(View v)
    {
        Intent intent = new Intent(this , SpinnerActivity.class);
        this.startActivity(intent);
    }

    public void openRelativeLayoutActivity(View v)
    {
        Intent intent = new Intent(this , RelativeLayoutActivity.class);
        this.startActivity(intent);
    }

    public void openConstraintLayoutActivity(View v)
    {
        Intent intent = new Intent(this , ConstraintLayoutActivity.class);
        this.startActivity(intent);
    }

    public void openPickerActivity(View v)
    {
        Intent intent = new Intent(this , PickerActivity.class);
        this.startActivity(intent);
    }

    public void openNotificationActivity(View v)
    {
        Intent intent = new Intent(this , NotificationActivity.class);
        this.startActivity(intent);
    }

    public void openSpecialActivity(View v)
    {
        Intent intent = new Intent(this , ResultActivity.class);
        this.startActivity(intent);
    }

    public void openBubbleActivity(View v)
    {
        Intent intent = new Intent(this , BubbleActivity.class);
        this.startActivity(intent);
    }


    public void openAppActivity(View v)
    {
        Intent intent = new Intent(this , AppActivity.class);
        this.startActivity(intent);
    }

    public void openImageActivity(View v)
    {
        Intent intent = new Intent(this , ImageActivity.class);
        this.startActivity(intent);
    }


    public void openUiActivity(View v)
    {
        Intent intent = new Intent(this , UiActivity.class);
        this.startActivity(intent);
    }

    public void openSwiperActivity(View v)
    {
        Intent intent = new Intent(this , SwiperActivity.class);
        this.startActivity(intent);
    }

    public void showMessageFrame(View v)
    {
        String text = "hello toast";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this , text , duration);
        toast.show();
    }

    public void showMessageWithFrame(View v)
    {
        String text = "hello toast";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this , text , duration);
        toast.setGravity(Gravity.TOP | Gravity.LEFT , 0 , 0);
        toast.show();
    }

    public void showMessageWithStyle(View v)
    {
        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.checkbox_and_radio , (ViewGroup) this.findViewById(R.id.layout));

        int duration = Toast.LENGTH_SHORT;
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER_VERTICAL , 0 , 0);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }

    public void showDialog(View v)
    {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("对话框测试~")
                .setPositiveButton("同意", (dialog , id) -> {
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // FIRE ZE MISSILES!
//                        }
//                    }
                    Toast.makeText(this , "你点击了同意按钮" , Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("拒绝", (dialog , id) -> {
                    Toast.makeText(this , "你点击了拒绝按钮" , Toast.LENGTH_SHORT).show();
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showDialogWithList(View v)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("对话框标题")
                .setItems(R.array.one , (dialog , id) -> {
                    Toast.makeText(this , "你点击的id: " + id , Toast.LENGTH_SHORT).show();
                })
                .create();
        alertDialog.show();
    }

    public void copyText(View v)
    {
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        /**
         * 添加数据到剪贴板
         * 1. 创建 ClipData 对象
         * 2. 包含 ClipDescription + 多个 ClipData.Item 对象
         */
        ClipData clipData = ClipData.newPlainText("simple text" , "hello boys and girls");
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this , "文本复制成功" , Toast.LENGTH_SHORT).show();
    }

    public void copyUri(View v)
    {
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        Uri copyUri = Uri.parse("https://www.baidu.com");
        ClipData clipData = ClipData.newUri(this.getContentResolver() , "URI" , copyUri);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this , "uri复制成功" , Toast.LENGTH_SHORT).show();
    }

    public void copyIntent(View v)
    {
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        Intent intent = new Intent(this , PickerActivity.class);
        ClipData clipData = ClipData.newIntent("intent" , intent);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this , "intent 复制成功" , Toast.LENGTH_SHORT).show();
    }

    public void paste(View v)
    {
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        if (!clipboardManager.hasPrimaryClip()) {
            Toast.makeText(this , "剪贴板中没有获取到数据" , Toast.LENGTH_SHORT).show();
            return ;
        }
        ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
        CharSequence text = item.getText();
        if (text != null) {
            Toast.makeText(this , "剪贴板中的数据text：" + text , Toast.LENGTH_SHORT).show();
            return ;
        }

        Uri uri = item.getUri();
        if (uri != null) {
            Toast.makeText(this , "剪贴板中的数据uri：" + uri.getScheme() + "://" + uri.getHost() + uri.getPath() , Toast.LENGTH_SHORT).show();
            return ;
        }
        Intent intent = item.getIntent();
        if (intent != null) {
            Toast.makeText(this , "剪贴板中的数据intent，3s后打开新的 intent" , Toast.LENGTH_SHORT).show();
            (new Thread(() -> {
                try {
                    Thread.sleep(3 * 1000);
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                this.startActivity(intent);
                return ;
            })).start();
            return ;
        }
        Toast.makeText(this , "剪贴板中不存在数据 或者 存在未知的数据" , Toast.LENGTH_SHORT).show();
    }

    public void openSearchActivity(View v)
    {
        Intent intent = new Intent(this , SearchActivity.class);
        this.startActivity(intent);
    }

    public void openSendActivity(View v)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT , "你好啊，我很好");
        this.startActivity(intent);
    }

    public void startBackgroundService(View v)
    {
        // 您应该是中使用显式的 intent 来启动服务
        Intent intent = new Intent(this , TestService.class);
        intent.putExtra("type" , "background");
        this.startService(intent);
        Tool.snackbar(v , "后台服务已经启动，请在 ide logcat 中查看运行日志");
    }

    public void startForegroundService(View v)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return ;
        }
        Intent intent = new Intent(this , TestService.class);
        intent.putExtra("type" , "foreground");
        // 特殊：
        // 1. 要求 android 8.0，即 api level >= 26
        // 2. 要求 android.permission.FOREGROUND_SERVICE 权限
        this.startForegroundService(intent);
    }

    public void sendStaticBroadcast(View v)
    {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.static");
        // 注意 google 官方文档中并没有以下这句代码！
        // 所以导致学习进度卡在这儿一段时间
        // 一定要加上这句，否则无法触发
        // 具体原因请看链接：https://blog.csdn.net/qq_41037945/article/details/104150633
        // android 官网描述：https://developer.android.com/guide/components/broadcasts
        // 原因就在于从 android 8 开始，也就是 api level >= 26 开始
        // 清单文件中声明的广播接收器 针对隐式广播 已经不再支持
        // 所以，无法通过过滤器 + action 来处理隐式广播
        // 只能通过 setComponent 来指明具体需要触发的广播
        intent.setComponent(new ComponentName(this , StaticBroadcastReceiver.class));
        this.sendBroadcast(intent);
        Tool.log("已经发送静态广播");
        Tool.tip(this , "已经发送静态广播");
    }

    public void registerDynamicBroadcastReceiver(View v)
    {
        BroadcastReceiver broadcastReceiver = new DynamicBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter();
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.intent.action.dynamic");

        this.registerReceiver(broadcastReceiver , intentFilter);
        Tool.log("动态广播注册成功");
        Tool.tip(this , "动态广播注册成功");
    }

    public void sendDynamicBroadcast(View v)
    {
//        this.registerDynamicBroadcastReceiver(v);

        Intent intent = new Intent();
        intent.setAction("android.intent.action.dynamic");
        this.sendBroadcast(intent);
        Tool.log("已经发送动态广播");
        Tool.tip(this , "已经发送动态广播");

    }

    // 插入新值
    public void insertForContentProvider(View v)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(UserDictionary.Words.APP_ID , "com.test.test");
        newValues.put(UserDictionary.Words.WORD , "running");
        newValues.put(UserDictionary.Words.FREQUENCY , "100");

//        Uri uri = this.getContentResolver()
//                .insert(UserDictionary.Words.CONTENT_URI , newValues);

//        UserDictionary.Words.addWord( this , "newMedicalWord", 1, UserDictionary.Words.LOCALE_TYPE_CURRENT);
        Locale locale = new Locale("en_US");
        UserDictionary.Words.addWord(this , "running" ,
        100, "shutcut", locale);
//        Tool.log("running 插入成功: " + uri.getHost());
        Tool.log("running 插入成功: ");
        Tool.tip(this ,"running 插入成功");
    }

    public void queryForContentProvider(View v)
    {
        String searchString = "%r%";
        // 内容提供程序
        String[] projection = {
                // 查询相关字段
                UserDictionary.Words._ID ,
                UserDictionary.Words.WORD ,
                UserDictionary.Words.LOCALE
        };
        String selection = "";
        String sortOrder = "";
        String[] selectionArgs;
        if (searchString.isEmpty()) {
            selection = null;
            selectionArgs = null;
        } else {
            selection = "";
            ArrayList<String> selectionArgsList = new ArrayList<>();
            selection += UserDictionary.Words.WORD + " like ?";
            selectionArgsList.add(searchString);
            selectionArgs = new String[selectionArgsList.size()];
            for (int i = 0; i < selectionArgsList.size(); ++i)
            {
                selectionArgs[i] = selectionArgsList.get(i);
            }
        }
        Tool.log("table_name: " + UserDictionary.Words.CONTENT_URI + "; selection: " + selection + "; args: " + Arrays.deepToString(selectionArgs));
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(
                UserDictionary.Words.CONTENT_URI ,
                projection ,
                selection ,
                selectionArgs ,
                sortOrder
        );
        // 获取内容
        if (cursor == null) {
            Tool.log("cursor null");
            Tool.tip(this ,"cursor null");
            return ;
        }
        if (cursor.getCount() < 1) {
            Tool.log("查询结果为空：" + cursor.getCount());
            Tool.tip(this , "查询结果为空：" + cursor.getCount());
            return ;
        }
        int index = cursor.getColumnIndex(UserDictionary.Words.WORD);
        Tool.log("Cursor::moveToNext 方法结果：" + cursor.moveToNext());
        while (cursor.moveToNext())
        {
            String newWord = cursor.getString(index);
            Tool.log("从数据库中获取到的 word: " + newWord);
            Tool.tip(this ,"从数据库中获取到的 word: " + newWord);
        }
        Tool.log("查询程序执行完毕");
    }

    public void openDemoActivity(View v)
    {
        Intent intent = new Intent(this , DemoActivity.class);
        this.startActivity(intent);
    }

    public void openMotionLayoutActivity(View v)
    {
        Intent intent = new Intent(this , MotionLayoutActivity.class);
        this.startActivity(intent);
    }

    public void openCardViewActivity(View v)
    {
        Intent intent = new Intent(this , CardViewActivity.class);
        this.startActivity(intent);
    }

    public void openRecyclerViewActivity(View v)
    {
        Intent intent = new Intent(this , RecyclerViewActivity.class);
        this.startActivity(intent);
    }

    public void openTestActivity(View v)
    {
        Intent intent = new Intent(this , TestActivity.class);
        this.startActivity(intent);
    }

    public void openFrameLayoutActivity(View v)
    {
        Intent intent = new Intent(this , FrameLayoutActivity.class);
        this.startActivity(intent);
    }

    public void openTableLayoutActivity(View v)
    {
        Intent intent = new Intent(this , TableLayoutActivity.class);
        this.startActivity(intent);
    }

    public void openAttrAnimationActivity(View v)
    {
        Intent intent = new Intent(this , AttrAnimationActivity.class);
        this.startActivity(intent);
    }
}
