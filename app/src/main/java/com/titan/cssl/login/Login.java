package com.titan.cssl.login;

/**
 * Created by hanyw on 2017/9/13/013.
 * 登录接口
 */

public interface Login {
    /**
     * 跳转
     */
    void  onNext();

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
