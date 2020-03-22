package com.test.test;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.test.test.lib.Tool;

public class SpinnerActivity extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        this.setContentView(R.layout.spinner);
        super.onCreate(bundle);

        // 通过代码来填充 下拉列表选项
        Spinner one = this.findViewById(R.id.one);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this , R.array.list , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        one.setAdapter(adapter);

        // 添加下拉选项选择事件
        Spinner two = this.findViewById(R.id.two);
        two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent , View v , int pos , long id)
            {
                String val = (String) parent.getItemAtPosition(pos);
                if (val.equals("请选择...")) {
                    return ;
                }
                Tool.snackbar(v , "你选中了：" + val);
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                Tool.snackbar(two , "你什么也没有选择");
            }
        });
    }
}
