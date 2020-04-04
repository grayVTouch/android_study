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

    // x 水平方向上的滚动量
    private int amountX = 0;

    // y 垂直方向上的滚动量
    private int amountY = 0;

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
            // x 累计滚动量
            this.amountX += x;
            // y 累计滚动量
            this.amountY += y;
            // 变化量
            this.onScrollChangeWithSumListener.onScrollChanged(this , this.amountX , this.amountY);
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
