package com.titan.cssl.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.titan.BaseActivity;
import com.titan.BaseViewModel;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;
import com.titan.data.Injection;

/**
 * Created by hanyw on 2017/11/3/003.
 * 地图浏览
 */
public class MapBrowseActivity extends BaseActivity {
    private MapBrowseFragment fragment;
    private MapBrowseViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_browse);
        fragment = (MapBrowseFragment) findOrCreateViewFragment();
        viewModel = (MapBrowseViewModel) findOrCreateViewModel();
        fragment.setViewModel(viewModel);
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public Fragment findOrCreateViewFragment() {
        MapBrowseFragment fragment = (MapBrowseFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_frame);
        if (fragment == null) {
            fragment = MapBrowseFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.map_frame);
        }
        return fragment;
    }

    @Override
    public BaseViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<MapBrowseViewModel> holder = (ViewModelHolder<MapBrowseViewModel>)
                getSupportFragmentManager().findFragmentByTag(VIEWMODEL_TAG);
        if (holder == null || holder.getViewmodel() == null) {
            MapBrowseViewModel model = new MapBrowseViewModel(fragment, Injection.provideDataRepository(this));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(model), VIEWMODEL_TAG);
            return model;
        }
        return holder.getViewmodel();
    }
}
