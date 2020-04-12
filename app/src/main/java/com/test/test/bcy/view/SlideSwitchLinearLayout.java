package com.test.test.bcy.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.test.lib.Tool;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.function.Consumer;

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

    // 是否渲染完毕
    private boolean isRender = false;

    // 是否能够移动
    private boolean canMove = false;

    private int startX = 0;
    private int startY = 0;
    private int endX = 0;
    private int endY = 0;

    // 当前显示的界面
    private int position = 0;

    private int width = 0;

    private int height = 0;

    private double ratio = 0.5;

    private int innerTransX = 0;

    private long startTime = 0;

    private long endTime = 0;

    private AnimatorListener listener;

    // 适配器
    private Adapter adapter;

    public void onTouchStart(MotionEvent event)
    {
        this.startTime = Calendar.getInstance().getTimeInMillis();
        if (!this.isRender) {
            return ;
        }
        this.startX = (int) event.getX();
        this.startY = (int) event.getY();

        // 数据处理
        this.canMove = true;
        if (this.listener == null) {
            return ;
        }
        this.listener.onTouchStart(this.position);
    }

    public void onTouchMove(MotionEvent event)
    {
        if (!this.isRender) {
            return ;
        }
        if (!this.canMove) {
            return ;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        int amountX = x - startX;
        if (amountX == 0) {
            return ;
        }
        if (this.position == 0) {
            if (amountX > 0) {
                return ;
            }
        }
        if (this.position + 1 == this.adapter.getCount()) {
            if (amountX < 0) {
                return ;
            }
        }
        int transX = this.innerTransX;
        transX += amountX;
        this.inner.setTranslationX(transX);
        if (this.listener == null) {
            return ;
        }
        int action = amountX > 0 ? this.ANIMATION_PREV : this.ANIMATION_NEXT;
        double ratio = (double) amountX / this.width;
        this.listener.onTouchMove(this.position , ratio , amountX);
        this.listener.onMove(position , this.TOUCH_MOVE , action , ratio , amountX , 0);
    }

    public static final int ANIMATION_NEXT = 1;
    public static final int ANIMATION_PREV = -1;
    public static final int ANIMATION_ORIGIN = 0;

    public static final int TOUCH_START = 1;
    public static final int TOUCH_MOVE = 2;
    public static final int TOUCH_END = 3;

    // 决定类型
    public static final int DECISION_TIME = 1;
    public static final int DECISION_DISTANCE = 2;

    public void onTouchEnd(MotionEvent event)
    {
        SlideSwitchLinearLayout self = this;
        this.endTime = Calendar.getInstance().getTimeInMillis();
        if (!this.isRender) {
            return ;
        }
        this.canMove = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        int amountX = x - this.startX;
        if (this.position == 0) {
            if (amountX > 0) {
                return ;
            }
        }
        if (this.position + 1 == this.adapter.getCount()) {
            if (amountX < 0) {
                return ;
            }
        }
        if (amountX == 0) {
            return ;
        }
        // 当前正在执行的动作
        int action = this.ANIMATION_ORIGIN;
        int position;
        int decisionType = this.DECISION_DISTANCE;
        long duration = this.endTime - this.startTime;
        if (duration < 200) {
            if (amountX > 0) {
                action = this.ANIMATION_PREV;
                // 左滑-上一个
                position = this.position - 1;
                decisionType = this.DECISION_TIME;
            } else {
                // 左滑-下一个
                action = this.ANIMATION_NEXT;
                position = this.position + 1;
                decisionType = this.DECISION_DISTANCE;
            }
        } else {
            double ratio = Math.abs((double) amountX / this.width);
            if (ratio > this.ratio) {
                if (amountX > 0) {
                    // 右移-上一个
                    action = this.ANIMATION_PREV;
                    position = this.position - 1;
                } else {
                    // 左移-下一个
                    action = this.ANIMATION_NEXT;
                    position = this.position + 1;
                }
            } else {
                // 还原
                action = this.ANIMATION_ORIGIN;
                position = this.position;
            }
        }
        final int finalAction = action;
        final int startTransX = (int) self.inner.getTranslationX();
        int endTransX = -this.width * position;
        int amountTransX = endTransX - startTransX;
        Tool.log("amountTransX: " + amountTransX);
        ValueAnimator prev = ValueAnimator.ofInt(startTransX , endTransX);
        prev.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (int) animation.getAnimatedValue();
                int amountValue = value - startTransX;
                self.inner.setTranslationX(value);
                /**
                 *
                 * @param position
                 * @param touchState 触摸状态 end | move | up
                 * @param action 当前正在执行的动作： origin-复位 | next-下一个 | prev-上一个
                 * @param ratio 当前移动量占据每个页面宽度的百分比，有正负之分，正表示右滑 | 负表示左滑
                 * @param amountX 当前移动量，单位 px，同上
                 */
                double ratio = (double) amountValue / amountTransX;
                self.listener.onMove(self.position , self.TOUCH_END , finalAction , ratio , amountValue , amountTransX);
            }
        });
        prev.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                // 更新索引
                self.position = position;
                self.innerTransX = (int) self.inner.getTranslationX();

                if (self.listener == null) {
                    return ;
                }
                self.listener.onTouchEnd(position);
            }
        });
        prev.setDuration(100);
        prev.start();
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
        public abstract View instantiateItem(ViewGroup view , int position);

        // 根元素
        private SlideSwitchLinearLayout root;

        // 上级元素
        private ViewGroup parent;

        public void notifyDataSetChanged()
        {
            final Adapter self = this;
            int count = this.getCount();
            View[] views = new View[count];
            // 当数据发生变化的时候需要重新更新 适配器
            for (int i = 0; i < count; ++i)
            {
                // 初始化项目
                View view = this.instantiateItem(this.parent , i);
                views[i] = view;
            }
            // 初始化视图尺寸
            this.root.post(new Runnable() {
                public void run()
                {
                    // 初始化设置容器元素的宽度
                    int rootW  = self.root.getWidth();
                    int parentW = self.getCount() * rootW;
                    ViewGroup.LayoutParams params = parent.getLayoutParams();
                    params.width = parentW;
                    // 设置容器宽度
                    self.parent.setLayoutParams(params);
                    for (int i = 0; i < count; ++i)
                    {
                        View v = views[i];
                        ViewGroup.LayoutParams vParams = v.getLayoutParams();
                        vParams.width = rootW;
                        v.setLayoutParams(vParams);
                    }
                    self.root.width = rootW;
                    self.root.height = self.root.getHeight();
                    self.root.isRender = true;
                }
            });
        }

        // 这个请在 setAdapter 方法里面调用
        // 用来设置 适配器所处的 容器元素
        public void setViewGroup(SlideSwitchLinearLayout root , ViewGroup parent)
        {
            this.root = root;
            this.parent = parent;
        }
    }

    private LinearLayout inner;

    // 初始化滑动切换组件
    public void initView()
    {
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        // 添加视图
        this.addView(linearLayout);
        this.inner = linearLayout;

    }

    // 设置 adapter
    public void setAdapter(Adapter adapter)
    {
        this.adapter = adapter;
        // 添加容器视图
        this.initView();
        // 设置视图
        adapter.setViewGroup(this , this.inner);
        // 更新数据
        adapter.notifyDataSetChanged();
    }

    public interface AnimatorListener
    {

        void onTouchMove(int position , double ratio , int amountX);

        void onTouchEnd(int position);

        void onTouchStart(int position);

        /**
         *
         * @param position
         * @param touchState 触摸状态 end | move | up
         * @param action 当前正在执行的动作： origin-复位 | next-下一个 | prev-上一个
         * @param ratio 当前移动量占据每个页面宽度的百分比，有正负之分，正表示右滑 | 负表示左滑
         * @param amountX 当前移动量，单位 px，同上
         * @param amount  仅在 touchState = TOUCH_END & action = ORIGIN 的时候有效，用来判断还原的运动方向
         */
        void onMove(int position , int touchState , int action , double ratio , int amountX , int amount);
    }

    public void addListener(AnimatorListener listener)
    {
        this.listener = listener;
    }
}
