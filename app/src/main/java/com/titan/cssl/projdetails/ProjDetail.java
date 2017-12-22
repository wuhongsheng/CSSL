package com.titan.cssl.projdetails;

import com.titan.model.ProjDetailMeasure;

import java.util.List;

/**
 * Created by hanyw on 2017/11/3/003.
 * 项目详细信息接口
 */

public interface ProjDetail {
    /**
     * 页面选择
     *
     * @param pager 页码
     */
    void pagerSelect(int pager);

    /**
     * 查看项目措施详细信息
     */
    void measureInfo(ProjDetailMeasure.subBean subBean);

    /**
     * 显示信息
     *
     * @param info 信息内容
     */
    void showToast(String info);

    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 关闭进度条
     */
    void stopProgress();

    /**
     * 刷新页面数据
     * @param type
     */
    void refresh(int type);

    /**
     * 展示项目某一条的信息
     * @param list
     */
    void showSubInfo(List<String[]> list);

    /**
     * 打开地图
     */
    void showMap();
}
