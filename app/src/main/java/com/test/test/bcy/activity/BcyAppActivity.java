package com.test.test.bcy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.test.test.R;
import com.test.test.lib.Tool;

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
    }
}
