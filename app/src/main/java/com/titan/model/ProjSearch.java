package com.titan.model;

import java.io.Serializable;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目查询
 */

public class ProjSearch implements Serializable{
    private String NUM;//项目编号
    private String NAME;//项目名称
    private String TIME;//申请时间
    private String STATE;//审批状态
    private String TYPE;//项目类型
    private String DISTANCE;//据当前位置的距离

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getDISTANCE() {
        return DISTANCE;
    }

    public void setDISTANCE(String DISTANCE) {
        this.DISTANCE = DISTANCE;
    }
}
