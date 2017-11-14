package com.titan.cssl.projsearch;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索接口
 */

public interface ProjSearchSet {

    /**
     * 检索设置
     */
    void searchSet();

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
    void projDetails();
    /**
     * 点击结果项进入详情页面
     */
    void search();

    /**
     * 根据当前位置搜索项目
     */
    void locSearch();
}
