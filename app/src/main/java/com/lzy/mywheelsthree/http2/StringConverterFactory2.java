package com.lzy.mywheelsthree.http2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by bullet on 2017/11/14.
 */

public class StringConverterFactory2 extends Converter.Factory {
    public static final StringConverterFactory2 INSTANCE = new StringConverterFactory2();

    public static StringConverterFactory2 create(){
        return INSTANCE;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, String>() {
            @Override
            public String convert(ResponseBody value) throws IOException {
                return value.string();
            }
        } ;
    }
}
