package com.tequeno.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 继承 AppCompatActivity 可以自动解决一些兼容问题
 */
public class LoginOkActivity extends AppCompatActivity {

    private final static String TAG = "LoginOkActivity";

    /**
     * 组件加载时首先被调用的方法
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginok);

        Button btnLogin = findViewById(R.id.btn_logout);

        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            // 退出登录之后 清空栈再跳转 重新回到登录页面
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}