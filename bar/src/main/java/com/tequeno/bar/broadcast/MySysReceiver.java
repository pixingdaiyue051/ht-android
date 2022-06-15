package com.tequeno.bar.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.tequeno.bar.R;

/**
 * <h3>Standard Broadcast Actions</h3>
 *
 * <p>These are the current standard actions that Intent defines for receiving
 * broadcasts (usually through {@link Context#registerReceiver} or a
 * &lt;receiver&gt; tag in a manifest).
 *
 * <ul>
 *     <li> ACTION_TIME_TICK
 *     <li> ACTION_TIME_CHANGED
 *     <li> ACTION_TIMEZONE_CHANGED
 *     <li> ACTION_BOOT_COMPLETED
 *     <li> ACTION_PACKAGE_ADDED
 *     <li> ACTION_PACKAGE_CHANGED
 *     <li> ACTION_PACKAGE_REMOVED
 *     <li> ACTION_PACKAGE_RESTARTED
 *     <li> ACTION_PACKAGE_DATA_CLEARED
 *     <li> ACTION_PACKAGES_SUSPENDED
 *     <li> ACTION_PACKAGES_UNSUSPENDED
 *     <li> ACTION_UID_REMOVED
 *     <li> ACTION_BATTERY_CHANGED
 *     <li> ACTION_POWER_CONNECTED
 *     <li> ACTION_POWER_DISCONNECTED
 *     <li> ACTION_SHUTDOWN
 * </ul>
 */
public class MySysReceiver extends BroadcastReceiver {

    private final static String TAG = "MySysReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent) {
            return;
        }
        if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
            Log.d(TAG, "系统时钟广播每分钟发送一次: " + System.currentTimeMillis());
        }
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            Log.d(TAG, "设备息屏: ");
        }
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            Log.d(TAG, "唤醒屏幕: ");
        }
        if (Intent.ACTION_DREAMING_STOPPED.equals(intent.getAction())) {
            Log.d(TAG, "ACTION_DREAMING_STOPPED: unknown");
        }
        if (Intent.ACTION_DREAMING_STARTED.equals(intent.getAction())) {
            Log.d(TAG, "ACTION_DREAMING_STARTED: unknown");
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            Log.d(TAG, "onReceive: 网络变更");
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            Log.d(TAG, "网络大类: " + networkInfo.getTypeName()); // 网络大类
            Log.d(TAG, "网络子分类: " + networkInfo.getSubtypeName()); // 网络子分类
            Log.d(TAG, "网络状况: " + networkInfo.getState().toString()); // 网络状况
            Log.d(TAG, "网络状况: " + networkInfo.getDetailedState().toString()); // 网络状况
        }
    }
}