package com.lzy.mywheelsthree.http;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by bullet on 2018/2/2.
 */

public class RxManage {
    private CompositeDisposable mComposable = new CompositeDisposable();

    public void register(Disposable disposable){

        mComposable.add(disposable);
    }
    public void unSubscribe(){
        mComposable.dispose();
    }

}
