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

        SlideSwitchLinearLayout slideSwitchLinearLayout = this.findViewById(R.id.slider_outer);

        HashMap<String,Object> map = new HashMap<>();
        map.put("canMove" , 0);
        map.put("startX" , 0);
        map.put("startY" , 0);
        map.put("endX" , 0);
        map.put("endY" , 0);

        slideSwitchLinearLayout.setOnTouchStartListener((View view , int x , int y) -> {
            map.put("canMove" , 1);
            map.put("startX" , x);
            map.put("startY" , y);
        });

        slideSwitchLinearLayout.setOnTouchMoveListener((View view , int x , int y) -> {
            int canMove = (int) map.get("canMove");
            if (canMove == 1) {
                // 先判断方向
                int startX = (int) map.get("startX");
                int startY = (int) map.get("startY");
                int amountX = x - startX;
                int amountY = y - startY;
                Tool.log("变化量x: " + amountX + "; 变化量y: " + amountY);

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                if (params.leftMargin < 0) {

                }
            }
        });

        slideSwitchLinearLayout.setOnTouchEndListener((View view , int x , int y) -> {
            map.put("canMove" , 0);
        });

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
            public void instantiateItem(ViewGroup viewGroup , int position)
            {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                View view = inflater.inflate(R.layout.bcy_app_view_pager_adapter_item , viewGroup , false);

                // 添加视图
                viewGroup.addView(view);

                // 设置文本
                TextView text = view.findViewById(R.id.text);
                text.setText(this.value[position]);

                //
            }
        }
        // 实例化适配器
        MyAdapter adapter = new MyAdapter(subject);
        // 设置适配器
        slideSwitchLinearLayout.setAdapter(adapter);

//        Tool.log("child count: " + slideSwitchLinearLayout.getChildCount());

        ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.logo);

        // 添加视图
        slideSwitchLinearLayout.addView(view);
        Tool.log("添加视图");

        // 通过 java 代码添加视图的方式
        LinearLayout linearTest = this.findViewById(R.id.linear_test);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            try {
                // 这边足够证明这个地方是没有问题的
                Thread.sleep(5 * 1000);
                ImageView view1 = new ImageView(linearTest.getContext());
                view1.setImageResource(R.drawable.jd_logo);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                view1.setLayoutParams(params);
                // 添加视图
                linearTest.addView(view1);
                Tool.log("数据测试...");
            } catch(Exception e) {
                // 打印出错误日志
                e.printStackTrace();
            }
        } , 3* 1000);
    }

    public void run()
    {
        this.initNavMenu();
        this.initViewPager();
    }
}
