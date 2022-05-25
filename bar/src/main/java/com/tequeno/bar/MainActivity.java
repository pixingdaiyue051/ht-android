package com.tequeno.bar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.fragment.FragmentActivity;
import com.tequeno.bar.listview.ListViewActivity;
import com.tequeno.bar.service.ServiceActivity;
import com.tequeno.bar.viewpager.ViewPagerActivity;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn_intent1);
        Button btnMyIntent = findViewById(R.id.btn_my_intent);
        Button btnListView = findViewById(R.id.btn_list_view);
        Button btnViewPager = findViewById(R.id.btn_view_pager);
        Button btnFg = findViewById(R.id.btn_fg);
        Button btnS = findViewById(R.id.btn_s);
        btn1.setOnClickListener(this::intent1);
        btnMyIntent.setOnClickListener(this::myIntent);
        btnListView.setOnClickListener(this::listView);
        btnViewPager.setOnClickListener(this::viewPager);
        btnFg.setOnClickListener(this::fg);
        btnS.setOnClickListener(this::service);
    }

    private void intent1(View view) {
        Intent intent = new Intent(this, Intent1Activity.class);
        startActivity(intent);
    }

    private void myIntent(View view) {
        Intent intent = new Intent(this, MyIntentActivity.class);
        startActivity(intent);
    }

    private void listView(View view) {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    private void viewPager(View view) {
        Intent intent = new Intent(this, ViewPagerActivity.class);
        startActivity(intent);
    }

    private void fg(View view) {
        Intent intent = new Intent(this, FragmentActivity.class);
        startActivity(intent);
    }

    private void service(View view) {
        Intent intent = new Intent(this, ServiceActivity.class);
        startActivity(intent);
    }
}