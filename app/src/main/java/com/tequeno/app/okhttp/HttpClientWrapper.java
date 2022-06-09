package com.tequeno.app.okhttp;

import android.util.Log;

import androidx.core.util.Consumer;

import com.tequeno.app.MyApplication;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpClientWrapper {

    private final static String TAG = "HttpClientWrapper";

    private static HttpClientWrapper INSTANCE;
    private OkHttpClient client;
    public MediaType mediaTypeJson;
    public Request.Builder reqBuilder;

    private static void init() {
        HttpClientWrapper util = new HttpClientWrapper();
        util.client = new OkHttpClient();
        util.mediaTypeJson = MediaType.get("application/json; charset=utf-8");
        util.reqBuilder = new Request.Builder().header("appTenantId", "1");
        INSTANCE = util;
    }

    public static HttpClientWrapper getInstance() {
        if (null == INSTANCE) {
            synchronized (HttpClientWrapper.class) {
                if (null == INSTANCE) {
                    init();
                }
            }
        }
        return INSTANCE;
    }

    public void header(String name, String value) {
        reqBuilder.header(name, value);
    }

    public <T> void post(String url, String json, String typeKey, Consumer<T> consumer) {
        RequestBody requestBody = RequestBody.create(json, mediaTypeJson);
        Request request = reqBuilder
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Log.e(TAG, "post: response isSuccessful false");
                return;
            }
            ResponseBody responseBody = response.body();
            if (null == responseBody) {
                Log.e(TAG, "post: body is null");
                return;
            }
            String res = responseBody.string();
            Log.d(TAG, "post: " + res);

            ResponseWrapper<T> res1 = GsonWrapper.getInstance().fromJson(res, typeKey);

            // TODO 返回错误结果 另作处理
            if (0 != res1.code && !res1.success) {
                return;
            }
            // 使用返回值
            if (null != consumer) {
                consumer.accept(res1.data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "post: ", e);
        }
    }

    public <T> void postAsync(String url, String json, String typeKey, Consumer<T> consumer) {
        MyApplication.getInstance().asyncTask(() -> this.post(url, json, typeKey, consumer));
    }
}