package com.lzy.mywheelsthree.ftp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.lzy.mywheelsthree.R;
import com.lzy.mywheelsthree.utils.MyToast;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by bullet on 2017/12/26.
 */

public class FtpAcitivity extends AppCompatActivity {

    private static final String TAG = "FtpAcitivity";


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftp);

        initView();
    }

    private void initView() {

        //上传功能
        //new FTP().uploadMultiFile为多文件上传
        //new FTP().uploadSingleFile为单文件上传
        Button buttonUpload = (Button) findViewById(R.id.button_upload);



        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("下载中...");
        progressDialog.setMax(100);




        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        // 上传
                        String location =Environment.getExternalStorageDirectory().getPath()+"/sintel.mp4";
                        File file = new File(location);
                        String upname = UUID.randomUUID().toString().replace("-","")+"."+getFileType(location);

                        try {

                            //单文件上传
                            new FTP().uploadSingleFile(file, "",upname,new FTP.UploadProgressListener(){

                                @Override
                                public void onUploadProgress(String currentStep,long uploadSize,File file) {
                                    // TODO Auto-generated method stub
                                    Log.d(TAG, currentStep);
                                    if(currentStep.equals(FtpConfig.FTP_UPLOAD_SUCCESS)){
                                        Log.d(TAG, "-----shanchuan--successful");
                                        progressDialog.dismiss();
                                    } else if(currentStep.equals(FtpConfig.FTP_UPLOAD_LOADING)){


                                        long fize = file.length();
                                        float num = (float)uploadSize / (float)fize;
                                        final int result = (int)(num * 100);

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.setProgress(result);
                                            }
                                        }).start();


                                        Log.d(TAG, "-----shangchuan---"+result + "%");
                                    }
                                }
                            });
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });

        //下载功能
        Button buttonDown = (Button)findViewById(R.id.button_down);
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        // 下载
                        try {

                            //单文件下载
                            new FTP().downloadSingleFile("/Animals.mp4","/mnt/sdcard/download/","ftpTest.docx",new FTP.DownLoadProgressListener(){

                                @Override
                                public void onDownLoadProgress(String currentStep, long downProcess, File file) {
                                    Log.d(TAG, currentStep);
                                    if(currentStep.equals(FtpConfig.FTP_DOWN_SUCCESS)){
                                        Log.d(TAG, "-----xiazai--successful");
                                    } else if(currentStep.equals(FtpConfig.FTP_DOWN_LOADING)){
                                        Log.d(TAG, "-----xiazai---"+downProcess + "%");
                                    }
                                }

                            });

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });

        //删除功能
        Button buttonDelete = (Button)findViewById(R.id.button_delete);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = Environment.getExternalStorageDirectory().getPath()+"/sintel.mp4";
                File file = new File(location);

                Log.d(TAG, "onClick Name : "+file.getName());
                String s = UUID.randomUUID().toString().replace("-","")+"."+getFileType(location);
                Log.d(TAG, "onClick UUID: "+s);
                MyToast.makeText(s);
            }
        });



//
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        // 删除
//                        try {
//
//                            new FTP().deleteSingleFile("/fff/ftpTest.docx",new FTP.DeleteFileProgressListener(){
//
//                                @Override
//                                public void onDeleteProgress(String currentStep) {
//                                    Log.d(TAG, currentStep);
//                                    if(currentStep.equals(FtpConfig.FTP_DELETEFILE_SUCCESS)){
//                                        Log.d(TAG, "-----shanchu--success");
//                                    } else if(currentStep.equals(FtpConfig.FTP_DELETEFILE_FAIL)){
//                                        Log.d(TAG, "-----shanchu--fail");
//                                    }
//                                }
//
//                            });
//
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//                    }
//                }).start();
//
//            }
//        });

    }



    /***
     * 获取文件类型
     *
     * @param paramString 文件名
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');

        if (i <= -1) {
            return str;
        }
        str = paramString.substring(i + 1);

        return str;
    }
}
