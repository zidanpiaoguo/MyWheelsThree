package com.lzy.mywheelsthree.http;

import com.lzy.mywheelsthree.model.Attestation;
import com.lzy.mywheelsthree.model.TokenData;
import com.lzy.mywheelsthree.model.UserInfor;
import com.lzy.mywheelsthree.model.UserInformation;
import com.lzy.mywheelsthree.model.UploadImage;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by bullet on 2017/11/10.
 */

public interface RetrofitService {


    @POST("userLogin.do")
    @FormUrlEncoded
    Observable<BaseResult<UserInfor>> login(
            @Field("username") String userId,
            @Field("password") String password);



    @POST("login")
    Observable<BaseResult<TokenData>> getToken(@Body Map<String,Object> map);


    @POST("getMemberInfo")
    Observable<BaseResult<UserInformation>> getUser();



    @Streaming
    @GET
    Observable<ResponseBody> getDownload(@Url String url);


    @Multipart
    @POST("uploadPortrait")
    Observable<BaseResult<UploadImage>> uploadPortrait(@Part List<MultipartBody.Part> partList);


    //这种上传字符,会自动加双引号
    @Multipart
    @POST("index.php?app=upload&act=portrait")
    Observable<BaseResult<String>> setHead(@Part("user_id") int id,@Part List<MultipartBody.Part> partList);


    //这种就不会含有双引号
    @Multipart
    @POST("index.php?app=upload&act=auth_image")
    Observable<BaseResult<Attestation>> Attestation(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> partList);

}
