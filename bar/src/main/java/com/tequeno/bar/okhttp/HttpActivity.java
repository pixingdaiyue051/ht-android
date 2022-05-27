package com.tequeno.bar.okhttp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.R;

public class HttpActivity extends AppCompatActivity {

    private final static String TAG = "HttpActivity";
    private HttpUtil httpUtil;
    private EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        ed = findViewById(R.id.h_ed);
        Button btnOk = findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(this::ok);

        httpUtil = HttpUtil.getInstance();

    }

    private void ok(View view) {
        String url = "http://qinshitong.work:8082/admin/tenantUser/login";

//        httpUtil.get(url, msg -> Log.d(TAG, "ok: " + msg));

        httpUtil.post(url, "{\"password\":\"123456\",\"username\":\"abc\"}", msg -> {
            Log.d(TAG, "ok: " + msg);
            httpUtil.post("http://qinshitong.work:8082/admin/tenantUser/list", "", msg1 -> Log.d(TAG, "ok: " + msg1));
        });
    }
}