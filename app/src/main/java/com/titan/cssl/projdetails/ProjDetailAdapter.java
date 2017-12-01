package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemProjDetailBinding;
import com.titan.cssl.databinding.ItemProjDetailExplvBinding;
import com.titan.model.ProjDetailBaseinfo;
import com.titan.util.ListViewUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hanyw on 2017/11/3/003.
 * 项目详情
 */

public class ProjDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<String[]> list;
    private ProjDetailViewModel viewModel;

    public ProjDetailAdapter(Context context, List<String[]> list, ProjDetailViewModel viewModel) {
        this.mContext = context;
        this.list = list;
        this.viewModel = viewModel;
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
        ItemProjDetailBinding binding;
        if (view == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_proj_detail, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }
        ArrayList<String[]> tempList = new ArrayList<>();
        tempList.add(list.get(i));
        binding.setVariable(BR.list,tempList);
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }
}
