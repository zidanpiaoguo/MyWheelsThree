package com.lzy.mywheelsthree.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lzy.mywheelsthree.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by bullet on 2018/1/2.
 */

public class OkHttpActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        String url = "http://write.blog.csdn.net/postlist/0/0/enabled/1";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


        File file = new File("");
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);

        RequestBody body1 = new FormBody.Builder()
                .add("name","sd")
                .add("time","ni")
                .build();




        Request request1 = new Request.Builder()
                .url("")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request1).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


            }
        });


    }
}
