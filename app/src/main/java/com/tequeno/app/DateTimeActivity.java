package com.tequeno.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class DateTimeActivity extends AppCompatActivity {

    private final static String TAG = "DateTimeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime);

        TextView tv = findViewById(R.id.tv_datetime1);

        // 固定日期对话框
        DatePicker datePicker = findViewById(R.id.form_date_picker);
        Button btnFixedDateOk = findViewById(R.id.btn_fixed_date_ok);
        btnFixedDateOk.setOnClickListener(view -> {
            int year = datePicker.getYear();
            int month = datePicker.getMonth() + 1;
            int day = datePicker.getDayOfMonth();
            String text = String.format(Locale.CHINA, "选择的日期 %d-%d-%d", year, month, day);
            Log.d(TAG, "btnFixedDateOk: " + text);
            tv.setText(text);
        });
        // 固定时间选择框
        TimePicker timePicker = findViewById(R.id.form_time_picker);
        timePicker.setIs24HourView(true); // 设置24小时制
        Button btnFixedTimeOk = findViewById(R.id.btn_fixed_time_ok);
        btnFixedTimeOk.setOnClickListener(view -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String text = String.format(Locale.CHINA, "选择的时间 %d:%d", hour, minute);
            Log.d(TAG, "btnFixedTimeOk: " + text);
            tv.setText(text);
        });
        // 弹出日期选择框
        Button btnDateDialog = findViewById(R.id.btn_date_dialog);
        btnDateDialog.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this);
            datePickerDialog.setOnDateSetListener((view1, year, month, day) -> {
                String text = String.format(Locale.CHINA, "选择的日期 %d-%d-%d", year, month + 1, day);
                Log.d(TAG, "btnDateDialog: " + text);
                tv.setText(text);
            });
            datePickerDialog.show();
        });
        // 弹出时间选择框
        Button btnTimeDialog = findViewById(R.id.btn_time_dialog);
        btnTimeDialog.setOnClickListener(view -> {
            // 传入初始化的时间和24小时显示制  改成spinner选择形式 android.R.style.Theme_Holo_Light_Dialog
            Calendar instance = Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, (view12, hour, minute) -> {
                String text = String.format(Locale.CHINA, "选择的时间 %d:%d", hour, minute);
                Log.d(TAG, "btnTimeDialog: " + text);
                tv.setText(text);
            }, instance.get(Calendar.HOUR_OF_DAY), instance.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        });
    }
}