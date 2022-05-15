package com.tequeno.rdm;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyApplication extends Application {

    private final String TAG = this.getClass().getSimpleName();

    private static MyApplication mApp;

    public static MyApplication getInstance() {
        return mApp;
    }

    /**
     * app启动时调用
     */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        mApp = this;
    }

    /**
     * 永远不会被调用
     */
    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate: ");
        super.onTerminate();
    }

    /**
     * app配置发生变化时调用 比如 竖屏变横屏
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: ");
        super.onConfigurationChanged(newConfig);
    }
}