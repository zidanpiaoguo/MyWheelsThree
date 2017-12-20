package com.lzy.mywheelsthree;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lzy.mywheelsthree.http.BaseDownload;
import com.lzy.mywheelsthree.http.BaseObserver;
import com.lzy.mywheelsthree.http.BaseResult;
import com.lzy.mywheelsthree.http.Network;
import com.lzy.mywheelsthree.http.RetrofitFactory;
import com.lzy.mywheelsthree.http2.BaseObserver2;
import com.lzy.mywheelsthree.http2.Network2;
import com.lzy.mywheelsthree.http2.RetrofitFactory2;
import com.lzy.mywheelsthree.model.TokenData;
import com.lzy.mywheelsthree.model.UploadImage;
import com.lzy.mywheelsthree.model.UserData;
import com.lzy.mywheelsthree.model.UserInformation;
import com.lzy.mywheelsthree.utils.Autils;
import com.lzy.mywheelsthree.utils.ImageCompression;
import com.lzy.mywheelsthree.utils.MyToast;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private TextView text;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
        init();

    }

    private void test() {
        text = (TextView) findViewById(R.id.test);
    }

    private void init() {

//        LoadingDialog.showDialogForLoading(this);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httptest();
            }
        });
    }

    /**
     * 正规返回值，用gson 直接解析
     */
    private void httptest() {

//        //异常返回数据请求
//        http2test();

//        //嵌套请求。
//        NestRequest();


//        //一次性请求
//        BasicRequest();


//        //下载请求
//        Download();

//        //上传图片
//        uploadImage();

        final String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String fileName = fileDir + "/" + "4.jpg";

        File file = new File(fileName);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        builder.addFormDataPart("portrait_image",file.getName(),requestBody);

        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitFactory.getInstance()
                .setHead(1,parts)
                .compose(Network.<BaseResult<String>>compose())
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onHandleSuccess(String s) {

                        MyToast.makeText(s);
                        Log.d(TAG, "onHandleSuccess: "+s);
                    }
                });
//

    }

    private void NestRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "15736709271");
        map.put("password", "123456");
        RetrofitFactory
                .getInstance()
                .getToken(map)
                .flatMap(new Function<BaseResult<TokenData>, ObservableSource<BaseResult<UserInformation>>>() {
                    @Override
                    public ObservableSource<BaseResult<UserInformation>> apply(BaseResult<TokenData> tokenDataBaseResult) throws Exception {
                        return RetrofitFactory.getInstance().getUser();
                    }
                })
                .compose(Network.<BaseResult<UserInformation>>compose())
                .subscribe(new BaseObserver<UserInformation>(MainActivity.this) {
                    @Override
                    protected void onHandleSuccess(UserInformation userInformation) {
                        Log.d(TAG, "onHandleSuccess: " + userInformation.getCertifiId());

                        MyToast.makeText(userInformation.toString());
                    }
                });
    }

    private void BasicRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "15736709271");
        map.put("password", "123456");
        RetrofitFactory
                .getInstance()
                .getToken(map)
                .compose(Network.<BaseResult<TokenData>>compose())
                .subscribe(new BaseObserver<TokenData>(MainActivity.this) {
                    @Override
                    protected void onHandleSuccess(TokenData tokenData) {
                        Log.d(TAG, "onHandleSuccess: " + tokenData.getToken());
                    }
                });
    }


    /**
     * 上传图片
     */
    private void uploadImage() {
        final String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String fileName = fileDir + "/" + "te.jpg";

        File file = new File(fileName);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型

        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        //imgfile 后台接收图片流的参数名

        //如果上传多张图片，就用for循环添加多张图片
        builder.addFormDataPart("portrait", file.getName(), imageBody);



        List<MultipartBody.Part> parts = builder.build().parts();


        RetrofitFactory.getInstance()
                .uploadPortrait(parts)
                .compose(Network.<BaseResult<UploadImage>>compose())
                .subscribe(new BaseObserver<UploadImage>(MainActivity.this) {
                    @Override
                    protected void onHandleSuccess(UploadImage uploadImage) {
                        Log.d(TAG, "onHandleSuccess: " + uploadImage.getPortrait());
                        MyToast.makeText(uploadImage.getPortrait());
                    }
                });
    }


    /**
     * 下载apk
     * 由于可能需要不同网络环境，对应不同下载情况
     * 这个就没加网络限制，需要自己外部检查是什么网络
     */
    private void Download() {
        String url = "http://106.14.249.176:8080/app/freebuy.apk";
        final String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String fileName = "bullet_" + "1" + ".apk";
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("下载中...");
        progressDialog.setMax(100);
        progressDialog.show();


        RetrofitFactory.getInstance()
                .getDownload(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new BaseDownload(MainActivity.this, fileDir, fileName) {
                    @Override
                    public void inProgress(float progress) {
                        progressDialog.setProgress((int) (100 * progress));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onNext: ");
                        text.setText("下载成功！");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                    }
                });
    }


    /**
     * 异常返回值，自己写的 解析
     */

    private void http2test() {
        Observable<String> observable = RetrofitFactory2.getInstance()
                .login("admin", "admin");
        observable
                .compose(Network2.<String>compose(MainActivity.this))
                .subscribe(new BaseObserver2<UserData>(MainActivity.this, UserData.class) {
                    @Override
                    protected void onHandleSuccess(UserData userData) {
                        Log.d(TAG, "onHandleSuccess: " + userData.toString());


                    }
                });
    }
}



