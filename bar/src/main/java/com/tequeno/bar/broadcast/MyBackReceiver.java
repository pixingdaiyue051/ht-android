package com.tequeno.bar.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyBackReceiver extends BroadcastReceiver {

    private final static String TAG = "MyBackReceiver";

    /**
     * 该方法的处理逻辑会在主线程中执行 如果是长时间的连接操作可以改为后台线程执行
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + context + intent);

        Executors.newFixedThreadPool(5);
    }
}