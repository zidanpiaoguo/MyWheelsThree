package com.lzy.mywheelsthree.http2;

import android.content.Context;
import android.util.Log;

import com.lzy.mywheelsthree.utils.JsonUtils;
import com.lzy.mywheelsthree.utils.MyToast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by bullet on 2017/11/10.
 */

public abstract class BaseObserver2<T> implements Observer<String> {
    private static final String TAG = "BaseObserver2";

    private Context mContext;
    private Class cls;

    public BaseObserver2(Context mContext, Class<T> cls) {
        this.mContext = mContext.getApplicationContext();
        this.cls = cls;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String s) {


        if (JsonUtils.getJsonStr(s,"status").equals("0")) {
//            BaseResult2 result = JsonUtils.fromJson(s, BaseResult2.class);
            String data = JsonUtils.getJsonStr(s, "data");
            T t = (T) JsonUtils.fromJson(data, cls);
            Log.d(TAG, "onNext: " + data);
            onHandleSuccess(t);

        } else {
            String message = JsonUtils.getJsonStr(s, "massage");
            Log.d(TAG, "onNext: " + message);
            onHandleError(message);
        }

    }


    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError: " + e.toString());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }

    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        Log.d(TAG, "onHandleError: " + msg);
        MyToast.makeText(msg);
    }

}
