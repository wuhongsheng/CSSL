package com.titan.cssl.measures;

/**
 * Created by hanyw on 2017/11/17/017.
 * 项目措施接口
 */

public interface Measure {
    /**
     * 添加整改意见
     */
    void addOpinion();

    /**
     * 打开图片
     */
    void openPhoto();

    /**
     * 显示进度条
     */
    void  showProgress();

    /**
     * 关闭进度条
     */
    void  stopProgress();

    /**
     * 显示信息
     * @param info 信息内容
     */
    void  showToast(String info);
}
