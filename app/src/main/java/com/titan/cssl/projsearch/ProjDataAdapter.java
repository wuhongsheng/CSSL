package com.titan.cssl.projsearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemProjectSearchBinding;
import com.titan.model.ProjSearch;

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

    public ProjDataAdapter(Context context,ProjSearchViewModel viewModel) {
        this.mContext = context;
        this.viewModel = viewModel;
        this.list = viewModel.projSearchList.get();
    }

    public void setDate(List<ProjSearch> list){
        this.list = list;
        this.notifyDataSetChanged();
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
        binding.setVariable(BR.projSearch,list.get(i));
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }
}
