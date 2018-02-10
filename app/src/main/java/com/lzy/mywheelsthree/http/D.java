package com.lzy.mywheelsthree.http;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by bullet on 2018/2/6.
 */

public abstract class D implements Function<ResponseBody, File> {
    private static final String TAG = "D";
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;


    public D(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }



    @Override
    public File apply(ResponseBody t) throws Exception {

        Log.d(TAG, "apply: ");
        return saveFile(t);
        
        
    }
    public abstract void inProgress(float progress);

    public File saveFile(ResponseBody response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[1024*1024];
        int len = 0;
        FileOutputStream fos = null;
        try {


            is = response.byteStream();
            final long total = response.contentLength();
            Log.d(TAG, "saveFile: "+total);
            long sum = 0;

            //创建获取缓存目录
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //创建文件
            File file = new File(dir, destFileName);

            fos = new FileOutputStream(file);

            Log.d(TAG, "saveFile:==== "+len);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);

                final long finalSum = sum;
                Log.d(TAG, "saveFile: "+sum);

                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {

                        inProgress(finalSum * 1.0f / total);

                    }
                });
                thread.start();
            }
            fos.flush();

            return file;

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
