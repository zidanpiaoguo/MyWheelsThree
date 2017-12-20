package com.lzy.mywheelsthree.model;

/**
 * Created by 刘振远 on 2017/12/1.
 */

public class TokenData  {


    /**
     * token : fb38fad89bb0444c97f9e7a671409d48
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenData{" +
                "token='" + token + '\'' +
                '}';
    }
}
