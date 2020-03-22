package com.test.test;

//import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AppActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.app);

        Toolbar toolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // 要添加应用栏必须实现该方法
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.one , menu);
        if (true) {
            menu.add(Menu.NONE , Menu.FIRST , Menu.NONE , "share");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean superHandleRes = super.onOptionsItemSelected(menuItem);
        Intent intent;
        switch (menuItem.getItemId())
        {
            case R.id.menu_1:
                intent = new Intent(this , MainActivity.class);
                this.startActivity(intent);
                break;
            case R.id.menu_2:
                intent = new Intent(this , PickerActivity.class);
                this.startActivity(intent);
                break;
        }
        return superHandleRes;
    }

    public void openNewActivity(View v)
    {
        Intent intent = new Intent(this , ThirdFloorActivity.class);
        this.startActivity(intent);
    }
}
