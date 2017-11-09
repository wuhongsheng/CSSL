package com.titan.cssl.reserveplan;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemReservePlanBinding;

import java.util.List;

/**
 * Created by hanyw on 2017/11/9/009.
 * 预案列表适配器
 */

public class ProjReservePlanAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;
    private ProjReservePlanViewModel model;
    public ProjReservePlanAdapter(Context context, List<String> list,ProjReservePlanViewModel model){
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
        ItemReservePlanBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_reserve_plan,viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setVariable(BR.value,list.get(i));
        binding.setViewmodel(model);
        return binding.getRoot();
    }
}
