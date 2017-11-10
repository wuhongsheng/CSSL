package com.titan.cssl.projsearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;

import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemProjectSearchBinding;
import com.titan.model.ProjSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目数据
 */

public class ProjDataAdapter extends BaseAdapter {

    private Context mContext;
    /**
     * 当前数据
     */
    private List<ProjSearch> list;
    /**
     * 项目检索viewmodel
     */
    private ProjSearchViewModel viewModel;

    public ProjDataAdapter(Context context, List<ProjSearch> list, ProjSearchViewModel viewModel) {
        this.mContext = context;
        this.viewModel = viewModel;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemProjectSearchBinding binding;
        if (view == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_project_search,
                    viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setVariable(BR.num,list.get(i).getNUM());
        binding.setVariable(BR.name, list.get(i).getNAME());
        binding.setVariable(BR.time, list.get(i).getTIME());
        binding.setVariable(BR.type, list.get(i).getTYPE());
        binding.setVariable(BR.state, list.get(i).getSTATE());
        binding.setVariable(BR.distance,list.get(i).getDISTANCE());
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }
}
