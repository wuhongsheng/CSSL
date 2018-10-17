package com.titan.cssl.projdetails;

import com.titan.base.BaseAdapter;
import com.titan.cssl.R;
import com.titan.model.ProjDetailItemModel;

import java.util.List;

public class ProjSummaryAdapter extends BaseAdapter {

    private List<ProjDetailItemModel> items;
    private ProjDetail detail;

    public ProjSummaryAdapter(List<ProjDetailItemModel> items,ProjDetail detail) {
        this.items = items;
        this.detail = detail;
    }

    @Override
    public int getLayoutIdForPosition(int position) {
        int layerId = 0;
        switch (items.get(position).getType()) {
            case 0:
                layerId = R.layout.item_proj_detail;
                break;
            case 1:
                layerId = R.layout.item_proj_summary_parent;
                break;
            case 2:
                layerId = R.layout.item_proj_summary_child;
                break;
            case 3:
                layerId = R.layout.item_proj_summary_child2;
                break;
        }
        return layerId;
    }

    @Override
    public Object getLayoutViewModel(int position) {
        ProjContentItemViewModel itemViewModel = new ProjContentItemViewModel(detail);
        itemViewModel.item.set(items.get(position));
        itemViewModel.subItem.set(items.get(position).getSubContent());
        return itemViewModel;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
