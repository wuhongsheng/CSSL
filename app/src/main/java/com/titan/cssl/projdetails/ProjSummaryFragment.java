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
import com.titan.cssl.databinding.FragProjSurveyBinding;
import com.titan.util.TitanItemDecoration;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目概况
 */

public class ProjSummaryFragment extends Fragment {

    private FragProjSurveyBinding binding;
    private ProjDetailViewModel viewModel;
    private ProjDetail detail;

    public static ProjSummaryFragment getInstance() {
        return new ProjSummaryFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_proj_survey, container, false);
        return binding.getRoot();
    }

    public void setData() {
//        ProjDetailAdapter adapter = new ProjDetailAdapter(mContext, viewModel.projDetailInfo.get(), viewModel);
//        binding.projDetailLv.setAdapter(adapter);
//        ListViewUtil.setListViewHeightBasedOnChildren(binding.projDetailLv);

//        if (viewModel.projInfoParent.get() == null || viewModel.projInfoChild.get().size() <= 0) {
//            return;
//        }
//        List<Map<String, ?>> list = viewModel.projInfoChild.get();
//        ProjSummaryExpLVAdapter expLVAdapter = new ProjSummaryExpLVAdapter(mContext,
//                viewModel.projInfoParent.get(), list, viewModel);
//        binding.projDetailExplv.setAdapter(expLVAdapter);
//        binding.projDetailExplv.setGroupIndicator(null);
//        //展开
//        ListViewUtil.groupExpandListener(binding.projDetailExplv);
//        //收起
//        ListViewUtil.collapseListener(binding.projDetailExplv);
//        Log.e("tag", "summary:" + viewModel.projInfoParent.get() + "," + viewModel.projInfoChild.get());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        binding.projDetailRv.setLayoutManager(manager);
        binding.projDetailRv.addItemDecoration(new TitanItemDecoration());
        ProjSummaryAdapter adapter = new ProjSummaryAdapter(viewModel.detailItemModelList, detail);
        binding.projDetailRv.setAdapter(adapter);

    }
}