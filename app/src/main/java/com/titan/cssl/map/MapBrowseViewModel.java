package com.titan.cssl.map;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.titan.BaseViewModel;
import com.titan.cssl.remote.RemoteData;
import com.titan.data.source.DataRepository;
import com.titan.model.ProjSearch;

import java.util.List;
import java.util.Map;

public class MapBrowseViewModel extends BaseViewModel implements LocationDisplay.LocationChangedListener {

    private MapBrowse mapBrowse;
    private Point point;
    //是否启用采集 false否 true是
    public ObservableBoolean isCollection = new ObservableBoolean(false);
    //采集状态 true正在采集 false关闭采集
    public ObservableBoolean collectionStatu = new ObservableBoolean(false);
    //点数据集合
    public ObservableField<PointCollection> points = new ObservableField<>();

    public MapBrowseViewModel(MapBrowse mapBrowse, DataRepository dataRepository) {
        this.mapBrowse = mapBrowse;
        this.mDataRepository = dataRepository;
    }

    @Override
    public void onLocationChanged(LocationDisplay.LocationChangedEvent locationChangedEvent) {
        point = locationChangedEvent.getLocation().getPosition();
    }

    //定位当前设备位置
    public void onLoction() {
        mapBrowse.onLoction(point);
    }

    //采集点数据
    public void onMeasure() {
        collectionStatu.set(!collectionStatu.get());
        if (collectionStatu.get()) {
            startMeasure();
        } else {
            endMeasure();
        }
    }

    //开始采集
    public void startMeasure() {
        addPoint();
    }

    //结束采集
    public void endMeasure() {
        mapBrowse.endMeasure();
    }

    //确认选点
    public void addPoint() {
        if (collectionStatu.get()) {
            mapBrowse.addPoint();
        }
    }

    //撤销
    public void undo() {
        mapBrowse.undo();
    }

    //保存
    public void save() {
        StringBuilder builder = new StringBuilder();
        for (Point point : points.get()) {
            Point pp = (Point) GeometryEngine.project(point, SpatialReferences.getWgs84());
            builder.append(pp.getX()).append(",").append(pp.getY()).append(";");
        }
        String zb = builder.substring(0, builder.length() - 1);
        Log.e("tag", "zb:" + zb);
        ProjSearch projSearch = mDataRepository.getProjSearch();
        InsertZB(projSearch.getNUM(), convert(projSearch.getTYPE()), zb);
    }

    //取消
    public void cancel() {
        mapBrowse.cancel();
    }

    public void InsertZB(String projectBH, String projectType, String projectZB) {
        mapBrowse.showProgress();
        mDataRepository.InsertZB(projectBH, projectType, projectZB, new RemoteData.infoCallback() {
            @Override
            public void onFailure(String info) {
                mapBrowse.stopProgress();
                mapBrowse.showToast(info);
            }

            @Override
            public void onSuccess(String info) {
                mapBrowse.stopProgress();
                mapBrowse.showToast(info);
                isCollection.set(false);
                points.set(null);
            }
        });
    }

    private String convert(String projectType) {
        String result = "";
        switch (projectType) {
            case "3万㎡以下":
                result = "1";
                break;
            case "3-8万㎡":
                result = "2";
                break;
            case "8万㎡以上":
                result = "3";
                break;
        }
        return result;
    }

}
