package com.titan.cssl.login;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;

import com.titan.base.BaseActivity;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityLoginBinding;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;
import com.titan.data.Injection;


/**
 * Created by hanyw on 2017/9/13/013.
 * 登录
 */

public class LoginActivity extends BaseActivity {
    public static final String LOGIN_VIEWMODEL_TAG = "LOGIN_VIEWMODEL_TAG";

    private LoginViewModel mViewModel;
    private LoginFragment mFragment;
    private SharedPreferences sharedPreferences;
    private ActivityLoginBinding binding;

    //矢量支持
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.activity_login,
                null,false);
        setContentView(binding.getRoot());
        sharedPreferences = MyApplication.sharedPreferences;
        mFragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        mFragment.setViewModel(mViewModel);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        setSupportActionBar(binding.loginToolbar);
//        binding.loginToolbar.setTitle(getResources().getString(R.string.appname));
//        binding.loginToolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        binding.loginToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        initData();
        MyApplication.getInstance().addActivity(this);
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
        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.login_frame);
        if (fragment == null) {
            fragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.login_frame);
        }
        return fragment;
    }

    public LoginViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<LoginViewModel> holder =
                (ViewModelHolder<LoginViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(LOGIN_VIEWMODEL_TAG);
        if (holder == null || holder.getViewmodel() == null) {
            LoginViewModel viewModel = new LoginViewModel(mFragment, Injection.provideDataRepository(mContext));
            ActivityUtils.addFragmentToActivity
                    (getSupportFragmentManager(), ViewModelHolder.createContainer(viewModel), LOGIN_VIEWMODEL_TAG);
            return viewModel;
        }
        return holder.getViewmodel();
    }
}
