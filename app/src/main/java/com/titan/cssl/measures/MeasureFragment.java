package com.titan.cssl.measures;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.titan.cssl.R;
import com.titan.cssl.databinding.FragMeasureBinding;
import com.titan.cssl.localcensor.ProjOpinionActivity;
import com.titan.model.ProjDetailMeasure;
import com.titan.util.ListViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/17/017.
 * 水保措施详情页
 */

public class MeasureFragment extends Fragment implements Measure {

    private Context mContext;
    private FragMeasureBinding binding;
    private MeasureViewModel viewModel;
    private ProjDetailMeasure.subBean subBean;

    public static MeasureFragment getInstance(ProjDetailMeasure.subBean subBean) {
        MeasureFragment sington = new MeasureFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("subBean", subBean);
        sington.setArguments(bundle);
        return sington;
    }

    public void setViewModel(MeasureViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_measure, container, false);
        mContext = getActivity();
        binding.setViewmodel(viewModel);
        initData();
        initView();
        return binding.getRoot();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (getArguments() != null) {
            subBean = (ProjDetailMeasure.subBean) getArguments().getSerializable("subBean");
        }
    }

    /**
     * 初始化页面
     */
    private void initView() {
        viewModel.brief.set(subBean.getJYSM());
        viewModel.depict.set(viewModel.getString(subBean.getMS()));
        viewModel.invest.set(viewModel.getString(subBean.getTZ()));

        MeasureFileAdapter photoAdapter = new MeasureFileAdapter(mContext,
                viewModel.getPhotoList(subBean.getXCZP()),viewModel);
        binding.projPhotoList.setAdapter(photoAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(binding.projPhotoList);

        MeasureFileAdapter docAdapter = new MeasureFileAdapter(mContext,
                viewModel.getDocList(subBean.getDOC()),viewModel);
        binding.projDocList.setAdapter(docAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(binding.projDocList);

        if (subBean.getXCZF().size()==0) {
            viewModel.hasInfo.set(false);
            return;
        }
        viewModel.hasInfo.set(true);
        viewModel.opinion.set(subBean.getXCZF().get(0).getCheckResult());
        ListViewUtil.setListViewHeightBasedOnChildren(binding.opinionPhotoList);
    }

    /**
     * 添加整改意见
     */
    @Override
    public void addOpinion() {
        Intent intent = new Intent(mContext, ProjOpinionActivity.class);
        intent.putExtra("measure",subBean);
        startActivity(intent);
    }

    @Override
    public void openPhoto(String url) {
        viewModel.getPhoto(mContext,url);
    }
}
