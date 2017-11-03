package com.titan.cssl.projdetails;

import android.content.Context;

import com.titan.BaseViewModel;
import com.titan.cssl.util.ToastUtil;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目详情model
 */

public class ProjDetailViewModel extends BaseViewModel {

    private Context mContext;
    private ProjDetail projDetail;

    public ProjDetailViewModel(Context context, ProjDetail projDetail) {
        this.mContext = context;
        this.projDetail = projDetail;
    }

    public void pagerSelect(int pager) {
        projDetail.pagerSelect(pager);
    }

    public void test() {
        ToastUtil.setToast(mContext, "asd");
    }


}
