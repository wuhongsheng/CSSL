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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.titan.BaseActivity;
import com.titan.BaseViewModel;
import com.titan.MyApplication;
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
    /**
     * 信息页面list
     */
    private List<Fragment> mList = new ArrayList<>();
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
        viewModel = new ProjDetailViewModel(mContext, this);
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
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.map_mode:
                List<String> list1 = new ArrayList<>();
                List<String> list2 = new ArrayList<>();
                List<String> list3 = new ArrayList<>();
                list1.add("112.97519625669649,28.190151091879166");
                list1.add("112.97925660276465,28.187339233709384");
                list1.add("112.97441692070188,28.183706421250637");
                list1.add("112.9713166414733,28.188153808851457");

                list2.add("112.97878159843323,28.192224655341477");

                list3.add("112.98360324579546,28.186365745578613");
                list3.add("112.98701507407029,28.191908416694027");

                Intent intent = new Intent(mContext, MapBrowseActivity.class);
                //传入坐标参数
                intent.putExtra("polygon", (Serializable) list1);
                intent.putExtra("point",(Serializable) list2);
                intent.putExtra("line",(Serializable) list3);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
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

        Intent intent = getIntent();
        views = new View[0];
        //如果是8万㎡以上项目就关闭基本信息页面
        if (intent!=null){
            Log.e("tag","projtype:"+intent.getStringExtra("projType"));
            String projType = intent.getStringExtra("projType");
            if (projType.contains("8万㎡以上")){
                viewModel.hasBaseinfo.set(false);
                views = new View[]{binding.detailTab.survey,
                        binding.detailTab.projMeasure};
            }else {
                views = new View[]{binding.detailTab.baseInfo, binding.detailTab.survey,
                        binding.detailTab.projMeasure};
            }
        }

        binding.projInfoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.detailTab.slider
                        .getLayoutParams();

                lp.leftMargin = (int) (positionOffset*sliderWidth+position*sliderWidth);
                if (position!= views.length-1){
                    setTabSelected(positionOffset, views[position], views[position+1]);
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
        ProjSummaryFragment surveyFragment = ProjSummaryFragment.getInstance();
        ProjmeasureFragment measureFragment = ProjmeasureFragment.getInstance();
        measureFragment.setViewModel(viewModel);
        boolean b = views.length == 3 ? mList.add(infoFragment) : mList.add(null);
        mList.add(surveyFragment);
        mList.add(measureFragment);
        FragmentManager manager = getSupportFragmentManager();
        adapter = new ProjDetailViewPagerAdapter(manager, mList);
    }

    /**
     * 切换viewPager
     */
    private void updataViewPager(int position) {
        views[position].setSelected(true);
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
     * @param pager 页面下标
     */
    @Override
    public void pagerSelect(int pager) {
        if (views.length==2){
            pager = pager-1;
        }
        if (pager<0){
            return;
        }
        binding.projInfoPager.setCurrentItem(pager);
        updataViewPager(pager);
    }

    @Override
    public void addCensor() {
        Intent intent = new Intent(mContext, ProjLocalCensorActivity.class);
        startActivity(intent);
    }
}