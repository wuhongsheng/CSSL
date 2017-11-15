package com.titan.cssl.remote;

/**
 * Created by hanyw on 2017/11/13/013.
 * 网络请求处理接口
 */

public interface RemoteData {

    interface loginCallback{
        void onFailure(String info);
        void onSuccess(String info);
    }

    void login(String name,String password,loginCallback callback);
}
