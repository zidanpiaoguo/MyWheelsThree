package com.lzy.mywheelsthree.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bullet on 2017/11/10.
 */

public class RetrofitFactory {
//    public final static String BASE_URL = "http://10.10.10.158/member/";

    public static String BASE_URL = "http://chk.emall.co.jp/";

    private final static long CONNECT_TIMEOUT = 30;

    public final static int READ_TIMEOUT = 100;


    //Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
    private static OkHttpClient httpClient = new OkHttpClient.Builder()

            //添加头文件，目前接口不用加，就
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

//                    //第一种方法
//                      Request.Builder builder =chain.request().newBuilder();
//                      builder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                      builder.addHeader("token","123");
//
                    Request request = chain.request().newBuilder()
//                            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                            .addHeader("token", "fb38fad89bb0444c97f9e7a671409d48")
                            .build();
                    return chain.proceed(request);
                }
            })
            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {

                }
            }).setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  //连接超时时间
            .retryOnConnectionFailure(true)  //失败重拨。可以取消掉
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)  //读取超时
            .build();

    private static RetrofitService retrofitService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RetrofitService.class);


    public static RetrofitService getInstance() {
        return retrofitService;
    }


    /**
     * 个别有不同url 的
     *
     * @param url
     * @return
     */

    public static RetrofitService getInstance(String url) {
        RetrofitService retrofitService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(RetrofitService.class);
        return retrofitService;
    }


    /**
     * 解决gson 对于空字符，抛出异常的情况
     *
     * @return
     */
    private static Gson buildGson() {
        return new GsonBuilder().serializeNulls().create();
    }
}
