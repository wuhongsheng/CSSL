package com.titan.cssl.search;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.titan.cssl.BaseActivity;
import com.titan.cssl.R;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;
import com.titan.util.DeviceUtil;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索
 */

public class SearchActivity extends BaseActivity {
    public static final String SEARCH_VIEWMODEL_TAG = "SEARCH_VIEWMODEL_TAG";

    private Context mContext;

    private SearchViewModel mViewModel;
    private SearchFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_search);
        fragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        fragment.setViewModel(mViewModel);

        //判断是否是pad，是则使用横屏
        if(DeviceUtil.isTablet(mContext)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public SearchFragment findOrCreateViewFragment() {
        SearchFragment fragment = (SearchFragment) getSupportFragmentManager()
                .findFragmentById(R.id.search_frame);
        if (fragment == null) {
            fragment = SearchFragment.getInstance(mContext);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.search_frame);
        }
        return fragment;
    }

    @Override
    public SearchViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<SearchViewModel> holder = (ViewModelHolder<SearchViewModel>)
                getSupportFragmentManager().findFragmentByTag(SEARCH_VIEWMODEL_TAG);
        if (holder==null||holder.getViewmodel()==null){
            SearchViewModel model = new SearchViewModel(fragment);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(model),SEARCH_VIEWMODEL_TAG);
            return model;
        }
        return holder.getViewmodel();
    }
}
