package com.tequeno.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Link1Activity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link1);

        // 根据id得到对应的控件
        TextView tv1 = findViewById(R.id.tv_link1_1);
        TextView tv2 = findViewById(R.id.tv_link1_2);
        TextView tv3 = findViewById(R.id.tv_link1_3);
        Button btn = findViewById(R.id.btn_link1);
        // 设置文本
        // 不要直接使用字符串 在values.xml配置 通过以下方式获得 推荐使用第一种方式
//        tv1.setText(getText(R.string.tv2_text));
//        tv1.setText(getString(R.string.tv2_text));
//        tv1.setText(R.string.tv2_text);
//        tv1.setText("首都巴格达发生他人合法呢");
//        // 设置字体大小
//        // 默认会使用sp
//        tv1.setTextSize(30);
//        // 设置颜色
//        // 可以使用6位或者8位表示 默认情况下透明度为00(完全透明)
//        tv1.setTextColor(0xFFFF0000);
////        tv1.setTextColor(getColor(R.color.teal_200));
////        tv1.setTextColor(Color.parseColor("#FFFF0000"));
//        // 设置背景色
//        tv1.setBackgroundColor(Color.RED);
////        tv1.setBackgroundResource(R.color.purple_500);
////        tv1.setBackground(AppCompatResources.getDrawable(this, R.drawable.ic_launcher_background));
//
//        // 设置视图的宽高
//        // 获得layoutParams---修改layoutParams---重新设置layoutParams
//        ViewGroup.LayoutParams tv3LayoutParams = tv3.getLayoutParams();
//        tv3LayoutParams.width = MainUtil.dip2Px(this, 200f);
//        tv3LayoutParams.height = MainUtil.dip2Px(this, 100f);
//        tv3.setLayoutParams(tv3LayoutParams);

        // 需要和待跳转activity数据交互 需要先注册 注册流程需要提前
        ActivityResultLauncher<Intent> register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Log.i(TAG, result.toString());
            Intent resultData = result.getData();
            if (null != resultData) {
                Bundle bundle = resultData.getExtras();
                bundle.keySet().forEach(k -> Log.i(TAG, String.format("%s - %s", k, bundle.get(k))));
            }
        });

        btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Link2Activity.class);
            // 设置跳转意图启动标志
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            // 向跳转activity添加自定义数据
            // 需要使用到Bundle 以下三种方式最终都会使用到Bundle 在intent内部设置它的mExtras属性 相同的key会覆盖
            // 1.
            intent.putExtra("param1", "params1");
            intent.putExtra("param2", "params2");
            // 2.
            Bundle bundle = new Bundle();
            bundle.putString("param3", "param3");
            bundle.putInt("param2", 4233);
            intent.putExtras(bundle);
            // 3.
            Intent extraIntent = new Intent();
            extraIntent.putExtra("param3", 965485);
            intent.putExtras(extraIntent);

//            // 跳转至下一个activity
//            startActivity(intent);

            register.launch(intent);
        });
    }
}