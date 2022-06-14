package com.tequeno.bar.service;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.R;

import java.util.List;

public class ServiceActivity extends AppCompatActivity {

    private final static String TAG = "ServiceActivity";
    private ServiceConnection conn;
    private Intent serviceIntent;

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
        // MyService
        serviceIntent = new Intent(this, MyService.class)
                .putExtra("param1", "param1");
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                Log.d(TAG, "onServiceConnected: " + name + iBinder);
//                MyBindService.MyBinder b = (MyBindService.MyBinder) iBinder;
//                b.test();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected: " + name);
            }
        };
    }

    private void startAService(View view) {
        startService(serviceIntent);
    }

    private void stopAService(View view) {
        stopService(serviceIntent);
    }

    private void bindAService(View view) {
        Intent bindServiceIntent = new Intent(this, MyBindService.class);
        bindService(serviceIntent, conn, BIND_AUTO_CREATE);
    }

    private void unbindAService(View view) {
        unbindService(conn);



        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        // 获取正在运行的服务（此处设置最多取1000个）
        List<ActivityManager.RunningServiceInfo> runningServices = manager
                .getRunningServices(1000);

    }
}