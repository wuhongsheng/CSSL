package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.titan.cssl.R;
import com.titan.cssl.databinding.FragBaseInfoBinding;
import com.titan.data.source.DataRepository;
import com.titan.model.ProjDetailBaseinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/2/002.
 * 基本信息
 */

public class ProjBaseInfoFragment extends Fragment {

    private Context mContext;
    private FragBaseInfoBinding binding;
    private ProjDetailViewModel viewModel;
    private List<ProjDetailBaseinfo> list;
    private static ProjBaseInfoFragment Sington;

    public static ProjBaseInfoFragment getInstance(){
        if (Sington==null){
            Sington = new ProjBaseInfoFragment();
        }
        return Sington;
    }

    public void setViewModel(ProjDetailViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_base_info,container,false);
        initData();
        ProjDetailAdapter adapter = new ProjDetailAdapter(mContext,list);
        binding.projBaseInfo.setAdapter(adapter);
        return binding.getRoot();
    }

    private void initData(){
        List<ProjDetailBaseinfo> list = new ArrayList<>();
        for (int i = 0;i<20;i++){
            ProjDetailBaseinfo baseinfo = new ProjDetailBaseinfo();
//            baseinfo.setName("测试"+i);
//            baseinfo.setValue("建设范围建立完善排水系统；弃渣场设置挡土墙、排水设施并进行土地整治；施工场地进行土地整治；绿化区域土地平整。开挖、填筑边坡挡土墙防护；"+i);
            list.add(baseinfo);
        }
        this.list = list;
    }
}