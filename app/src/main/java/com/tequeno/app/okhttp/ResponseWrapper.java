package com.tequeno.app.okhttp;

public class ResponseWrapper<T> {

    public final static String TAG = "ResponseWrapper";
    public final static String TAG_1 = "ResponseWrapper_1";

    public int code;
    public String msg;
    public boolean success;
    public T data;
}