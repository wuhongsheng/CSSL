package com.titan.cssl.statistics;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.titan.BaseActivity;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityStatisticsBinding;
import com.titan.cssl.login.LoginActivity;
import com.titan.cssl.reserveplan.ProjReservePlanActivity;
import com.titan.cssl.util.ActivityUtils;
import com.titan.cssl.util.ViewModelHolder;
import com.titan.data.Injection;
import com.titan.util.MaterialDialogUtil;

/**
 * Created by hanyw on 2017/12/7/007.
 * 数据统计
 */

public class StatisticsActivity extends BaseActivity {

    public static final String STATISTICS_VIEWMODEL_TAG = "STATISTICS_VIEWMODEL_TAG";

    private StatisticsViewModel mViewModel;
    private StatisticsFragment mFragment;
    private ActivityStatisticsBinding binding;
    private long mExitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_statistics,
                null,false);
        setContentView(binding.getRoot());
        mFragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        mFragment.setViewModel(mViewModel);
        binding.statisticsToolbar.setTitle(mContext.getResources().getString(R.string.proj_statistics));
        setSupportActionBar(binding.statisticsToolbar);
        binding.statisticsToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        binding.statisticsToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if ((System.currentTimeMillis() - mExitTime) > 1500){
                Toast.makeText(mContext,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else {
                MyApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.proj_plan:
                Intent intent = new Intent(mContext, ProjReservePlanActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.proj_signout:
                MaterialDialogUtil.showSureDialog(mContext, "确定退出登录吗")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                signOut();
                                dialog.dismiss();
                            }
                        }).build().show();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 退出登录
     */
    private void signOut() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public StatisticsFragment findOrCreateViewFragment() {
        StatisticsFragment fragment = (StatisticsFragment) getSupportFragmentManager().findFragmentById(R.id.statistics_frame);
        if (fragment == null) {
            fragment = StatisticsFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.statistics_frame);
        }
        return fragment;
    }

    @Override
    public StatisticsViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<StatisticsViewModel> holder =
                (ViewModelHolder<StatisticsViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(STATISTICS_VIEWMODEL_TAG);
        if (holder == null || holder.getViewmodel() == null) {
            StatisticsViewModel viewModel = new StatisticsViewModel(mContext,mFragment, Injection.provideDataRepository(mContext));
            ActivityUtils.addFragmentToActivity
                    (getSupportFragmentManager(), ViewModelHolder.createContainer(viewModel), STATISTICS_VIEWMODEL_TAG);
            return viewModel;
        }
        return holder.getViewmodel();
    }
}
