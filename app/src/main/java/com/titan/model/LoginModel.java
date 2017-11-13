package com.titan.model;

import java.io.Serializable;

/**
 * Created by hanyw on 2017/11/13/013.
 * 用户登录
 */

public class LoginModel implements Serializable {
    /**
     * 用户权限
     */
    private String ROLE;

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }
}
