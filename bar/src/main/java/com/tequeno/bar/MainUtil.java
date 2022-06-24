package com.tequeno.bar;

import android.content.Context;
import android.widget.Toast;

import java.time.LocalDate;

public final class MainUtil {

    /**
     * 将dip转换成px
     *
     * @param context 视图上下文 用户获取当前设备像素密度
     * @param dip
     * @return
     */
    public static int dip2Px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dip + 0.5F);
    }

    public static void toast(String msg) {
        Context context = MyApplication.getInstance().getApplicationContext();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String day() {
        return LocalDate.now().toString();
    }
}