package com.tequeno.bar.service;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class MyJobService extends JobIntentService {

    private final static String TAG = "MyJobService";

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: " + intent);
        String param1 = intent.getExtras().getString("param1");
        Log.d(TAG, "onHandleWork: " + param1);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: " + isStopped());
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + isStopped());
    }
}