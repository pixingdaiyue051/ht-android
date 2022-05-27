package com.tequeno.bar.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.tequeno.bar.R;

public class BroadcastActivity extends AppCompatActivity {

    private final static String TAG = "BroadcastActivity";
    private MyBackReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        Button btnStatic = findViewById(R.id.btn_static_receiver);
        Button btnDynamic = findViewById(R.id.btn_dynamic_receiver);
        btnStatic.setOnClickListener(this::staticReceiver);
        btnDynamic.setOnClickListener(this::dynamicReceiver);

        // 动态注册广播
        IntentFilter filter = new IntentFilter(getString(R.string.export_name_mybackreceiver));
        br = new MyBackReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(br, filter);
    }

    private void staticReceiver(View view) {
        Intent intent = new Intent(getString(R.string.export_name_myreceiver));
//        sendBroadcast(intent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void dynamicReceiver(View view) {
        Intent intent = new Intent(getString(R.string.export_name_mybackreceiver));
//        sendBroadcast(intent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(br);
    }
}