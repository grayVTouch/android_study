package com.test.test.lib;

import android.content.Context;
import android.util.AttributeSet;

public class ScrollView extends android.widget.ScrollView
{
    public ScrollView(Context context)
    {
        super(context);
    }

    public ScrollView(Context context , AttributeSet attrs)
    {
        super(context , attrs);
    }

    // 滚动监听
    private ScrollView.OnScrollViewListener onScrollViewListener;

    // 滚动改变
    private ScrollView.OnScrollViewListener onScrollChangeWithSumListener;

    public void setOnScrollChangeListener(ScrollView.OnScrollViewListener listener)
    {
        this.onScrollViewListener = listener;
    }


    public void setOnScrollChangeWithSumListener(ScrollView.OnScrollViewListener listener)
    {
        this.onScrollChangeWithSumListener = listener;
    }

    public void onScrollChanged(int x , int y , int oldX , int oldY)
    {
        if (this.onScrollViewListener != null) {
            // 非累计滚动
            this.onScrollViewListener.onScrollChanged(this , x , y , oldX , oldY);
        }

        if (this.onScrollChangeWithSumListener != null) {
            int sumX = this.getScrollX();
            int sumY = this.getScrollY();
            this.onScrollChangeWithSumListener.onScrollChanged(this , sumX , sumY);
        }
    }

    // 在 java 里面，针对内部接口来说，修饰符 static 是多余的
    public static interface OnScrollViewListener {
        default void onScrollChanged(ScrollView view , int x , int y , int oldX , int oldY)
        {

        }

        default void onScrollChanged(ScrollView view  , int x , int y)
        {

        }
    }

}
