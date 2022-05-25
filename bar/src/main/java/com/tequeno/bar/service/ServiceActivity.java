package com.tequeno.bar.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.R;

public class ServiceActivity extends AppCompatActivity {

    private final static String TAG = "ServiceActivity";
    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Button btnStart = findViewById(R.id.btn_start);
        Button btnStop = findViewById(R.id.btn_stop);
        Button btnBind = findViewById(R.id.btn_bind);
        Button btnUnbind = findViewById(R.id.btn_unbind);
        btnStart.setOnClickListener(this::startAService);
        btnStop.setOnClickListener(this::stopAService);
        btnBind.setOnClickListener(this::bindAService);
        btnUnbind.setOnClickListener(this::unbindAService);
    }

    private void startAService(View view) {
        // MyService
        Intent service = new Intent(this, MyService.class)
                .putExtra("param1", "param1");
        startService(service);
    }

    private void stopAService(View view) {
        stopService(new Intent(this, MyService.class));
    }

    private void bindAService(View view) {
        Intent service = new Intent(this, MyService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected: " + name + service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected: " + name);
            }
        };
        bindService(service, conn, BIND_AUTO_CREATE);
    }

    private void unbindAService(View view) {
        unbindService(conn);
    }
}