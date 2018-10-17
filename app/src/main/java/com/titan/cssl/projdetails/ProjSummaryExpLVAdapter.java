package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.util.MyFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hanyw on 2017/11/28/028.
 * 概要信息二级内容适配器
 */

public class ProjSummaryExpLVAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private String pName;
    private List<Map<String,?>> cList;
    private ProjDetailViewModel viewModel;

    public ProjSummaryExpLVAdapter(Context context, String pName, List<Map<String,?>> cList,
                                   ProjDetailViewModel viewModel) {
        this.mContext = context;
        this.pName = pName;
        this.cList = cList;
        this.viewModel = viewModel;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        return cList.size();
    }

    @Override
    public Object getGroup(int i) {
        return pName;
    }

    @Override
    public Object getChild(int i, int i1) {
        return cList.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
//        ItemProjSummaryParentBinding binding;
//        if (view == null) {
//            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_proj_summary_parent
//                    , viewGroup, false);
//        } else {
//            binding = DataBindingUtil.getBinding(view);
//        }
//        binding.setVariable(BR.value, pName);
//        return binding.getRoot();
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
//        ViewDataBinding binding;
//        if (view == null) {
//            if (pName.equals("项目组成")){
//                binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_proj_summary_child
//                        , viewGroup, false);
//            }else {
//                binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_proj_summary_child2
//                        , viewGroup, false);
//            }
//        } else {
//            binding = DataBindingUtil.getBinding(view);
//        }
//        Map<String,?> map = cList.get(i1);
//        ArrayList<String[]> list = new ArrayList<>();
//        for (String k:map.keySet()){
//            String[] array = new String[2];
//            array[0] = MyFileUtil.getProperties(mContext,k);
//            array[1] = map.get(k)+"";
//            list.add(array);
//        }
//        binding.setVariable(BR.type,pName.equals("项目组成"));
//        binding.setVariable(BR.list,list);
//        binding.setVariable(BR.viewmodel,viewModel);
//        return binding.getRoot();
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
