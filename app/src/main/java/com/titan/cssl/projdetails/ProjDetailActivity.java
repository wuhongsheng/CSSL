package com.titan.cssl.projdetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.titan.BaseActivity;
import com.titan.BaseViewModel;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityProjDetailBinding;
import com.titan.cssl.map.MapBrowseActivity;
import com.titan.cssl.measures.MeasureActivity;
import com.titan.data.Injection;
import com.titan.model.ProjDetailMeasure;
import com.titan.util.MaterialDialogUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目详细信息
 */

public class ProjDetailActivity extends BaseActivity implements ProjDetail {

    private ProjDetailViewModel viewModel;
    private ActivityProjDetailBinding binding;
    /**
     * 信息页面list
     */
    private List<Fragment> mList = new ArrayList<>();

    private List<String> fragList = new ArrayList<>();

    /**
     * 信息页面适配器
     */
    private ProjDetailViewPagerAdapter adapter;
    /**
     * 滑块宽度
     */
    private int sliderWidth;
    /**
     * 信息页面数组
     */
    private View[] views;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_proj_detail,
                null, false);
        setContentView(binding.getRoot());
        viewModel = new ProjDetailViewModel(this, Injection.provideDataRepository(mContext), this);
        binding.setViewmodel(viewModel);
        binding.detailTab.setViewmodel(viewModel);
        initView();
        initData();
        binding.projInfoPager.setAdapter(adapter);
        initTabLineWidth();

        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_mode:
                viewModel.netRequest(1, true);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void showMap() {
        Intent intent = new Intent(mContext, MapBrowseActivity.class);
        //传入坐标参数
        intent.putExtra("coordinate", (Serializable) viewModel.coordinateList.get());
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewModel.isRefresh.get()) {
                viewModel.isRefresh.set(false);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化页面
     */
    private void initView() {
        Toolbar toolbar = binding.detailToolbar;
        toolbar.setTitle("长沙水土保持");
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

        binding.detailRefresh.setColorSchemeResources(R.color.blue, R.color.colorAccent, R.color.aqua);
        binding.detailRefresh.setDistanceToTriggerSync(100);
        binding.detailRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        binding.detailRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!viewModel.isRefresh.get()) {
                    getData(viewModel.currentFrag.get(), true);
                }
            }
        });

        Intent intent = getIntent();
        views = new View[0];
        //如果是8万㎡以上项目就关闭基本信息页面
        if (intent != null) {
            Log.e("tag", "projtype:" + intent.getStringExtra("projType"));
            String projType = intent.getStringExtra("projType");
            if (projType == null) {
                views = new View[]{binding.detailTab.baseInfo, binding.detailTab.survey,
                        binding.detailTab.projMeasure};
            } else if (projType.contains("8万㎡以上")) {
                viewModel.hasBaseinfo.set(false);
                views = new View[]{binding.detailTab.survey, binding.detailTab.projMeasure};
            } else {
                views = new View[]{binding.detailTab.baseInfo, binding.detailTab.survey,
                        binding.detailTab.projMeasure};
            }
        }

        binding.projInfoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.detailTab.slider
                        .getLayoutParams();

                lp.leftMargin = (int) (positionOffset * sliderWidth + position * sliderWidth);
                if (position != views.length - 1) {
                    setTabSelected(positionOffset, views[position], views[position + 1]);
                }
                binding.detailTab.slider.setLayoutParams(lp);
            }

            //通过滑动切换时对toolbar中的按钮进行状态改变
            @Override
            public void onPageSelected(int position) {
                updataViewPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化fragment
     */
    private void initData() {
        ProjBaseInfoFragment infoFragment = ProjBaseInfoFragment.getInstance();
        infoFragment.setViewModel(viewModel);
        ProjSummaryFragment surveyFragment = ProjSummaryFragment.getInstance();
        surveyFragment.setViewModel(viewModel);
        ProjmeasureFragment measureFragment = ProjmeasureFragment.getInstance();
        measureFragment.setViewModel(viewModel);
        boolean b = views.length == 3 ? mList.add(infoFragment) : mList.add(null);
        mList.add(surveyFragment);
        mList.add(measureFragment);
        FragmentManager manager = getSupportFragmentManager();
        adapter = new ProjDetailViewPagerAdapter(manager, mList);
        getData(0, false);
    }

    private void getData(int position, boolean flag) {
        String projType = getIntent().getExtras().get("projType").toString();
        if (projType != null && projType.equals("8万㎡以上")) {
            viewModel.getData(position + 1, flag);
            viewModel.currentFrag.set(position + 1);
            if (!fragList.contains((position + 1) + "")) {
                fragList.add((position + 1) + "");
            }
        } else {
            viewModel.getData(position, flag);
            viewModel.currentFrag.set(position);
            if (!fragList.contains(position + "")) {
                fragList.add(position + "");
            }
        }
        viewModel.fragList.set(fragList);
    }

    /**
     * 切换viewPager
     */
    private void updataViewPager(int position) {
        views[position].setSelected(true);
        for (View view : views) {
            if (view != views[position]) {
                view.setSelected(false);
            }
        }
        getData(position, false);
    }

    /**
     * 设置滑块宽度
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        int screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.detailTab.slider
                .getLayoutParams();
        lp.width = screenWidth / views.length;
        sliderWidth = screenWidth / views.length;
        binding.detailTab.slider.setLayoutParams(lp);
    }

    /**
     * 当屏幕滑动超过一半时就切换选中的页面
     */
    public static void setTabSelected(float positionOffset, View before, View after) {
        if (positionOffset >= 0.5) {
            after.setSelected(true);
            before.setSelected(false);
        } else if (positionOffset < 0.5) {
            before.setSelected(true);
            after.setSelected(false);
        }
    }

    @Override
    public Fragment findOrCreateViewFragment() {
        return null;
    }

    @Override
    public BaseViewModel findOrCreateViewModel() {
        return null;
    }


    /**
     * 点击table选择页面
     *
     * @param pager 页面下标
     */
    @Override
    public void pagerSelect(int pager) {
        if (pager < 0) {
            return;
        }
        if (views.length == 2) {
            pager = pager - 1;
        }
        binding.projInfoPager.setCurrentItem(pager);
        updataViewPager(pager);
    }

    /**
     * 查看项目措施详细信息
     */
    @Override
    public void measureInfo(ProjDetailMeasure.subBean subBean) {
        Intent intent = new Intent(mContext, MeasureActivity.class);
        intent.putExtra("subBean", subBean);
        startActivity(intent);
    }

    @Override
    public void showToast(String info) {
        Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        MaterialDialogUtil.showLoadProgress(mContext, mContext.getString(R.string.loading), null).show();
    }

    @Override
    public void stopProgress() {
        MaterialDialogUtil.stopProgress();
    }

    @Override
    public void refresh(int type) {
        switch (type) {
            case 0:
                ProjBaseInfoFragment fragment1 = (ProjBaseInfoFragment) mList.get(0);
                fragment1.setData();
                break;
            case 1:
                ProjSummaryFragment fragment2 = (ProjSummaryFragment) mList.get(1);
                fragment2.setData();
                break;
            case 2:
                ProjmeasureFragment fragment3 = (ProjmeasureFragment) mList.get(2);
                fragment3.setData();
                break;
            default:
                break;
        }
        binding.detailRefresh.setRefreshing(false);
    }

    @Override
    public void showSubInfo(List<String> list) {
        MaterialDialogUtil.showInfoDialog(mContext,"详细信息",list).show();
    }
}