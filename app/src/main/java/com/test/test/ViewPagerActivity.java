package com.test.test;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.test.lib.Tool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerActivity extends AppCompatActivity
{
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.view_pager);
        this.run();
    }

    private String[] title = {"page0" , "page1" , "page2" , "page3" , "page4" , "page5"};

    protected void run()
    {
        // 数据测试
        Tool.log("hello boys and girls");

        ViewPager viewPager = this.findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerActivity.ViewPagerAdapter(this));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Tool.log("position: " + position + ";page change amount: " + positionOffset + "; px: " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                Tool.log("页面状态变化 - state: " + state);
            }
        });
    }

    static class A extends PagerAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    static class ViewPagerAdapter extends PagerAdapter {

        private ViewPagerActivity context;

        ViewPagerAdapter(ViewPagerActivity context)
        {
            this.context = context;
        }

        // 得到选项卡的数量
        @Override
        public int getCount()
        {
            return this.context.title.length;
        }

        // 判断当前的视图是否为返回的对象
        @Override
        public boolean isViewFromObject(View view , Object object)
        {
            return view == object;
        }

        // 获取每个项的标题
        @Override
        public CharSequence getPageTitle(int position)
        {
            Tool.log("position: " + position + "; title: " + this.context.title[position]);

            return this.context.title[position];
        }

        // 实例化项目
        @Override
        public Object instantiateItem(ViewGroup viewGroup , int position)
        {
            View view = LayoutInflater.from(this.context).inflate(R.layout.pager_item , viewGroup , false);
            TextView text = view.findViewById(R.id.text);
            text.setText(this.context.title[position]);
            // 手动添加
            viewGroup.addView(view);
            Tool.log("view pager adapter instantiateItem");
            return view;
        }

        // 销毁项目的时候
        @Override
        public void destroyItem(ViewGroup viewGroup , int position , Object object)
        {
            Tool.log("view pager adapter destroyItem");
            viewGroup.removeView((View) object);
        }

    }
}
