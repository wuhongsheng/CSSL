package com.titan.cssl.projsearch.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.titan.cssl.R;
import com.titan.cssl.databinding.FragBaseInfoBinding;
import com.titan.cssl.databinding.FragProjMeasureBinding;

/**
 * Created by hanyw on 2017/11/2/002.
 * 水土保持措施
 */

public class ProjmeasureFragment extends Fragment {

    private Context mContext;
    private FragProjMeasureBinding binding;
    private ProjDetailViewModel viewModel;
    private static ProjBaseFactsFragment Sington;

    public static ProjBaseFactsFragment getInstance(){
        if (Sington==null){
            Sington = new ProjBaseFactsFragment();
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
        return binding.getRoot();
    }
}
