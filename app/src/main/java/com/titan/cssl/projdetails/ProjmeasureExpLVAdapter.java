package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemProjMeasureChildBinding;
import com.titan.cssl.databinding.ItemProjMeasureParentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/3/003.
 * 水保措施
 */

public class ProjmeasureExpLVAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> list;

    public ProjmeasureExpLVAdapter(Context context, List<String> list){
        this.list = list;
        this.mContext = context;
    }
    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
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
        ItemProjMeasureParentBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_proj_measure_parent,
                    viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setVariable(BR.value,list.get(i));
        return binding.getRoot();
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ItemProjMeasureChildBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_proj_measure_child,
                    viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        List<String> jpgList = new ArrayList<>();
        List<String> docList = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            jpgList.add("测试"+j+".jpg");
            docList.add("测试"+j+".doc");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,R.layout.item_arrayadapter_test,jpgList);
        binding.projPhotoList.setAdapter(arrayAdapter);
        setListViewHeightBasedOnChildren(binding.projPhotoList);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(mContext,R.layout.item_arrayadapter_test,docList);
        binding.projDocList.setAdapter(arrayAdapter1);
        setListViewHeightBasedOnChildren(binding.projDocList);
        return binding.getRoot();
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        //初始化高度
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //计算子项View的宽高，注意listview所在的要是linearlayout布局
            listItem.measure(0, 0);
            //统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
            /*
         * listView.getDividerHeight()获取子项间分隔符占用的高度，有多少项就乘以多少个,
         * 5是在listview中填充的子view的padding值
         * params.height最后得到整个ListView完整显示需要的高度
         * 最后将params.height设置为listview的高度
         */
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount())) + 5;
        listView.setLayoutParams(params);
    }
}
