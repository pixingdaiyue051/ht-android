package com.tequeno.app.ndk;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.app.R;

public class NdkActivity extends AppCompatActivity {

    private final static String TAG = "NdkActivity";

    private native void testInC();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        // 设置页面内的布局文件
        setContentView(R.layout.activity_ndk);

        Button btnNdk = findViewById(R.id.btn_ndk_cs);
        btnNdk.setOnClickListener(v-> testInC());
    }
}
