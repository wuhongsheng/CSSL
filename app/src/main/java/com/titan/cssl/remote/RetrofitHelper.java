package com.titan.cssl.remote;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.titan.cssl.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by hanyw on 2017/11/8/008.
 *
 * Retrofit 初始化
 */

public class RetrofitHelper {
    private Context mCntext;

    private static NetworkMonitor networkMonitor;
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    public static RetrofitHelper getInstance(Context context){
        if (instance == null){
            instance = new RetrofitHelper(context);
            networkMonitor=new LiveNetworkMonitor(context);
        }
        return instance;
    }
    private RetrofitHelper(Context mContext){
        mCntext = mContext;
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addNetworkInterceptor(new MyNetworkInterceptor());
        okHttpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mCntext.getResources().getString(R.string.serverhost))//接口
                .client(okHttpClientBuilder.build())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }
    private class MyNetworkInterceptor implements Interceptor
    {
        @Override
        public Response intercept(Chain chain) throws IOException {
            boolean connected = networkMonitor.isConnected();
            if (networkMonitor.isConnected()) {
                return chain.proceed(chain.request());
            } else {
               // ToastUtil.setToast(mCntext,"网络错误，请检查网络连接");
            }
            return null;
        }
    }
}
