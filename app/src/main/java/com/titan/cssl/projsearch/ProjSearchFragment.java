package com.titan.cssl.projsearch;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.SearchView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.titan.cssl.R;
import com.titan.cssl.databinding.DialogSearchSetBinding;
import com.titan.cssl.databinding.FragSearchBinding;
import com.titan.cssl.reserveplan.ProjReservePlanActivity;
import com.titan.model.ProjSearch;
import com.titan.cssl.projdetails.ProjDetailActivity;
import com.titan.cssl.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索
 */

public class ProjSearchFragment extends Fragment implements ProjSearchSet {

    public static final String TIMESET_TAG = "TIMESET_TAG";

    public static final String PROJECTTYPESET_TAG = "PROJECTTYPESET_TAG";

    public static final String PROJECTSTATUSET_TAG = "PROJECTSTATUSET_TAG";

    private String[] typeArray = new String[]{"3万㎡以下", "3-8万㎡", "8万㎡以上"};

    private String[] stateArray = new String[]{"审查中", "专家审查中", "审查通过", "审查未通过"};

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
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
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
        inflater.inflate(R.menu.menu_search,menu);
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
            default:
                break;
        }
        return true;
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
        for (int i = 0; i < 30; i++) {
            ProjSearch search = new ProjSearch();
            search.setNUM(""+i);
            search.setNAME(nameArray[i%4]);
            search.setSTATE(stateArray[i%4]);
            search.setTIME(timeArray[i%4]);
            search.setTYPE(typeArray[i%3]);
            search.setDISTANCE(distanceArray[i%4]);
            list.add(search);
        }
        projDataAdapter = new ProjDataAdapter(mContext, list,mViewModel);
        binding.projectList.setAdapter(projDataAdapter);
        binding.projectList.setTextFilterEnabled(true);
    }

    @Override
    public void searchSet() {
        searchSetBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.dialog_search_set, null, false);
        if (setDialog==null){
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
        mViewModel.startTime.set("请选择");
        mViewModel.endTime.set("请选择");
        mViewModel.projectType.set("请选择");
        mViewModel.projectStatus.set("请选择");
    }

    public void search() {
        Log.e("tag",mViewModel.startTime.get()+","+mViewModel.endTime.get()+","+mViewModel.projectType.get()
        +","+mViewModel.projectStatus.get()+","+mViewModel.isChecked.get()+","+mViewModel.keyWord.get());
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

    private String valueFormat(String value){
        if (value==null||value.equals("请选择")){
            value = "";
        }
        return value;
    }
}
