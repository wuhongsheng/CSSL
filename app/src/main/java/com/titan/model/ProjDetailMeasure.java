package com.titan.model;

/**
 * Created by hanyw on 2017/11/3/003.
 * 项目措施
 */

public class ProjDetailMeasure {
    private String CSNAME;//措施名称
    private String CSLB;//类别
    private String MS;//描述
    private String XCZP;//现场照片
    private String SJFA;//设计方案
    private String SPSX;//审批手续

    public String getCSNAME() {
        return CSNAME;
    }

    public void setCSNAME(String CSNAME) {
        this.CSNAME = CSNAME;
    }

    public String getCSLB() {
        return CSLB;
    }

    public void setCSLB(String CSLB) {
        this.CSLB = CSLB;
    }

    public String getMS() {
        return MS;
    }

    public void setMS(String MS) {
        this.MS = MS;
    }

    public String getXCZP() {
        return XCZP;
    }

    public void setXCZP(String XCZP) {
        this.XCZP = XCZP;
    }

    public String getSJFA() {
        return SJFA;
    }

    public void setSJFA(String SJFA) {
        this.SJFA = SJFA;
    }

    public String getSPSX() {
        return SPSX;
    }

    public void setSPSX(String SPSX) {
        this.SPSX = SPSX;
    }
}
