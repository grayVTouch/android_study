package com.test.test.lib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.test.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder>
{

    private ArrayList<String> data;

    public ShopAdapter(ArrayList<String> data)
    {
        this.data = data;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent , int viewType)
    {
        // 注意，这个地方可以通过布局填充器扩充现有视图的布局
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // 在父布局的基础上，填充 新的 布局
        View view = inflater.inflate(R.layout.shop_item , parent , false);
        ShopViewHolder viewHolder = new ShopViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShopViewHolder viewHolder , int position)
    {
        // 这边可以填充数据
        String value = this.data.get(position);
        // 当前索引下的值
        Tool.log("当前索引下的 value: " + value);
        LinearLayout layout = (LinearLayout) viewHolder.getView();
        // 修改用户名
        TextView username = layout.findViewById(R.id.username);
        username.setText(value);
    }

    @Override
    public int getItemCount()
    {
        return this.data.size();
    }
}
