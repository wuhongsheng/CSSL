package com.titan.cssl.projdetails;

import android.databinding.ObservableField;

import com.titan.base.BaseViewModel;
import com.titan.model.ProjDetailItemModel;

import java.util.ArrayList;
import java.util.List;

public class ProjContentItemViewModel extends BaseViewModel {

    public ObservableField<ProjDetailItemModel> item = new ObservableField<>();

    public ObservableField<ProjDetailItemModel.SubContent> subItem = new ObservableField<>();

    private ProjDetail detail;
    public ProjContentItemViewModel(ProjDetail detail){
        this.detail = detail;
    }

    public void showSubInfo(){
        switch (item.get().getType()){
            case 0:
                detail.showSubInfo(getList(0));
                break;
            case 2:
                detail.showSubInfo(getList(2));
                break;
            case 3:
                detail.showSubInfo(getList(3));
                break;
        }
    }

    private List<String[]> getList(int type){
        List<String[]> list = new ArrayList<>();
        switch (type){
            case 0:
                String[] strs1 = new String[2];
                strs1[0]= item.get().getName();
                strs1[1] = item.get().getValue();
                list.add(strs1);
                break;
            case 2:
                String[] strs2 = new String[2];
                strs2[0]= item.get().getSubContent().getBuildArea();
                strs2[1] = item.get().getSubContent().getBuildAreaValue();
                String[] strs3 = new String[2];
                strs3[0]= item.get().getSubContent().getLengArea();
                strs3[1] = item.get().getSubContent().getLengAreaValue();
                String[] strs4 = new String[2];
                strs4[0]= item.get().getSubContent().getWfl();
                strs4[1] = item.get().getSubContent().getWflValue();
                String[] strs5 = new String[2];
                strs5[0]= item.get().getSubContent().getTfl();
                strs5[1] = item.get().getSubContent().getTflValue();
                list.add(strs2);
                list.add(strs3);
                list.add(strs4);
                list.add(strs5);
                break;
            case 3:
                String[] strs6 = new String[2];
                strs6[0]= item.get().getSubContent().getYear();
                strs6[1] = item.get().getSubContent().getYearValue();
                String[] strs7 = new String[2];
                strs7[0]= item.get().getSubContent().getInvestment();
                strs7[1] = item.get().getSubContent().getInvestmentValue();
                String[] strs8 = new String[2];
                strs8[0]= item.get().getSubContent().getInstructions();
                strs8[1] = item.get().getSubContent().getInstructionsValue();
                list.add(strs6);
                list.add(strs7);
                list.add(strs8);
                break;
        }
        return list;
    }
}
