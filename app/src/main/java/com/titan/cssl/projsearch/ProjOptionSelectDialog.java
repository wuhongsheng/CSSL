package com.titan.cssl.projsearch;

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

import java.util.List;

/**
 * Created by hanyw on 2017/11/1/001.
 * 检索设置 项目类型、审批状态选择
 */

public class ProjOptionSelectDialog extends DialogFragment implements OptionSelect {

    private DialogOptionselectProjectsearchBinding binding;

    private ProjSearchViewModel viewModel;

    private ProjSearchViewModel searchFragViewModel;

    /**
     * 参数
     */
    private List<String> list;

    /**
     * 参数类型 1：项目类型；2：审批状态
     */
    private int type;

    public void setViewModel(ProjSearchViewModel viewModel, ProjSearchViewModel searchFragViewModel) {
        this.viewModel = viewModel;
        this.searchFragViewModel = searchFragViewModel;
    }

    public static ProjOptionSelectDialog getInstance(int type) {
        ProjOptionSelectDialog dialog = new ProjOptionSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
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
        ProSearchSetAdapter adapter = new ProSearchSetAdapter(getActivity(), viewModel, list);
        binding.optionSelectList.setAdapter(adapter);
    }

    @Override
    public void select(String value) {
        this.dismiss();
        switch (type) {
            case 1:
                searchFragViewModel.projectType.set(value);
                break;
            case 2:
                searchFragViewModel.projectStatus.set(value);
                break;
            default:
                break;
        }
    }
}
