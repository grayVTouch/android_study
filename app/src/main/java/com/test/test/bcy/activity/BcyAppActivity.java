package com.test.test.bcy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.test.MainActivity;
import com.test.test.R;
import com.test.test.bcy.adapter.BcyAppViewPagerAdapter;
import com.test.test.lib.Tool;

public class BcyAppActivity extends AppCompatActivity
{
    @Override
    public void onBackPressed()
    {

    }

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.bcy_app);
        this.run();
    }

    // 初始化菜单栏
    public void initNavMenu()
    {
        BcyAppActivity self = this;

        LinearLayout home = this.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(self , MainActivity.class);
                self.startActivity(intent);
            }
        });

    }

    public void initViewPager()
    {
        RelativeLayout tabsLayout = this.findViewById(R.id.tabs_layout);
        ViewPager viewPager = this.findViewById(R.id.view_pager);
        View tabLine = this.findViewById(R.id.tab_line);

        // 除了宽度 和 高度这个需要渲染完成后才能获取到之外
        // 诸如 padding | margin | translation 等这些用户手动设置的
        // 参数，都不需要等待视图渲染完成后获取
        int extraV = 0;
        int paddingStartForTabsLayout = tabsLayout.getPaddingStart();
        int paddingEndForTabsLayout = tabsLayout.getPaddingEnd();
        extraV += paddingStartForTabsLayout + paddingEndForTabsLayout;
        // 外边距值
        int marginEndForItem = Tool.dpToPx(this , 10);
        // 正常的游标占据文本的比率
        double cursorRatio = 0.6;

        int position = 1;
        String[] subject = {
                "关注" ,
                "发现" ,
                "兴趣部落" ,
                "COS" ,
                "动画" ,
                "新番" ,
                "时尚" ,
                "手工" ,
                "情感"
        };
        TextView[] textViews = new TextView[subject.length];

//        Tool.log("比较结果：" + (1.0 == 1));

        // 记录上一个位置，然后根据当前滚动期间给定的位置，进行判断是前进还是后退
        // 根据前进还是后退，来决定对主题底部的线进行移动
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // 源：位置
            private int position = 0;

            // 源：最小的宽度
            private int cursorWidth = 0;

            // 源：最小的 left
            private int cursorLeft = 0;

            // 源：一般的 marginStart
            private int halfMarginStart = 0;

            // 是否首次触发
            private boolean once = true;

            // 当前页面滚动状态
            private int state;

            private int marginEnd = 0;

            // 是否已经达到最大高度
            private boolean isFitMaxW = false;

            // 是否已经停止动画
            private boolean isSettling = false;

            // 当前的 ratio
            private double ratio = 0;

            // 当前的 位置
            private int positionPixel = 0;

            // 运动的方向 left-向左 right-向右
            private String dir;


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                Tool.log("当前 position: " + position + "; 上一个: " + this.position);
                if (this.once) {
                    // 他默认会调用一次，请忽略这一次
                    this.once = false;
                    return ;
                }
//                Tool.log("position: " + position + "; positionOffset: " + positionOffset + "; positionOffsetPixels: " + positionOffsetPixels);
                int endPosition = position;
                if (this.position == position) {
                    if (positionOffsetPixels <= 0) {
                        return ;
                    }
                    if (position == 0) {
                        endPosition = 1;
                    } else {
                        endPosition = Math.max(subject.length - 1 , 1);
                    }
                }
                // 最大宽度
                TextView textView = textViews[endPosition];
                int textViewW = textView.getWidth();
                int endCursorW = (int) (textViewW * cursorRatio);
                int marginVal = (textViewW - endCursorW) / 2;
                int maxCursorW = this.cursorWidth + this.halfMarginStart + this.marginEnd + marginVal + endCursorW;
                int cursorWAmount = maxCursorW - this.cursorWidth;
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabLine.getLayoutParams();

//                Tool.log("游标的最大宽度：" + maxCursorW + "; 游标的原始宽度：" + this.cursorWidth + "; 游标的 源 marginLeft: " + this.cursorLeft + "; ");
                String dir = this.position > endPosition ? "left" : "right";
                if (this.state == ViewPager.SCROLL_STATE_DRAGGING) {
                    // 用户正在拖动的过程中
                    if (dir == "left") {
                        return ;
                    }
                    double ratio = positionOffset * 2;
                    if (this.isFitMaxW) {
                        // 缩小，位置移动的过程
                        double copyRatio = ratio - 1;
//                        double copyRatio = ratio - 1;
                        int curAmount = (int) (cursorWAmount * copyRatio);
//                        Tool.log("maxCursorW: " + params.width + "; cal_maxCursorW: " + maxCursorW + "; amount: " + curAmount + "cursorLeft: " + this.cursorLeft);
                        params.width = maxCursorW - curAmount;
//                        params.leftMargin = this.cursorLeft + curAmount;
                        params.leftMargin = this.cursorLeft + curAmount;
                    } else {
                        ratio = Math.min(1 , ratio);
                        int curAmount = (int) (cursorWAmount * ratio);
//                        Tool.log("ratio: " + ratio + "; maxCursorW: " + maxCursorW + "; 比较结果：" + (ratio == 1) + "; ratio: " + ratio);
                        params.width = Math.min(this.cursorWidth + curAmount , maxCursorW);
                        if (ratio == 1) {
                            // 符合最大的宽度
                            this.isFitMaxW = true;
                        }
                    }
                    tabLine.setLayoutParams(params);
                } else {
                    // 用户回弹的过程
                    if (this.state == ViewPager.SCROLL_STATE_SETTLING) {
                        if (dir == "left") {
                            return ;
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position)
            {
                // 更新当前的位置
                this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
//                Tool.log("view_pager: on page scroll state changed");
                this.state = state;
                // 状态会发生改变
                // 0 - 动画完全停滞状态，用户手指也已经松开 SCROLL_STATE_IDEL
                // 1 - 用户手指触摸滑动中 SCROLL_STATE_DRAGGING
                // 2 - 用户手指松开正在自动调整中 SCROLL_STATE_SETTLING
                // 获取并缓存当前选中视图的 尺寸

                if (state != ViewPager.SCROLL_STATE_DRAGGING) {
                    if (state == ViewPager.SCROLL_STATE_SETTLING) {
                        // 松手的过程
                        this.isFitMaxW = false;
                        // 表明已经松手了
                        this.isSettling = true;
                    }
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        // 动画已经停止
                        this.isSettling = false;
                        Tool.log("动画过程");
                    }
                    return ;
                }
                TextView textView = textViews[this.position];
                int textViewW = textView.getWidth();
                this.cursorWidth = (int) (textViewW * cursorRatio);
                this.cursorLeft = ((ViewGroup.MarginLayoutParams) tabLine.getLayoutParams()).getMarginStart();
                this.halfMarginStart = (int) ((textViewW - this.cursorWidth) / 2);
                this.marginEnd = ((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).rightMargin;
                Tool.log("on page scroll state changed: cursorLeft: " + this.cursorLeft);
            }
        });
        LinearLayout tabs = this.findViewById(R.id.tabs);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < subject.length; ++i)
        {
            String cur = subject[i];
            View view = inflater.inflate(R.layout.bcy_app_tab_item , tabs , false);
            TextView text = (TextView) view;
            tabs.addView(view);
            // 设置 view_pager 单个项目的文本内容

            text.setText(cur);
            // 如果是最后生成的一个元素，他的 右边距 设置为 0
//            text.setTypeface(Typeface.NORMAL , Typeface.BOLD);
//            text.setTextSize(18);
//            text.setFontSize
            if (i == 0) {
                // 第一个
                text.post(new Runnable() {
                    @Override
                   public void run()
                   {
                       // 初始化 游标 的宽度 和 位置
                       int textW = text.getWidth();
                       int tabLineW = (int) (textW * cursorRatio);
                       int cursorStart = (int) ((textW - tabLineW) / 2);
                       ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabLine.getLayoutParams();
                       params.width = tabLineW;
                       params.leftMargin = cursorStart;
                       Tool.log("设置的 leftMargin: " + cursorStart);
                       tabLine.setLayoutParams(params);
                   }
                });
            }
            if (i == subject.length - 1) {
                // 最后一个文本,marginEnd 设置为 0
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
                // 取消最后一个元素的 marginEnd 为 0
                layoutParams.setMarginEnd(0);
            }
            // 缓存文本
            textViews[i] = text;
        }
        // 初始化每个 view_pager
        BcyAppViewPagerAdapter adapter = new BcyAppViewPagerAdapter(subject);
        viewPager.setAdapter(adapter);
    }

    public void run()
    {
        this.initNavMenu();
        this.initViewPager();
    }
}
