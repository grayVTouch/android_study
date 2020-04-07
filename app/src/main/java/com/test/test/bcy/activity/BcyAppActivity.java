package com.test.test.bcy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.test.test.MainActivity;
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
        BcyAppActivity self = this;

        LinearLayout home = this.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(self , MainActivity.class);
                self.startActivity(intent);
            }
        });
    }
}
