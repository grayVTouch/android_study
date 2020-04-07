package com.test.test;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.test.test.lib.Tool;

import java.util.HashMap;

public class AttrAnimationActivity extends AppCompatActivity
{
    private String one;
    private String two;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.attr_animation);
        this.run();
    }

    private void startValueAnimator()
    {
        final AttrAnimationActivity self = this;

        ImageView imageView = this.findViewById(R.id.image);
        Button startBtn = this.findViewById(R.id.start_btn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (self.one == null) {
                    self.one = "show";
                }
                float value = imageView.getAlpha();
                if (self.one.equals("show")) {
                    self.one = "hide";
                    ValueAnimator hideAnimator = ValueAnimator.ofFloat(value, 0);
                    hideAnimator.setDuration(1000);
                    hideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            float value = (float) animator.getAnimatedValue();
                            imageView.setAlpha(value);
                        }
                    });
                    hideAnimator.start();
                    return;
                }
                self.one = "show";
                ValueAnimator showAnimator = ValueAnimator.ofFloat(value , 1);
                showAnimator.setDuration(1000);
                showAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator)
                    {
                        float value = (float) animator.getAnimatedValue();
                        imageView.setAlpha(value);
                    }
                });
                showAnimator.start();
            }
        });
    }

    private void startObjectAnimator()
    {
        final AttrAnimationActivity self = this;
        ImageView imageView = this.findViewById(R.id.image);
        Button objAnimator = this.findViewById(R.id.obj_animator);
        objAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (self.two == null) {
                    self.two = "move";
                }
                float value = imageView.getTranslationY();
                if (self.two == "move") {
                    // 垂直平移 50dp
                    self.two = "origin";
                    ObjectAnimator animator = ObjectAnimator.ofFloat(imageView , "translationY" , 500);
                    animator.setDuration(1000);
                    animator.start();
                    return ;
                }
                // 垂直平移 50dp
                self.two = "move";
                ObjectAnimator animator = ObjectAnimator.ofFloat(imageView , "translationY" , 0);
                animator.setDuration(1000);
                animator.start();
            }
        });
    }

    private void startAnimatorManager()
    {
        Button btn = this.findViewById(R.id.animator_manager);
        ImageView image = this.findViewById(R.id.image);

        ObjectAnimator showForAlpha = ObjectAnimator.ofFloat(image , "alpha" , 1);
        ObjectAnimator hideForAlpha = ObjectAnimator.ofFloat(image , "alpha" , 0);
        ObjectAnimator down = ObjectAnimator.ofFloat(image , "translationY" , 500);
        ObjectAnimator up = ObjectAnimator.ofFloat(image , "translationY" , 0);

        showForAlpha.setDuration((long) (0.5 * 1000));
        hideForAlpha.setDuration(1 * 1000);
        down.setDuration((long) (1.5 * 1000));
        up.setDuration(2 * 1000);

        HashMap<String,String> map = new HashMap<>();
        map.put("status" , "show");

        btn.setOnClickListener((View view) -> {
            AnimatorSet set = new AnimatorSet();
            if (map.get("status") == "show") {
                map.put("status" , "hide");
                set.play(hideForAlpha).with(down);
                set.start();
                return ;
            }
            map.put("status" , "show");
            // 动画播放顺序
            set.play(showForAlpha).with(up);
            // 链式动画开始运行
            set.start();
        });

        // 监听动画过程中的重要事件
        down.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void startLayoutAnimation()
    {
        Button btn = this.findViewById(R.id.layout_animation);
        LinearLayout layout = this.findViewById(R.id.layout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                layout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void startXmlAnimation()
    {
        ImageView image = this.findViewById(R.id.image_animator);
        Button btn = this.findViewById(R.id.xml_animator_btn);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                AnimationDrawable animator = (AnimationDrawable) image.getBackground();
                // 开始动画
                animator.start();

            }
        });


    }

    private void run()
    {
        this.startValueAnimator();
        this.startObjectAnimator();
        this.startAnimatorManager();
        this.startLayoutAnimation();
        this.startXmlAnimation();
    }
}
