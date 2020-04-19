package com.test.test.bcy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.test.MainActivity;
import com.test.test.R;
import com.test.test.bcy.view.SliderSwitchLinearLayout;
import com.test.test.lib.Tool;

import java.util.Calendar;

public class BcyAppActivity extends AppCompatActivity
{
    @Override
    public void onBackPressed()
    {
        Calendar calendar = Calendar.getInstance();
        long unixtime = calendar.getTimeInMillis();
        String backPressCountKey = "back_pressed_count_2";
        String lastBackPressedTimeKey = "last_back_pressed_time_2";
        int backPressedCount = Tool.Storage.get(this , backPressCountKey , 1);
        if (backPressedCount < 2) {
            Tool.Storage.put(this , backPressCountKey , ++backPressedCount);
            Tool.Storage.put(this , lastBackPressedTimeKey , unixtime);
            Tool.tip(this , "再按一次退出 app");
        } else {
            long lastBackPressedTime = Tool.Storage.get(this , lastBackPressedTimeKey , unixtime);
            int duration = 3 * 1000;
            if (unixtime > lastBackPressedTime && unixtime - lastBackPressedTime < duration) {
                // 结束当前 activity；实际就是退出 app
                Tool.Storage.put(this , backPressCountKey , 1);
//                this.finish();
                Tool.log("后期再看如何退出 app ");
            }
        }

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

        /**
         * 初始化导航标签
         */
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
                       int cursorStart = (textW - tabLineW) / 2;
                       Tool.setLayoutParams(tabLine , "width" , tabLineW);
                       tabLine.setTranslationX(cursorStart);
                   }
                });
            }
            if (i == subject.length - 1) {
                // 最后一个文本,marginEnd 设置为 0
                Tool.setLayoutParams(text , "rightMargin" , 0);
            }
            // 缓存文本
            textViews[i] = text;
        }

        SliderSwitchLinearLayout slider = this.findViewById(R.id.slider_outer);
        class MyAdapter extends SliderSwitchLinearLayout.Adapter
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
        // 添加动画监听
        slider.addListener(new SliderSwitchLinearLayout.AnimatorListener() {
            private int tabLineW = 0;
            // 当前的宽度
            private int tabLineCurW = 0;

            private int tabLineMaxW = 0;

            private int tabLineStartX = 0;

            private int finalTabLineStartX = 0;

            private int tabLineEndX = 0;

            private int copyTabLineX = 0;

            private int startTabLineMargin = 0;

            private int endTabLineMargin = 0;

            private int tabLineEndW = 0;

            private int startViewMargin = 0;

            private int amountW = 0;

            private boolean hasExpectedResForTouchMove = false;

            private boolean hasExpectedResForTouchEnd = false;

            private boolean directNext = true;

            // 宽度变化值：当前值 -> 最大值
            private int amountWFromCurrentToMax = 0;

            private int amountWFromCurrentToEnd = 0;

            private int amountWFromCurrentToStart = 0;

            private boolean isMoreThanMax = false;
            private boolean isMoreThanMaxCopy = false;
//            private boolean isLessThanMax = true;

            private boolean isCompleted = true;

            private int finalTabLineCurW = 0;

            @Override
            public void onTouchMove(int position, double ratio, int amountX)
            {

            }

            @Override
            public void onTouchEnd(int position)
            {
                this.hasExpectedResForTouchMove = false;
                this.hasExpectedResForTouchEnd = false;
                this.isMoreThanMax = false;
                this.isCompleted = true;
            }

            @Override
            public void onTouchStart(int position)
            {
                if (!this.isCompleted) {
                    return ;
                }
                this.tabLineW = Tool.getLayoutParams(tabLine , "width");
                this.tabLineStartX = (int) tabLine.getTranslationX();
                this.finalTabLineStartX = this.tabLineStartX;
                this.directNext = true;
                this.isMoreThanMaxCopy = false;
                this.isCompleted = false;
            }

            @Override
            public void onMove(int position, int touchState, int action, double ratio, int amountX , int amount)
            {
                // 计算初始值
                if (!hasExpectedResForTouchMove && touchState == SliderSwitchLinearLayout.TOUCH_MOVE) {
                    // Move 过程中，计算出最终值
                    this.hasExpectedResForTouchMove = true;
                    if (action == SliderSwitchLinearLayout.ANIMATION_NEXT) {
                        int endPosition = position + 1;
                        View startView = textViews[position];
                        View endView = textViews[endPosition];
                        int startViewW = startView.getWidth();
                        int endViewW = endView.getWidth();
                        this.tabLineCurW = tabLine.getWidth();
                        this.tabLineEndW = (int) (endViewW * cursorRatio);
                        this.startTabLineMargin = (startViewW - this.tabLineW ) / 2;
                        this.endTabLineMargin = (endViewW - this.tabLineEndW) / 2;
                        this.startViewMargin = Tool.getLayoutParams(startView , "rightMargin");
                        this.tabLineMaxW = this.tabLineW + this.startTabLineMargin + this.startViewMargin + this.endTabLineMargin + this.tabLineEndW;
                        this.amountW = this.tabLineMaxW - this.tabLineW;
                        this.amountWFromCurrentToMax = this.tabLineMaxW - this.tabLineW;
                    }
                    if (action == SliderSwitchLinearLayout.ANIMATION_PREV) {

                    }
                }
                if (!this.hasExpectedResForTouchEnd && touchState == SliderSwitchLinearLayout.TOUCH_END) {
                    this.hasExpectedResForTouchEnd = true;
                    if (action == SliderSwitchLinearLayout.ANIMATION_PREV) {

                    }
                    if (action == SliderSwitchLinearLayout.ANIMATION_ORIGIN) {
                        // 还原
                        if (amount > 0) {
                            // 运动方向：从左往右
                            this.tabLineCurW = tabLine.getWidth();
                            this.amountWFromCurrentToStart = this.tabLineCurW - this.tabLineW;
                        }
                        if (amount < 0) {
                            // 运动方向：从右往左
                        }
                    }
                    if (action == SliderSwitchLinearLayout.ANIMATION_NEXT) {
                        // 移动到下一个
                        int endPosition = position + 1;
                        View startView = textViews[position];
                        View endView = textViews[endPosition];
                        int startViewW = startView.getWidth();
                        int endViewW = endView.getWidth();
                        this.tabLineCurW = tabLine.getWidth();
                        this.finalTabLineCurW = this.tabLineCurW;
                        this.tabLineEndW = (int) (endViewW * cursorRatio);
                        this.startTabLineMargin = (startViewW - this.tabLineW ) / 2;
                        this.endTabLineMargin = (endViewW - this.tabLineEndW) / 2;
                        this.startViewMargin = Tool.getLayoutParams(startView , "rightMargin");
                        this.tabLineMaxW = this.tabLineW + this.startTabLineMargin + this.startViewMargin + this.endTabLineMargin + this.tabLineEndW;
                        this.tabLineStartX = (int) tabLine.getTranslationX();
                    }
                }

                if (touchState == SliderSwitchLinearLayout.TOUCH_MOVE) {
                    // 手指运动过程
                    if (action == SliderSwitchLinearLayout.ANIMATION_NEXT) {
                        ratio = Math.abs(ratio);
                        ratio = Math.min(1 , ratio);
                        double endRatio = ratio * 2;
                        if (endRatio < 1) {
                            int curTabLineX = (int) tabLine.getTranslationX();
                            if (curTabLineX != this.tabLineStartX) {
                                tabLine.setTranslationX(this.tabLineStartX);
                            }
                            int amountW = (int) (this.amountWFromCurrentToMax * endRatio);
                            int endW = this.tabLineW + amountW;
                            Tool.setLayoutParams(tabLine , "width" , endW);
                        } else {
                            if (!this.isMoreThanMax) {
                                Tool.setLayoutParams(tabLine , "width" , this.tabLineMaxW);
                                this.tabLineCurW = this.tabLineMaxW;
                                this.amountWFromCurrentToEnd = this.tabLineCurW - this.tabLineEndW;
                                this.isMoreThanMax = true;
                            } else {
                                endRatio = endRatio - 1;
                                int amountW = (int) (this.amountWFromCurrentToEnd * endRatio);
                                int endW = this.tabLineCurW - amountW;
                                int endX = this.tabLineStartX + amountW;
                                Tool.setLayoutParams(tabLine , "width" , endW);
                                tabLine.setTranslationX(endX);
                            }
                        }
                        return ;
                    }
                    if (action == SliderSwitchLinearLayout.ANIMATION_PREV) {

                        return ;
                    }
                } else if (touchState == SliderSwitchLinearLayout.TOUCH_END) {
                    // 手指触摸结束
                    if (action == SliderSwitchLinearLayout.ANIMATION_PREV) {
                        // 移动到上一个
                    }
                    if (action == SliderSwitchLinearLayout.ANIMATION_ORIGIN) {
                        // 还原
                        if (amount > 0) {
                            // 运动方向：从左往右
                            int amountW = (int) (this.amountWFromCurrentToStart * ratio);
                            int width = this.tabLineCurW - amountW;
                            Tool.setLayoutParams(tabLine , "width" , width);
                        }
                        if (amount < 0) {
                            // 运动方向：从右往左
                        }
                    }
                    if (action == SliderSwitchLinearLayout.ANIMATION_NEXT) {
                        // 移动到下一个
                        int tabLineCurW = tabLine.getWidth();
                        int curTabLineX = (int) tabLine.getTranslationX();
                        if (!this.isMoreThanMaxCopy && curTabLineX == this.finalTabLineStartX) {
                            this.directNext = false;
                            // 请先扩大宽度
                            if (tabLineCurW < this.tabLineMaxW) {
//                                Tool.log("步骤1：尚未达到最大值 curTabLineX: " + curTabLineX + "; tabLineStartX: " + this.tabLineStartX);
                                double copyRatio = Math.abs(ratio * 2);
                                this.amountWFromCurrentToMax = this.tabLineMaxW - this.finalTabLineCurW;
                                int amountW = (int) (this.amountWFromCurrentToMax * copyRatio);
                                int endW = this.finalTabLineCurW + amountW;
                                Tool.setLayoutParams(tabLine , "width" , endW);
                                this.tabLineCurW = endW;
                            } else {
                                this.isMoreThanMaxCopy = true;
                                Tool.setLayoutParams(tabLine , "width" , this.tabLineMaxW);
                                // 记住这个东西
//                                Tool.log("步骤2：达到最大值");
                                this.tabLineCurW = this.tabLineMaxW;
                            }
                        } else {
                            // 已经达到最大值，正在缩小到目标数值的过程
                            this.amountWFromCurrentToEnd = this.tabLineCurW - this.tabLineEndW;
//                            Tool.log("tabLineCurW: " + this.tabLineCurW + "; amountWFromCurrentToEnd: " + this.amountWFromCurrentToEnd + "; tabLineEndW: " + this.tabLineEndW);
                            int amountW;
                            if (this.directNext) {
                                amountW = (int) (this.amountWFromCurrentToEnd * ratio);
//                                Tool.log("直接进入到下一个项");
                            } else {
                                double copyRatio = Math.abs(ratio) * 2 - 1;
                                amountW = (int) (this.amountWFromCurrentToEnd * copyRatio);
//                                Tool.log("步骤3：进入到下一项");
                            }
//                            Tool.log("amountW: " + amountW + "; directNext: " + this.directNext);
                            int endW = this.tabLineCurW - amountW;
                            int endX = this.tabLineStartX + amountW;
                            Tool.setLayoutParams(tabLine , "width" , endW);
                            tabLine.setTranslationX(endX);
                        }
                    }
                }
            }
        });
    }

    private LinearLayout root;

    private void initView()
    {
        this.root = this.findViewById(R.id.root);
    }


    private void init()
    {
        BcyAppActivity self = this;
        this.root.post(new Runnable() {
            public void run()
            {
                self.initNavMenu();
                self.initViewPager();
            }
        });
    }

    public void run()
    {
        this.initView();
        this.init();
    }
}
