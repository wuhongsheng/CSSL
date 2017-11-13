package com.titan.cssl.projsearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.titan.BaseActivity;
import com.titan.cssl.R;
import com.titan.data.Injection;
import com.titan.data.local.LocalDataSource;
import com.titan.data.source.DataRepository;
import com.titan.cssl.databinding.ActivitySearchBinding;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
            ProjSearchViewModel model = new ProjSearchViewModel(fragment,Injection.provideDataRepository(mContext));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(model),SEARCH_VIEWMODEL_TAG);
            return model;
        }
        return holder.getViewmodel();
    }
}
