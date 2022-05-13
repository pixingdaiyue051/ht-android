package com.tequeno.rdm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SharedPrefsActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private TextView tv1;
    private TextView tv2;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        // 获取sharedPreferences
        // 指定保存的文件名和访问模式 private 表示只有当前项目可以访问
        preferences = getSharedPreferences("cs_cfg", Context.MODE_PRIVATE);


        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        Button btn1 = findViewById(R.id.btn1);

        btn1.setOnClickListener(this::save2Prefs);

        reloadFromPrefs();
    }

    private void save2Prefs(View view) {
        Log.d(TAG, "save2Prefs: ");
        SharedPreferences.Editor editor = preferences.edit();
        String text1 = tv1.getText().toString();
        String text2 = tv2.getText().toString();
        if (!TextUtils.isEmpty(text1)) {
            editor.putString(getString(R.string.text1), text1);
        }
        if (!TextUtils.isEmpty(text2)) {
            editor.putString(getString(R.string.text2), text2);
        }
        editor.apply();
    }

    private void reloadFromPrefs() {
        Log.d(TAG, "reloadFromPrefs: ");
        preferences.getAll().forEach((k, v) -> Log.d(TAG, String.format("%s-%s", k, v)));
        tv1.setText(preferences.getString(getString(R.string.text1), ""));
        tv2.setText(preferences.getString(getString(R.string.text2), ""));
    }
}