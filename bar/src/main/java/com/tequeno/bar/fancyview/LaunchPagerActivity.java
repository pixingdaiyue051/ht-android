package com.tequeno.bar.fancyview;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tequeno.bar.R;

public class LaunchPagerActivity extends AppCompatActivity {

    private final static String TAG = "LaunchPagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_pager);

        ViewPager viewPager = findViewById(R.id.vp);
        MyLaunchPagerAdapter launchPagerAdapter = new MyLaunchPagerAdapter(this);
        viewPager.setAdapter(launchPagerAdapter);
    }
}