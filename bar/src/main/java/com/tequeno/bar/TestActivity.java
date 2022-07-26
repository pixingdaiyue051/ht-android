package com.tequeno.bar;

import android.annotation.SuppressLint;
import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    private final static String TAG = "TestActivity";
    private final int what = 1;
    private Handler handler;
    private HandlerThread thread;
    private ActionCloseReceiver closeReceiver;
    private Handler h;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        // 设置屏幕固定方向 也可以在清单文件中设置 screenOrientation
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button btnStart = findViewById(R.id.btn_start);
        Button btnStop = findViewById(R.id.btn_stop);
        Button btnCs = findViewById(R.id.btn_cs);

        btnStart.setOnClickListener(this::start);
        btnStop.setOnClickListener(this::stop);
        btnCs.setOnClickListener(this::cs);

        // TODO 待测试
        IntentFilter filterClose = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS); // 监听系统广播
        closeReceiver = new ActionCloseReceiver();
        registerReceiver(closeReceiver, filterClose);
    }

    private void start(View view) {
        thread = new HandlerThread(TAG);
        thread.start();
        handler = new Handler(thread.getLooper(), msg -> {
            Log.d(TAG, "handler: " + System.currentTimeMillis());
            if (msg.what == what) {
                testHandlerThread();
            }
            return true;
        });
//        testHandlerThread();

        testThread();
    }

    private void stop(View view) {
        handler.removeMessages(what);
        handler.removeMessages(0);
    }

    private void cs(View view) {
        Log.d(TAG, "cs isAlive: " + thread.isAlive());
        Log.d(TAG, "cs1: " + handler.hasMessages(what));
        Log.d(TAG, "cs0: " + handler.hasMessages(0));

        Log.d(TAG, "cs h0: " + h.hasMessages(0));
    }

    /**
     * 使用全局定义的 HandlerThread 在处理完任务之后需要自己手动结束线程
     */
    private void testHandlerThread() {
//        Message msg = Message.obtain();
//        msg.what = what;
//        handler.sendMessageDelayed(msg, 3000L);
        handler.postDelayed(() -> {
            Log.d(TAG, "handler testHandlerThread: " + System.currentTimeMillis());
            testHandlerThread();
        }, 5000L);
    }

    /**
     * 线程池任务调度 当所有的任务都结束 线程被挂起
     */
    private void testThread() {
        MyApplication.getInstance().asyncTask(() -> {
            Log.d(TAG, "testThread: ");
            Looper.prepare();
            h = new Handler();
//            h.post(() -> Log.d(TAG, "testThread1: "));
        });
    }


    private class ActionCloseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ActionCloseReceiver", "onReceive: ");
            if (null == intent) {
                return;
            }
            String reason = intent.getStringExtra("reason");
            Log.d("ActionCloseReceiver", "ACTION_CLOSE_SYSTEM_DIALOGS: " + reason);

            if (isInPictureInPictureMode()) {
                return;
            }

            if ("homekey".equals(reason) || "recentapps".equals(reason)) {
                PictureInPictureParams.Builder pipBuilder = new PictureInPictureParams.Builder();
                // 设置画幅
                pipBuilder.setAspectRatio(new Rational(2, 1));
                enterPictureInPictureMode(pipBuilder.build());
            }
        }
    }

    /**
     * 画中画模式切换时触发
     *
     * @param isInPictureInPictureMode
     */
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        Log.d(TAG, "onPictureInPictureModeChanged: " + isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            Log.d(TAG, "进入画中画模式");
        } else {
            Log.d(TAG, "退出画中画模式");
        }
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: " + newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG, "onConfigurationChanged: 竖屏");
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "onConfigurationChanged: 横屏");
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        // 这一行非常关键 如果thread 不是后台持续应用 应该在activity销毁时一并销毁thread
        if (null != thread && thread.isAlive()) {
            thread.quitSafely();
        }
        unregisterReceiver(closeReceiver);
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }
}