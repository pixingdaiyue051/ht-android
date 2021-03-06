package com.tequeno.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Locale;

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
        return (int) (density * dip + 0.5f);
    }

    /**
     * 关闭输入法软键盘
     *
     * @param activity 触发软键盘的活动activity
     * @param view     触发软键盘的控件
     */
    public static void hideInputMethod(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void toast(String msg) {
        Context context = MyApplication.getInstance().getApplicationContext();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(String msg) {
        Context context = MyApplication.getInstance().getApplicationContext();
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 获取当前模式下的sha1
     *
     * @return
     */
    public static String sha1() {
        try {
            Context context = MyApplication.getInstance().getApplicationContext();
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String day() {
        return LocalDate.now().toString();
    }
}