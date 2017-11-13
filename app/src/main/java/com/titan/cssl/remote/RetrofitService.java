package com.titan.cssl.remote;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hanyw on 2017/11/8/008.
 *
 * Retrofit 接口
 */

public interface RetrofitService {

    @GET("/FireServices.asmx/Login")
    Observable<String> login(@Query("usernaem")String username, @Query("password")String pass);
}
