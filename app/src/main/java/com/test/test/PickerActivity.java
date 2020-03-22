package com.test.test;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import com.test.test.lib.Tool;

public class PickerActivity extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        this.setContentView(R.layout.picker);
        super.onCreate(bundle);
    }

    public void showTimePicker(View v)
    {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean is24HourView = true;
        TimePickerDialog picker = new TimePickerDialog(this , new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view , int hourOfDay , int minute)
            {
                Tool.snackbar(view , "当前选择的时间：" + hourOfDay + ":" + minute);
                System.out.println("时间改变时触发...");
            }
        } , hour , minute , true);

        picker.show();
    }

    public void showDatePicker(View v)
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog picker = new DatePickerDialog(this , new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view , int year , int month , int date)
            {
                Tool.snackbar(view , "当前选择的日期：" + year + "-" + month + "-" + date);
            }
        } , year , month , date);
        picker.show();
    }
}
