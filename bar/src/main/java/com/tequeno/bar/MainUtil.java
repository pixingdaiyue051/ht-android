package com.tequeno.bar;

import android.content.Context;
import android.widget.Toast;

import java.time.LocalDate;

public final class MainUtil {

    /**
     * 将dip转换成px
     *
     * @param dip
     * @return
     */
    public static int dip2Px(float dip) {
        Context context = MyApplication.getInstance().getApplicationContext();
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