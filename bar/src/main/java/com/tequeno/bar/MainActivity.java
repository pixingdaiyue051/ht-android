package com.tequeno.bar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.broadcast.BroadcastActivity;
import com.tequeno.bar.provider.ExternalFileActivity;
import com.tequeno.bar.fragment.FragmentActivity;
import com.tequeno.bar.glide.GlideActivity;
import com.tequeno.bar.listview.ListViewActivity;
import com.tequeno.bar.permission.PermissionActivity;
import com.tequeno.bar.service.ServiceActivity;
import com.tequeno.bar.sp.SharedPrefsActivity;
import com.tequeno.bar.sqlite.SQLiteDbActivity;
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
        Button btnBroadcast = findViewById(R.id.btn_broadcast);
        Button btnGlide = findViewById(R.id.btn_glide);
        btn1.setOnClickListener(this::intent1);
        btnMyIntent.setOnClickListener(this::myIntent);
        btnListView.setOnClickListener(this::listView);
        btnViewPager.setOnClickListener(this::viewPager);
        btnFg.setOnClickListener(this::fg);
        btnS.setOnClickListener(this::service);
        btnBroadcast.setOnClickListener(this::broadcast);
        btnGlide.setOnClickListener(this::glide);

        Button btnPrefs = findViewById(R.id.btn_shared_prefs);
        Button btnSql = findViewById(R.id.btn_sqlite);
        Button btnFile = findViewById(R.id.btn_external_file);
        Button btnPermission = findViewById(R.id.btn_permission);
        btnPrefs.setOnClickListener(this::prefs);
        btnSql.setOnClickListener(this::sql);
        btnFile.setOnClickListener(this::file);
        btnPermission.setOnClickListener(this::permission);
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

    private void broadcast(View view) {
        Intent intent = new Intent(this, BroadcastActivity.class);
        startActivity(intent);
    }

    private void glide(View view) {
        Intent intent = new Intent(this, GlideActivity.class);
        startActivity(intent);
    }

    private void prefs(View view) {
        Intent intent = new Intent(this, SharedPrefsActivity.class);
        startActivity(intent);
    }

    private void sql(View view) {
        Intent intent = new Intent(this, SQLiteDbActivity.class);
        startActivity(intent);
    }

    private void file(View view) {
        Intent intent = new Intent(this, ExternalFileActivity.class);
        startActivity(intent);
    }

    private void permission(View view) {
        Intent intent = new Intent(this, PermissionActivity.class);
        startActivity(intent);
    }

}