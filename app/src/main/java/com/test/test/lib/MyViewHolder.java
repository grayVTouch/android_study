package com.test.test.lib;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder
{

    private View view;

    public MyViewHolder(View view)
    {
        super(view);
        this.view = view;
    }

    public View getView()
    {
        return this.view;
    }
}
