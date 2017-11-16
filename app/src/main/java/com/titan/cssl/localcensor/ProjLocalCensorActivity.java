package com.titan.cssl.localcensor;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.titan.BaseActivity;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityLocalCensorBinding;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;

/**
 * Created by hanyw on 2017/11/6/006.
 * 项目现场审查
 */

public class ProjLocalCensorActivity extends BaseActivity {


    private static final String LOCALCENSOR_TAG = "LOCALCENSOR_TAG";
    private ActivityLocalCensorBinding binding;
    private ProjLocalCensorFragment fragment;
    private ProjLocalCensorViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.activity_local_censor,null,false);
        setContentView(binding.getRoot());
        fragment = findOrCreateViewFragment();
        viewModel = findOrCreateViewModel();
        fragment.setViewModel(viewModel);
        initView();

        MyApplication.getInstance().addActivity(this);
    }

    /**
     * 布局初始化
     */
    private void initView() {
        Toolbar toolbar = binding.censorToolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.appname));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public ProjLocalCensorFragment findOrCreateViewFragment() {
        ProjLocalCensorFragment fragment = (ProjLocalCensorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.censor_frame);
        if (fragment == null) {
            fragment = ProjLocalCensorFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.censor_frame);
        }
        return fragment;
    }

    @Override
    public ProjLocalCensorViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<ProjLocalCensorViewModel> holder = (ViewModelHolder<ProjLocalCensorViewModel>)
                getSupportFragmentManager().findFragmentByTag(LOCALCENSOR_TAG);
        if (holder==null||holder.getViewmodel()==null){
            ProjLocalCensorViewModel model = new ProjLocalCensorViewModel(fragment);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(model),LOCALCENSOR_TAG);
            return model;
        }
        return holder.getViewmodel();
    }
}
