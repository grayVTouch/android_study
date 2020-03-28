package com.test.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Gravity;
import android.view.TextureView;
import android.widget.FrameLayout;

import com.test.test.lib.Tool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CameraX extends AppCompatActivity
{

    Camera camera;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.camerax);
        this.run();
    }

    public void run()
    {
        CameraX self = this;
        TextureView texture = this.findViewById(R.id.texture);

        // 检查是否具备使用相机的权限，如果没有继续申请权限
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(this , new String[] {
                    Manifest.permission.CAMERA
            } , 0);
            Tool.tip(this , "申请相机权限");
            return ;
        }
        Tool.tip(this , "申请相机权限成功");

        // 具有权限
        // texture 质地
        // surface 表面
        TextureView view = new TextureView(this);
        view.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {

            // 当 TextureView 准备好使用的时候调用
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture , int width , int height)
            {
                self.camera = Camera.open();
                Camera.Size previewSize = self.camera.getParameters().getPreviewSize();
                texture.setLayoutParams(new FrameLayout.LayoutParams(previewSize.width , previewSize.height , Gravity.CENTER));
                try {
                    self.camera.setPreviewTexture(surfaceTexture);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                self.camera.startPreview();
                texture.setAlpha(1f);
                // 不要旋转
//                texture.setRotation(90f);
            }

            // 当 surfacetexture 缓存大小改变时调用
            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface , int width , int height)
            {

            }

            // 当 surfacetexture 准备销毁的时候调用
            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
            {
                // 停止预览
                self.camera.stopPreview();
                // 释放相机
                self.camera.release();
                return true;
            }

            // 当通过调用 SurfaceTexture#updateTextImage() 方法调用的时候
            // 触发
            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface)
            {

            }
        });

        // 设置相机视图
        this.setContentView(view);
    }

}
