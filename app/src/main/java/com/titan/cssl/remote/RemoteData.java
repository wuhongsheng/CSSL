package com.titan.cssl.remote;

import java.util.List;
import java.util.Map;

/**
 * Created by hanyw on 2017/11/13/013.
 * 网络请求处理接口
 */

public interface RemoteData {

    interface Callback {
        void onFailure(String info);

        void onSuccess(List<? extends Map<String, ?>> info);
    }

    interface infoCallback{
        void onFailure(String info);

        void onSuccess(String info);
    }

    void login(String name, String password, Callback callback);

    void projSearch(String starttime, String endtime, String projecttype, String state,
                    String keyword, String role, String org, String id, String pageSize,
                    String pageIndex, Callback callback);

    void ProjectInfo(String ID, int type, String projecttype, Callback callback);

    void InsertXCZFData(String json, infoCallback callback);
}
