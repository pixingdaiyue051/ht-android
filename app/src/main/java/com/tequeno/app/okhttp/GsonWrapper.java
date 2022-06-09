package com.tequeno.app.okhttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tequeno.app.login.LoginResDto;
import com.tequeno.app.login.PageResDto;
import com.tequeno.app.login.UserResDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonWrapper {

    private static GsonWrapper INSTANCE;
    private Gson gson;
    private Map<String, TypeToken> map;

    private static void init() {
        GsonWrapper util = new GsonWrapper();
        util.gson = new GsonBuilder().setPrettyPrinting().create();
        util.map = new HashMap<>();
        util.map.put(ResponseWrapper.TAG, new TypeToken<ResponseWrapper<Boolean>>() {
        });
        util.map.put(ResponseWrapper.TAG_1, new TypeToken<ResponseWrapper<String>>() {
        });
        util.map.put(LoginResDto.TAG, new TypeToken<ResponseWrapper<LoginResDto>>() {
        });
        util.map.put(UserResDto.TAG, new TypeToken<ResponseWrapper<UserResDto>>() {
        });
        util.map.put(UserResDto.TAG_LIST, new TypeToken<ResponseWrapper<List<UserResDto>>>() {
        });
        util.map.put(UserResDto.TAG_PAGE, new TypeToken<ResponseWrapper<PageResDto<UserResDto>>>() {
        });
        INSTANCE = util;
    }

    public static GsonWrapper getInstance() {
        if (null == INSTANCE) {
            synchronized (GsonWrapper.class) {
                if (null == INSTANCE) {
                    init();
                }
            }
        }
        return INSTANCE;
    }

    public ResponseWrapper fromJson(String json, String typeKey) {
        TypeToken typeToken = map.get(typeKey);

        return gson.fromJson(json, typeToken.getType());
    }

    public String toJson(Object t) {
        return gson.toJson(t);
    }
}