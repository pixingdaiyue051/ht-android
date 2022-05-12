package com.tequeno.bar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnReset = findViewById(R.id.btn_reset);
        EditText et1 = findViewById(R.id.et1);
        EditText et2 = findViewById(R.id.et2);
        ProgressBar pb1 = findViewById(R.id.pb1);
        ProgressBar pb2 = findViewById(R.id.pb2);
        Button btnPb1 = findViewById(R.id.btn_pb1);
        Button btnPb2 = findViewById(R.id.btn_pb2);

        // 按钮点击回调事件
        btnLogin.setOnClickListener(view -> {
            Log.d(TAG, "按钮点击回调事件");
            String name = et1.getText().toString();
            String password = et2.getText().toString();
            Log.d(TAG, "获取到输入的信息:" + name + ":" + password);
        });
        // 按钮长按回调事件
        // 返回值false表示事件会继续向下传递
        btnLogin.setOnLongClickListener(view -> {
            Log.d(TAG, "按钮长按回调事件");
            return false;
        });
        // 按钮内移动回调事件
        // 返回值false表示事件会继续向下传递
        // motionEvent 触摸类型
        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(TAG, "按钮内移动回调事件");
                Log.d(TAG, "onTouch:motionEvent:" + motionEvent.getAction());
                return false;
            }
        });

        btnReset.setOnClickListener(view -> {
            Log.d(TAG, "btnReset");
            et1.getText().clear();
            et2.getText().clear();
        });

        btnPb1.setOnClickListener(view -> pb1.setVisibility((View.INVISIBLE == pb1.getVisibility()) ? View.VISIBLE : View.INVISIBLE));
        btnPb2.setOnClickListener(view -> {
//            boolean animating = false;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                animating = pb2.isAnimating();
//            }
//            Log.d(TAG, "pb2 isAnimating " + animating);
//            if(animating) {
//
//            }
            int progress = pb2.getProgress();
            progress += 10;
            pb2.setProgress(progress);
        });

        // 创建系统服务句柄
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 开启一个服务通知channel 并注册到manager上
        // manager和channel一对多
        // 后续需要使用manager发出通知 取消通知
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(TAG, "系统测试", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

//        Intent intent = new Intent(this, MyNotification.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 ,intent, 0);
        // 创建一个消息通知 指明需要注册到的channel
        // channel和notification多对一
        Notification notification = new NotificationCompat.Builder(this, TAG)
                .setContentTitle("系统通知")   // 标题
                .setContentText("水仙不开花 装蒜呢") // 内容
                .setSmallIcon(R.drawable.ic_baseline_account_circle_24) // 小图标
//                .setLargeIcon(BitmapFactory.decodeFile("/data/pic/131099.jpg")) // 大图标
                .setColor(Color.CYAN) // 小图标颜色
                .setAutoCancel(true) // 点击后自动消失
                .setWhen(System.currentTimeMillis()) // 通知发出时的时间
//                .setContentIntent(pendingIntent) // 点击后跳转activity
                .build();

        Button btnNotify1 = findViewById(R.id.btn_notify1);
        Button btnNotify2 = findViewById(R.id.btn_notify2);

        btnNotify1.setOnClickListener(view -> manager.notify(1, notification));
        btnNotify2.setOnClickListener(view -> manager.cancel(1));
    }
}