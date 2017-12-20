package com.lzy.mywheelsthree.http;

import android.content.Context;
import android.util.Log;

import com.lzy.mywheelsthree.R;
import com.lzy.mywheelsthree.utils.MyToast;
import com.lzy.mywheelsthree.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by bullet on 2017/11/10.
 */

public  class Network {

    /**
     * 线程调度
     */
    public  static   <T> ObservableTransformer<T, T> compose() {

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




    /**
     *
     * 保存数据
     *
     * @param response   返回值
     * @param destFileDir   目标文件存储的文件夹路径
     * @param destFileName  目标文件存储的文件名
     * @return
     * @throws IOException
     */
    public static float saveFile(ResponseBody response,String destFileDir,String destFileName) throws IOException {
        final float[] progress = new float[1];

        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {


            is = response.byteStream();
            final long total = response.contentLength();
            long sum = 0;

            //创建获取缓存目录
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //创建文件
            File file = new File(dir, destFileName);

            fos = new FileOutputStream(file);

            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;



                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {

                        progress[0] =finalSum * 1.0f / total;

                    }
                });
                thread.start();
            }
            fos.flush();

//            return file;
            return progress[0];

        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }

        }

    }

}
