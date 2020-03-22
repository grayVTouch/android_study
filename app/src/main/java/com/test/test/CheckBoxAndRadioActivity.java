package com.test.test;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.test.test.lib.Tool;

public class CheckBoxAndRadioActivity extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        this.setContentView(R.layout.checkbox_and_radio);
        super.onCreate(bundle);

        ToggleButton toggleButton = this.findViewById(R.id.toggle);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton btn , boolean isChecked)
            {
                if (isChecked) {
                    Tool.snackbar(btn , "切换按钮：你开启了按钮");
                    return ;
                }
                Tool.snackbar(btn , "切换按钮：你关闭了按钮");
            }
        });

        Switch switchButton = this.findViewById(R.id.switchBtn);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton btn , boolean isChecked)
            {
                if (isChecked) {
                    Tool.snackbar(btn , "切换开关：你开启了按钮");
                    return ;
                }
                Tool.snackbar(btn , "切换开关：你关闭了按钮");
            }
        });


    }

    public void checkboxClickEvent(View v)
    {
        CheckBox  checkbox = (CheckBox) v;
        if (!checkbox.isChecked()) {
            Tool.snackbar(v , "你反选了复选框：" + checkbox.getText());
            return ;
        }
        Tool.snackbar(v , "你选中了复选框：" + checkbox.getText());
    }

    public void radioClickEvent(View v)
    {
        RadioButton radio = (RadioButton) v;
        if (!radio.isChecked()) {
            Tool.snackbar(v , "你反选了单选框：" + radio.getText());
            return ;
        }
        Tool.snackbar(v , "你选中了单选框：" + radio.getText());
    }
}
