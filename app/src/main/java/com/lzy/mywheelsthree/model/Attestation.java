package com.lzy.mywheelsthree.model;

/**
 * Created by 刘振远 on 2017/12/14.
 */

public class Attestation {

    /**
     * auth_image1 : data/files/ahtu_img/40@3x.png
     * auth_image2 : data/files/ahtu_img/60@2x.png
     * auth_image3 : data/files/ahtu_img/60@3x.png
     */

    private String auth_image1;
    private String auth_image2;
    private String auth_image3;

    public String getAuth_image1() {
        return auth_image1;
    }

    public void setAuth_image1(String auth_image1) {
        this.auth_image1 = auth_image1;
    }

    public String getAuth_image2() {
        return auth_image2;
    }

    public void setAuth_image2(String auth_image2) {
        this.auth_image2 = auth_image2;
    }

    public String getAuth_image3() {
        return auth_image3;
    }

    public void setAuth_image3(String auth_image3) {
        this.auth_image3 = auth_image3;
    }
}
