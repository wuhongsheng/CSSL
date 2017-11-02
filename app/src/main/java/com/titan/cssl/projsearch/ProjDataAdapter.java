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
import com.titan.cssl.model.ProjSearch;

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
     * 原始数据
     */
    private List<ProjSearch> backList;
    /**
     * 自定义Filter
     */
    private MyFilter myFilter;
    /**
     * 项目检索viewmodel
     */
    private ProjSearchViewModel viewModel;

    public ProjDataAdapter(Context context, List<ProjSearch> list, ProjSearchViewModel viewModel) {
        this.mContext = context;
        this.viewModel = viewModel;
        this.list = list;
        this.backList = list;
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
        binding.setVariable(BR.name, list.get(i).getName());
        binding.setVariable(BR.time, list.get(i).getStartTime());
        binding.setVariable(BR.type, list.get(i).getType());
        binding.setVariable(BR.statu, list.get(i).getStatu());
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }

    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    public class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            String str = charSequence.toString().trim();
            if (TextUtils.isEmpty(str)) {
                list = backList;
            } else {
                list = new ArrayList<>();
                for (ProjSearch value : backList) {
                    if (value.getName().contains(charSequence) || value.getStartTime().contains(charSequence) ||
                            value.getType().contains(charSequence) || value.getStatu().contains(charSequence)) {
                        list.add(value);
                    }
                }
            }
            result.values = list;
            result.count = list.size();
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list = (List<ProjSearch>) filterResults.values;
            if (filterResults.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
