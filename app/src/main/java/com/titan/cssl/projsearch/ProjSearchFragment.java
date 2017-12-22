package com.titan.cssl.projsearch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.DialogSearchSetBinding;
import com.titan.cssl.databinding.FragSearchBinding;
import com.titan.cssl.databinding.ItemFootViewBinding;
import com.titan.cssl.login.LoginActivity;
import com.titan.cssl.projdetails.ProjDetailActivity;
import com.titan.cssl.reserveplan.ProjReservePlanActivity;
import com.titan.location.LocationService;
import com.titan.util.MaterialDialogUtil;

import java.util.Arrays;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索
 */
@RuntimePermissions
public class ProjSearchFragment extends Fragment implements ProjSearchSet {

    public static final String TIMESET_TAG = "TIMESET_TAG";

    public static final String PROJECTTYPESET_TAG = "PROJECTTYPESET_TAG";

    public static final String PROJECTSTATUSET_TAG = "PROJECTSTATUSET_TAG";

    /**
     * 获取到数据
     */
    public static final int GETDATA = 0;
    /**
     * 没有获取到定位点
     */
    public static final int NOLOCPOINT = 1;

    /**
     * 项目内容列表屏幕范围内最后一个item
     */
    private int last_index;
    /**
     * 项目内容列表总数
     */
    private int total_index;

    /**
     * 项目类型数组
     */
    private String[] typeArray = new String[]{"3万㎡以下", "3-8万㎡", "8万㎡以上"};

    /**
     * 项目审批状态数组
     */
    private String[] stateArray = new String[]{"提交审核中", "审核通过", "审核不通过"};

    private Context mContext;

    private FragSearchBinding binding;

    private ProjSearchViewModel mViewModel;

    private ItemFootViewBinding footViewBinding;

    /**
     * 等待起始时间
     */
    private long mExitTime = 0;

    /**
     * 检索参数设置dialog
     */
    private MaterialDialog setDialog;
    private ProjSearchViewModel setTimeViewModel;
    /**
     * 时间选择dialog
     */
    private ProjTimeSetDialog timeSetDialog;
    /**
     * 检索数据适配器
     */
    private ProjDataAdapter projDataAdapter;
    /**
     * 百度定位服务
     */
    private LocationService mLocationService;

    private Handler handler;
    private MaterialDialog dialog;
    private MyThread thread;

    public static ProjSearchFragment getInstance() {
        return new ProjSearchFragment();
    }

    public void setViewModel(ProjSearchViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_search, container, false);
        binding.setViewmodel(mViewModel);
        initView();
        initHandler();
        ProjSearchFragmentPermissionsDispatcher.initBDLocWithCheck(this);
        mViewModel.search(false);
        return binding.getRoot();
    }

    /**
     * 页面初始化
     */
    private void initView() {
        footViewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.item_foot_view, null, false);
        binding.projectList.addFooterView(footViewBinding.getRoot(), null, false);

        Toolbar toolbar = binding.searchToolbar;
        setHasOptionsMenu(true);
        toolbar.setTitle(mContext.getResources().getString(R.string.proj_search));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        binding.searchRefresh.setColorSchemeResources(R.color.blue, R.color.colorAccent, R.color.aqua);
        binding.searchRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        binding.searchRefresh.setDistanceToTriggerSync(100);
        binding.searchRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mViewModel.isRefresh.get()) {
                    mViewModel.pageIndex.set(1);
                    mViewModel.search(false);
                    mViewModel.hasMore.set(true);
                }
            }
        });
    }

    /**
     * 初始化handler
     */
    private void initHandler() {
        handler = new Handler(mContext.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case GETDATA:
                        mViewModel.projSearchList.set(mViewModel
                                .valueFormat(mViewModel.projSearchListBack.get()));
                        if (mViewModel.isLocal.get()) {
                            mViewModel.distanceSort();
                        }
                        projDataAdapter.setDate(mViewModel.projSearchList.get());
                        stopProgress();
                        break;
                    case NOLOCPOINT:
                        stopProgress();
                        MaterialDialogUtil.showSureDialog(mContext, "没有获取到当前位置")
                                .build().show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.proj_search:
                searchSet();
                break;
//            case R.id.proj_plan:
//                Intent intent = new Intent(mContext, ProjReservePlanActivity.class);
//                mContext.startActivity(intent);
//                break;
//            case R.id.proj_signout:
//                MaterialDialogUtil.showSureDialog(mContext, "确定退出登录吗")
//                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                signOut();
//                                dialog.dismiss();
//                            }
//                        }).build().show();
//                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 退出登录
     */
    private void signOut() {
        //关闭定位监听
        if (mLocationService != null) {
            mLocationService.unregisterListener(mViewModel);
            mLocationService.stop();
        }
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭定位监听
        if (mLocationService != null) {
            mLocationService.unregisterListener(mViewModel);
            mLocationService.stop();
        }
    }

    /**
     * 初始化百度定位
     */
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void initBDLoc() {
        mLocationService = MyApplication.locationService;
        mLocationService.registerListener(mViewModel);
        mLocationService.setLocationOption(mLocationService.getDefaultLocationClientOption());
        mLocationService.start();
    }

    /**
     * 检索参数设置
     */
    public void searchSet() {
        DialogSearchSetBinding searchSetBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.dialog_search_set, null, false);
        if (setDialog == null) {
            setDialog = new MaterialDialog.Builder(mContext)
                    .title("项目检索设置")
                    .customView(searchSetBinding.getRoot(), true)
                    .positiveText("确定")
                    .negativeText("取消")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mViewModel.pageIndex.set(1);
                            mViewModel.hasMore.set(true);
                            mViewModel.search(false);
                            dialog.dismiss();
                        }
                    })
                    .build();
        }
        searchSetBinding.setKeyword.clearFocus();
        setDialog.show();
        searchSetBinding.setViewmodel(mViewModel);
    }


    /**
     * 刷新项目数据
     *
     * @param isLoadMore 是否加载更多，true 是；false 否
     */
    @Override
    public void refresh(boolean isLoadMore) {
        if (!isLoadMore) {
            projDataAdapter = new ProjDataAdapter(mContext, mViewModel);
            binding.projectList.setAdapter(projDataAdapter);
        }
        binding.searchRefresh.setRefreshing(false);
        //上拉加载
        binding.projectList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (last_index == total_index && (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)) {
                    //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
                    if (!mViewModel.isRefresh.get() && mViewModel.hasMore.get()) {
                        //不处于加载状态的话对其进行加载
                        mViewModel.isRefresh.set(true);
                        //设置刷新界面可见
                        loadComplete(true);
                        mViewModel.search(true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                last_index = i + i1;
                total_index = i2;
            }
        });
        if (thread==null){
            thread = new MyThread();
        }
        thread.start();
    }

    /**
     * 手动开启定位权限后刷新数据时获取位置信息
     */
    public class MyThread extends Thread {
            @Override
            public void run() {
                int flag = ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                while (true) {
                    if (mExitTime == 0) {
                        mExitTime = System.currentTimeMillis();
                    }
                    //已经授予定位权限
                    if (flag == PackageManager.PERMISSION_GRANTED) {
                        //定位是否初始化
                        if (mLocationService == null) {
                            initBDLoc();
                        }
                        //等待定位或超时
                        if (mViewModel.locPoint.get() != null ||
                                (System.currentTimeMillis() - mExitTime) > 5000) {
                            break;
                        }
                    }
                }
                if (mViewModel.locPoint.get() == null) {
                    Message message = new Message();
                    message.what = NOLOCPOINT;
                    handler.sendMessage(message);
                    return;
                }
                //定位成功或没有授予定位权限
                if (mViewModel.locPoint.get() != null || flag == PackageManager.PERMISSION_DENIED) {
                    Message message = new Message();
                    message.what = GETDATA;
                    handler.sendMessage(message);
                }
        }
    }

    /**
     * 项目列表加载完成
     *
     * @param state 内容是否结束标识位 true 未结束；false 结束
     */
    public void loadComplete(boolean state) {
        if (state) {
            mViewModel.isRefresh.set(false);
            footViewBinding.projectEnd.setVisibility(View.GONE);
            footViewBinding.footProgress.setVisibility(View.VISIBLE);
        } else {
            if (mViewModel.hasMore.get()) {
                mViewModel.hasMore.set(false);
                mViewModel.isRefresh.set(false);
                footViewBinding.projectEnd.setVisibility(View.VISIBLE);
                footViewBinding.footProgress.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 显示内容结束提示
     */
    @Override
    public void showEnd() {
        loadComplete(false);
    }

    /**
     * 显示加载提示
     */
    @Override
    public void showLoading() {
        loadComplete(true);
    }

    /**
     * 根据当前位置搜索
     */
    @Override
    public void locSearch() {
        ProjSearchFragmentPermissionsDispatcher.initBDLocWithCheck(this);
    }

    @Override
    public void showToast(String info) {
        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        dialog = MaterialDialogUtil.showLoadProgress(mContext, mContext.getString(R.string.loading)).build();
        dialog.show();
    }

    @Override
    public void stopProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
        binding.searchRefresh.setRefreshing(false);
    }

    /**
     * 开始时间设置
     */
    @Override
    public void startTimeSet() {
        showSetTimeDialog(true);
    }

    /**
     * 结束时间设置
     */
    @Override
    public void endTimeSet() {
        showSetTimeDialog(false);
    }

    /**
     * 项目类型设置
     */
    @Override
    public void projectTypeSet() {
        List<String> list = Arrays.asList(typeArray);
        ProjOptionSelectDialog dialog = ProjOptionSelectDialog.getInstance(1);
        ProjSearchViewModel model = new ProjSearchViewModel(dialog);
        dialog.setList(list);
        dialog.setViewModel(model, mViewModel);
        dialog.show(getFragmentManager(), PROJECTTYPESET_TAG);
    }

    /**
     * 项目审批状态设置
     */
    @Override
    public void approvalStatuSet() {
        List<String> list = Arrays.asList(stateArray);
        ProjOptionSelectDialog dialog = ProjOptionSelectDialog.getInstance(2);
        ProjSearchViewModel model = new ProjSearchViewModel(dialog);
        dialog.setList(list);
        dialog.setViewModel(model, mViewModel);
        dialog.show(getFragmentManager(), PROJECTSTATUSET_TAG);
    }

    /**
     * 查看项目详细信息
     *
     * @param type 项目类型 1：3万㎡以下；2：3-8万㎡；3：8万㎡以上
     */
    @Override
    public void projDetails(String type) {
        Intent intent = new Intent(mContext, ProjDetailActivity.class);
        intent.putExtra("projType", type);
        mContext.startActivity(intent);
    }

    /**
     * 时间设置dialog
     *
     * @param flag true：开始时间；false：结束时间
     */
    private void showSetTimeDialog(boolean flag) {
        if (timeSetDialog == null) {
            timeSetDialog = new ProjTimeSetDialog();
        }
        if (setTimeViewModel == null) {
            setTimeViewModel = new ProjSearchViewModel(timeSetDialog);
        }
        setTimeViewModel.timeSet.set(flag);
        timeSetDialog.setViewModel(setTimeViewModel, mViewModel);
        timeSetDialog.show(getFragmentManager(), TIMESET_TAG);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProjSearchFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        if (thread==null){
            thread = new MyThread();
        }
        thread.start();
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showRationale(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void permissionDenied() {
        Toast.makeText(mContext, "已拒绝权限，定位功能将无法使用，若想使用请开启权限", Toast.LENGTH_LONG).show();
        if (mViewModel.isLocal.get()) {
            mViewModel.isLocal.set(false);
        }
    }
}