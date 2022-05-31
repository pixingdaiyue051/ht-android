package com.tequeno.app.map;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.tequeno.app.R;

public class MapActivity extends AppCompatActivity {

    private final static String TAG = "MapActivity";
    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient mLocationClient;
    private Intent locateServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        LinearLayout mapLayout = findViewById(R.id.activity_map);

        AMapOptions aOptions = new AMapOptions()
//                .zoomGesturesEnabled(false)// 禁止通过手势缩放地图
//                .scrollGesturesEnabled(false)// 禁止通过手势移动地图
                .tiltGesturesEnabled(false)// 禁止通过手势倾斜地图
                .camera(new CameraPosition.Builder()
                        .target(new LatLng(30.00D, 120.00D))
                        .zoom(10).bearing(0).tilt(30).build());
        mapView = new MapView(this, aOptions);
        mapView.onCreate(savedInstanceState); // 这里必须显示调用一次onCreate方法才能创建地图
        mapLayout.addView(mapView);
//        aMap = mapView.getMap();
//        myLocationStyle();

                startService(locateServiceIntent);
        locate();
    }

    private void locate() {
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption()
                .setOnceLocation(false) //使用单次定位 默认false
                .setNeedAddress(true) // 地址信息
                .setInterval(5000L) // 定位时间间隔
                .setLocationCacheEnable(false); // 启用缓存
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(location -> {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.d(TAG, "locate: "+ location.getErrorCode() + location + latitude + "-" + longitude);
        });
        mLocationClient.startLocation();
    }

    /**
     * 设置蓝点
     */
    private void myLocationStyle() {

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。

//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
////以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。

        myLocationStyle.interval(3000L); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(location -> {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            Log.d(TAG, "myLocationStyle: " + longitude + "--" + latitude);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy(); // 组件销毁时同时也要销毁地图
        mLocationClient.stopLocation(); // 停止定位
        stopService(locateServiceIntent);
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