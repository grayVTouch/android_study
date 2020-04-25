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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
//                "兴趣" ,
                "COS" ,
//                "角色" ,
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
            if (i == subject.length - 1) {
                // 最后一个文本,marginEnd 设置为 0
                Tool.setLayoutParams(text , "rightMargin" , 0);
            }
            // 缓存文本
            textViews[i] = text;
        }
        ArrayList<HashMap<String,Integer>> endRes = new ArrayList<>();
        tabs.post(new Runnable() {
            public void run()
            {
                // 渲染完成之后，设置首个视图
                // 计算出游标在每个视图的 translationX | width
                for (int i = 0; i < textViews.length; ++i)
                {
                    TextView textView = textViews[i];
                    int textViewW = textView.getWidth();
                    int tabLineEndW = (int) (textViewW * cursorRatio);
                    int tabLineEndX = 0;
                    HashMap<String,Integer> hm = new HashMap<>();
                    hm.put("width" , tabLineEndW);
                    for (int n = 0; n <= i; ++n)
                    {
                        TextView textViewCopy = textViews[n];
                        int textViewWCopy = textViewCopy.getWidth();
                        if (n == i) {
                            int endViewMargin = (textViewWCopy - tabLineEndW) / 2;
                            tabLineEndX += endViewMargin;
                            break;
                        }
                        int rightMargin = Tool.getLayoutParams(textViewCopy , "rightMargin");
                        tabLineEndX += textViewWCopy + rightMargin;
                    }
                    hm.put("translationX" , tabLineEndX);
                    endRes.add(hm);
                    if (i == 0) {
                        // 初始化设置
                        tabLine.setTranslationX(tabLineEndX);
                        Tool.setLayoutParams(tabLine , "width" , tabLineEndW);
                    }
                }
            }
        });

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

            private float tabLineStartX = 0;

            private float finalTabLineStartX = 0;

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
                this.hasExpectedResForTouchEnd = false;
                this.hasExpectedResForTouchMove = false;

                // 设置最终值
                HashMap<String,Integer> hm = endRes.get(position);
                Tool.setLayoutParams(tabLine , "width" , hm.get("width"));
                tabLine.setTranslationX(hm.get("translationX"));
            }

            @Override
            public void onTouchStart(int position)
            {

            }

            @Override
            public void onMove(int position, int touchState, int action, double ratio, int amountX , int amount)
            {
                Tool.log("当前位置：" + position + "; touchState: " + touchState + "; action: " + action + "; ratio: " + ratio + "; amountX: " + amountX + "; amount: " + amount);
                if (touchState == SliderSwitchLinearLayout.TOUCH_MOVE) {
                    // 移动的过程
                    return ;
                }
                if (touchState == SliderSwitchLinearLayout.TOUCH_END) {
                    // 松开手指的过程
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
