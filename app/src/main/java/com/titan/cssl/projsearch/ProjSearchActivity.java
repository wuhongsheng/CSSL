package com.titan.cssl.projsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.titan.cssl.BaseActivity;
import com.titan.cssl.R;
import com.titan.cssl.data.local.LocalDataSource;
import com.titan.cssl.data.source.DataRepository;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索
 */

public class ProjSearchActivity extends BaseActivity {
    public static final String SEARCH_VIEWMODEL_TAG = "SEARCH_VIEWMODEL_TAG";

    private ProjSearchViewModel mViewModel;
    private ProjSearchFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        fragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        fragment.setViewModel(mViewModel);

    }

    @Override
    public ProjSearchFragment findOrCreateViewFragment() {
        ProjSearchFragment fragment = (ProjSearchFragment) getSupportFragmentManager()
                .findFragmentById(R.id.search_frame);
        if (fragment == null) {
            fragment = ProjSearchFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.search_frame);
        }
        return fragment;
    }

    @Override
    public ProjSearchViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<ProjSearchViewModel> holder = (ViewModelHolder<ProjSearchViewModel>)
                getSupportFragmentManager().findFragmentByTag(SEARCH_VIEWMODEL_TAG);
        if (holder==null||holder.getViewmodel()==null){
            LocalDataSource source = LocalDataSource.getInstance(mContext);
            DataRepository dataRepository = DataRepository.getInstance(source);
            ProjSearchViewModel model = new ProjSearchViewModel(fragment,dataRepository);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(model),SEARCH_VIEWMODEL_TAG);
            return model;
        }
        return holder.getViewmodel();
    }
}
