package com.titan.cssl.remote;


import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by hanyw on 2017/11/8/008.
 * <p>
 * Retrofit 接口
 */

public interface RetrofitService {

    /**
     * 用户登录
     *
     * @param username
     * @param pass
     * @return
     */
    @GET("/CSSTBCMobileWebservice.asmx/Login")
    Observable<String> Login(@Query("username") String username, @Query("password") String pass);

    /**
     * 文件下载
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downFile(@Url String url);

    /**
     * 项目检索
     *
     * @param starttime
     * @param endtime
     * @param projecttype
     * @param state
     * @param keyword
     * @param role
     * @param org
     * @param id
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @GET("/CSSTBCMobileWebservice.asmx/ProjectSearch")
    Observable<String> ProjectSearch(@Query("starttime") String starttime,
                                     @Query("endtime") String endtime,
                                     @Query("projecttype") String projecttype,
                                     @Query("state") String state,
                                     @Query("keyword") String keyword,
                                     @Query("role") String role,
                                     @Query("org") String org,
                                     @Query("id") String id,
                                     @Query("pageSize") String pageSize,
                                     @Query("pageIndex") String pageIndex);

    /**
     * 整改意见上报
     *
     * @return
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/CSSTBCMobileWebservice.asmx/InsertXCZFData")
    Observable<String> InsertXCZFData(@Field("JsonString") String json);

    /**
     * 项目信息
     *
     * @param ID
     * @param type
     * @param projecttype
     * @return
     */
    @GET("/CSSTBCMobileWebservice.asmx/ProjectInfo")
    Observable<String> ProjectInfo(@Query("ID") String ID, @Query("type") int type,
                                   @Query("projecttype") String projecttype);

    @GET("/CSSTBCMobileWebservice.asmx/Statistics")
    Observable<String> Statistics();
}
