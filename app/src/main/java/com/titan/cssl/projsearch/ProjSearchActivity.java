package com.titan.cssl.projsearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.titan.BaseActivity;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivitySearchBinding;
import com.titan.cssl.statistics.StatisticsFragment;
import com.titan.cssl.statistics.StatisticsViewModel;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;
import com.titan.data.Injection;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索
 */

public class ProjSearchActivity extends BaseActivity {
    public static final String SEARCH_VIEWMODEL_TAG = "SEARCH_VIEWMODEL_TAG";

    private ProjSearchViewModel mViewModel;
    private ProjSearchFragment fragment;
    private ActivitySearchBinding binding;
    private long mExitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SDKInitializer.initialize(getApplicationContext());
        binding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.activity_search,
                null,false);
        setContentView(binding.getRoot());
        fragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        fragment.setViewModel(mViewModel);
        mViewModel.projType.set(getIntent().getExtras().getInt("type"));
        mViewModel.getProjType();
        MyApplication.getInstance().addActivity(this);
    }

    /**
     * 按两次退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode==KeyEvent.KEYCODE_BACK){
//            if ((System.currentTimeMillis() - mExitTime) > 1500){
//                Toast.makeText(mContext,"再按一次退出程序",Toast.LENGTH_SHORT).show();
//                mExitTime = System.currentTimeMillis();
//            }else {
//                MyApplication.getInstance().exit();
//            }
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
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
            ProjSearchViewModel model = new ProjSearchViewModel(mContext,fragment,Injection.provideDataRepository(mContext));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(model),SEARCH_VIEWMODEL_TAG);
            return model;
        }
        return holder.getViewmodel();
    }
}
