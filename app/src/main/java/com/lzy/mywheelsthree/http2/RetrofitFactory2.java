package com.lzy.mywheelsthree.http2;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by bullet on 2017/11/11.
 */

public class RetrofitFactory2 {
    private static final String BASE_URL = "http://zhichuang.api.51zhiyuan.net/";

    private final static  long CONNECT_TIMEOUT = 60;

    public final static   int READ_TIMEOUT = 100;

    public final static int WRITE_TIMEOUT = 60;


    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {


                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
    //设置头文件
//            .addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    return null;
//                }
//            })



    private static RetrofitService2 retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .addConverterFactory(StringConverterFactory2.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitService2.class);

    public static RetrofitService2 getInstance(){
        return retrofit;
    }







}
