package com.tequeno.bar.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tequeno.bar.R;

public class LaunchFgActivity extends AppCompatActivity {

    private final static String TAG = "LaunchFgActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_fg);

        ViewPager viewPager = findViewById(R.id.vp);
        MyLaunchFgAdapter adapter = new MyLaunchFgAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}