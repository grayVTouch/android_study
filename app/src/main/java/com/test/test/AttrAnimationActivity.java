package com.test.test;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.test.lib.Tool;

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

    private void run()
    {
        this.startValueAnimator();
        this.startObjectAnimator();
    }
}
