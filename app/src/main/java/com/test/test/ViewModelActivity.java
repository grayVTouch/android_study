package com.test.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.test.test.lib.Tool;
import com.test.test.model.MyViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public class ViewModelActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.view_model);

        this.run();
    }

    public void run()
    {
        Button increment = this.findViewById(R.id.increment);
        Button decrement = this.findViewById(R.id.decrement);

        EditText amount = this.findViewById(R.id.amount);
        TextView value = this.findViewById(R.id.value);

        MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        // 先给 livedata 添加观察器
        myViewModel.getCount().observe(this , new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer)
            {
                // 当值发生改变的时候，自动应用到 对应的元素里面
                value.setText(String.valueOf(integer));
            }
        });

        // 当屏幕发生旋转的时候防止 操作状态丢失
        value.setText(String.valueOf(myViewModel.getCount().getValue()));

        increment.setOnClickListener((View view) -> {
            int curAmount = Integer.valueOf(amount.getText().toString());
//            Tool.log("increment 当前设置的变化之：" + curAmount);
//            int number = myViewModel.getNumber();
//            myViewModel.setNumber(++number);
//            value.setText(String.valueOf(number));
            myViewModel.incCount(curAmount);
        });

        decrement.setOnClickListener((View view) -> {
            int curAmount = Integer.valueOf(amount.getText().toString());
            Tool.log("decrement 当前设置的变化值：" + curAmount);
//            int number = myViewModel.getNumber();
//            myViewModel.setNumber(--number);
//            value.setText(String.valueOf(number));
            myViewModel.decCount(curAmount);
        });
    }
}
