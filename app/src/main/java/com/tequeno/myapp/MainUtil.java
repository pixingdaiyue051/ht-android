package com.tequeno.myapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

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

    public static String toJsonString(String uname, String pwd) {
        return new Gson().toJson(new LoginDto().setUname(uname).setPwd(pwd));
    }
}