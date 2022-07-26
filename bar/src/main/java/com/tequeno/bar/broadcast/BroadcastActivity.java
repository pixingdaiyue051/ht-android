package com.tequeno.bar.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.tequeno.bar.MyApplication;
import com.tequeno.bar.R;

public class BroadcastActivity extends AppCompatActivity {

    private final static String TAG = "BroadcastActivity";
    private final static String DYNAMIC_RECEIVER = "_receiver.dynamic";
    private final static String ORDERED_RECEIVER = "_receiver.ordered";
    public final static String ALARM_RECEIVER = "_receiver.alarm";
    private MyBackReceiver br;
    private MyOrderedReceiverA a;
    private MyOrderedReceiverB b;
    private MyOrderedReceiverC c;
    private MySysReceiver sysReceiver;
    private MyAlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        Button btnStatic = findViewById(R.id.btn_static_receiver);
        Button btnDynamic = findViewById(R.id.btn_dynamic_receiver);
        Button btnOrdered = findViewById(R.id.btn_ordered_receiver);
        Button btnAlarm = findViewById(R.id.btn_alarm_receiver);
        btnStatic.setOnClickListener(this::staticReceiver);
        btnDynamic.setOnClickListener(this::dynamicReceiver);
        btnOrdered.setOnClickListener(this::orderedReceiver);
        btnAlarm.setOnClickListener(this::alarmReceiver);

        // 动态注册广播
        IntentFilter filter = new IntentFilter(DYNAMIC_RECEIVER);
        br = new MyBackReceiver();
        // 不需要跨进程跨应用之间的通信的前提下 使用LocalBroadcastManager效率更高
        LocalBroadcastManager.getInstance(this).registerReceiver(br, filter);

        // 注册有序广播 接收者可以中断广播传输
        // 优先级越高的越早接收 数字越大优先级越高默认0
        // 优先级相同 先注册的先接收
        IntentFilter filterA = new IntentFilter(ORDERED_RECEIVER);
        a = new MyOrderedReceiverA();
        registerReceiver(a, filterA);

        IntentFilter filterB = new IntentFilter(ORDERED_RECEIVER);
        filterB.setPriority(3);
        b = new MyOrderedReceiverB();
        registerReceiver(b, filterB);

        IntentFilter filterC = new IntentFilter(ORDERED_RECEIVER);
        c = new MyOrderedReceiverC();
        registerReceiver(c, filterC);

        // 接收系统广播
        IntentFilter filterSys = new IntentFilter();
        filterSys.addAction(Intent.ACTION_TIME_TICK); // 系统时钟
        filterSys.addAction(Intent.ACTION_SCREEN_OFF);
        filterSys.addAction(Intent.ACTION_SCREEN_ON);
        filterSys.addAction(Intent.ACTION_DREAMING_STOPPED);
        filterSys.addAction(Intent.ACTION_DREAMING_STARTED);
        filterSys.addAction(ConnectivityManager.CONNECTIVITY_ACTION); // 网络变更
        sysReceiver = new MySysReceiver();
        registerReceiver(sysReceiver, filterSys);

        // 自定义闹钟接收器
        // 由于需要使用到系统时钟所以不能使用LocalBroadcastManager
        // Context也使用和project绑定的Context代替this 防止activity销毁后receiver仍持有对它的引用而内存泄露
        IntentFilter filterAlarm = new IntentFilter(ALARM_RECEIVER);
        alarmReceiver = new MyAlarmReceiver(MyApplication.getInstance());
        registerReceiver(alarmReceiver, filterAlarm);
    }

    /**
     * 静态广播
     * android 8.0 之后删除了大部分静态广播 防止app退出之后还能继续接收广播 仍要使用 需要指明接收者类全路径
     * 因此推荐使用动态广播
     *
     * @param view
     */
    @Deprecated
    private void staticReceiver(View view) {
        Intent intent = new Intent(getString(R.string.export_name_myreceiver));
        intent.setClassName(this, "com.tequeno.bar.broadcast.MyReceiver");
        sendBroadcast(intent);
    }

    /**
     * 动态广播
     *
     * @param view
     */
    private void dynamicReceiver(View view) {
        Intent intent = new Intent(DYNAMIC_RECEIVER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /**
     * 有序广播
     *
     * @param view
     */
    private void orderedReceiver(View view) {
        Intent intent = new Intent(ORDERED_RECEIVER);
        // 发送一个有序广播
        // 不指定接收者的权限要求
        sendOrderedBroadcast(intent, null);
    }

    /**
     * 系统闹钟
     *
     * @param view
     */
    private void alarmReceiver(View view) {
        if (null != alarmReceiver) {
            alarmReceiver.send();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != br) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(br);
        }
        if (null != a) {
            unregisterReceiver(a);
        }
        if (null != b) {
            unregisterReceiver(b);
        }
        if (null != c) {
            unregisterReceiver(c);
        }
        if (null != sysReceiver) {
            unregisterReceiver(sysReceiver);
        }
        if (null != alarmReceiver) {
            unregisterReceiver(alarmReceiver);
        }
    }
}