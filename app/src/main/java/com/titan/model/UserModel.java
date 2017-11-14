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
     * 用户所在区域
     */
    private String ROGE;

    /**
     * 用户权限
     */
    private String ROLE;

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

    public String getROGE() {
        return ROGE;
    }

    public void setROGE(String ROGE) {
        this.ROGE = ROGE;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }
}
