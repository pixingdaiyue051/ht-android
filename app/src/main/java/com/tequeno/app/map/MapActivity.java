package com.tequeno.app.map;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.tequeno.app.MainUtil;
import com.tequeno.app.R;
import com.tequeno.app.okhttp.GsonWrapper;
import com.tequeno.app.permission.PermissionEnum;
import com.tequeno.app.permission.PermissionUtil;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private final static String TAG = "MapActivity";
    private MapView mapView;
    private AMap aMap;
    private EditText et;
    private MapLocation mapLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initMap(savedInstanceState);
//        myLocationStyle();

        et = findViewById(R.id.et_point_num);

        Button btnStart = findViewById(R.id.btn_start_locate);
        btnStart.setOnClickListener(view -> {
            boolean b = PermissionUtil.checkPermission(this, Arrays.asList(PermissionEnum.ACCESS_FINE_LOCATION, PermissionEnum.ACCESS_BACKGROUND_LOCATION));
            if(b) {
                startLocate();
            }
        });

        Button btnStop = findViewById(R.id.btn_stop_locate);
        btnStop.setOnClickListener(view -> {
            if (null == mapLocation) {
                return;
            }
            mapLocation.stopLocate();
            MainUtil.toast("结束定位!!!");
        });

        Button btnPoint = findViewById(R.id.btn_load_points);
        btnPoint.setOnClickListener(this::drawPolyLine);

        Button btnStatus = findViewById(R.id.btn_status);
        btnStatus.setOnClickListener(v -> {
            if (null == mapLocation) {
                return;
            }
            mapLocation.status();
        });

    }

    /**
     * 设置地图初始化位置 创建地图view
     */
    private void initMap(Bundle savedInstanceState) {

        AMapOptions aOptions = new AMapOptions()
//                .zoomGesturesEnabled(false)// 禁止通过手势缩放地图
//                .scrollGesturesEnabled(false)// 禁止通过手势移动地图
                .tiltGesturesEnabled(false)// 禁止通过手势倾斜地图
                .camera(new CameraPosition.Builder()
                        .target(new LatLng(30.02D, 120.12D))
                        .zoom(10).bearing(0).tilt(30).build());
        mapView = new MapView(this, aOptions);
        mapView.onCreate(savedInstanceState); // 这里必须显示调用一次onCreate方法才能创建地图
        aMap = mapView.getMap();
        LinearLayout mapLayout = findViewById(R.id.content_map);
        mapLayout.addView(mapView);
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

    private void drawPolyLine(View view) {
        int listSize = 100;
        String text = et.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            listSize = Integer.parseInt(text);
        }
        String textDay = MainUtil.day();
        List<LatLng> list = MapOpenHelper.getInstance().query(textDay, listSize);
        if (null == list || list.isEmpty()) {
            return;
        }

        String dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + textDay + ".txt";

        try (FileChannel fileChannel = FileChannel.open(Paths.get(dir), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            ByteBuffer wrap = ByteBuffer.wrap(GsonWrapper.getInstance().toJson(list).getBytes(StandardCharsets.UTF_8));
            fileChannel.write(wrap);
            wrap.clear();
        } catch (Exception e) {
            Log.e(TAG, "save: error", e);
        }

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.setPoints(list);
        aMap.addPolyline(polylineOptions.width(MainUtil.dip2Px(2)).color(Color.parseColor("#BA144D")));
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

    private void startLocate() {
        if (null == mapLocation) {
            mapLocation = new MapLocation();
        }
        mapLocation.startLocate();
        MainUtil.toast( "开始定位...");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: " + requestCode + Arrays.toString(permissions) + Arrays.toString(grantResults));

        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            startLocate();
        }

    }
}