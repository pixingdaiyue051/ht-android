package com.tequeno.app.okhttp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.app.MyApplication;
import com.tequeno.app.R;
import com.tequeno.app.login.LoginResDto;
import com.tequeno.app.login.PageResDto;
import com.tequeno.app.login.UserResDto;

/**
 * 发送http请求必须是非主线程 修改ui必须使用主线程
 * 因此不能在异步的任务里处理对ui的相关修改  需要在ui线程中提前定义一个handler
 */
public class HttpActivity extends AppCompatActivity {

    private final static String TAG = "HttpActivity";
    private HttpClientWrapper http;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        tv = findViewById(R.id.h_tv);
        Button btnHttp = findViewById(R.id.btn_http);
        Button btnHttps = findViewById(R.id.btn_https);
        Button btnCs = findViewById(R.id.btn_cs);

        btnHttp.setOnClickListener(this::http);
        btnHttps.setOnClickListener(this::https);
        btnCs.setOnClickListener(this::cs);

        http = HttpClientWrapper.getInstance();
    }

    private void http(View view) {
        String url = "https://qinshitong.work/middle/user/login";
        String url1 = "https://qinshitong.work/middle/user/list";

        http.<LoginResDto>postAsync(url, "{\"password\": \"123456\",\"username\": \"jh\"}", LoginResDto.TAG, dto -> {
            http.header("sign", dto.sign);
            http.<PageResDto<UserResDto>>post(url1, "{}", UserResDto.TAG_PAGE, pageDto -> {
                Log.d(TAG, "http: " + pageDto.count);
            });
            MyApplication.getInstance().run(() -> tv.setText(dto.sign));
        });
    }

    private void cs(View view) {
        String url = "http://qinshitong.work:8888/viva/okhttp_cs";

        http.<String>postAsync(url, "{\"password\": \"123456\",\"username\": \"jh\"}", ResponseWrapper.TAG_1, dto -> {
            Log.d(TAG, "cs: " + dto);
            MyApplication.getInstance().run(() -> tv.setText(dto));
        });
    }

    private void https(View view) {
        String url = "https://qinshitong.work/v2/demo/log";
        http.<Boolean>postAsync(url, "", ResponseWrapper.TAG, msg -> {
            Log.d(TAG, "prod: " + msg);
            MyApplication.getInstance().run(() -> tv.setText(String.valueOf(msg)));
        });
    }
}