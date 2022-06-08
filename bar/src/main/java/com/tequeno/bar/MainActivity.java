package com.tequeno.bar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.broadcast.BroadcastActivity;
import com.tequeno.bar.fancyview.LaunchPagerActivity;
import com.tequeno.bar.fragment.FragmentActivity;
import com.tequeno.bar.fragment.LaunchFgActivity;
import com.tequeno.bar.glide.GlideActivity;
import com.tequeno.bar.fancyview.GridViewActivity;
import com.tequeno.bar.fancyview.ListViewActivity;
import com.tequeno.bar.permission.PermissionActivity;
import com.tequeno.bar.provider.ExternalFileActivity;
import com.tequeno.bar.service.ServiceActivity;
import com.tequeno.bar.sp.SharedPrefsActivity;
import com.tequeno.bar.fancyview.SpinnerActivity;
import com.tequeno.bar.sqlite.SQLiteDbActivity;
import com.tequeno.bar.fancyview.ViewPagerActivity;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn_intent1);
        btn1.setOnClickListener(this::intent1);
        Button btnMyIntent = findViewById(R.id.btn_my_intent);
        btnMyIntent.setOnClickListener(this::myIntent);
        Button btnListView = findViewById(R.id.btn_list_view);
        btnListView.setOnClickListener(this::listView);
        Button btnViewPager = findViewById(R.id.btn_view_pager);
        btnViewPager.setOnClickListener(this::viewPager);
        Button btnFg = findViewById(R.id.btn_fg);
        btnFg.setOnClickListener(this::fg);
        Button btnS = findViewById(R.id.btn_s);
        btnS.setOnClickListener(this::service);
        Button btnBroadcast = findViewById(R.id.btn_broadcast);
        btnBroadcast.setOnClickListener(this::broadcast);
        Button btnGlide = findViewById(R.id.btn_glide);
        btnGlide.setOnClickListener(this::glide);
        Button btnPrefs = findViewById(R.id.btn_shared_prefs);
        btnPrefs.setOnClickListener(this::prefs);
        Button btnSql = findViewById(R.id.btn_sqlite);
        btnSql.setOnClickListener(this::sql);
        Button btnFile = findViewById(R.id.btn_external_file);
        btnFile.setOnClickListener(this::file);
        Button btnPermission = findViewById(R.id.btn_permission);
        btnPermission.setOnClickListener(this::permission);
        Button btnSpinner = findViewById(R.id.btn_spinner);
        btnSpinner.setOnClickListener(this::spinner);
        Button btnGridView = findViewById(R.id.btn_grid_view);
        btnGridView.setOnClickListener(this::gridView);
        Button btnLaunch = findViewById(R.id.btn_launch);
        btnLaunch.setOnClickListener(this::launch);
        Button btnLaunchFg = findViewById(R.id.btn_launch_fg);
        btnLaunchFg.setOnClickListener(this::launchFg);
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

    private void spinner(View view) {
        Intent intent = new Intent(this, SpinnerActivity.class);
        startActivity(intent);
    }

    private void gridView(View view) {
        Intent intent = new Intent(this, GridViewActivity.class);
        startActivity(intent);
    }

    private void launch(View view) {
        Intent intent = new Intent(this, LaunchPagerActivity.class);
        startActivity(intent);
    }

    private void launchFg(View view) {
        Intent intent = new Intent(this, LaunchFgActivity.class);
        startActivity(intent);
    }
}