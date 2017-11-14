package com.titan.cssl.projdetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.titan.BaseActivity;
import com.titan.BaseViewModel;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityProjDetailBinding;
import com.titan.cssl.localcensor.ProjLocalCensorActivity;
import com.titan.cssl.map.MapBrowseActivity;

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
    private List<Fragment> mList = new ArrayList<>();
    private ProjDetailViewPagerAdapter adapter;
    private int sliderWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_proj_detail,
                null, false);
        setContentView(binding.getRoot());
        viewModel = new ProjDetailViewModel(mContext, this);
        binding.setViewmodel(viewModel);
        binding.detailTab.setViewmodel(viewModel);
        initView();
        initData();
        binding.projInfoPager.setAdapter(adapter);
        initTabLineWidth();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.map_mode:
                List<String> list = new ArrayList<>();
                list.add("102.97564887393251,24.634365809695332");
                list.add("102.96198641900425,24.600065189416615");
                Intent intent = new Intent(mContext, MapBrowseActivity.class);
                //传入坐标参数
                intent.putExtra("coordinate", (Serializable) list);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

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

        final View[] views = new View[]{binding.detailTab.baseInfo, binding.detailTab.survey,
                binding.detailTab.projMeasure};

        binding.projInfoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.detailTab.slider.getLayoutParams();

                lp.leftMargin = (int) (positionOffset*sliderWidth+position*sliderWidth);
                if (position!=views.length-1){
                    setTabSelected(positionOffset,views[position],views[position+1]);
                }
                binding.detailTab.slider.setLayoutParams(lp);
            }

            //通过滑动切换时对toolbar中的按钮进行状态改变
            @Override
            public void onPageSelected(int position) {
                boolean[] state = new boolean[3];
                state[position] = true;
                updataViewPager(state[0], state[1], state[2]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        ProjBaseInfoFragment infoFragment = ProjBaseInfoFragment.getInstance();
        ProjSummaryFragment surveyFragment = ProjSummaryFragment.getInstance();
        ProjmeasureFragment measureFragment = ProjmeasureFragment.getInstance();
        measureFragment.setViewModel(viewModel);
        mList.add(infoFragment);
        mList.add(surveyFragment);
        mList.add(measureFragment);
        FragmentManager manager = getSupportFragmentManager();
        adapter = new ProjDetailViewPagerAdapter(manager, mList);
    }

    /*切换viewPager*/
    private void updataViewPager(boolean info, boolean facts, boolean measure) {
        binding.detailTab.baseInfo.setSelected(info);
        binding.detailTab.survey.setSelected(facts);
        binding.detailTab.projMeasure.setSelected(measure);
    }

    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        int screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.detailTab.slider
                .getLayoutParams();
        lp.width = screenWidth / 3;
        sliderWidth = screenWidth / 3;
        binding.detailTab.slider.setLayoutParams(lp);
    }

    /*当屏幕滑动超过一半时就切换选中的页面*/
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


    @Override
    public void pagerSelect(int pager) {
        binding.projInfoPager.setCurrentItem(pager);
        boolean[] state = new boolean[3];
        state[pager] = true;
        updataViewPager(state[0], state[1], state[2]);
    }

    @Override
    public void addCensor() {
        Intent intent = new Intent(mContext, ProjLocalCensorActivity.class);
        startActivity(intent);
    }
}