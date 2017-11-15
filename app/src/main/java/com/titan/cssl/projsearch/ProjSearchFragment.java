package com.titan.cssl.projsearch;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.esri.arcgisruntime.geometry.Point;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.DialogSearchSetBinding;
import com.titan.cssl.databinding.FragSearchBinding;
import com.titan.cssl.login.LoginActivity;
import com.titan.cssl.reserveplan.ProjReservePlanActivity;
import com.titan.location.LocationService;
import com.titan.model.ProjSearch;
import com.titan.cssl.projdetails.ProjDetailActivity;
import com.titan.util.MaterialDialogUtil;
import com.titan.util.TitanUtil;
import com.titan.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
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

    private String[] typeArray = new String[]{"3万㎡以下", "3-8万㎡", "8万㎡以上"};

    private String[] stateArray = new String[]{"审查中", "专家审查中", "审查通过", "审查未通过"};

    private float[] testArray = new float[20];

    private String[] timeArray = new String[]{"2016-3-9", "2016-5-6", "2013-8-16", "2017-10-17"};
    private String[] distanceArray = new String[]{"距离:2.5km", "距离:25km", "距离:55km", "距离:7km"};

    private String[] nameArray = new String[]{"中国铁建·18公馆", "泰禹家园六期项目 ",
            "湖南生物机电职业技术学院学生公寓楼（东湖校区）", "湘天•禧悦汇（奥林匹克花园五期）"};


    private Context mContext;

    private FragSearchBinding binding;

    private static ProjSearchFragment fragment;

    private ProjSearchViewModel mViewModel;
    private MaterialDialog setDialog;
    private DialogSearchSetBinding searchSetBinding;
    private ProjSearchViewModel setTimeViewModel;
    private ProjTimeSetDialog timeSetDialog;
    private ProjDataAdapter projDataAdapter;
    private LocationService mLocationService;

    public static ProjSearchFragment getInstance() {
        if (fragment == null) {
            fragment = new ProjSearchFragment();
        }
        return fragment;
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
        Toolbar toolbar = binding.searchToolbar;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle(getResources().getString(R.string.appname));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        initData();
        return binding.getRoot();
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
            case R.id.proj_plan:
                Intent intent = new Intent(mContext, ProjReservePlanActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.proj_signout:
                MaterialDialog dialog = MaterialDialogUtil.showSureDialog(mContext, "确定退出吗")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                signOut();
                                dialog.dismiss();
                            }
                        }).build();
                dialog.show();
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
        if (mLocationService != null) {
            mLocationService.unregisterListener(mViewModel);
            mLocationService.stop();
        }
        Intent intent1 = new Intent(mContext, LoginActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent1);
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayout layout = binding.searchLinear;
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
        layout.requestFocus();
    }

    private void initData() {
        List<ProjSearch> list = new ArrayList<>();
        ProjSearch[] searches = new ProjSearch[30];
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            ProjSearch search = new ProjSearch();
            search.setNUM("" + i);
            search.setNAME(nameArray[i % 4]);
            search.setSTATE(stateArray[i % 4]);
            search.setTIME(timeArray[i % 4]);
            search.setTYPE(typeArray[i % 3]);
            search.setZB(random.nextFloat() + random.nextInt(20) + "");
//            list.add(search);
            searches[i] = search;
        }
        quickSort(searches, 0, 29);
        list.addAll(Arrays.asList(searches));
        projDataAdapter = new ProjDataAdapter(mContext, list, mViewModel);
        binding.projectList.setAdapter(projDataAdapter);
        binding.projectList.setTextFilterEnabled(true);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void initBDLoc() {
        mLocationService = MyApplication.locationService;
        mLocationService.registerListener(mViewModel);
        mLocationService.setLocationOption(mLocationService.getDefaultLocationClientOption());
        mLocationService.start();
    }

    @Override
    public void searchSet() {
        searchSetBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
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
                            search();
                            dialog.dismiss();
                        }
                    })
                    .build();
        }
        setDialog.show();
        searchSetBinding.setViewmodel(mViewModel);
        searchSetBinding.setKeyword.setFocusable(false);
        mViewModel.startTime.set("请选择");
        mViewModel.endTime.set("请选择");
        mViewModel.projectType.set("请选择");
        mViewModel.projectStatus.set("请选择");
    }

    public void search() {
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            testArray[i] = random.nextFloat() + random.nextInt(20);
        }
//        quickSort(testArray,0,19);
        Log.e("tag", "array:" + Arrays.toString(testArray));
        Log.e("tag", mViewModel.startTime.get() + "," + mViewModel.endTime.get() + "," + mViewModel.projectType.get()
                + "," + mViewModel.projectStatus.get() + "," + mViewModel.isChecked.get() + "," + mViewModel.keyWord.get());
    }

    @Override
    public void locSearch() {
        ProjSearchFragmentPermissionsDispatcher.initBDLocWithCheck(this);
    }

    @Override
    public void startTimeSet() {
        showSetTimeDialog(true);
    }


    @Override
    public void endTimeSet() {
        showSetTimeDialog(false);
    }

    @Override
    public void projectTypeSet() {
        List<String> list = Arrays.asList(typeArray);
        ProjOptionSelectDialog dialog = ProjOptionSelectDialog.getInstance(1);
        ProjSearchViewModel model = new ProjSearchViewModel(dialog);
        dialog.setList(list);
        dialog.setViewModel(model, mViewModel);
        dialog.show(getFragmentManager(), PROJECTTYPESET_TAG);
    }

    @Override
    public void approvalStatuSet() {
        List<String> list = Arrays.asList(stateArray);
        ProjOptionSelectDialog dialog = ProjOptionSelectDialog.getInstance(2);
        ProjSearchViewModel model = new ProjSearchViewModel(dialog);
        dialog.setList(list);
        dialog.setViewModel(model, mViewModel);
        dialog.show(getFragmentManager(), PROJECTSTATUSET_TAG);
    }

    @Override
    public void projDetails() {
        Intent intent = new Intent(mContext, ProjDetailActivity.class);
        mContext.startActivity(intent);
    }


    private static void quickSort(ProjSearch[] a, int left, int right) {
        if (left > right) {
            return;
        }
        int i = left;
        int j = right;
        float pivot = Float.parseFloat(a[left].getZB());
        ProjSearch pivotObj = a[left];
        while (i != j) {
            while (Float.parseFloat(a[j].getZB()) >= pivot && i < j)
                j--;
            while (Float.parseFloat(a[i].getZB()) <= pivot && i < j)
                i++;
            if (i < j) {
                ProjSearch t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        a[left] = a[i];
        a[i].setZB(pivot + "");
        a[i] = pivotObj;
//        float pivot = a[left];
//        while (i != j) {
//            while (a.[j] >= pivot && i < j)
//                j--;
//            while (a[i] <= pivot && i < j)
//                i++;
//            if (i < j) {
//                float t = a[i];
//                a[i] = a[j];
//                a[j] = t;
//            }
//        }
//        a[left] = a[i];
//        a[i] = pivot;
        quickSort(a, left, i - 1);
        quickSort(a, i + 1, right);
    }

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

    private String valueFormat(String value) {
        if (value == null || value.equals("请选择")) {
            value = "";
        }
        return value;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProjSearchFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);

    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showRationale(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void permissionDenied() {
        Toast.makeText(mContext, "已拒绝权限，定位功能将无法使用，若想使用请开启权限", Toast.LENGTH_LONG).show();
        if (mViewModel.isChecked.get()) {
            mViewModel.isChecked.set(false);
        }
    }
}
