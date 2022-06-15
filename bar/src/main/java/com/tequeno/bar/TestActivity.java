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
import android.util.Log;
import android.util.Rational;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    private final static String TAG = "TestActivity";
    private int what;
    private Handler handler;
    private HandlerThread thread;
    private ActionCloseReceiver closeReceiver;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        // 设置屏幕固定方向 也可以在清单文件中设置 screenOrientation
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        what = 1;

        thread = new HandlerThread(TAG);
        thread.start();
        handler = new Handler(thread.getLooper());
        test();

        // TODO 待测试
        IntentFilter filterClose = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS); // home按键 任务列表
        closeReceiver = new ActionCloseReceiver();
        registerReceiver(closeReceiver, filterClose);
    }

    private void test() {
        handler.postDelayed(() -> {
            Log.d(TAG, "handler hammer: " + System.currentTimeMillis());
            test();
        }, 5000L);
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
        // 这一行非常关键 如果thread 不是后台持续应用 应该在哎activity销毁时一并销毁thread
        thread.quitSafely();
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(closeReceiver);
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }
}