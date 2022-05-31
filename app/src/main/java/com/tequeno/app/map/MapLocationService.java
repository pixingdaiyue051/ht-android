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

public class MapLocationService extends Service {

    private final static String TAG = "MapLocationService";
    private Handler handler;
    private HandlerThread thread;

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
        handler.post(() -> {
//在LocationService中启动定位
            AMapLocationClient mLocationClient = new AMapLocationClient(this.getApplicationContext());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
// 使用连续定位
            mLocationOption.setOnceLocation(false);
// 每5s定位一次
            mLocationOption.setInterval(5000L);
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.setLocationListener(aMapLocation -> {
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                Log.d(TAG, "onStartCommand: " + latitude + "-" + longitude);
            });
            mLocationClient.startLocation();
        });
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.quitSafely();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}