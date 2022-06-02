package com.tequeno.app.okhttp;

public class ResponseWrapper<T> {
    public int code;
    public String msg;
    public String cipher;
    public T data;
    public String traceId;
}