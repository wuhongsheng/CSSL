package com.titan.cssl.projsearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.titan.BaseActivity;
import com.titan.cssl.R;
import com.titan.data.local.LocalDataSource;
import com.titan.data.source.DataRepository;
import com.titan.cssl.databinding.ActivitySearchBinding;
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
    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.activity_search,
                null,false);
        setContentView(binding.getRoot());
        fragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        fragment.setViewModel(mViewModel);
        setSupportActionBar(binding.searchToolbar);
        binding.searchToolbar.setTitle(getResources().getString(R.string.appname));
        binding.searchToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        binding.searchToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
