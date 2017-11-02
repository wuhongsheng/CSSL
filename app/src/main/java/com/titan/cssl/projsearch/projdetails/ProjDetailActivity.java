package com.titan.cssl.projsearch.projdetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.titan.cssl.BaseActivity;
import com.titan.cssl.BaseViewModel;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityProjDetailBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目详细信息
 */

public class ProjDetailActivity extends BaseActivity{

    private ProjDetailViewModel viewModel;
    private ActivityProjDetailBinding binding;
    private List<Fragment> mList = new ArrayList<>();
    private ProjDetailViewPagerAdapter adapter;
    private int sliderWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_detail);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.activity_proj_detail,
                null,false);
        viewModel = new ProjDetailViewModel();
        binding.detailTab.setViewmodel(viewModel);
        setSupportActionBar(binding.localMusicToolbar);
        initView();
        initData();
        binding.projInfoPager.setAdapter(adapter);
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        binding.projInfoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.detailTab.slider.getLayoutParams();
                if (position == 0) {
                    lp.leftMargin = (int) (positionOffset * sliderWidth);
                    setTabSelected(positionOffset,binding.detailTab.baseInfo,binding.detailTab.basicFacts);
                } else if (position == 1) {
                    lp.leftMargin = (int) (positionOffset * sliderWidth) + sliderWidth;
                    setTabSelected(positionOffset,binding.detailTab.basicFacts,binding.detailTab.projMeasure);
                } else if (position == 2) {
                    lp.leftMargin = (int) (positionOffset * sliderWidth) + 3 * sliderWidth;
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
        Fragment infoFragment = new ProjBaseInfoFragment();
        Fragment factsFragment = new ProjBaseFactsFragment();
        Fragment measureFragment = new ProjmeasureFragment();
        mList.add(infoFragment);
        mList.add(factsFragment);
        mList.add(measureFragment);
        FragmentManager manager = getSupportFragmentManager();
        adapter = new ProjDetailViewPagerAdapter(manager, mList);
    }

    /*切换viewPager*/
    private void updataViewPager(boolean info, boolean facts, boolean measure) {
        binding.detailTab.baseInfo.setSelected(info);
        binding.detailTab.basicFacts.setSelected(facts);
        binding.detailTab.projMeasure.setSelected(measure);
    }

    /*当屏幕滑动超过一半时就切换选中的页面*/
    public static void setTabSelected(float positionOffset,View before,View after) {
        if (positionOffset>=0.5){
            after.setSelected(true);
            before.setSelected(false);
        }else if (positionOffset<0.5){
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
}
