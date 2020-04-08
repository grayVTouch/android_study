package com.test.test.bcy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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

        // 记录上一个位置，然后根据当前滚动期间给定的位置，进行判断是前进还是后退
        // 根据前进还是后退，来决定对主题底部的线进行移动
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // 源：位置
            private int position = 0;

            // 源：最小的宽度
            private int cursorWidth = 0;

            // 源：最小的 left
            private int cursorLeft = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                String dir = "";
                if (this.position == position) {
                    if (position == 0) {
                        // 首页
                        if (positionOffsetPixels <= 0) {
                            // 用户正在移动屏幕进行界面切换（方向向右）
                            return ;
                        }
                        dir = "toRight";
                    } else if (position == subject.length) {
                        // 最后一页
                        if (positionOffsetPixels <= 0) {
                            // 最后一页，并且用户没有往左移动
                            return ;
                        }
                        dir = "toLeft";
                    } else {
                        // 其他情况直接跳出
                        return ;
                    }
                } else {
                    if (position > this.position) {
                        dir = "toRight";
                    } else {
                        dir = "toLeft";
                    }
                }
                Tool.log("当前用户的滚动方向：" + dir + "; origin_position: " + this.position + "; position: " + position);
                /**
                 * 计算游标的最大长度
                 * 计算游标的 marginEnd 最大值
                 * 计算游标正常的尺寸（占据的百分比）
                 *
                 */
                TextView text = textViews[position];
                int textWidth = text.getWidth();
                // 游标正常的尺寸大小
                int cursorWidth = this.cursorWidth;
                // 游标正常尺寸下的 marginEnd 值是多少
                // 游标在切换后的项里面占据的 marginStart 值
                int marginStartForTextView = (int) ((textWidth - cursorWidth) / 2);
                // 之前的项占据的 marginStart 值的大小
                int marginStartForSum = 0;
                for (int i = 0; i < position; ++i)
                {
                    // 计算
                    TextView textView = textViews[i];
                    marginStartForSum += textView.getWidth();
                    marginStartForSum += ((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).getMarginStart();
                }
                // 所以最大（或 最小）的 marginStart 的值
                int marginStartVal = marginStartForSum + marginStartForTextView;

                // 计算出游标在源位置上的宽度
                TextView originText = textViews[this.position];
                int originTextWidth = originText.getWidth();
                // 游标的正常宽度
                int widthForTabLine = (int) (originTextWidth * cursorRatio);
                // 计算出一半的长度
                int extraVal = (originTextWidth - widthForTabLine) / 2;
                // 计算出 marginEnd
                int maxWidthForTabLine = widthForTabLine + extraVal + marginEndForItem + marginStartForTextView + cursorWidth;
                int amountForTabLine = maxWidthForTabLine - cursorWidth;

                        // 获取游标当前的 left 值
                ViewGroup.MarginLayoutParams layoutParamsForTabLine = (ViewGroup.MarginLayoutParams) tabLine.getLayoutParams();
                int curMarginStartForTabLine = layoutParamsForTabLine.getMarginStart();
                // 由于游标这个地方的过渡有两个过程
                // 第一 将长度拉到最大（位置不变）
                // 第二 将长度减到最小（位置发生变化）
                // 上述两个过程被认为是同一个动画效果，即过程
                // 所以由 viewpager 的完成度来指示游标的完成度，需要 *2
                // 通过 *2 来评判步骤一是否完成，然后进行步骤2
                if (dir == "toLeft") {
                    // 用户滑动屏幕向左移动

                    return ;
                }
                // 用户滑动屏幕向右移动
                double realRatio = positionOffset * 2;
                Tool.log("realRatio: " + realRatio + "; originRatio: " + positionOffset);

                if (realRatio <= 1) {
                    // 增加过程
                    int amount = (int) (amountForTabLine * realRatio);
                    int endWidth = widthForTabLine + amount;
                    // 设置宽度的过程需要通过设置上级的 LayoutParams
                    int heightForTabLine = tabLine.getHeight();
                    // 设置宽高
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tabLine.getLayoutParams();
                    // 设置宽度
                    params.width = endWidth;
                    tabLine.setLayoutParams(params);
                    Tool.log("现有宽度：" + endWidth + "; 源宽度：" + widthForTabLine);
                    return ;
                }
                // 缩小过程
                int amount = (int) (amountForTabLine * (realRatio - 1));
                int endWidth = widthForTabLine - amount;
                // 设置宽度的过程需要通过设置上级的 LayoutParams
                int heightForTabLine = tabLine.getHeight();
                // 设置宽高
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tabLine.getLayoutParams();
                // 设置宽度
                params.width = endWidth;
                tabLine.setLayoutParams(params);
                tabLine.setTranslationX(amount);
                Tool.log("现有宽度：" + endWidth + "; 源宽度：" + widthForTabLine);
            }

            @Override
            public void onPageSelected(int position)
            {
                // 页面切换成功的时候，可以看看
                Tool.log("页面切换成功");
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                // 状态会发生改变
                // 0 - 动画完全停滞状态，用户手指也已经松开
                // 1 - 用户手指触摸滑动中
                // 2 - 用户手指松开正在自动调整中
                // 获取并缓存当前选中视图的 尺寸
                TextView textView = textViews[this.position];
                this.cursorWidth = (int) (textView.getWidth() * cursorRatio);
                this.cursorLeft = ((ViewGroup.MarginLayoutParams) tabLine.getLayoutParams()).getMarginStart();
//                Tool.log("状态发生改变：" + state);
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
