package com.test.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.test.test.databinding.DataBindingBinding;
import com.test.test.lib.Tool;
import com.test.test.model.MyViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DataBindingActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        DataBindingBinding binding = DataBindingUtil.setContentView(this , R.layout.data_binding);
//        binding.test
        this.run();
    }

    public void run()
    {

    }
}
