package com.tequeno.app;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    private final static String TAG = "MyApplication";
    public final static String DB_NAME = "tequeno.db";

    private static MyApplication mApp;
    private ExecutorService mThreadPool;
    private Handler mHandler;

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
     * 执行一个异步任务
     *
     * @param r
     */
    public void asyncTask(Runnable r) {
        if (null == mThreadPool) {
            mThreadPool = Executors.newCachedThreadPool();
        }
        mThreadPool.execute(r);
    }

    /**
     * 往主线程队列添加任务
     *
     * @param r
     */
    public void run(Runnable r) {
        run(r, 0L);
    }

    /**
     * 往主线程队列添加任务
     *
     * @param r
     * @param delay
     */
    public void run(Runnable r, long delay) {
        if (null == mHandler) {
            mHandler = new Handler(getMainLooper());
        }
        mHandler.postDelayed(r, delay);
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

    /**
     * 打开当前应用的应用信息 以便开启权限
     */
    public void appInfo() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}