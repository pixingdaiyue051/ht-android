package com.tequeno.bar.okhttp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.R;

/**
 * 发送http请求必须是非主线程 修改ui必须使用主线程
 * 因此不能在异步的任务里处理对ui的相关修改  需要在ui线程中提前定义一个handler
 */
public class HttpActivity extends AppCompatActivity {

    private final static String TAG = "HttpActivity";
    private HttpUtil httpUtil;
    private TextView tv;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        tv = findViewById(R.id.h_tv);
        Button btnCs = findViewById(R.id.btn_cs);
        Button btnProd = findViewById(R.id.btn_prod);
        Button btnHttps = findViewById(R.id.btn_https);

        btnCs.setOnClickListener(this::cs);
        btnProd.setOnClickListener(this::prod);
        btnHttps.setOnClickListener(this::https);

        httpUtil = HttpUtil.getInstance();
        handler = new Handler();
    }

    private void cs(View view) {
        String url = "http://qinshitong.work:8082/server/demo/time";

//        httpUtil.get(url, msg -> Log.d(TAG, "ok: " + msg));

        httpUtil.post(url, msg -> {
            Log.d(TAG, "cs: " + msg);
            handler.post(() -> tv.setText(msg));
        });
    }

    private void prod(View view) {
        String url = "http://jiansuotong.top:8888/nmgjb/demo/time";
        httpUtil.get(url, msg -> {
            Log.d(TAG, "prod: " + msg);
            handler.post(() -> tv.setText(msg));
        });
    }

    private void https(View view) {
        String url = "https://jiansuotong.top/app/demo/one";
        httpUtil.get(url, msg -> {
            Log.d(TAG, "prod: " + msg);
            handler.post(() -> tv.setText(msg));
        });
    }
}