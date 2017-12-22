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

import com.titan.cssl.R;
import com.titan.cssl.databinding.FragProjSurveyBinding;
import com.titan.util.ListViewUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目概况
 */

public class ProjSummaryFragment extends Fragment{

    private Context mContext;
    private FragProjSurveyBinding binding;
    private ProjDetailViewModel viewModel;

    public static ProjSummaryFragment getInstance() {
        return new ProjSummaryFragment();
    }

    public void setViewModel(ProjDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_proj_survey, container, false);
        return binding.getRoot();
    }

    public void setData() {
        ProjDetailAdapter adapter = new ProjDetailAdapter(mContext, viewModel.projDetailInfo.get(), viewModel);
        binding.projDetailLv.setAdapter(adapter);
        ListViewUtil.setListViewHeightBasedOnChildren(binding.projDetailLv);

        if (viewModel.projInfoParent.get() == null || viewModel.projInfoChild.get().size() <= 0) {
            return;
        }
        List<Map<String, ?>> list = viewModel.projInfoChild.get();
        ProjSummaryExpLVAdapter expLVAdapter = new ProjSummaryExpLVAdapter(mContext,
                viewModel.projInfoParent.get(), list, viewModel);
        binding.projDetailExplv.setAdapter(expLVAdapter);
        binding.projDetailExplv.setGroupIndicator(null);
        //展开
        groupExpandListener(binding.projDetailExplv);
        //收起
        collapseListener(binding.projDetailExplv);
        Log.e("tag", "summary:" + viewModel.projInfoParent.get() + "," + viewModel.projInfoChild.get());
    }

    /**
     * 收起子级时计算高度
     *
     * @param listView
     */
    private void collapseListener(final ExpandableListView listView) {
        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ListViewUtil.setListViewHeightBasedOnChildren(listView);
            }
        });
    }

    /**
     * 展开子级时计算高度
     *
     * @param listView
     */
    private void groupExpandListener(final ExpandableListView listView) {
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ListViewUtil.setListViewHeightBasedOnChildren(listView);

            }
        });
    }
}