package com.lzy.mywheelsthree.model;

import com.google.gson.annotations.SerializedName;

/**
 * UserInfor
 * Created by jaycee on 2017/6/23.
 */
public class UserInfor {

    /**
     * password : 21232f297a57a5a743894a0e4a801fc3
     * neckname : 张三
     * icon : style/image/img/icon1.jpg
     * uuid : z001
     * username : admin
     */
    @SerializedName("password")
    private String password;
    @SerializedName("neckname")
    private String neckname;
    @SerializedName("icon")
    private String icon;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("username")
    private String username;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNeckname() {
        return neckname;
    }

    public void setNeckname(String neckname) {
        this.neckname = neckname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserInfor{" +
                "password='" + password + '\'' +
                ", neckname='" + neckname + '\'' +
                ", icon='" + icon + '\'' +
                ", uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
