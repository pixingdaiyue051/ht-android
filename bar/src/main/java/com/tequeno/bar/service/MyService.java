package com.tequeno.bar.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.stream.IntStream;

/**
 * 单个线程处理:HandlerThread + Looper + Handler
 * 线程池处理:Executors.newCachedThreadPool()
 */
public class MyService extends Service {

    private final static String TAG = "MyService";
    private Handler handler;
    private HandlerThread thread;
    private String paramM;

    public MyService() {
        Log.d(TAG, TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        paramM = "M";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: " + intent + flags + startId);
        String param1 = intent.getExtras().getString("param1");
        Log.d(TAG, "onStartCommand: " + param1);
        initThread(startId);
        testThreadAlive();
        return START_NOT_STICKY;
    }

    private void initThread(int startId) {
        thread = new HandlerThread(TAG);
        thread.start();
        handler = new Handler(thread.getLooper(), msg -> {
            Log.d(TAG, "handleMessage: " + msg);
            return true;
        });

        IntStream.rangeClosed(1, 6)
                .boxed()
                .forEach(i -> {
                    Message msg = Message.obtain();
                    msg.what = startId;
                    msg.arg1 = i;
                    handler.sendMessageDelayed(msg, i * 5000L);
                });

        // 1min后自毁
//        handler.postAtTime(() -> stopSelf(startId), SystemClock.uptimeMillis() + 60000);
    }

    private void destroyThread() {
        if (null == thread) {
            return;
        }
        boolean quited = thread.quitSafely();
        Log.d(TAG, "destroyThread: " + thread);
        Log.d(TAG, "destroyThread: " + handler);
        if (quited) {
            thread = null;
            handler = null;
        }
    }

    private void testThreadAlive() {
        // 每隔5s检查是否活着 本质上还是使用了sendMessageDelayed 只不过回调方法没有配置在handler上
        handler.postDelayed(() -> {
            if (thread.isAlive()) {
                Log.d(TAG, "HandlerThread is still isAlive: " + thread);
                Log.d(TAG, "timestamp: " + System.currentTimeMillis());
                Log.d(TAG, "thread: " + thread.getId() + "," + thread.getThreadId() + "," + thread.getName());
                testThreadAlive();
            } else {
                Log.d(TAG, "HandlerThread is dead: " + thread);
            }
        }, 5000L);
    }

    /**
     * 当其他组件调用了stopService或者当前service调用了stopSelf时触发
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: " + this);
        this.destroyThread();
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public void test() {
            Log.d(TAG, "test: " + paramM);
        }
    }
}