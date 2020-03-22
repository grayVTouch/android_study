package com.test.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.test.lib.MyAdapter;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewActivity extends AppCompatActivity
{
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.recycler_view);
        this.run();

    }

    private void run()
    {
        // 给现有的 RecyclerView 添加适配器
        RecyclerView recyclerView = this.findViewById(R.id.recycler_view);

        // 如果知道性能发生了变化，可以使用这个设置来提高性能
        // 在内容中不要改变 recyclerView 的布局尺寸
        recyclerView.setHasFixedSize(true);

        // 获取 RecyclerView 的布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        // 设置其水平方向
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);

        // 创建适配器
        MyAdapter myAdapter = new MyAdapter(new MyAdapter.AdapterDataUnit[] {
                new MyAdapter.AdapterDataUnit(R.drawable.logo , "￥650.00" , "￥856.00") ,
                new MyAdapter.AdapterDataUnit(R.drawable.logo , "￥650.00" , "￥856.00") ,
                new MyAdapter.AdapterDataUnit(R.drawable.logo , "￥650.00" , "￥856.00") ,
                new MyAdapter.AdapterDataUnit(R.drawable.logo , "￥650.00" , "￥856.00") ,
                new MyAdapter.AdapterDataUnit(R.drawable.logo , "￥650.00" , "￥856.00") ,
        });

        // 添加适配器
        recyclerView.setAdapter(myAdapter);
    }


}