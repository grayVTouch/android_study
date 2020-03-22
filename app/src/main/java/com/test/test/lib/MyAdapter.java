package com.test.test.lib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.test.test.R;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    private MyAdapter.AdapterDataUnit[] data;

    public MyAdapter(MyAdapter.AdapterDataUnit[] data)
    {
        this.data = data;
    }

    public static class AdapterDataUnit {
        private int image;
        private String textTop;
        private String textBtm;

        public AdapterDataUnit(int image , String textTop , String textBtm)
        {
            this.image = image;
            this.textTop = textTop;
            this.textBtm = textBtm;
        }

        public int getImage()
        {
            return this.image;
        }

        public String getTextTop()
        {
            return this.textTop;
        }

        public String getTextBtm()
        {
            return this.textBtm;
        }
    }

    // 将适配器填充到布局之前，会先调用 适配器的 onCreateViewHolder 方法
    // 该方法要求返回 ViewHolder ，也就是视图持有者
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType)
    {
        // 创建布局填充器（布局填充器 用来扩展原有视图）
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // 给父视图扩充给定的视图
        View view = layoutInflater.inflate(R.layout.recycler_item , parent , false);
        // 创建视图持有人
        MyViewHolder myViewHolder = new MyViewHolder(view);
        // 返回视图持有人
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder , int position)
    {
        View view = myViewHolder.getView();
        LinearLayout linearLayout = (LinearLayout) view;
        ImageView imageView = linearLayout.findViewById(R.id.ico);
        TextView textTop = linearLayout.findViewById(R.id.text_top);
        TextView textBtm = linearLayout.findViewById(R.id.text_btm);
        MyAdapter.AdapterDataUnit adapterDataUnit = this.data[position];
        imageView.setImageResource(adapterDataUnit.getImage());
        textTop.setText(adapterDataUnit.getTextTop());
        textBtm.setText(adapterDataUnit.getTextBtm());
    }

    @Override
    public int getItemCount()
    {
        return this.data.length;
    }
}
