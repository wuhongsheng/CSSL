package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.bumptech.glide.Glide;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemProjRecordChildBinding;
import com.titan.cssl.databinding.ItemProjRecordParentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/12/8/008.
 */

public class ProjRecordExpLVAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> pList;
    private List<List<String[]>> cList;
    private ProjDetailViewModel viewModel;

    public ProjRecordExpLVAdapter(Context context, List<String> pList,List<List<String[]>> cList, ProjDetailViewModel viewModel){
        this.mContext = context;
        this.pList = pList;
        this.cList = cList;
        this.viewModel = viewModel;
    }

    public void setData(List<List<String[]>> cList){
        this.cList = cList;
    }
    @Override
    public int getGroupCount() {
        return pList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return cList.size();
    }

    @Override
    public Object getGroup(int i) {
        return pList.get(i);
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
        ItemProjRecordParentBinding binding;
        if (view==null){
            binding= DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_proj_record_parent,
                    viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setValue(pList.get(i));
        return binding.getRoot();
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ItemProjRecordChildBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_proj_record_child,viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        ArrayList<String[]> arrayList = (ArrayList<String[]>) cList.get(i1);
        binding.setList(arrayList);
        if (arrayList.get(2)[0].equals("电子签名")){
            Glide.with(mContext)
                    .load(arrayList.get(2)[1])
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(binding.approvalImage);
            binding.supervisePeople.setVisibility(View.GONE);
        }
        if (arrayList.get(2)[0].equals("记录人")){
            binding.approvalImage.setVisibility(View.GONE);
        }
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
