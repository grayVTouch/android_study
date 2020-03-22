package com.test.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.test.test.lib.Tool;

public class DemoActivity extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.demo);
        this.run();

    }

    public void run()
    {

//        TextView text = this.findViewById(R.id.text);
//        //
//        int width = text.getWidth();
//        int height = text.getHeight();
//
//        int measuredWidth = text.getMeasuredWidth();
//        int measuredHeight = text.getMeasuredHeight();
//
//        Tool.log("普通宽度：" + width + "；普通高度：" + height);
//        Tool.log("测量宽度：" + measuredWidth + "；测量高度：" + measuredHeight);
//
//        LinearLayout layout = this.findViewById(R.id.layout);
//        int layoutW = layout.getWidth();
//        int layoutH = layout.getHeight();
//        int measuredLayoutW = layout.getMeasuredWidth();
//        int measuredLayoutH = layout.getMeasuredHeight();
//
//        Tool.log("layout 普通宽度: " + layoutW + "; layout 普通高度：" + layoutH);
//        Tool.log("layout 测量宽度: " + measuredLayoutW + "; layout 测量高度：" + measuredLayoutH);
    }

}