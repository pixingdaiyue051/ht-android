package com.tequeno.bar;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    private final static String TAG = "TestActivity";
    private int what;
    private Handler handler;
    private HandlerThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        what = 1;

        thread = new HandlerThread(TAG);
        thread.start();
        handler = new Handler(thread.getLooper());
        test();
    }

    private void test() {
        handler.postDelayed(() -> {
            Log.d(TAG, "handler: hammer " + what);
            test();
        },5000L);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 这一行非常关键 如果thread 不是后台持续应用 应该在哎activity销毁时一并销毁thread
        thread.quitSafely();
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }
}