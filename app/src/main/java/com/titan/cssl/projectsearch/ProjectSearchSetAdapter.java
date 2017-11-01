package com.titan.cssl.projectsearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemSearchDialogBinding;

import java.util.List;

/**
 * Created by hanyw on 2017/11/1/001.
 */

public class ProjectSearchSetAdapter extends BaseAdapter {

    private Context mContext;

    private ProjectSearchViewModel viewModel;

    private List<String> list;

    public ProjectSearchSetAdapter(Context context, ProjectSearchViewModel viewModel, List<String> list){
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
        ItemSearchDialogBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_search_dialog,viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setVariable(BR.name,list.get(i));
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }
}
