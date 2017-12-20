package com.lzy.mywheelsthree.http;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.lzy.mywheelsthree.http.loading1.LoadingDialog;
import com.lzy.mywheelsthree.utils.MyToast;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.lzy.mywheelsthree.http.BaseObserver.ExceptionReason.CONNECT_ERROR;
import static com.lzy.mywheelsthree.http.BaseObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.lzy.mywheelsthree.http.BaseObserver.ExceptionReason.PARSE_ERROR;
import static com.lzy.mywheelsthree.http.BaseObserver.ExceptionReason.UNKNOWN_ERROR;

/**
 * Created by bullet on 2017/11/10.
 */

public abstract class BaseObserver<T> implements Observer<BaseResult<T>> {
    private static final String TAG = "BaseObserver";

    private Context mContext;


    public BaseObserver(Context context) {
        this.mContext =  context;
        LoadingDialog.showDialogForLoading((Activity) mContext);
    }

    @Override
    public void onSubscribe( Disposable d) {


    }



    @Override
    public void onNext( BaseResult<T> tBaseResult) {
       LoadingDialog.cancelDialogForLoading();
        if (tBaseResult .isSuccess()){
            T t = tBaseResult.getData();
            Log.d(TAG, "onNext: "+tBaseResult.toString());
            onHandleSuccess(t);
        }else {
            Log.d(TAG, "onNext: "+tBaseResult.toString());
            onHandleError(tBaseResult.getMassage());
        }
    }

    @Override
    public void onError( Throwable e) {
        LoadingDialog.cancelDialogForLoading();

        Log.d(TAG, "onError: "+e.toString());

        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(PARSE_ERROR);
        } else {
            onException(UNKNOWN_ERROR);
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                MyToast.makeText("网络连接失败,请检查网络");
                break;

            case CONNECT_TIMEOUT:
                MyToast.makeText("连接超时,请稍后再试");

                break;

            case BAD_NETWORK:
                MyToast.makeText("服务器异常");

                break;

            case PARSE_ERROR:
                MyToast.makeText("解析服务器响应数据失败");
                break;

            case UNKNOWN_ERROR:
            default:
                MyToast.makeText("未知错误");
                break;
        }
    }


    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }



    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        MyToast.makeText(msg);
    }

}
