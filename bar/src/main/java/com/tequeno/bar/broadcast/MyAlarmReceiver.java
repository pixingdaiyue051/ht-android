package com.tequeno.bar.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 利用系统闹钟实现定时任务
 * 使用timer handler都可以实现类似功能
 * 但是 AlarmManager 是系统级别的 进程不存在时也可以触发(存疑) TODO 待验证
 */
public class MyAlarmReceiver extends BroadcastReceiver {

    private final static String TAG = "MyAlarmReceiver";
    private final PendingIntent pendingIntent;
    private final AlarmManager alarmManager;

    public MyAlarmReceiver(Context ctx) {
        // 初始意图
        Intent intent = new Intent(BroadcastActivity.ALARM_RECEIVER);
        // 根据初始意图构建一个延时意图
        pendingIntent = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "自定义闹钟定时发送: " + System.currentTimeMillis());
        if (null == intent) {
            return;
        }
        // 重复调用 以实现定时任务式触发
        send();
    }

    public void send() {
        // 发送延时的系统闹钟广播
//        alarmManager.set(AlarmManager.RTC_WAKEUP, 3000L, pendingIntent);
        // Android 6.0 之后 需要使用下面的方法
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 3000L, pendingIntent);
//        // 不推荐使用setRepeating因为不一定能触发
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000L, pendingIntent);
//        // 手动停止定时器
//        alarmManager.cancel(pendingIntent);
    }

}