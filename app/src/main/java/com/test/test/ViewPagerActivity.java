package com.test.test;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.test.lib.Tool;

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
    }

    static class ViewPagerAdapter extends PagerAdapter {

        private ViewPagerActivity context;

        ViewPagerAdapter(ViewPagerActivity context)
        {
            this.context = context;
        }

        @Override
        public int getCount()
        {
            return this.context.title.length;
        }

        @Override
        public boolean isViewFromObject(View view , Object object)
        {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return this.context.title[position];
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup , int position)
        {
            View view = LayoutInflater.from(this.context).inflate(R.layout.pager_item , viewGroup , false);
            // 手动添加
            viewGroup.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup , int position , Object object)
        {
            viewGroup.removeView((View) object);
        }

    }
}
