package com.lzy.mywheelsthree.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bullet on 2017/11/10.
 */

public class BaseResult<T> {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String massage;
    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return status == 0;
    }

    public int getStatus() {
        return status;
    }

    public String getMassage() {
        return massage;
    }

    public T getData() {
        return data;
    }
}
