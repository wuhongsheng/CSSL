package com.titan.cssl.projsearch;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索接口
 */

public interface ProjSearchSet {

    /**
     * 检索设置：设置开始时间
     */
    void startTimeSet();

    /**
     * 检索设置：设置结束时间
     */
    void endTimeSet();

    /**
     * 检索设置：项目类型设置
     */
    void projectTypeSet();

    /**
     * 检索设置：审批状态设置
     */
    void approvalStatuSet();

    /**
     * 点击结果项进入详情页面
     */
    void projDetails(String type);

    /**
     * 根据当前位置搜索项目
     */
    void locSearch();

    /**
     * 显示信息
     * @param info 信息内容
     */
    void  showToast(String info);

    /**
     * 显示进度条
     */
    void  showProgress();

    /**
     * 关闭进度条
     */
    void  stopProgress();

    /**
     * 刷新页面
     * @param isLoadMore 是否上拉加载 true 是；false 否
     */
    void refresh(boolean isLoadMore);

    void showEnd();

    void showLoading();
}
