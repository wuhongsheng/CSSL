package com.titan.model;

import java.io.Serializable;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目查询
 */

public class ProjSearch implements Serializable{
    /**
     * ID : 704160e6-1a35-4873-bdf5-698c75332de0
     * NUM : 2017062001002
     * NAME : 黎托街道大桥社区二区三期安置房建设项目
     * TIME : 2017/6/20 11:03:15
     * STATE : 1
     * TYPE : 1
     * ZB : 113.036175,28.1719583333333;
     */

    private String ID;
    private String NUM;
    private String NAME;
    private String TIME;
    private String STATE;
    private String TYPE;
    private String ZB;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

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

    public String getZB() {
        return ZB;
    }

    public void setZB(String ZB) {
        this.ZB = ZB;
    }
}
