package com.titan.cssl.measures;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.titan.BaseActivity;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityMeasureBinding;
import com.titan.cssl.statistics.StatisticsFragment;
import com.titan.cssl.statistics.StatisticsViewModel;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;
import com.titan.data.Injection;
import com.titan.model.ProjDetailMeasure;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目措施
 */

public class MeasureActivity extends BaseActivity {

    private ActivityMeasureBinding binding;
    private static final String MEASURE_VIEWMODEL_TAG = "MEASURE_VIEWMODEL_TAG";
    private MeasureFragment fragment;
    private MeasureViewModel viewModel;
    private ProjDetailMeasure.subBean subBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_measure,
                null, false);
        setContentView(binding.getRoot());
        initView();
        initData();
        fragment = findOrCreateViewFragment();
        viewModel = findOrCreateViewModel();
        fragment.setViewModel(viewModel);
        MyApplication.getInstance().addActivity(this);
    }


    /**
     * 初始化页面
     */
    private void initView() {
        Toolbar toolbar = binding.measureToolbar;
        toolbar.setTitle(mContext.getString(R.string.measure));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 初始化数据
     */
    private void initData(){
        Intent intent = getIntent();
        if (intent!=null){
            subBean = (ProjDetailMeasure.subBean) intent.getSerializableExtra("subBean");
        }
    }

    @Override
    public MeasureFragment findOrCreateViewFragment() {
        MeasureFragment fragment = (MeasureFragment) getSupportFragmentManager()
                .findFragmentById(R.id.measure_frame);
        if (fragment == null) {
            fragment = MeasureFragment.getInstance(subBean);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.measure_frame);
        }
        return fragment;
    }

    @Override
    public MeasureViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<MeasureViewModel> holder = (ViewModelHolder<MeasureViewModel>)
                getSupportFragmentManager().findFragmentByTag(MEASURE_VIEWMODEL_TAG);
        if (holder==null||holder.getViewmodel()==null){
            MeasureViewModel model = new MeasureViewModel(fragment, Injection.provideDataRepository(mContext));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(model),MEASURE_VIEWMODEL_TAG);
            return model;
        }
        return holder.getViewmodel();
    }
}