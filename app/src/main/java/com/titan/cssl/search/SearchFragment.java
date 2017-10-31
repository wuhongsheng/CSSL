package com.titan.cssl.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.titan.cssl.R;
import com.titan.cssl.databinding.DialogSearchSetBinding;
import com.titan.cssl.databinding.FragSearchBinding;
import com.titan.cssl.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索
 */

public class SearchFragment extends Fragment implements Search {

    private static Context mContext;

    private FragSearchBinding binding;

    private static SearchFragment fragment;

    private SearchViewModel mViewModel;
    private MaterialDialog setDialog;
    private View setDialogCustomView;

    public static SearchFragment getInstance(Context context){
        if (fragment==null){
            mContext = context;
            fragment = new SearchFragment();
        }
        return fragment;
    }

    public void setViewModel(SearchViewModel viewModel){
        this.mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        setDialog = new MaterialDialog.Builder(mContext)
                .customView(R.layout.dialog_search_set,true)
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
        setDialogCustomView = setDialog.getCustomView();
        TimePicker timePicker = setDialogCustomView.findViewById(R.id.set_time_tm);
        timePicker.setIs24HourView(true);
    }

    @Override
    public void timeChoose() {
        DialogSearchSetBinding binding1 = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.dialog_search_set, (ViewGroup) setDialogCustomView.getParent(),false);
        RadioButton btn = setDialogCustomView.findViewById(R.id.set_time_rd);
        binding1.setStatuRd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ToastUtil.setToast(mContext,"asd");
                }
            }
        });
        binding1.setStatuRd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.setToast(mContext,"qwe");
            }
        });
        ToastUtil.setToast(mContext,"123");
    }
}
