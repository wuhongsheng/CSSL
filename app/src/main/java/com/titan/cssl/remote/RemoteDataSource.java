package com.titan.cssl.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.titan.model.UserModel;
import com.titan.model.ResultModel;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hanyw on 2017/11/13/013.
 * 网络数据请求
 */

public class RemoteDataSource implements RemoteData{

    private Context mContext;

    private static RemoteDataSource sington;

    private RemoteDataSource(Context context){
        mContext = context;
    }

    public static RemoteDataSource getInstance(Context context){
        if (sington==null){
            sington = new RemoteDataSource(context);
        }
        return sington;
    }


    @Override
    public void login(String name, String password, final loginCallback callback) {
        final Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer().login(name,password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = new HttpResultFunc<String>().call(e);
                        if (message==null){
                            callback.onFailure(e.getMessage());
                        }else {
                            callback.onFailure(message);
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        ResultModel<UserModel> resultModel = gson.fromJson(s,ResultModel.class);
                        if (resultModel.getResult()){
                            callback.onSuccess(gson.toJson(resultModel.getContent()));
                        }else {
                            callback.onFailure(resultModel.getMessage());
                        }
                    }
                });
    }
}
