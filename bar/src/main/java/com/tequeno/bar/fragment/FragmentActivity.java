package com.tequeno.bar.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tequeno.bar.R;

public class FragmentActivity extends AppCompatActivity {

    private final static String TAG = "FragmentActivity";
    private Test1Fragment fragment1;
    private Test2Fragment fragment2;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Button btn1 = findViewById(R.id.btn_1);
        Button btn2 = findViewById(R.id.btn_2);
        btn1.setOnClickListener(this::btn1);
        btn2.setOnClickListener(this::btn2);
        fragment1 = new Test1Fragment();
        // 和fragment通信
        // 1.向fragment直接传递参数
        fragment1.setParam1("的风格虎骨");
        // 2.使用bundle一次传输多类型数据
        Bundle args = new Bundle();
        args.putString("param2", "你可以发给大家");
        fragment1.setArguments(args);
        // 3.使用接口
        fragment1.setCallback(() -> "均衡投放愿意i客户");

        fragment2 = new Test2Fragment();
        fragmentManager = getSupportFragmentManager();
    }

    private void btn1(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl, fragment1);
        transaction.commit();
    }

    private void btn2(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl, fragment2);
        transaction.commit();
    }
}