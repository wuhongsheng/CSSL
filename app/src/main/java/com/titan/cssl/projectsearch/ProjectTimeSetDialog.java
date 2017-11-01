package com.titan.cssl.projectsearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.titan.cssl.R;
import com.titan.cssl.databinding.DialogTimeSetBinding;
import com.titan.cssl.util.ToastUtil;

/**
 * Created by hanyw on 2017/11/1/001.
 */

public class ProjectTimeSetDialog extends DialogFragment implements DateChoose{

    private DialogTimeSetBinding binding;

    private ProjectSearchViewModel viewModel;

    private ProjectSearchViewModel searchFragViewModel;

    public void setViewModel(ProjectSearchViewModel viewModel,ProjectSearchViewModel searchFragViewModel){
        this.viewModel = viewModel;
        this.searchFragViewModel = searchFragViewModel;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_time_set,container,false);
        binding.setViewmodel(viewModel);
        binding.setTimepicker.setIs24HourView(true);
        return binding.getRoot();
    }


    @Override
    public void startTime() {
        viewModel.timeSet.set(true);
        String time = viewModel.year.get()+"-"+viewModel.month.get()+"-"+viewModel.day.get()+" "
                +viewModel.hour.get()+"-"+viewModel.minute.get();
        ToastUtil.setToast(getActivity(),time);
        searchFragViewModel.startTime.set(time);
        this.dismiss();
    }

    @Override
    public void endTime() {
        String time = viewModel.year.get()+"-"+viewModel.month.get()+"-"+viewModel.day.get()+" "
                +viewModel.hour.get()+"-"+viewModel.minute.get();
        ToastUtil.setToast(getActivity(),time);
        searchFragViewModel.endTime.set(time);
        this.dismiss();
    }

}
