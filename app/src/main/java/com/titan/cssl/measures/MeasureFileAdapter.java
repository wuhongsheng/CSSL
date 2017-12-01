package com.titan.cssl.measures;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemCensorImgBinding;
import com.titan.cssl.databinding.ItemMeasureBinding;
import com.titan.model.ProjDetailMeasure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/6/006.
 * 现场审查照片适配器
 */

public class MeasureFileAdapter extends BaseAdapter {

    private Context mContext;
    private List<String[]> list;//图片数据
    private MeasureViewModel viewModel;

    public MeasureFileAdapter(Context context, List<String[]> list,
                              MeasureViewModel viewModel) {
        this.mContext = context;
        this.list = setListData(list);
        this.viewModel = viewModel;
    }

    private List<String[]> setListData(List<String[]> list){
        if (list==null||list.size()<=0){
            list = new ArrayList<>();
            list.add(new String[]{"暂无数据",""});
        }
        return list;
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
        ItemMeasureBinding binding;
        if (view == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_measure, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setVariable(BR.name, list.get(i)[0]);
        binding.setVariable(BR.url, list.get(i)[1]);
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }
}
