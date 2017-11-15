package com.titan.cssl.projsearch;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.model.LatLng;
import com.titan.BaseViewModel;
import com.titan.data.source.DataRepository;
import com.titan.util.ToastUtil;


/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索viewmodel
 */

public class ProjSearchViewModel extends BaseViewModel implements BDLocationListener {
    private ProjSearchSet projSearchSet;

    private DateChoose dateChoose;

    private OptionSelect optionSelect;

    private DataRepository mDataRepository;

    /**
     * 判断设置的时间 true：开始时间；false：结束时间
     */
    public ObservableBoolean timeSet = new ObservableBoolean();

    public ObservableBoolean isChecked = new ObservableBoolean();

    public ObservableField<String> startTime = new ObservableField<>();

    public ObservableField<String> endTime = new ObservableField<>();

    public ObservableField<String> projectType = new ObservableField<>();

    public ObservableField<String> projectStatus = new ObservableField<>();

    public ObservableField<String> keyWord = new ObservableField<>();

    public ObservableField<String> projNum = new ObservableField<>();

    /**
     * 当前坐标点
     */
    public ObservableField<LatLng> locPoint = new ObservableField<>();


    public ProjSearchViewModel(ProjSearchSet projSearchSet, DataRepository mDataRepository) {
        this.projSearchSet = projSearchSet;
        this.mDataRepository = mDataRepository;
    }


    public ProjSearchViewModel(DateChoose dateChoose) {
        this.dateChoose = dateChoose;
    }


    public ProjSearchViewModel(OptionSelect optionSelect) {
        this.optionSelect = optionSelect;
    }


    /**
     * 检索设置dialog
     */
    public void searchSet() {
        projSearchSet.searchSet();
    }

    public void startTimeSet() {
        projSearchSet.startTimeSet();
    }

    public void endTimeSet() {
        projSearchSet.endTimeSet();
    }

    public void setTimeSure() {
        if (timeSet.get()) {
            dateChoose.startTime();
        } else {
            dateChoose.endTime();
        }
    }

    public void projectTypeSet() {
        projSearchSet.projectTypeSet();
    }

    public void approvalStatuSet() {
        projSearchSet.approvalStatuSet();
    }

    public void optionSelect(String value) {
        optionSelect.select(value);
    }

    public void projDetails(String num) {
        projSearchSet.projDetails();
        mDataRepository.setProjNum(num);
        projNum.set(num);
        Log.e("tag","num:"+num);
    }

    public void search() {
        projSearchSet.search();
    }

    public void locSearch(){
        projSearchSet.locSearch();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {
            double latitude = bdLocation.getLatitude();    //获取纬度信息
            double longitude = bdLocation.getLongitude();    //获取经度信息
            float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = bdLocation.getCoorType();
            locPoint.set(new LatLng(latitude,longitude));
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            Log.e("tag","lat:"+latitude+","+"lon:"+longitude+"radius："+radius+","+coorType);
        }else {
            ToastUtil.setToast(mContext,"定位失败："+bdLocation.getLocTypeDescription()+",请检查权限是否授予");
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
