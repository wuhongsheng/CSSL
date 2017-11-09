package com.titan.cssl.localcensor;

import android.databinding.ObservableBoolean;

import com.titan.BaseViewModel;

/**
 * Created by hanyw on 2017/11/6/006.
 * 现场审查viewmodel
 */

public class ProjLocalCensorViewModel extends BaseViewModel {
    public ObservableBoolean isTrue = new ObservableBoolean();
    public ObservableBoolean isFalse = new ObservableBoolean();

    private ProjLocalCensor localCensor;

    public ProjLocalCensorViewModel(ProjLocalCensor localCensor) {
        this.localCensor = localCensor;
    }

    /**
     * 添加照片
     */
    public void addImage() {
        localCensor.addImage();
    }

    /**
     * 提交审查数据
     */
    public void localCensorSubmit() {
        localCensor.localCensorSubmit();
    }

    /**
     * 删除照片
     *
     * @param position 照片数组下标
     */
    public void del(int position) {
        localCensor.del(position);
    }
}
