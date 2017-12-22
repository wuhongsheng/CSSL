package com.titan.cssl.statistics;

import java.util.List;

/**
 * Created by hanyw on 2017/12/7/007.
 * 项目统计接口
 */

public interface Statistics {
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

    void setData();

    void initBarChart();
}
