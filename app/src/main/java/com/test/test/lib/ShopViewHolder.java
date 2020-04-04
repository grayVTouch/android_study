package com.test.test.lib;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ShopViewHolder extends RecyclerView.ViewHolder
{
    private View view;

    public ShopViewHolder(View view)
    {
        super(view);
        this.view = view;
    }

    public View getView()
    {
        return this.view;
    }

    public void setView(View view)
    {
        this.view = view;
    }
}
