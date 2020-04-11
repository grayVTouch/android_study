package com.test.test.bcy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.test.lib.Tool;

public class SlideSwitchLinearLayout extends LinearLayout
{
    public SlideSwitchLinearLayout(Context context)
    {
        super(context);
    }

    public SlideSwitchLinearLayout(Context context , AttributeSet attrs)
    {
        super(context , attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                this.onTouchStart(event);
                break;
            case MotionEvent.ACTION_MOVE:
                this.onTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                this.onTouchEnd(event);
                break;
        }
        super.dispatchTouchEvent(event);
        return true;
    }

    public void onTouchStart(MotionEvent event)
    {
        if (this.onTouchStartListener == null) {
            return ;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        this.onTouchStartListener.onTouchStart(this , x , y);
    }

    public void onTouchMove(MotionEvent event)
    {
        if (this.onTouchMoveListener == null) {
            return ;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        this.onTouchMoveListener.onTouchMove(this , x , y);
    }

    public void onTouchEnd(MotionEvent event)
    {
        if (this.onTouchEndListener == null) {
            return ;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        this.onTouchEndListener.onTouchEnd(this , x , y);
    }

    private OnTouchStartListener onTouchStartListener;

    private OnTouchMoveListener onTouchMoveListener;

    private OnTouchEndListener onTouchEndListener;

    public void setOnTouchStartListener(OnTouchStartListener listener)
    {
        this.onTouchStartListener = listener;
    }

    public void setOnTouchMoveListener(OnTouchMoveListener listener)
    {
        this.onTouchMoveListener = listener;
    }

    public void setOnTouchEndListener(OnTouchEndListener listener)
    {
        this.onTouchEndListener = listener;
    }

    public interface OnTouchStartListener
    {
        void onTouchStart(View view , int x , int y);
    }

    public interface OnTouchMoveListener
    {
        void onTouchMove(View view , int x , int y);
    }

    public interface OnTouchEndListener
    {
        void onTouchEnd(View view , int x , int y);
    }

    public static abstract class Adapter
    {
        // 必须实现
        public abstract int getCount();

        // 必须实现
        public abstract void instantiateItem(ViewGroup view , int position);

        // 根元素
        private ViewGroup root;

        // 上级元素
        private ViewGroup parent;

        public void notifyDataSetChanged()
        {
            this.root.post(() -> {
                // 初始化设置容器元素的宽度
                int rootW  = this.root.getWidth();
                int parentW = this.getCount() * rootW;
                ViewGroup.LayoutParams params = parent.getLayoutParams();
                params.width = parentW;
                // 设置容器宽度
                this.parent.setLayoutParams(params);
            });

            // 当数据发生变化的时候需要重新更新 适配器
            for (int i = 0; i < this.getCount(); ++i)
            {
                // 初始化项目
                this.instantiateItem(this.root , i);
            }
        }

        // 这个请在 setAdapter 方法里面调用
        // 用来设置 适配器所处的 容器元素
        public void setViewGroup(ViewGroup root , ViewGroup parent)
        {
            this.root = root;
            this.parent = parent;
        }
    }

    private LinearLayout linearLayout;

    // 初始化滑动切换组件
    public void initView()
    {
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        // 添加视图
        this.addView(linearLayout);
        this.linearLayout = linearLayout;

    }

    // 设置 adapter
    public void setAdapter(Adapter adapter)
    {
        // 添加容器视图
        this.initView();
        // 设置视图
        adapter.setViewGroup(this , this.linearLayout);
        // 更新数据
        adapter.notifyDataSetChanged();
    }
}
