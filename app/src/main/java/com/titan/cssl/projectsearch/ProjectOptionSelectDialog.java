package com.titan.cssl.projectsearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.titan.cssl.R;
import com.titan.cssl.databinding.DialogOptionselectProjectsearchBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/1/001.
 */

public class ProjectOptionSelectDialog extends DialogFragment implements OptionSelect {


    private DialogOptionselectProjectsearchBinding binding;

    private ProjectSearchViewModel viewModel;

    private ProjectSearchViewModel searchFragViewModel;

    private List<String> list;

    public void setViewModel(ProjectSearchViewModel viewModel,ProjectSearchViewModel searchFragViewModel){
        this.viewModel = viewModel;
        this.searchFragViewModel = searchFragViewModel;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window dialogWindow = getDialog().getWindow();
        getDialog().setCanceledOnTouchOutside(true);
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = getResources().getDisplayMetrics().widthPixels;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_optionselect_projectsearch,
                container, false);
        binding.setViewmodel(viewModel);
        initData();
        return binding.getRoot();
    }

    private void initData() {
        ProjectSearchSetAdapter adapter = new ProjectSearchSetAdapter(getActivity(),viewModel,list);
        binding.optionSelectList.setAdapter(adapter);
    }

    @Override
    public void select(String name) {
        this.dismiss();
        searchFragViewModel.projectType.set(name);
    }
}
