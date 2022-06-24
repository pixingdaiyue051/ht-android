package com.tequeno.bar;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import com.tequeno.bar.sqlite.MyDataBase;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    private final static String TAG = "MyApplication";

    public final static String DB_NAME = "test.db";

    private static MyApplication mApp;
    private ExecutorService mThreadPool;
    private Handler mHandler;
    private MyDataBase mDb;

    public static MyApplication getInstance() {
        return mApp;
    }

    /**
     * app启动时调用
     */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        mApp = this;
    }

    /**
     * 执行一个异步任务
     *
     * @param r
     */
    public void asyncTask(Runnable r) {
        if (null == mThreadPool) {
            mThreadPool = Executors.newCachedThreadPool();
        }
        mThreadPool.execute(r);
    }

    /**
     * 往主线程队列添加任务
     *
     * @param r
     */
    public void run(Runnable r) {
        run(r, 0L);
    }

    /**
     * 往主线程队列添加任务
     *
     * @param r
     * @param delay
     */
    public void run(Runnable r, long delay) {
        if (null == mHandler) {
            mHandler = new Handler(getMainLooper());
        }
        mHandler.postDelayed(r, delay);
    }

    public MyDataBase getDb() {
        if (null == mDb) {
            mDb = Room.databaseBuilder(this, MyDataBase.class, "test2.db")
                    .allowMainThreadQueries() // 允许使用主线程查询数据库 主线程是UI线程 不应该这么做
                    .addMigrations() // 数据库迁移时自动备份
                    .build();
        }
        return mDb;
    }

    /**
     * 永远不会被调用
     */
    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate: ");
        super.onTerminate();
    }

    /**
     * app配置发生变化时调用 比如 竖屏变横屏
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: " + newConfig);
    }

    /**
     * 打开当前应用的应用信息 以便开启权限
     */
    public void appInfo() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 打开启动安装应用的程序
     */
    public void apkInstaller(String apkPath) {
//        Uri uri = Uri.parse(apkPath);
        Uri uri = FileProvider.getUriForFile(this, getString(R.string.bar_file_provider), new File(apkPath));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 授权读取安装包的uri路径
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置文件类型apk
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }
}