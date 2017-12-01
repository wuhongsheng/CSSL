package com.titan.cssl.localcensor;

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

import java.util.List;

/**
 * Created by hanyw on 2017/11/6/006.
 * 现场审查照片适配器
 */

public class ProjOpinionImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;//图片地址
    private ProjOpinionViewModel model;

    public ProjOpinionImageAdapter(Context context, List<String> list, ProjOpinionViewModel model) {
        this.mContext = context;
        this.list = list;
        this.model = model;
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
        ItemCensorImgBinding binding;
        if (view == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_censor_img, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }
        Glide.with(mContext).load(list.get(i)).into(binding.itemCensorImg);
        binding.setVariable(BR.position, i);
        binding.setViewmodel(model);
        return binding.getRoot();
    }
}
