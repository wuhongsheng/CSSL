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
import com.titan.cssl.databinding.FragProjSurveyBinding;
import com.titan.data.source.DataRepository;
import com.titan.model.ProjDetailBaseinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目概况
 */

public class ProjSurveyFragment extends Fragment {

    private Context mContext;
    private FragProjSurveyBinding binding;
    private ProjDetailViewModel viewModel;
    private List<ProjDetailBaseinfo> list;
    private static ProjSurveyFragment Sington;

    public static ProjSurveyFragment getInstance(){
        if (Sington==null){
            Sington = new ProjSurveyFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_proj_survey,container,false);
        initData();
        ProjDetailAdapter adapter = new ProjDetailAdapter(mContext,list);
        binding.projSurvey.setAdapter(adapter);
        return binding.getRoot();
    }

    private void initData(){
        Log.e("tag", "getProjNum1:"+ DataRepository.getProjNum());
        List<ProjDetailBaseinfo> list = new ArrayList<>();
        for (int i = 0;i<20;i++){
            ProjDetailBaseinfo baseinfo = new ProjDetailBaseinfo();
            list.add(baseinfo);
        }
        this.list = list;
    }
}
