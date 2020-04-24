package com.test.test.bcy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.test.test.MainActivity;
import com.test.test.R;
import com.test.test.bcy.adapter.BcyAppViewPagerAdapter;
import com.test.test.bcy.view.SlideSwitchLinearLayout;
import com.test.test.lib.Tool;

import java.util.HashMap;

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

        LinearLayout tabs = this.findViewById(R.id.tabs);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < subject.length; ++i)
        {
            String cur = subject[i];
            View view = inflater.inflate(R.layout.bcy_app_tab_item , tabs , false);
            TextView text = (TextView) view;
            tabs.addView(view);

            text.setText(cur);

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
                       // Tool.setLayoutParams(tabLine , "leftMargin" , cursorStart);
                       Tool.setLayoutParams(tabLine , "width" , tabLineW);
                       tabLine.setTranslationX(cursorStart);
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

        SlideSwitchLinearLayout slider = this.findViewById(R.id.slider_outer);
        

        class MyAdapter extends SlideSwitchLinearLayout.Adapter
        {
            private String[] value;

            public MyAdapter(String[] value)
            {
                this.value = value;
            }

            public int getCount()
            {
                return this.value.length;
            }

            @Override
            public View instantiateItem(ViewGroup vg , int position)
            {
                LayoutInflater inflater = LayoutInflater.from(vg.getContext());
                View view = inflater.inflate(R.layout.bcy_app_view_pager_adapter_item , vg , false);
                TextView text = view.findViewById(R.id.text);
                text.setText(this.value[position]);
                vg.addView(view);
                return view;
            }
        }
        // 实例化适配器
        MyAdapter adapter = new MyAdapter(subject);
        // 设置适配器
        slider.setAdapter(adapter);
        slider.addListener(new SlideSwitchLinearLayout.AnimatorListener() {
            private int tabLineW = 0;

            // 当前的宽度
            private int curTabLineW = 0;

            private int tabLineMaxW = 0;

            private int tabLineStartX = 0;

            private int finalTabLineStartX = 0;

            private int tabLineEndX = 0;

            private int copyTabLineX = 0;

            private int startTabLineMargin = 0;

            private int endTabLineMargin = 0;

            private int endTabLineW = 0;

            private int startViewMargin = 0;

            private int amountW = 0;

            private boolean hasExpectedResForTouchMove = false;

            private boolean hasExpectedResForTouchEnd = false;

            private boolean directNext = true;

            @Override
            public void onTouchMove(int position, double ratio, int amountX)
            {

            }

            @Override
            public void onTouchEnd(int position)
            {
                this.hasExpectedResForTouchMove = false;
                this.hasExpectedResForTouchEnd = false;
            }

            @Override
            public void onTouchStart(int position)
            {
                this.tabLineW = Tool.getLayoutParams(tabLine , "width");
                this.tabLineStartX = (int) tabLine.getTranslationX();
                this.finalTabLineStartX = this.tabLineStartX;
                this.directNext = true;
            }

            @Override
            public void onMove(int position, int touchState, int action, double ratio, int amountX , int amount)
            {
                if (!this.hasExpectedResForTouchMove || !this.hasExpectedResForTouchEnd) {
                    // 计算出相关值
                    if (touchState == SlideSwitchLinearLayout.TOUCH_MOVE) {
                        this.hasExpectedResForTouchMove = true;
                        // 手指运动过程
                        if (action == SlideSwitchLinearLayout.ANIMATION_NEXT) {
                            int endPosition = position + 1;
                            View startView = textViews[position];
                            View endView = textViews[endPosition];
                            int startViewW = startView.getWidth();
                            int endViewW = endView.getWidth();
                            this.endTabLineW = (int) (endViewW * cursorRatio);
                            this.startTabLineMargin = (startViewW - this.tabLineW ) / 2;
                            this.endTabLineMargin = (endViewW - this.endTabLineW) / 2;
                            this.startViewMargin = Tool.getLayoutParams(startView , "rightMargin");
                            this.tabLineMaxW = this.tabLineW + this.startTabLineMargin + this.startViewMargin + this.endTabLineMargin + this.endTabLineW;
                            this.amountW = this.tabLineMaxW - this.tabLineW;
                        }
                        if (action == SlideSwitchLinearLayout.ANIMATION_PREV) {

                        }
                    }
                    if (touchState == SlideSwitchLinearLayout.TOUCH_END) {
                        this.hasExpectedResForTouchEnd = true;
                        if (action == SlideSwitchLinearLayout.ANIMATION_PREV) {

                        }
                        if (action == SlideSwitchLinearLayout.ANIMATION_ORIGIN) {
                            // 还原
                            if (amount > 0) {
                                // 运动方向：从左往右
                                this.tabLineMaxW = tabLine.getWidth();
                                this.amountW = this.tabLineMaxW - this.tabLineW;
                            }
                            if (amount < 0) {
                                // 运动方向：从右往左
                            }
                        }
                        if (action == SlideSwitchLinearLayout.ANIMATION_NEXT) {
                            // 移动到下一个
                            int endPosition = position + 1;
                            View startView = textViews[position];
                            View endView = textViews[endPosition];
                            int startViewW = startView.getWidth();
                            int endViewW = endView.getWidth();
                            this.curTabLineW = tabLine.getWidth();
                            this.endTabLineW = (int) (endViewW * cursorRatio);
                            this.startTabLineMargin = (startViewW - this.tabLineW ) / 2;
                            this.endTabLineMargin = (endViewW - this.endTabLineW) / 2;
                            this.startViewMargin = Tool.getLayoutParams(startView , "rightMargin");
                            this.tabLineMaxW = this.tabLineW + this.startTabLineMargin + this.startViewMargin + this.endTabLineMargin + this.endTabLineW;
                            this.amountW = this.curTabLineW - this.endTabLineW;
                            this.tabLineStartX = (int) tabLine.getTranslationX();
                            Tool.log("移动到下一个，解算出最终值");
                        }
                    }
                }

                if (touchState == SlideSwitchLinearLayout.TOUCH_MOVE) {
                    // 手指运动过程
                    if (action == SlideSwitchLinearLayout.ANIMATION_NEXT) {
                        ratio = Math.abs(ratio);
                        double endRatio = ratio * 2;
                        if (endRatio < 1) {
                            int amountW = (int) (this.amountW * endRatio);
                            int endW = this.tabLineW + amountW;
                            Tool.setLayoutParams(tabLine , "width" , endW);
                        } else {
                            endRatio = endRatio - 1;
                            int amountW = (int) (this.amountW * endRatio);
                            int endW = this.tabLineMaxW - amountW;
                            Tool.setLayoutParams(tabLine , "width" , endW);
                            int endX = this.tabLineStartX + amountW;
                            tabLine.setTranslationX(endX);
                        }
                        return ;
                    }
                    if (action == SlideSwitchLinearLayout.ANIMATION_PREV) {

                        return ;
                    }
                } else if (touchState == SlideSwitchLinearLayout.TOUCH_END) {
                    // 手指触摸结束
                    if (action == SlideSwitchLinearLayout.ANIMATION_PREV) {
                        // 移动到上一个
                    }
                    if (action == SlideSwitchLinearLayout.ANIMATION_ORIGIN) {
                        // 还原
                        if (amount > 0) {
                            // 运动方向：从左往右
                            int amountW = (int) (this.amountW * ratio);
                            int width = this.tabLineMaxW - amountW;
                            Tool.setLayoutParams(tabLine , "width" , width);
                        }
                        if (amount < 0) {
                            // 运动方向：从右往左
                        }
                    }
                    if (action == SlideSwitchLinearLayout.ANIMATION_NEXT) {
                        // 移动到下一个
                        int curTabLineW = tabLine.getWidth();
                        int curTabLineX = (int) tabLine.getTranslationX();
//                        Tool.log("curTabLineW: ");
                        if (curTabLineX <= this.finalTabLineStartX) {
                            // 非直接跳转，需要一个过渡
                            this.directNext = false;
                            // 请先扩大宽度
                            if (curTabLineW < this.tabLineMaxW) {
                                Tool.log("步骤1：尚未达到最大值 curTabLineX: " + curTabLineX + "; tabLineStartX: " + this.tabLineStartX);
                                double copyRatio = Math.abs(ratio * 2);
                                int totalAmountW = this.tabLineMaxW - this.curTabLineW;
                                int amountW = (int) (totalAmountW * copyRatio);
                                int endW = this.curTabLineW + amountW;
                                Tool.setLayoutParams(tabLine , "width" , endW);
                                this.curTabLineW = endW;
                            } else {
//                                return ;
                                double copyRatio = Math.abs(ratio * 2 - 1);
                                int totalAmountW = this.curTabLineW - this.endTabLineW;
                                int amountW = (int) (totalAmountW * copyRatio);
                                int endW = this.curTabLineW - amountW;
                                int endX = this.tabLineStartX + amountW;
                                Tool.setLayoutParams(tabLine , "width" , endW);
                                tabLine.setTranslationX(endX);
                                // 记住这个东西
                                Tool.log("步骤2：达到最大值");
                            }
                        } else {
                            // 已经达到最大值，正在缩小到目标数值的过程
                            if (this.directNext) {
                                int totalAmountW = this.curTabLineW - this.endTabLineW;
                                int amountW = (int) (totalAmountW * ratio);
                                int endW = this.curTabLineW - amountW;
                                int endX = this.tabLineStartX + amountW;
                                Tool.setLayoutParams(tabLine , "width" , endW);
                                tabLine.setTranslationX(endX);
                                Tool.log("直接进入到下一个项");
                            } else {
                                double copyRatio = Math.abs(ratio) * 2 - 1;
                                int totalAmountW = this.curTabLineW - this.endTabLineW;
                                int amountW = (int) (totalAmountW * copyRatio);
                                int endW = this.curTabLineW - amountW;
                                int endX = this.tabLineStartX + amountW;
                                Tool.setLayoutParams(tabLine , "width" , endW);
                                tabLine.setTranslationX(endX);
                                Tool.log("步骤3：进入到下一项 amountW: " + this.amountW + "; ratio: " + ratio + "; copyRatio: " + copyRatio + "; curTabLineW: " + this.curTabLineW + "; curTabLineX: " + this.copyTabLineX);
//                                Tool.log("步骤3：缩小到目标参数的过程, amountW: " + amountW + "; copyTabLineX: " + this.copyTabLineX + "; endW: " + endW + "; endX: " + endX + ";ratio: " + ratio);
                            }
                        }
                    }
                }
            }
        });
    }

    protected void onStart()
    {
        super.onStart();
        View root = this.findViewById(R.id.root);

        int w = root.getWidth();
        Tool.log("rootW: " + w);

        root.post(() -> {
            Tool.log("runnable rootW: " + root.getWidth());
        });
    }

    public void run()
    {
        this.initNavMenu();
        this.initViewPager();
    }
}
