package com.tequeno.bar.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyOrderedReceiverC extends BroadcastReceiver {

    private final static String TAG = "MyOrderedReceiverC";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + context + intent);
//        // 中断传输 不再传递
//        abortBroadcast();
    }
}