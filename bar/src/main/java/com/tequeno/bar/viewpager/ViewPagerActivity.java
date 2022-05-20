package com.tequeno.bar.viewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tequeno.bar.R;

import java.util.Arrays;

public class ViewPagerActivity extends AppCompatActivity {

    private final static String TAG = "ViewPagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ViewPager viewPager = findViewById(R.id.vp);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.view_pager_1, null);
        View view2 = inflater.inflate(R.layout.view_pager_2, null);
        View view3 = inflater.inflate(R.layout.view_pager_3, null);

        MyAdapter myAdapter = new MyAdapter(Arrays.asList(view1, view2, view3), this);
        viewPager.setAdapter(myAdapter);

    }
}