package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.titan.cssl.R;
import com.titan.cssl.databinding.FragBaseInfoBinding;
import com.titan.model.ProjDetailBaseinfo;
import com.titan.util.TitanItemDecoration;

import java.util.List;

/**
 * Created by hanyw on 2017/11/2/002.
 * 基本信息
 */

public class ProjBaseInfoFragment extends Fragment {

    private FragBaseInfoBinding binding;
    private ProjDetailViewModel viewModel;
    private ProjDetail detail;

    public static ProjBaseInfoFragment getInstance() {
        return new ProjBaseInfoFragment();
    }

    public void setDetail(ProjDetail detail) {
        this.detail = detail;
    }
    public void setViewModel(ProjDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_base_info, container, false);
        return binding.getRoot();
    }

    public void setData() {

//        ProjDetailAdapter adapter = new ProjDetailAdapter(mContext, viewModel.projDetailInfo.get(),viewModel);
//        binding.projBaseInfo.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        binding.projBaseInfo.setLayoutManager(manager);
        binding.projBaseInfo.addItemDecoration(new TitanItemDecoration());
        ProjSummaryAdapter adapter = new ProjSummaryAdapter(viewModel.detailItemModelList, detail);
        binding.projBaseInfo.setAdapter(adapter);
    }
}