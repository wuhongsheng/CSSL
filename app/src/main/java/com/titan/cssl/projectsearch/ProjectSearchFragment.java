package com.titan.cssl.projectsearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.titan.cssl.R;
import com.titan.cssl.databinding.DialogSearchSetBinding;
import com.titan.cssl.databinding.FragSearchBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索
 */

public class ProjectSearchFragment extends Fragment implements ProjectSearch {

    public static final String TIMESET_TAG = "TIMESET_TAG";

    private String[] projectArray = new String[]{"3万㎡以下","3-8万㎡","8万㎡以上"};
    private Context mContext;

    private FragSearchBinding binding;

    private static ProjectSearchFragment fragment;

    private ProjectSearchViewModel mViewModel;
    private MaterialDialog setDialog;
    private DialogSearchSetBinding searchSetBinding;
    private ProjectSearchViewModel setTimeViewModel;
    private ProjectTimeSetDialog timeSetDialog;

    public static ProjectSearchFragment getInstance(){
        if (fragment==null){
            fragment = new ProjectSearchFragment();
        }
        return fragment;
    }

    public void setViewModel(ProjectSearchViewModel viewModel){
        this.mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_search,container,false);
        binding.setViewmodel(mViewModel);
        initData();
        return binding.getRoot();
    }


    private void initData(){
        List<String> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add("项目"+i);
        }
        ProjectDataAdapter adapter = new ProjectDataAdapter(mContext,list);
        binding.mainProjectList.setAdapter(adapter);
    }

    @Override
    public void searchSet() {
        searchSetBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.dialog_search_set, null,false);
        setDialog = new MaterialDialog.Builder(mContext)
                .customView(searchSetBinding.getRoot(),true)
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
                        dialog.dismiss();
                    }
                })
                .build();
        setDialog.show();
        searchSetBinding.setViewmodel(mViewModel);
        mViewModel.startTime.set("请选择");
        mViewModel.endTime.set("请选择");
        mViewModel.projectType.set("请选择");
        mViewModel.projectStatus.set("请选择");
    }

    @Override
    public void startTimeSet() {
//        ProjectTime time = new ProjectTime();
//        time.setYear(mViewModel.year.get());
//        time.setMonth(mViewModel.month.get());
//        time.setDay(mViewModel.month.get());
//        time.setHour(mViewModel.hour.get());
//        time.setMinute(mViewModel.minute.get());
//        mViewModel.projectTimeMap.get().put("start",time);
        showSetTimeDialog(true);
    }


    @Override
    public void endTimeSet() {
        showSetTimeDialog(false);
    }

    @Override
    public void projectTypeSet() {
        List<String> list = Arrays.asList(projectArray);
        ProjectOptionSelectDialog dialog = new ProjectOptionSelectDialog();
        ProjectSearchViewModel model = new ProjectSearchViewModel(dialog);
        dialog.setList(list);
        dialog.setViewModel(model,mViewModel);
        dialog.show(getFragmentManager(),"qweasd");
    }

    @Override
    public void approvalStatuSet() {

    }

    private void showSetTimeDialog(boolean flag) {
        if (timeSetDialog==null){
            timeSetDialog = new ProjectTimeSetDialog();
        }
        if (setTimeViewModel==null){
            setTimeViewModel = new ProjectSearchViewModel(timeSetDialog);
        }
        setTimeViewModel.timeSet.set(flag);
        timeSetDialog.setViewModel(setTimeViewModel,mViewModel);
        timeSetDialog.show(getFragmentManager(),TIMESET_TAG);
    }


}
