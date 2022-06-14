package com.tequeno.bar.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 绑定方式启动service 当所有的client都断开 service自动结束(onDestroy)
 */
public class MyBindService extends Service {

    private final static String TAG = "MyBindService";

    public MyBindService() {
        Log.d(TAG, TAG);
    }

    private int paramI;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        paramI = 213532;
    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: " + paramI);
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + paramI);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: " + paramI);
        super.onRebind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: " + paramI);
        return new MyBinder();
    }

    /**
     * 通过对client(activity)提供IBinder接口访问当前service内的数据
     */
    public class MyBinder extends Binder {
        public void test() {
            Log.d(TAG, "test: " + paramI);
        }
    }
}