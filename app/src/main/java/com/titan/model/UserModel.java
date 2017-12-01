package com.titan.model;

import java.io.Serializable;

/**
 * Created by hanyw on 2017/11/13/013.
 * 用户
 */

public class UserModel implements Serializable {
    /**
     * 用户名
     */
    private String USERNAME;
    /**
     * 密码
     */
    private String PASSWORD;
    /**
     * 用户所属区域ID
     */
    private String ORG;

    /**
     * 用户角色ID
     */
    private String ROLE;

    /**
     * 用户id
     */
    private String ID;

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getORG() {
        return ORG;
    }

    public void setORG(String ORG) {
        this.ORG = ORG;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
