package com.tequeno.app.map;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.tequeno.app.R;

public class MapActivity extends AppCompatActivity {

    private final static String TAG = "MapActivity";
    private MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState); // 这里必须显示调用一次onCreate方法才能创建地图
        aMap = mapView.getMap();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy(); // 组件销毁时同时也要销毁地图
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause(); // 暂停绘制
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume(); // 继续绘制
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState); // 保存当前状态
    }
}