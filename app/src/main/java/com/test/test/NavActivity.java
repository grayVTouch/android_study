package com.test.test;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

public class NavActivity extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.nav);
        this.run();
    }

    public void run()
    {
        BottomNavigationView nav = this.findViewById(R.id.nav);
        /**
         * LABEL_VISIBILITY_AUTO 菜单标签在 <=3 各的时候表现为 已标记，在 >= 4 个的时候显示为已选择
         * LABEL_VISIBILITY_LABELED 标签显示在所有的导航项目中
         * LABEL_VISIBILITY_SELECTED 标签显示在选定的导航项目上
         * LABEL_VISIBILITY_UNLABELED 标签不会显示在任何导航项上
         */
        nav.setLabelVisibilityMode(com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
    }
}
