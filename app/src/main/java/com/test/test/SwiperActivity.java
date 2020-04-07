package com.test.test;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.test.test.lib.ScrollView;
import com.test.test.lib.ShopAdapter;
import com.test.test.lib.Tool;

import java.util.ArrayList;
import java.util.Collection;

public class SwiperActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        Tool.log("onCreate");
        super.onCreate(bundle);
        this.setContentView(R.layout.swiper);
        this.run();
    }

    public void run()
    {
        SwipeRefreshLayout swipeRefreshLayout = this.findViewById(R.id.swiper);
        RecyclerView recycler = this.findViewById(R.id.recycler);

        swipeRefreshLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view , MotionEvent event)
            {
//                // 获取视图的尺寸
//                recycler.post(new Runnable() {
//                    @Override
//                    public void run()
//                    {
//
//                    }
//                });

                // 测量宽度 + 测量高度
//                        Tool.log("recycler view : 测量宽度：" + recycler.getMeasuredWidth() + "; 测量宽度：" + recycler.getMeasuredHeight() + "; 宽度：" + recycler.getWidth() + "; 高度：" + recycler.getHeight());
//                        Tool.log("scroll view : 测量宽度：" + scrollView.getMeasuredWidth() + "; 测量宽度：" + scrollView.getMeasuredHeight() + "; 宽度：" + scrollView.getWidth() + "; 高度：" + scrollView.getHeight());
//                Tool.log("scroll view x: " + scrollView.getScrollX() + "; y: " + scrollView.getScrollY() + " ;getX: " + scrollView.getX() + "; getY: " + scrollView.getY());
//                Tool.log("recycler view x: " + recycler.getScrollX() + "; y: " + recycler.getScrollY() + "; getX: " + recycler.getX() + "; getY:" + recycler.getY());

//                Tool.log("x: " + scrollView.getScrollX() + "; y: " + scrollView.getScrollY());
//                switch (event.getAction())
//                {
//                    case MotionEvent.ACTION_DOWN:
//                        Tool.log("x: " + scrollView.getScrollX() + "; y: " + scrollView.getScrollY());
//                        break;
//                }
//                return false;
                return false;
            }
        });

        // 避免内容发生改变的时候整个布局失效
        recycler.setHasFixedSize(true);

        // 设置布局填充器（这个说白了就是 LinearLayout）
        // 讲实在的，LayoutManager 的直接子类就两个： LinearLayoutManager | GridLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);

        ArrayList<String> data = new ArrayList<>();

        String[] dataInner = new String[] {
                "yueshu" ,
                "shuyue" ,
                "running" ,
                "grayVTouch"
        };
        for (int i = 0; i < dataInner.length; ++i)
        {
            data.add(dataInner[i]);
        }
        // 设置 适配器
        ShopAdapter adapter = new ShopAdapter(data);
        // 向 recyclerview 填充 适配器
        recycler.setAdapter(adapter);
        // 监听话动刷新的动作
        SwipeRefreshLayout swipe = this.findViewById(R.id.swiper);
//        swipe.setOnTouchListener();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh()
            {
                // 开始刷新了
                Tool.log("用户手动开始刷新了");
                // 设置刷新状态
                // true-刷新状态
                // false-取消刷新状态
                // 所有的延迟性相关的操作都是在子线程中执行
                // 否则主线程中会卡住
                (new Thread(() -> {
                    try {
                        // 1s 后取消刷新操作
                        Thread.sleep(1 * 1000);
                        swipe.setRefreshing(false);
                    } catch(Exception e) {}
                })).start();
            }
        });

        // 滚动监听
//        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(RecyclerView view , int newState)
//            {
//                Tool.log("状态名称：" + newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView view , int dx , int dy)
//            {
//                Tool.log("addOnScrollListener 水平滚动量：" + dx + "; 垂直滚动量：" + dy);
//            }
//        });

        // 设置滚动变化监听器
//        recycler.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view , int x , int y , int oldX , int oldY)
//            {
//                // 这个有兼容性问题
//                // 只能运行在 api level < 23 或 < android 6
//                // 获取滚动条的最大滚动高度
////                int measuredH = recycler.getMeasuredHeight();
////                int conH = recycler.getHeight();
//
//                // 事实证明
//                // setOnScrollChangeListener 对
//                // recyclerview 无效
//                int swipeH = swipe.getHeight();
//                int recyclerViewH = recycler.getHeight();
//
//                Tool.log("setOnScrollChangeListener: y = " + y + "; swipeH = " + swipeH + "; recyclerViewH: " + recyclerViewH);
//            }
//        });

        SwiperActivity self = this;

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView view , int state)
            {
                // 0-停止滚动
                // 1-滚动中
//                Tool.log("addOnScrollListener: 滚动状态变更：" + state);
            }

            @Override
            public void onScrolled(RecyclerView view , int dx , int dy)
            {
                // 这个获取到的是当前的滚动量
                // 如果获取累计的滚动量
                int measuredHeight = recycler.getMeasuredHeight();
                int height = recycler.getHeight();
                Rect rect = new Rect();
                // 获取视图的可见区域
                recycler.getWindowVisibleDisplayFrame(rect);

                // getMeasuredHeight 或 getHeight 获取到的都是可视区域的高度
                // 通过 view.getWindowVisibleDisplayFrame(rect) 方法可以获取到当前视图的可见区域范围
                Tool.log(JSON.toJSONString(rect) + "; rect height: " + Tool.pxToDp(self , rect.bottom) + "; height: " + Tool.pxToDp(self,recycler.getHeight()) + "; measuredHeight: " + Tool.pxToDp(self , recycler.getMeasuredHeight()));
//                Tool.log("addScrollListener: dx: " + dx + "; dy: " + dy + "; measuredH: " + measuredHeight + "; height: " + height);
            }
        });

        Tool.log("recycler 添加监听成功");

        (new Thread(() -> {
            try {
                Thread.sleep(5 * 1000);
                data.add("one");
                data.add("two");
                data.add("three");
                // 更新适配器
                adapter.notifyDataSetChanged();
                // 这边有使用 Log.d() 相关的方法
                Tool.log("新增了完毕");
            } catch(Exception e) {
                Tool.log("异常：" + e.getMessage());
            }
        })).start();

        //
        ScrollView scrollView = this.findViewById(R.id.scroll_view);

        // 变化
        scrollView.setOnScrollChangeWithSumListener(new ScrollView.OnScrollViewListener() {
            @Override
            public void onScrollChanged(ScrollView view , int x , int y)
            {
                int clientH = scrollView.getHeight();
                Rect rect = new Rect();
                // 设置视图尺寸
                scrollView.getDisplay().getRectSize(rect);
                // getWindowVisibleRect
//                Tool.log("left: " + rect.left + "; top: " + rect.top + "; right: " + rect.right + "; bottom: " + rect.bottom);
                int realH = rect.bottom - rect.top;
                int scrollH = realH - clientH;
                // 这个东西获取到的高度是错误的！不正确的！
                Rect rect1 = new Rect();
                scrollView.getWindowVisibleDisplayFrame(rect1);
                int realH1 = rect1.bottom - rect1.top;


//                Tool.log("垂直滚动距离：" + y + "; 水平滚动距离：" + x + "; 最大滚动距离：" + scrollH + "; clientH: " + clientH + "; measuredH: " + scrollView.getChildAt(0).getMeasuredHeight());
                Tool.log("垂直滚动距离：" + y + "; 水平滚动距离：" + x + "; 最大滚动距离：" + (realH1 - clientH) + "; clientH: " + clientH + "; measuredH: " + scrollView.getChildAt(0).getHeight());
            }
        });
    }
}
