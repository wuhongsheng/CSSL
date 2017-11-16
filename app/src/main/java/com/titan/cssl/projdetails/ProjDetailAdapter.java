package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemProjDetailBinding;
import com.titan.model.ProjDetailBaseinfo;

import java.util.List;

/**
 * Created by hanyw on 2017/11/3/003.
 * 项目详情
 */

public class ProjDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProjDetailBaseinfo> list;

    public ProjDetailAdapter(Context context, List<ProjDetailBaseinfo> list){
        this.mContext = context;
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
        ItemProjDetailBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_proj_detail,viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setVariable(BR.name,"测试字段");
        binding.setVariable(BR.value,"测试字段值");
        return binding.getRoot();
    }
}
