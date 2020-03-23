package com.test.test;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.test.test.lib.Tool;

import androidx.appcompat.app.AppCompatActivity;

public class StyleActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.style);

        this.run();
    }

    // 开始进度条动画
    private void startProgress()
    {
        Button button = this.findViewById(R.id.start_animate);
        ProgressBar progressBar = this.findViewById(R.id.progress_bar_horizontal);
        Context self = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int minProgress = 0;
                int maxProgress = 100;

                (new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        int value = progressBar.getProgress();
                        while (true)
                        {
                            if (value < minProgress || value > maxProgress) {
                                Looper.prepare();
                                Toast.makeText(self , "进度条已经加载完毕" , Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                break;
                            }
                            Tool.log("当前更新到的值：" + value);
                            progressBar.setProgress(value++);
                            try {
                                Thread.sleep(50);
                            } catch(Exception e) {
                                Tool.log("发生异常：" + e.getMessage());
                            }
                        }
                    }
                })).start();
            }
        });
    }

    private void startDateSelector()
    {
        CalendarView calendarView = this.findViewById(R.id.calendar);
        TextView textView = this.findViewById(R.id.selectText);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView , int year , int month , int dayOfMonth)
            {
                /**
                 * 注意 month ，是从 0 开始的， 0-11
                 */
                textView.setText("year: " + year + "; month: " + (month + 1) + "; day: " + dayOfMonth);
            }
        });


    }

    public void startRatingBar()
    {
        Button btn = this.findViewById(R.id.rating_bar_btn);
        RatingBar ratingBar = this.findViewById(R.id.rating_bar);

        StyleActivity self = this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Tool.tip(self , "当前设置的评分：" + ratingBar.getRating());
            }
        });
    }

    private void startSeekBar()
    {
        Button seekbar1= this.findViewById(R.id.seek_bar_btn_1);
        Button seekbar2= this.findViewById(R.id.seek_bar_btn_2);
        SeekBar seekbarWithLine = this.findViewById(R.id.seek_bar_with_line);
        SeekBar seekbarNoLine = this.findViewById(R.id.seek_bar_no_line);

        StyleActivity self = this;

        seekbar1.setOnClickListener((View view) -> {
            // 提示内容
            Tool.tip(this , String.valueOf(seekbarNoLine.getProgress()));
        });

        seekbar2.setOnClickListener((View view) -> {
            // 提示内容
            Tool.tip(this , String.valueOf(seekbarWithLine.getProgress()));
        });

        seekbarNoLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // 进度条变化的时候
            @Override
            public void onProgressChanged(SeekBar seekBar , int progress , boolean fromUser)
            {
                Tool.log("发生变化，当前的进度：：" + progress);
            }

            // 当用户按下的时候触发
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                Tool.log("开始按下");
            }

            // 当用户停止按下的时候触发
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                Tool.log("停止按下");
            }
        });

    }

    // 获取用户输入
    public void getUserInput(View view)
    {
        SearchView searchView = this.findViewById(R.id.search);
        CharSequence value = searchView.getQuery();
        Tool.tip(this , "用户搜索的值：" + value);
    }

    public void run()
    {
        this.startProgress();
        this.startDateSelector();
        this.startRatingBar();
        this.startSeekBar();
    }
}
