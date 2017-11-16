package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.ObservableBoolean;

import com.titan.BaseViewModel;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目详情model
 */

public class ProjDetailViewModel extends BaseViewModel {

    private Context mContext;
    private ProjDetail projDetail;
    /**
     * 项目类型
     */
    public ObservableBoolean hasBaseinfo = new ObservableBoolean(true);

    public ProjDetailViewModel(Context context, ProjDetail projDetail) {
        this.mContext = context;
        this.projDetail = projDetail;
    }

    public void pagerSelect(int pager) {
        projDetail.pagerSelect(pager);
    }

    public void addCensor() {
        projDetail.addCensor();
    }


}
