package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemProjRecordChildBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/12/4/004.
 */

public class ProjRecordAdapter extends BaseAdapter {
    private Context mContext;
    private List<List<String[]>> list;
    private ProjDetailViewModel viewModel;

    public ProjRecordAdapter(Context context,List<List<String[]>> list, ProjDetailViewModel viewModel){
        this.mContext = context;
        this.list = list;
        this.viewModel = viewModel;
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
        ItemProjRecordChildBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_proj_record_child,viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        ArrayList<String[]> arrayList = (ArrayList<String[]>) list.get(i);
        binding.setList(arrayList);
        if (arrayList.get(2)[0].equals("电子签名")){
//            Glide.with(mContext)
//                    .load(arrayList.get(2)[1])
//                    .placeholder(R.drawable.loading)
//                    .error(R.drawable.error)
//                    .into(binding.approvalImage);
//            binding.supervisePeople.setVisibility(View.GONE);
            binding.projPeople.setVisibility(View.GONE);
        }
//        if (arrayList.get(2)[0].equals("记录人")){
//            binding.approvalImage.setVisibility(View.GONE);
//        }
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }
}
