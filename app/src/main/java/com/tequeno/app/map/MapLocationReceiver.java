package com.tequeno.app.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tequeno.app.R;

@Deprecated
public class MapLocationReceiver extends BroadcastReceiver {

    private final static String TAG = "MapLocationReceiver";
    private MapLocation mapLocation;

    /**
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + context + intent);
        String alarmLocation = context.getString(R.string.expose_map_receiver);

        if (!alarmLocation.equals(intent.getAction())) {
            return;
        }
        if (null == mapLocation) {
            mapLocation = new MapLocation();
        }
        if (!mapLocation.isLocateServiceRunning) {
            mapLocation.startLocate();
        }
    }
}