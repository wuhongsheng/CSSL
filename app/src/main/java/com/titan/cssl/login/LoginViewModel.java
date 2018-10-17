package com.titan.cssl.login;

import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titan.base.BaseViewModel;
import com.titan.MyApplication;
import com.titan.cssl.remote.RemoteData;
import com.titan.data.source.DataRepository;
import com.titan.model.UserModel;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by hanyw on 2017/9/13/013.
 * 用户登录
 */

public class LoginViewModel extends BaseViewModel {
    private Login login;

    /**
     * 用户名
     */
    public final ObservableField<String> username = new ObservableField<>();
    /**
     * 密码
     */
    public final ObservableField<String> password = new ObservableField<>();
    /**
     * 记住密码
     */
    public final ObservableBoolean isremember = new ObservableBoolean();

    public LoginViewModel(Login login, DataRepository dataRepository) {
        this.login = login;
        this.mDataRepository = dataRepository;
    }

    /**
     * 登录
     */
    public void onLogin() {
        if (checkEmpty()) {
            login.showToast("请输入用户名和密码");
            return;
        }
        login.showProgress();

        mDataRepository.login(username.get().trim(), password.get().trim(), new RemoteData.Callback() {
            @Override
            public void onFailure(String info) {
                login.stopProgress();
                login.showToast(info);
                Log.e("tag","error："+info);
            }

            @Override
            public void onSuccess(List<? extends Map<String, ?>> info) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<UserModel>>(){}.getType();
                List<UserModel> userModel = gson.fromJson(gson.toJson(info),type);
                mDataRepository.setUserModel(userModel.get(0));
                SharedPreferences.Editor editor = MyApplication.sharedPreferences.edit();
                editor.putBoolean("isremember", isremember.get());
                editor.putString("ic_user_name", username.get().trim());
                editor.putString("ic_password", password.get().trim());
                editor.apply();
                login.onNext();
                login.stopProgress();
            }
        });
//        if (!username.get().trim().equals("admin")) {
//            login.stopProgress();
//            login.showToast("用户名错误");
//            return;
//        }
//        if (!password.get().trim().equals("123456")) {
//            login.stopProgress();
//            login.showToast("密码错误");
//            return;
//        }
//        login.stopProgress();
//        login.onNext();
    }

    /**
     * 记住用户名
     */
    public void onCheckRemember() {
        if (checkEmpty()) {
            isremember.set(!isremember.get());
            login.showToast("请输入用户名或密码");
            return;
        }
        if (isremember.get()) {
            isremember.set(true);
        } else {
            login.showToast("已取消记住用户名");
            isremember.set(false);
        }
        SharedPreferences.Editor editor = MyApplication.sharedPreferences.edit();
        editor.putBoolean("isremember", isremember.get());
        editor.putString("ic_user_name", username.get().trim());
        editor.putString("ic_password", password.get().trim());
        editor.apply();
    }

    /**
     * 检查用户名或密码是否为空
     *
     * @return 任意一个为空返回true，都不为空返回false
     */
    private boolean checkEmpty() {
        return TextUtils.isEmpty(username.get()) || TextUtils.isEmpty(password.get());
    }
    public void test(){
        Log.e("tag","test");
    }
}
