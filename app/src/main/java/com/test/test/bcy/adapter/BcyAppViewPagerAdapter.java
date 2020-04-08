package com.test.test.bcy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.test.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class BcyAppViewPagerAdapter extends PagerAdapter
{

    private String[] subject;

    public BcyAppViewPagerAdapter(String[] subject)
    {
        this.subject = subject;
    }

    // 获取子项的数量
    @Override
    public int getCount() {
        return this.subject.length;
    }

    // 检查返回的对象是否是目标对象
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup vg , int position)
    {
        LayoutInflater inflater = LayoutInflater.from(vg.getContext());
        View view = inflater.inflate(R.layout.bcy_app_view_pager_adapter_item , vg , false);

        // 初始化内容
        TextView text = view.findViewById(R.id.text);
        text.setText(this.subject[position]);

        vg.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup vg , int position , Object obj)
    {
        vg.removeView((View) obj);
    }


}
