package com.titan.cssl.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.titan.cssl.R;
import com.titan.cssl.databinding.FragLoginBinding;
import com.titan.cssl.projsearch.ProjSearchActivity;
import com.titan.cssl.statistics.StatisticsActivity;
import com.titan.util.MaterialDialogUtil;
import com.titan.util.MyFileUtil;

/**
 * Created by hanyw on 2017/9/13/013.
 * 用户登录
 */

public class LoginFragment extends Fragment implements Login {
    private Context mContext;
    private FragLoginBinding fragLoginBinding;
    private LoginViewModel loginViewModel;
    private MaterialDialog dialog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public void setViewModel(LoginViewModel viewModel) {
        loginViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        fragLoginBinding = FragLoginBinding.inflate(inflater, container, false);
        fragLoginBinding.setViewmodel(loginViewModel);
        return fragLoginBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onNext() {
        if (isAdded()) {
            Intent intent = new Intent(mContext, StatisticsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void showProgress() {
        dialog = MaterialDialogUtil.showLoadProgress(mContext, mContext.getString(R.string.logging)).build();
        dialog.show();
    }

    @Override
    public void stopProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void showToast(String info) {
        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
    }
}