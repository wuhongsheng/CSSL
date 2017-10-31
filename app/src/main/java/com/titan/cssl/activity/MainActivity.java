package com.titan.cssl.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.titan.cssl.BaseActivity;
import com.titan.cssl.BaseViewModel;
import com.titan.cssl.MyApplication;
import com.titan.cssl.R;

public class MainActivity extends BaseActivity {

    @Override
    public Fragment findOrCreateViewFragment() {
        return null;
    }

    @Override
    public BaseViewModel findOrCreateViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        MyApplication.getInstance();
    }
}
