package com.tequeno.bar.glide;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tequeno.bar.R;

import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GlideActivity extends AppCompatActivity {

    private final static String TAG = "GlideActivity";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        iv = findViewById(R.id.iv_g);
        Button btnLoad = findViewById(R.id.btn_load);
        Button btnUnload = findViewById(R.id.btn_unload);
        btnLoad.setOnClickListener(this::load);
        btnUnload.setOnClickListener(this::unLoad);


// https://family-dev-v2-z.oss-cn-hangzhou.aliyuncs.com/theme_index/271021927257214976.png
// https://family-dev-v2-z.oss-cn-hangzhou.aliyuncs.com/theme_wallet/271021941354270720.png
// https://family-dev-v2-z.oss-cn-hangzhou.aliyuncs.com/theme_my/271021957191962624.png
    }

    private void load(View view) {
        GlideApp.with(this)
                .load("https://family-dev-v2-z.oss-cn-hangzhou.aliyuncs.com/theme_index/271021927257214976.png")
                .fitCenter()
                .into(iv);
    }

    private void unLoad(View view) {
        GlideApp.with(this).clear(iv);
    }
}