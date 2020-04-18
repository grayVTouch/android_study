package com.test.test.bcy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.test.test.R;
import com.test.test.bcy.adapter.BcyWelcomeAdapter;
import com.test.test.bcy.adapter.BcyWelcomeAdapter.AdapterData;
import com.test.test.lib.Tool;

public class BcyWelcomeActivity extends AppCompatActivity
{

    // 屏蔽 back 键
    @Override
    public void onBackPressed()
    {
        Tool.log("back button is pressed!");
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        // 检查是否已经对用户显示过欢迎页
        // 如果仅仅是针对单个 activity 设置
        SharedPreferences storage = this.getPreferences(Context.MODE_PRIVATE);
        int welcomeOnce = storage.getInt("welcome_once" , 0);
        if (welcomeOnce > 0) {
            // 已经显示过欢迎页了，直接进入到半次元首页
            Intent intent = new Intent(this , BcyAppActivity.class);
            this.startActivity(intent);
            return ;
        }

        // 设置全屏
        try {
            Tool.fullscreen(this , "sticky");
        } catch(Exception e) {
            // 打印错误栈
            e.printStackTrace();
        }
        this.setContentView(R.layout.bcy_welcome);
        this.run();

//        Handler handler = new Handler();
//        handler.postDelayed(() -> {
//            Intent intent = new Intent(this , MainActivity.class);
//            this.startActivity(intent);
//            // 销毁该返回栈
//            this.finish();
//        } , 3* 1000);
    }

    public void run()
    {
        BcyWelcomeActivity self = this;
        ViewPager viewPager = this.findViewById(R.id.bcy_welcome_view_pager);
        AdapterData[] data = new AdapterData[] {
            new AdapterData(R.drawable.bcy_welcome_view_pager_06, "第 1 张") ,
            new AdapterData(R.drawable.bcy_welcome_view_pager_07, "第 2 张") ,
            new AdapterData(R.drawable.bcy_welcome_view_pager_08, "第 3 张") ,
            new AdapterData(R.drawable.bcy_welcome_view_pager_09, "第 4 张") ,
        };
        BcyWelcomeAdapter adapter = new BcyWelcomeAdapter(this , data);
        viewPager.setAdapter(adapter);

        // 填充索引
        LinearLayout linearLayout = this.findViewById(R.id.index_container);
        LayoutInflater inflater = LayoutInflater.from(this);
        View[] views = new View[data.length];
        for (int i = 0; i < data.length; ++i)
        {
            View view = inflater.inflate(R.layout.bcy_welcome_index_item , linearLayout , false);
            linearLayout.addView(view);
            if (i == 0) {
                // 选中首页
                view.setBackground(this.getDrawable(R.drawable.bcy_welcome_background_border_gray_highlight));
            }
            // 增加
            views[i] = view;
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
//                Tool.log("view pager 页面滚动中");
            }

            @Override
            public void onPageSelected(int position)
            {
                // 设置背景颜色
                for (int i = 0; i < views.length; ++i)
                {
                    View view = views[i];
                    if (i == position) {
                        // 选中样式
                        view.setBackground(self.getDrawable(R.drawable.bcy_welcome_background_border_gray_highlight));
                    } else {
                        // 未选中样式
                        view.setBackground(self.getDrawable(R.drawable.bcy_welcome_background_border_gray));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
//                Tool.log("view pager 页面状态切换");
            }
        });
    }
}
