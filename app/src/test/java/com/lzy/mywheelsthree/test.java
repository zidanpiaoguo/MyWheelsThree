package com.lzy.mywheelsthree;

import android.app.Activity;

import android.os.Looper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by bullet on 2018/2/26.
 */

public class test extends Activity{


    Observable<String> data = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {

        }
    });

}
