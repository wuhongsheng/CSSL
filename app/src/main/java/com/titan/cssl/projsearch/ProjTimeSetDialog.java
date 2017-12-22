package com.titan.cssl.projsearch;

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
import android.widget.Toast;

import com.titan.cssl.R;
import com.titan.cssl.databinding.DialogTimeSetBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hanyw on 2017/11/1/001.
 * 时间选择
 */

public class ProjTimeSetDialog extends DialogFragment implements DateChoose{

    private DialogTimeSetBinding binding;

    private ProjSearchViewModel viewModel;

    private ProjSearchViewModel searchFragViewModel;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    public void setViewModel(ProjSearchViewModel viewModel, ProjSearchViewModel searchFragViewModel){
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
        //设置dialog样式在底部
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
        int month = binding.setDatepicker.getMonth()+1;
        startTime = binding.setDatepicker.getYear()+"-"+month+"-"+binding.setDatepicker.getDayOfMonth()+" "
                +binding.setTimepicker.getCurrentHour()+":"+binding.setTimepicker.getCurrentMinute();
        if (!compareTime(startTime,endTime)){
            Toast.makeText(getActivity(),"开始时间不能小于结束时间",Toast.LENGTH_SHORT).show();
            return;
        }
        searchFragViewModel.startTime.set(startTime);
        this.dismiss();
    }

    @Override
    public void endTime() {
        int month = binding.setDatepicker.getMonth()+1;
        endTime = binding.setDatepicker.getYear()+"-"+month+"-"+binding.setDatepicker.getDayOfMonth()+" "
                +binding.setTimepicker.getCurrentHour()+":"+binding.setTimepicker.getCurrentMinute();
        if (!compareTime(startTime,endTime)){
            Toast.makeText(getActivity(),"结束时间不能小于开始时间",Toast.LENGTH_SHORT).show();
            return;
        }
        searchFragViewModel.endTime.set(endTime);
        this.dismiss();
    }

    /**
     * 时间大小比较
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    private boolean compareTime(String start,String end){
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            if (start==null||end==null){
                return true;
            }
            Log.e("tag","start:"+start);
            Date startDate = (Date) sdf.parseObject(start);
            Date endDate = (Date) sdf.parseObject(end);
            if (startDate.before(endDate)){
                result = true;
            }
        } catch (ParseException e) {
            Log.e("tag","timeError:"+e);
        }
        return result;
    }
}
