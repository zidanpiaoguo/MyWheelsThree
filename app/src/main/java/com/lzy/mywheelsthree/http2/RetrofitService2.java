package com.lzy.mywheelsthree.http2;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by bullet on 2017/11/13.
 */

public interface RetrofitService2 {

    //可以单独加请求头
//    @Headers("apikey:b86c2269fe6588bbe3b41924bb2f2da2")
    @POST("userLogin.do")
    @FormUrlEncoded
    Observable<String> login(@Field("username") String userid,@Field("password") String password);

}
