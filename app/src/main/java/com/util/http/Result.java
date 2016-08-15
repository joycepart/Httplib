package com.util.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bixinwei on 16/8/15.
 */
public class Result<T> {
    @SerializedName("ret")
    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
