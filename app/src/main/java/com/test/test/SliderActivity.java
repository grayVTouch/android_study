package com.test.test;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.test.test.lib.Tool;
import com.test.test.view.MyLinearLayout;
import com.test.test.view.TopLinearLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SliderActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        Tool.log("app on create");
        super.onCreate(bundle);
        this.setContentView(R.layout.slider);
        this.run();
    }

    private int count = 1;

    public void run()
    {
        final SliderActivity self = this;
        ArrayList<ImageView> imageList = new ArrayList<>();
        int min = 1;
        int max = 5;
        for (int i = min; i <= max; ++i) {
            int resId = this.getResources().getIdentifier("image_0" + i, "id", this.getPackageName());
            ImageView imageView = this.findViewById(resId);
            imageList.add(imageView);
        }

        ConstraintLayout container = this.findViewById(R.id.container);
//        HorizontalScrollView outer = this.findViewById(R.id.slider_outer);
        LinearLayout outer = this.findViewById(R.id.slider_outer);
        LinearLayout inner = this.findViewById(R.id.slider_inner);
        Button next = this.findViewById(R.id.next);
        Button prev = this.findViewById(R.id.prev);

        container.post(new Runnable() {
            @Override
            public void run()
            {
                int singleMaxWForPx = outer.getWidth();
                int singleMaxHForPx = outer.getHeight();
                int paddingStart = outer.getPaddingStart();
                int paddingEnd = outer.getPaddingEnd();
                int paddingTop = outer.getPaddingTop();
                int paddingBottom = outer.getPaddingBottom();

                int singleWForPx = singleMaxWForPx - paddingStart - paddingEnd;
                int singleHForPx = singleMaxHForPx - paddingTop -paddingBottom;

                int singleMaxW = Tool.pxToDp(self , singleMaxWForPx);
                int singleMaxH = Tool.pxToDp(self , singleMaxHForPx);

//                Tool.log("singleMaxW: " + singleMaxW + "; singleMaxH: " + singleMaxH);
//                Tool.log("singleMaxW int: " + singleMaxWForPx + "; singleMaxH int: " + singleMaxHForPx + "; outer padding start: " + outer.getPaddingStart() + "; outer padding end: " + outer.getPaddingEnd());

//                inner.setLayoutParams(new HorizontalScrollView.LayoutParams(singleWForPx * (imageList.size() - 2) , HorizontalScrollView.LayoutParams.MATCH_PARENT));
                inner.setLayoutParams(new LinearLayout.LayoutParams(singleWForPx * imageList.size() , LinearLayout.LayoutParams.MATCH_PARENT));

                Tool.log("singleWForPx: " + singleWForPx + "; imageSize: " + imageList.size() + "; inner width: " + singleWForPx * (imageList.size() - 1));

                // 当前图片索引计数
                class Data {
                    public int maxIndex;
                    public int index;
                    public int minIndex;
                    public int minX;
                    public int maxX;
                    public boolean animationEnd = true;

                    public Data()
                    {
                        this.maxIndex = imageList.size() - 2;
                        this.minIndex = 1;
                        this.index = 1;
                        this.minX = -(imageList.size() - 1) * singleWForPx;
                        this.maxX = 0;

                    }
                }
                Data data = new Data();

                ArrayList<Integer> position = new ArrayList<>();
                for (int i = 0; i < imageList.size(); ++i)
                {
                    // 注意这边单位用的是 px
                    imageList.get(i).setLayoutParams(new LinearLayout.LayoutParams(singleWForPx , ViewGroup.LayoutParams.MATCH_PARENT));
                    position.add(-(i + 1) * singleWForPx);
                }
                // 正确显示第一个元素
                inner.setTranslationX(position.get(data.index - 1));

                next.setOnClickListener((View view) -> {
                    if (!data.animationEnd) {
                        Tool.log("next 动画进行中，不可操作");
                        return ;
                    }
                    int index = data.index + 1;
                    final int oIndex = index;
                    float endX;
                    if (index > data.maxIndex) {
                        index = data.minIndex;
                        endX = data.minX;
                    } else {
                        endX = position.get(index - 1);
                    }
                    Tool.log("更新后的索引：" + index + "; 目标 translationX: " + endX);
                    final int copyIndex = index;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(inner , "translationX" , endX);
                    animator.setDuration(1000);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator)
                        {
                            // 当动画开始的时候触发
                        }

                        @Override
                        public void onAnimationEnd(Animator animator)
                        {
                            // 当动画结束的时候触发
                            // 动画结束的时候更新当前索引
                            data.index = copyIndex;
                            if (oIndex > data.maxIndex) {
                                // 直接将当前位置指向索引为 最小的位置
                                int endX = position.get(1 - 1);
                                inner.setTranslationX(endX);
                            }
                            data.animationEnd = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator)
                        {
                            // 动画重复执行的时候触发
                        }

                        @Override
                        public void onAnimationCancel(Animator animator)
                        {
                            // 当动画被取消的时候触发
                        }
                    });
                    data.animationEnd = false;
                    animator.start();
                });

                prev.setOnClickListener((View view) -> {
                    if (!data.animationEnd) {
                        Tool.log("prev 动画进行中，不可操作");
                        return ;
                    }
                    int index = data.index - 1;
                    final int oIndex = index;
                    float endX;
                    if (index < data.minIndex) {
                        index = data.maxIndex;
                        endX = data.maxX;
                    } else {
                        endX = position.get(index - 1);
                    }
                    Tool.log("更新后的索引：" + index + "; 目标 translationX: " + endX);
                    final int copyIndex = index;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(inner , "translationX" , endX);
                    animator.setDuration(1000);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator)
                        {
                            // 当动画开始的时候触发
                        }

                        @Override
                        public void onAnimationEnd(Animator animator)
                        {
                            // 当动画结束的时候触发
                            // 动画结束的时候更新当前索引
                            data.index = copyIndex;
                            if (oIndex < data.minIndex) {
                                // 直接将当前位置指向索引为 最小的位置
                                int endX = position.get(data.maxIndex - 1);
                                inner.setTranslationX(endX);
                            }
                            data.animationEnd = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator)
                        {
                            // 动画重复执行的时候触发
                        }

                        @Override
                        public void onAnimationCancel(Animator animator)
                        {
                            // 当动画被取消的时候触发
                        }
                    });
                    data.animationEnd = false;
                    animator.start();
                });
            }
        });


    }
}
