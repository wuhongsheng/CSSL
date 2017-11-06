package com.titan.cssl.localcensor;

import android.databinding.ObservableBoolean;
import android.util.Log;

import com.titan.BaseViewModel;

/**
 * Created by hanyw on 2017/11/6/006.
 */

public class ProjLocalCensorViewModel extends BaseViewModel {
    public ObservableBoolean isTrue = new ObservableBoolean();
    public ObservableBoolean isFalse = new ObservableBoolean();

    private ProjLocalCensor localCensor;

    public ProjLocalCensorViewModel(ProjLocalCensor localCensor){
        this.localCensor = localCensor;
    }

    public void addImage(){
        localCensor.addImage();
    }
    public void localCensorSubmit(){
        localCensor.localCensorSubmit();
        Log.e("tag","isF"+isFalse.get()+",isT"+isTrue.get());
    }
}
