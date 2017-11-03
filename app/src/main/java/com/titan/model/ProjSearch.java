package com.titan.model;

import java.io.Serializable;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目查询
 */

public class ProjSearch implements Serializable{
    /**
     * 项目名称
     */
    private String name;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 项目类型
     */
    private String type;
    /**
     * 审批状态
     */
    private String statu;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }
}
