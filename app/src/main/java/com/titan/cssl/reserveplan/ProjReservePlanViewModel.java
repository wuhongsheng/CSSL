package com.titan.cssl.reserveplan;

import com.titan.BaseViewModel;

/**
 * Created by hanyw on 2017/11/9/009.
 * 预案查看viewmodel
 */

public class ProjReservePlanViewModel extends BaseViewModel {


    private ProjReservePlan projReservePlan;
    public ProjReservePlanViewModel(ProjReservePlan projReservePlan){
        this.projReservePlan = projReservePlan;
    }

    public void load(){
        projReservePlan.load();
    }
}
