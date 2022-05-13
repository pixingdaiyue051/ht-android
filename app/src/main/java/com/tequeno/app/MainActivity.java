package com.tequeno.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 继承 AppCompatActivity 可以自动解决一些兼容问题
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    /**
     * 组件加载时首先被调用的方法
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        // 设置页面内的布局文件
        setContentView(R.layout.activity_main);

        nextKick();

        implicitNextKick();

        login();

        metaData();

        scroll();

        btn();

        form();

        datetime();
    }

    /**
     * 显示意图跳转
     */
    private void nextKick() {
        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(view -> {
            // 创建一个意图用以跳转到其他activity
            // 意图需要使用一个上下文 由于使用了lambada表达式方法本身没有this指向 所以默认向外层查找使用到MainActivity

            // 1.
            Intent intent = new Intent(this, Link1Activity.class);
//            // 2.
//            Intent intent = new Intent();
//            intent.setClass(this, Link1.class);
//            // 3.
//            Intent intent = new Intent();
//            ComponentName componentName = new ComponentName(this, Link1.class);
//            intent.setComponent(componentName);
            // 以上三种方式最终都会调用new ComponentName(this, Link1.class)构造函数创建跳转实列 没有任何差别 注意使用最简单的方式
            // 使用componentName的构造方法(String,String) 可以在无法只引用类时使用 更灵活
            startActivity(intent);
        });
    }

    /**
     * 隐式意图跳转
     */
    private void implicitNextKick() {
        Button btnNextImplicit = findViewById(R.id.btn_next_implicit);
        btnNextImplicit.setOnClickListener(view -> {
//            Intent intent = new Intent();
//            // 声明意图的动作 此处是调用系统应用 开启拨号界面
//            intent.setAction(Intent.ACTION_DIAL);
//            // 向需要跳转的意图传递数据
//            intent.setData(Uri.parse("tel:123456"));

//            Intent intent = new Intent();
//            // 声明意图的动作 此处是调用系统应用 开启发短信界面
//            intent.setAction(Intent.ACTION_SENDTO);
//            // 向需要跳转的意图传递数据
//            intent.setData(Uri.parse("smsto:123456"));

            Intent intent = new Intent();
            // 跳转到其他模块或者第三方模块的应用
            intent.setAction("com.tequeno.bar.myintent");

            startActivity(intent);
        });
    }

    /**
     * 模拟登录
     */
    private void login() {
        Button btnLogin = findViewById(R.id.btn_fake_login);
        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 获取清单文件中元数据
     */
    private void metaData() {
        Button btnMetaData = findViewById(R.id.btn_meta_data);
        btnMetaData.setOnClickListener(view -> {
            PackageManager pm = getPackageManager();
            ActivityInfo activityInfo = null;
            try {
                activityInfo = pm.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (activityInfo != null) {
                Bundle bundle = activityInfo.metaData;
                bundle.keySet().forEach(k -> Log.i(TAG, String.format("%s - %s", k, bundle.get(k))));
            }
        });
    }

    /**
     * 滚动条
     */
    private void scroll() {
        Button btnScroll = findViewById(R.id.btn_scroll);
        btnScroll.setOnClickListener(view -> {
            Intent intent = new Intent(this, ScrollActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 按钮
     */
    private void btn() {
        Button btnScroll = findViewById(R.id.btn_btn);
        btnScroll.setOnClickListener(view -> {
            Intent intent = new Intent(this, BtnActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 表单设计
     */
    private void form() {
        Button btnScroll = findViewById(R.id.btn_form);
        btnScroll.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 日期时间
     */
    private void datetime() {
        Button btnScroll = findViewById(R.id.btn_datetime);
        btnScroll.setOnClickListener(view -> {
            Intent intent = new Intent(this, DateTimeActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }
}