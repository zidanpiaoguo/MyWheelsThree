package com.lzy.mywheelsthree.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by zidan on 2017/10/13.
 */

public class UserData {

    private User data;
    private String massage;
    private int status;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public class User  {

        /**
         * password : 21232f297a57a5a743894a0e4a801fc3
         * neckname : 张三
         * icon : style/image/img/icon1.jpg
         * uuid : z001
         * username : admin
         */
        @SerializedName("password")
        private String password;
        private String neckname;
        private String icon;
        private String uuid;
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

    @Override
    public String toString() {
        return "UserData{" +
                "data=" + data +
                ", massage='" + massage + '\'' +
                ", status=" + status +
                '}';
    }
}
