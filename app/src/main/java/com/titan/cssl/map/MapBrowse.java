package com.titan.cssl.map;

import com.esri.arcgisruntime.geometry.Point;

public interface MapBrowse {
    //定位当前设备位置
    void onLoction(Point point);

    //确认选点
    void addPoint();

    //撤销
    void undo();

    //取消采集
    void cancel();

    //结束采集
    void endMeasure();

    //导航
    void navigate();

    //项目定位
    void projLocation();

    //图层切换
    void layerChange();

    //坐标采集
    void collection();

    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 关闭进度条
     */
    void stopProgress();

    /**
     * 显示信息
     *
     * @param info 信息内容
     */
    void showToast(String info);

    //采集完成之后刷新项目范围显示
    void refreshGraphic();
}
