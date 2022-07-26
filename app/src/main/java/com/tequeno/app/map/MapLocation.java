package com.tequeno.app.map;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.tequeno.app.MainUtil;
import com.tequeno.app.R;
import com.tequeno.app.okhttp.GsonWrapper;
import com.tequeno.app.okhttp.HttpClientWrapper;
import com.tequeno.app.okhttp.ResponseWrapper;

import java.util.HashMap;

public class MapLocation {

    private final static String TAG = "MapLocation";

    // *** alarm receiver service实现地图定位 已废弃
    @Deprecated
    private LocalBroadcastManager broadcastManager;
    @Deprecated
    private MapLocationReceiver mapLocationReceiver;
    @Deprecated
    private PendingIntent pendingIntent;
    @Deprecated
    private AlarmManager alarmManager;
    @Deprecated
    public boolean isLocateServiceRunning;

    // *** 异步守护线程实现持续定位
    private AMapLocationClient mapLocationClient;
    private HandlerThread mapLocationThread;
    private long locateErrorCount;
    private Context ctx;
    private static MapLocation m;

    public static MapLocation getInstance() {
        if (null == m) {
            m = new MapLocation();
        }
        return m;
    }

    public MapLocation setContext(Context ctx) {
        this.ctx = ctx;
        return m;
    }

    /**
     * 开启系统服务 持续唤醒 map service
     * 由于系统唤醒服务 需要设置时间间隔>1min的操作 本身并不适合持续定位作业(时间间隔3-5s的服务)
     * 应该改成守护线程作业  使用 HandlerThread Handler
     */
    @Deprecated
    public void alarm() {
        String alarmLocation = ctx.getString(R.string.expose_map_receiver);

        IntentFilter filter = new IntentFilter(alarmLocation);
        mapLocationReceiver = new MapLocationReceiver();
        broadcastManager = LocalBroadcastManager.getInstance(ctx);
        broadcastManager.registerReceiver(mapLocationReceiver, filter);

        Intent intent = new Intent(alarmLocation);
        broadcastManager.sendBroadcast(intent);

        pendingIntent = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    /**
     * 开启后台定位
     */
    public void startLocate() {

//        mapLocationIntent = new Intent(mApp, MapLocationService.class);
//        startService(mapLocationIntent);

        if ((null != mapLocationThread && mapLocationThread.isAlive()) || null != mapLocationClient) {
            return;
        }

        String target = MainUtil.day();
        String mapThreadName = "thread-map-location-0";
        mapLocationThread = new HandlerThread(mapThreadName);
        mapLocationThread.setDaemon(true);
        mapLocationThread.start();
        Handler handler = new Handler(mapLocationThread.getLooper());
        handler.post(() -> {
            mapLocationClient = new AMapLocationClient(ctx);
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
// 使用连续定位
            mLocationOption.setOnceLocation(false);
// 定位时间间隔
            long interval = 3000L;
            mLocationOption.setInterval(interval);
            mapLocationClient.setLocationOption(mLocationOption);
            Map map = new Map();
            map.target = target;
            mapLocationClient.setLocationListener(aMapLocation -> {
                try {
                    Log.d(TAG, "locating: " + System.currentTimeMillis() + "--" + aMapLocation);
                    if (null == aMapLocation) {
                        throw new RuntimeException("aMapLocation is null");
                    }
                    int errorCode = aMapLocation.getErrorCode();
                    if (0 != errorCode) {
                        throw new RuntimeException("locating error " + aMapLocation.getErrorInfo());
                    }
                    double longitude = aMapLocation.getLongitude();
                    double latitude = aMapLocation.getLatitude();
                    if (0.0D == longitude || 0.0D == latitude) {
                        throw new RuntimeException("locating error 0.0");
                    }
                    map.longitude = longitude;
                    map.latitude = latitude;
                    MapOpenHelper.getInstance().insert(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "locationListener: ", e);
                    String url = "http://qinshitong.work:8888/viva/okhttp_cs";
                    HttpClientWrapper.getInstance().<String>post(url, e.getMessage(), ResponseWrapper.TAG_1, null);
                    if (locateErrorCount++ < 10) {
                        mapLocationClient.stopLocation();
                        handler.postDelayed(() -> mapLocationClient.startLocation(), interval);
                    } else {
                        stopLocate();
                    }
                }
            });
            mapLocationClient.startLocation();
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
        if (null != mapLocationClient) {
            mapLocationClient.stopLocation();
            //停止定位后，本地定位服务并不会被销毁 需要再调用 onDestroy
            mapLocationClient.onDestroy();
            mapLocationClient = null;
        }
        if (null != mapLocationThread && mapLocationThread.isAlive()) {
            mapLocationThread.quitSafely();
            mapLocationThread = null;
        }
        MapOpenHelper.getInstance().closeWdb();
        locateErrorCount = 0;
    }

    public void status() {
        boolean alive = mapLocationThread.isAlive();
        boolean daemon = mapLocationThread.isDaemon();
        boolean interrupted = mapLocationThread.isInterrupted();
        boolean started = mapLocationClient.isStarted();
        AMapLocation location = mapLocationClient.getLastKnownLocation();
        String deviceId = AMapLocationClient.getDeviceId(ctx);
        HashMap<String, Object> map = new HashMap<>();
        map.put("alive", alive);
        map.put("daemon", daemon);
        map.put("interrupted", interrupted);
        map.put("started", started);
        map.put("deviceId", deviceId);
        map.put("longitude", location.getLongitude());
        map.put("latitude", location.getLatitude());
        MainUtil.toastLong(GsonWrapper.getInstance().toJson(map));
    }
}
