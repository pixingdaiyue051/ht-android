package com.tequeno.app.map;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.tequeno.app.MyApplication;

@Deprecated
public class MapLocationService extends Service {

    private final static String TAG = "MapLocationService";
    private Handler handler;
    private HandlerThread thread;
    private AMapLocationClient mLocationClient;

    public MapLocationService() {
        Log.d(TAG, TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        thread = new HandlerThread(TAG);
        thread.start();
        handler = new Handler(thread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        handler.post(() -> {
//在LocationService中启动定位
            mLocationClient = new AMapLocationClient(this.getApplicationContext());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
// 使用连续定位
            mLocationOption.setOnceLocation(false);
// 定位时间间隔
            mLocationOption.setInterval(10000L);
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.setLocationListener(aMapLocation -> Log.d(TAG, "locating: " + System.currentTimeMillis() + "--" + aMapLocation.getLatitude()));
            mLocationClient.startLocation();
        });
        MyApplication.getInstance().isLocateServiceRunning = true;
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        mLocationClient.stopLocation();
        thread.quitSafely();
        MyApplication.getInstance().isLocateServiceRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}