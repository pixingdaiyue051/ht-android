package com.tequeno.app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.tequeno.app.map.Map;
import com.tequeno.app.map.MapLocationReceiver;
import com.tequeno.app.map.MapOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    private final static String TAG = "MyApplication";
    public final static String DB_NAME = "tequeno.db";

    private static MyApplication mApp;
    private Intent mapLocationIntent;
    private LocalBroadcastManager broadcastManager;
    private MapLocationReceiver mapLocationReceiver;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    public boolean isLocateServiceRunning;
    private AMapLocationClient mLocationClient;
    private HandlerThread mapThread;

    public static MyApplication getInstance() {
        return mApp;
    }

    private ExecutorService mThreadPool;

    /**
     * app启动时调用
     */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        mApp = this;
        mThreadPool = Executors.newCachedThreadPool();
    }

    public void asyncTask(Runnable r) {
        mThreadPool.execute(r);
    }

    /**
     * 开启系统服务 持续唤醒 map service
     * 由于系统唤醒服务 需要设置时间间隔>1min的操作 本身并不适合持续定位作业(时间间隔3-5s的服务)
     * 应该改成守护线程作业  使用 HandlerThread Handler
     */
    @Deprecated
    public void alarm() {
        String alarmLocation = getString(R.string.expose_map_receiver);

        IntentFilter filter = new IntentFilter(alarmLocation);
        mapLocationReceiver = new MapLocationReceiver();
        broadcastManager = LocalBroadcastManager.getInstance(mApp);
        broadcastManager.registerReceiver(mapLocationReceiver, filter);

        Intent intent = new Intent(alarmLocation);
        broadcastManager.sendBroadcast(intent);

        pendingIntent = PendingIntent.getBroadcast(mApp, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    /**
     * 开启后台定位
     */
    public void startLocate() {
//        mapLocationIntent = new Intent(mApp, MapLocationService.class);
//        startService(mapLocationIntent);

        String target = MainUtil.day();
        String mapThreadName = "thread-map-location-1";
        mapThread = new HandlerThread(mapThreadName);
        mapThread.setDaemon(true);
        mapThread.start();
        Handler handler = new Handler(mapThread.getLooper());
        handler.post(() -> {
            mLocationClient = new AMapLocationClient(mApp);
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
// 使用连续定位
            mLocationOption.setOnceLocation(false);
// 定位时间间隔
            mLocationOption.setInterval(3000L);
            mLocationClient.setLocationOption(mLocationOption);
            Map map = new Map();
            map.target = target;
            mLocationClient.setLocationListener(aMapLocation -> {
                Log.d(TAG, "locating: " + System.currentTimeMillis() + "--" + aMapLocation);
                map.longitude = aMapLocation.getLongitude();
                map.latitude = aMapLocation.getLatitude();
                MapOpenHelper.getInstance(mApp).insert(map);
            });
            mLocationClient.startLocation();
        });
    }

    /**
     * 停止后台定位 关闭定时任务
     */
    public void stopLocate() {
//        stopService(mapLocationIntent);
//        alarmManager.cancel(pendingIntent);
//        broadcastManager.unregisterReceiver(mapLocationReceiver);
//        isLocateServiceRunning = false;
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
        }
        if (null != mapThread && mapThread.isAlive()) {
            mapThread.quitSafely();
        }
        MapOpenHelper.getInstance(mApp).closeWdb();
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