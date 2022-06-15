package com.tequeno.bar.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    private final static String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + context + intent);

        // 设置震动时间和震动强度
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        VibrationEffect vibe = VibrationEffect.createOneShot(1000L, 2);
//        VibrationEffect vibe = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK);
        vibrator.vibrate(vibe);
    }
}