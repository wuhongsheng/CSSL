package com.titan.cssl.remote;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.titan.model.ResultModel;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hanyw on 2017/11/13/013.
 * 网络数据请求
 */

public class RemoteDataSource implements RemoteData {

    private Context mContext;

    private static RemoteDataSource sington;

    private RemoteDataSource(Context context) {
        mContext = context;
    }

    public static RemoteDataSource getInstance(Context context) {
        if (sington == null) {
            sington = new RemoteDataSource(context);
        }
        return sington;
    }


    /**
     * 用户登录
     *
     * @param name     用户名
     * @param password 密码
     * @param callback 接口回调
     */
    @Override
    public void login(String name, String password, final Callback callback) {
        final Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer().Login(name, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        setErrorInfo(e, callback);
                    }

                    @Override
                    public void onNext(String s) {
                        setSuccessInfo(s, callback);
                    }
                });
    }

    @Override
    public void projSearch(String starttime, String endtime, String projecttype, String state,
                           String keyword, String role, String org, String id, String pageSize,
                           String pageIndex, final Callback callback) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer()
                .ProjectSearch(starttime, endtime, projecttype, state, keyword, role, org, id, pageSize,
                        pageIndex);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        setErrorInfo(e, callback);
                    }

                    @Override
                    public void onNext(String s) {
                        setSuccessInfo(s, callback);
                    }
                });
    }

    @Override
    public void ProjectInfo(String ID, int type, String projecttype, final Callback callback) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer()
                .ProjectInfo(ID,type,projecttype);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        setErrorInfo(e,callback);
                    }

                    @Override
                    public void onNext(String s) {
                        setSuccessInfo(s,callback);
                    }
                });
    }

    @Override
    public void InsertXCZFData(String json, final infoCallback callback) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer()
                .InsertXCZFData(json);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        setInsertErrorInfo(e,callback);
                    }

                    @Override
                    public void onNext(String s) {
                        setInsertSuccessInfo(s,callback);
                    }
                });
    }

    private void setErrorInfo(Throwable e, Callback callback) {
        String message = new HttpResultFunc<String>().call(e);
        if (message == null) {
            callback.onFailure(e.getMessage());
        } else {
            callback.onFailure(message);
        }
    }

    private void setInsertErrorInfo(Throwable e, infoCallback callback) {
        String message = new HttpResultFunc<String>().call(e);
        if (message == null) {
            callback.onFailure(e.getMessage());
        } else {
            callback.onFailure(message);
        }
    }

    private void setSuccessInfo(String s, Callback callback) {
        Gson gson = new Gson();
        ResultModel<?> resultModel = gson.fromJson(s, ResultModel.class);
        if (resultModel.getResult()) {
                if (resultModel.getMessage().equals("未查询到数据记录！")){
                    callback.onFailure(resultModel.getMessage());
                    return;
                }
                callback.onSuccess(resultModel.getContent());
        } else {
            callback.onFailure(resultModel.getMessage());
        }
    }

    private void setInsertSuccessInfo(String s,infoCallback callback){
        if (s.equals("0")){
            callback.onFailure("上传失败");
            return;
        }
        if (s.equals("1")){
            callback.onSuccess("上传成功");
        }
    }
}
