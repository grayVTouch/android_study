package com.test.test.bcy.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.test.test.MainActivity;
import com.test.test.R;
import com.test.test.bcy.activity.BcyAppActivity;

public class BcyWelcomeAdapter extends PagerAdapter
{

    public static class AdapterData
    {

        private int image;

        private String text;

        public AdapterData(int image , String text)
        {
            this.image = image;
            this.text = text;
        }
    }

    private BcyWelcomeAdapter.AdapterData[] adapterData;
    private AppCompatActivity activity;

    public BcyWelcomeAdapter(AppCompatActivity activity , BcyWelcomeAdapter.AdapterData[] adapterData)
    {
        this.activity = activity;
        this.adapterData = adapterData;
    }



    @Override
    public int getCount()
    {
        return this.adapterData.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.bcy_welcome_view_pager_item, container , false);
        // 数据测试
        ImageView image = view.findViewById(R.id.image);
//        TextView text = view.findViewById(R.id.text);
        Button button = view.findViewById(R.id.to_main);

        BcyWelcomeAdapter.AdapterData data = this.adapterData[position];
        image.setImageResource(data.image);
//        text.setText(data.text);

        BcyWelcomeAdapter self = this;
        int maxPosition = this.adapterData.length - 1;

        // 具体原因请看该链接 https://www.jianshu.com/p/c92243287793
        container.addView(view);
        if (position > 0 && position < maxPosition) {
            // 非首页 和 最后一页的按钮影藏
            button.setVisibility(View.GONE);
        } else {
            if (position == 0) {
                button.setText("UI 首页");
            } else if (position == maxPosition) {
                button.setText("仿半次元 首页");
            } else {
                // 其他情况，待新增...
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent;
                    // 分别设置首页 和 最后一页按钮的功能
                    if (position == 0) {
                        // 首页
                        intent = new Intent(self.activity , MainActivity.class);
                    } else if (position == maxPosition) {
                        // 最后一页
                        // 进入到 仿半次元首页
                        // 如果点击后，那么将会设置访问状态
                        intent = new Intent(self.activity , BcyAppActivity.class);
                        SharedPreferences storage = self.activity.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = storage.edit();
                        editor.putInt("welcome_once" , 1);
                        editor.commit();
                    } else {
                        // 其他情况，后续补充...
                        return ;
                    }
                    self.activity.startActivity(intent);
                }
            });
        }
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
    }

    // 判断当前的视图是否为返回的对象
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }
}
