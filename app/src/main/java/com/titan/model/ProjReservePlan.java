package com.titan.model;

import java.util.List;

/**
 * Created by hanyw on 2017/11/8/008.
 * 项目预案
 */

public class ProjReservePlan {

    private List<String> YUAN;//预案名称
    private List<String> YUANURL;//预案链接
    public void setYUAN(List<String> YUAN) {
        this.YUAN = YUAN;
    }
    public List<String> getYUAN() {
        return YUAN;
    }

    public void setYUANURL(List<String> YUANURL) {
        this.YUANURL = YUANURL;
    }
    public List<String> getYUANURL() {
        return YUANURL;
    }

}
