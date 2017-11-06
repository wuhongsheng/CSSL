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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hanyw on 2017/11/2/002.
 * 水土保持措施
 */

public class ProjmeasureFragment extends Fragment {

    private Context mContext;
    private String[] measureArray = new String[]{"工程措施","植物措施","临时措施","管理措施","其它"};
    private FragProjMeasureBinding binding;
    private ProjDetailViewModel viewModel;
    private static ProjmeasureFragment Sington;
    private List<String> list = new ArrayList<>();

    public static ProjmeasureFragment getInstance(){
        if (Sington==null){
            Sington = new ProjmeasureFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_proj_measure,container,false);
        list=Arrays.asList(measureArray);
        ProjmeasureExpLVAdapter adapter = new ProjmeasureExpLVAdapter(mContext,list,viewModel);
        binding.projMeasureExplv.setGroupIndicator(null);
        binding.projMeasureExplv.setAdapter(adapter);
        return binding.getRoot();
    }

}
