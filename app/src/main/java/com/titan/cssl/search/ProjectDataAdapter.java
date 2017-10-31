package com.titan.cssl.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemProjectSearchBinding;

import java.util.List;

/**
 * Created by hanyw on 2017/10/31/031.
 */

public class ProjectDataAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;
    public ProjectDataAdapter(Context context,List<String> list){
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
        ItemProjectSearchBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_project_search,
                    viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setVariable(BR.name,list.get(i));
        return binding.getRoot();
    }
}
