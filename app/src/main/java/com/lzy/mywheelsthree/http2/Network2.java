package com.lzy.mywheelsthree.http2;

import android.content.Context;

import com.lzy.mywheelsthree.R;
import com.lzy.mywheelsthree.utils.MyToast;
import com.lzy.mywheelsthree.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bullet on 2017/11/10.
 */

public  class Network2 {

    /**
     * 线程调度
     */
    public  static   <T> ObservableTransformer<T, T> compose(final Context context) {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                            //     可添加网络连接判断等
                                if (!Utils.isNetworkAvailable()) {
                                    MyToast.makeText(R.string.toast_network_error);
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
