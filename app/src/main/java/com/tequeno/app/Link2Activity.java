package com.tequeno.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Link2Activity extends AppCompatActivity {

    private final static String TAG = "Link2Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link2);

        Button btn = findViewById(R.id.btn_link2);

        btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Link1Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(intent);

            // 设置回参结果 主动结束当前activity
            Intent intent1 = new Intent().putExtra("param_link2_1", 342);
            setResult(Activity.RESULT_OK, intent1);
            // 结束当前activity 退回到栈内下一个activity
            finish();
        });

        // 获得从上一个activity传递来的数据
        Bundle bundle = getIntent().getExtras();
//        Object param1 = bundle.get("param1");
//        Object param2 = bundle.get("param2");
//        Object param3 = bundle.get("param3");
        bundle.keySet().forEach(k -> Log.i(TAG, String.format("%s - %s", k, bundle.get(k))));
    }
}