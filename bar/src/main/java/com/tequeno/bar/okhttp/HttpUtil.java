package com.tequeno.bar.okhttp;

import android.util.Log;

import androidx.core.util.Consumer;

import com.tequeno.bar.MyApplication;

import java.util.concurrent.ExecutorService;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpUtil {

    private final static String TAG = "HttpUtil";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static HttpUtil INSTANCE;
    private ExecutorService pool;
    private OkHttpClient client;

    private static void init() {
        HttpUtil util = new HttpUtil();
        util.pool = MyApplication.getInstance().getThreadPool();
        util.client = new OkHttpClient();
        INSTANCE = util;
    }

    public static HttpUtil getInstance() {
        if (null == INSTANCE) {
            synchronized (HttpUtil.class) {
                if (null == INSTANCE) {
                    init();
                }
            }
        }
        return INSTANCE;
    }

    public void get(String url) {
        pool.execute(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                ResponseBody body = response.body();
                if (!response.isSuccessful()) {
                    Log.e(TAG, "get: response isSuccessful false");
                    return;
                }
                if (null == body) {
                    Log.e(TAG, "get: body is null");
                    return;
                }
                Log.d(TAG, "get: " + body.string());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "get: ", e);
            }
        });
    }

    public void get(String url, Consumer<String> consumer) {
        pool.execute(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "get: response isSuccessful false");
                    return;
                }
                ResponseBody body = response.body();
                if (null == body) {
                    Log.e(TAG, "get: body is null");
                    return;
                }
                String res = body.string();
                Log.d(TAG, "get: " + res);
                consumer.accept(res);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "get: ", e);
            }
        });
    }

    public void post(String url) {
        this.get(url);
    }

    public void post(String url, String json) {
        pool.execute(() -> {
            RequestBody requestBody = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                ResponseBody responseBody = response.body();
                if (!response.isSuccessful()) {
                    Log.e(TAG, "post: response isSuccessful false");
                    return;
                }
                if (null == responseBody) {
                    Log.e(TAG, "post: body is null");
                    return;
                }
                Log.d(TAG, "post: " + responseBody.string());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "post: ", e);
            }
        });
    }

    public void post(String url, Consumer<String> consumer) {
        this.get(url, consumer);
    }

    public void post(String url, String json, Consumer<String> consumer) {
        pool.execute(() -> {
            RequestBody requestBody = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                ResponseBody responseBody = response.body();
                if (!response.isSuccessful()) {
                    Log.e(TAG, "post: response isSuccessful false");
                    return;
                }
                if (null == responseBody) {
                    Log.e(TAG, "post: body is null");
                    return;
                }
                String res = responseBody.string();
                Log.d(TAG, "post: " + res);
                consumer.accept(res);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "post: ", e);
            }
        });
    }
}