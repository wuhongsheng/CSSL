package com.titan.cssl.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.titan.baselibrary.util.PadUtil;
import com.titan.cssl.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;
import com.titan.util.DeviceUtil;


/**
 * Created by hanyw on 2017/9/13/013.
 * 登录
 */

public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_VIEWMODEL_TAG = "LOGIN_VIEWMODEL_TAG";

    private LoginViewModel mViewModel;
    private LoginFragment mFragment;
    private Context mContext;
    private SharedPreferences sharedPreferences;

    //矢量支持
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_login);
        MyApplication.getInstance();
        sharedPreferences = MyApplication.sharedPreferences;
        mFragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        mFragment.setViewModel(mViewModel);

        //判断是否是pad，是则使用横屏
        if(DeviceUtil.isTablet(mContext)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initData();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        mViewModel.isremember.set(sharedPreferences.getBoolean("isremember", false));
        if (mViewModel.isremember.get()) {
            mViewModel.username.set(sharedPreferences.getString("ic_user_name", ""));
            mViewModel.password.set(sharedPreferences.getString("ic_password", ""));
        }
    }

    public LoginFragment findOrCreateViewFragment() {
        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }
        return fragment;
    }

    public LoginViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<LoginViewModel> holder =
                (ViewModelHolder<LoginViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(LOGIN_VIEWMODEL_TAG);
        if (holder == null || holder.getViewmodel() == null) {
            LoginViewModel viewModel = new LoginViewModel(mContext, mFragment);
            ActivityUtils.addFragmentToActivity
                    (getSupportFragmentManager(), ViewModelHolder.createContainer(viewModel), LOGIN_VIEWMODEL_TAG);
            return viewModel;
        }
        return holder.getViewmodel();
    }
}
