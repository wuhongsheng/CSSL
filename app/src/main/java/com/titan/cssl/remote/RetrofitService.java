package com.titan.cssl.remote;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by hanyw on 2017/11/8/008.
 *
 * Retrofit 接口
 */

public interface RetrofitService {

    @GET("/FireServices.asmx/Login")
    Observable<String> login(@Query("usernaem")String username, @Query("password")String pass);

    @Streaming
    @GET
    Observable<ResponseBody> downFile(@Url String url);


}
