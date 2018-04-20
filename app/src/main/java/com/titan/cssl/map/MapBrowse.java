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
}
