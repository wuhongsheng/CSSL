package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.titan.cssl.R;
import com.titan.cssl.databinding.FragProjMeasureBinding;
import com.titan.util.ListViewUtil;

/**
 * Created by hanyw on 2017/11/2/002.
 * 水土保持措施
 */
public class ProjmeasureFragment extends Fragment {

    private Context mContext;
    private FragProjMeasureBinding binding;
    private ProjDetailViewModel viewModel;
    public static final int LOAD_ERROR = 0;

    public static ProjmeasureFragment getInstance() {
        return new ProjmeasureFragment();
    }

    public void setViewModel(ProjDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_proj_measure,
                container, false);
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }

    void setData() {
        if (viewModel.measurePCpq.isEmpty() && viewModel.measurePJsq.isEmpty()) {
            viewModel.hasData.set(false);
            return;
        }
        binding.tvCpq.setVisibility(viewModel.measurePCpq.isEmpty() ? View.GONE : View.VISIBLE);
        binding.tvJsq.setVisibility(viewModel.measurePJsq.isEmpty() ? View.GONE : View.VISIBLE);
        ProjmeasureExpLVAdapter adapter1 = new ProjmeasureExpLVAdapter(mContext, viewModel.measurePCpq
                , viewModel.measureCCpq, viewModel, true);
        binding.projMeasureExplvPcq.setAdapter(adapter1);
        ListViewUtil.setListViewHeightBasedOnChildren(binding.projMeasureExplvPcq);
        //展开
        ListViewUtil.groupExpandListener(binding.projMeasureExplvPcq);
        //收起
        ListViewUtil.collapseListener(binding.projMeasureExplvPcq);
        ProjmeasureExpLVAdapter adapter2 = new ProjmeasureExpLVAdapter(mContext, viewModel.measurePJsq
                , viewModel.measureCJsq, viewModel, false);
        binding.projMeasureExplvJsq.setAdapter(adapter2);
        ListViewUtil.setListViewHeightBasedOnChildren(binding.projMeasureExplvJsq);
        //展开
        ListViewUtil.groupExpandListener(binding.projMeasureExplvJsq);
        //收起
        ListViewUtil.collapseListener(binding.projMeasureExplvJsq);

    }
}
