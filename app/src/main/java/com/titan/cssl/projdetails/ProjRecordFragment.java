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
import android.widget.ExpandableListView;

import com.nostra13.universalimageloader.utils.L;
import com.titan.cssl.R;
import com.titan.cssl.databinding.FragBaseInfoBinding;
import com.titan.cssl.databinding.FragRecordBinding;
import com.titan.util.ListViewUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目记录
 */

public class ProjRecordFragment extends Fragment {

    private Context mContext;
    private FragRecordBinding binding;
    private ProjDetailViewModel viewModel;

    public static ProjRecordFragment getInstance() {
        return new ProjRecordFragment();
    }

    public void setViewModel(ProjDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_record, container, false);
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }

    /**
     * 行政审批记录
     */
    public void approve() {
        ProjRecordAdapter adapter = new ProjRecordAdapter(mContext, viewModel.projApproval.get(),
                viewModel);
        binding.approvalList.setAdapter(adapter);
        ListViewUtil.setListViewHeightBasedOnChildren(binding.approvalList);
        viewModel.showApproval.set(true);
    }

    /**
     * 日常监督记录
     */
    public void supervise() {
        ProjRecordAdapter adapter = new ProjRecordAdapter(mContext, viewModel.projSupervise.get(),
                viewModel);
        binding.superviseList.setAdapter(adapter);
        viewModel.showSupervise.set(true);
        ListViewUtil.setListViewHeightBasedOnChildren(binding.superviseList);
    }

}