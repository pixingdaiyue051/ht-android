package com.tequeno.bar.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class MyBackReceiver extends BroadcastReceiver {

    private final static String TAG = "MyBackReceiver";

    /**
     * 该方法的处理逻辑会在主线程中执行 如果是长时间的连接操作可以改为后台线程执行
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + context + intent);

        // 设置震动时间和震动强度
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        VibrationEffect vibe = VibrationEffect.createOneShot(500L, 10);
//        VibrationEffect vibe = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK);
        vibrator.vibrate(vibe);
    }
}