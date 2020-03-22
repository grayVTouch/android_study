package com.test.test;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.image);

    }

    public void addImage(View v)
    {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.logo);
        imageView.setContentDescription("资源文件描述");
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        LinearLayout linearLayout = this.findViewById(R.id.layout);
        linearLayout.addView(imageView);
    }

    public void imageTransition(View v)
    {
        TransitionDrawable transition = (TransitionDrawable) this.getDrawable(R.drawable.expand_collapse);
        ImageView imageView = this.findViewById(R.id.transition_image);
        imageView.setImageDrawable(transition);
        imageView.setContentDescription("带有过度动画的图片");
        // 单位: ms
        transition.startTransition(5 * 1000);
    }

    public void addImageEvent(View v)
    {
        ImageView imageView = this.findViewById(R.id.image);

        // 添加图片
        Glide.with(this)
                .load(this.getDrawable(R.drawable.logo))
                .into(imageView);
    }
}
