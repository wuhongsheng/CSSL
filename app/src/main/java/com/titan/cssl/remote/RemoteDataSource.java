package com.titan.cssl.remote;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.titan.model.Approval;
import com.titan.model.ResultModel;
import com.titan.model.ResultModel2;
import com.titan.model.Supervise;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
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

    /**
     * 项目检索
     *
     * @param starttime   开始时间 yyyy-MM-dd HH:mm
     * @param endtime     结束时间 yyyy-MM-dd HH:mm
     * @param projecttype 项目类型 -1:"全部",1：3万㎡以下；2：3-8万㎡；3：8万㎡以上
     * @param state       审批状态
     * @param keyword     关键词
     * @param role        用户角色ID 3.0
     * @param org         用户所属区域ID
     * @param id          用户ID 29.0
     * @param pageSize    每页所包含数据条目数量 10
     * @param pageIndex   页码 1
     * @param callback    信息处理回调接口
     */
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

    /**
     * 项目详细信息
     *
     * @param ID          项目ID
     * @param type        信息类别(0，基本信息；1，概要信息；2，水保措施，3、行政审批记录；4、日常监督记录
     * @param projecttype 项目类型 -1:"全部",1：3万㎡以下；2：3-8万㎡；3：8万㎡以上
     * @param callback    信息处理回调接口
     */
    @Override
    public void ProjectInfo(String ID, final int type, String projecttype, final Callback callback) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer()
                .ProjectInfo(ID, type, projecttype);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    /**
     * 整改意见添加
     *
     * @param json     整改意见json
     * @param callback 信息处理回调接口
     */
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
                        setInsertErrorInfo(e, callback);
                    }

                    @Override
                    public void onNext(String s) {
                        setInsertSuccessInfo(s, callback);
                    }
                });
    }

    @Override
    public void InsertZB(String projectBH, String projectType, String projectZB, final infoCallback callback) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer()
                .InsertZB(projectBH, projectType, projectZB);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        setInsertErrorInfo(e, callback);
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        ResultModel2<?> resultModel = gson.fromJson(s, ResultModel2.class);
                        if (resultModel.getResult().equals("成功")) {
                            callback.onSuccess(resultModel.getMessage());
                        } else {
                            callback.onFailure(resultModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 项目统计
     */
    @Override
    public void Statistics(final Callback callback) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer().Statistics();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
    public void downLoadFile(String url, final infoCallback callback) {
        Observable<ResponseBody> observable = RetrofitHelper.getInstance(mContext).getServer()
                .downFile(url);
        Subscriber<ResponseBody> subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.e("tag", "end");
            }

            @Override
            public void onError(Throwable e) {
                String message = new HttpResultFunc<String>().call(e);
                if (message == null) {
                    callback.onFailure(e.getMessage());
                } else {
                    callback.onFailure(message);
                }
                Log.e("tag", "failed1" + e);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String path = DownLoadManager.writeResponseBodyToDisk(mContext, responseBody);
                if (!path.equals("")) {
                    callback.onSuccess(path);
                    Log.e("tag", "success:" + path);
                } else {
                    callback.onFailure("文件加载错误");
                }
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(subscriber);
    }


    /**
     * 网络请求错误信息处理
     *
     * @param e
     * @param callback
     */
    private void setErrorInfo(Throwable e, Callback callback) {
        String message = new HttpResultFunc<String>().call(e);
        if (message == null) {
            callback.onFailure(e.getMessage());
        } else {
            callback.onFailure(message);
        }
    }

    /**
     * 整改意见上传错误信息处理
     *
     * @param e
     * @param callback
     */
    private void setInsertErrorInfo(Throwable e, infoCallback callback) {
        String message = new HttpResultFunc<String>().call(e);
        if (message == null) {
            callback.onFailure(e.getMessage());
        } else {
            callback.onFailure(message);
        }
    }

    /**
     * 网络请求成功信息处理
     *
     * @param s
     * @param callback
     */
    private void setSuccessInfo(String s, Callback callback) {
        Gson gson = new Gson();
        ResultModel<?> resultModel = gson.fromJson(s, ResultModel.class);
        if (resultModel.getResult()) {
            if (resultModel.getMessage().equals("未查询到数据记录！") ||
                    resultModel.getMessage().equals("没有日常监督检查记录") ||
                    resultModel.getMessage().equals("没有审批流程")) {
                callback.onFailure(resultModel.getMessage());
                return;
            }
            callback.onSuccess(resultModel.getContent());
        } else {
            callback.onFailure(resultModel.getMessage());
        }
    }

    /**
     * 整改意见上传返回信息处理
     *
     * @param s
     * @param callback
     */
    private void setInsertSuccessInfo(String s, infoCallback callback) {
        if (s.equals("0")) {
            callback.onFailure("上传失败");
        }
        if (s.equals("1")) {
            callback.onSuccess("上传成功");
        } else {
            callback.onFailure(s);
        }
    }
}
