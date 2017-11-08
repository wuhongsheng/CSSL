package com.titan.model;

import java.util.List;

/**
 * Created by hanyw on 2017/11/8/008.
 * 现场执法
 */

public class ProjCensor {
    private String NUM;//项目编号
    private String ZJYJ;//专家意见以审批提交时间作为显示名称和查询参数,时间格式为:yyyy-MM-dd HH:mm
    private List<String> ZPMC;//照片名称
    private List<String> ZPURL;//照片对应链接
    private Boolean SCYJ;//审查意见; true:属实;false:不属实

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    public String getZJYJ() {
        return ZJYJ;
    }

    public void setZJYJ(String ZJYJ) {
        this.ZJYJ = ZJYJ;
    }

    public List<String> getZPMC() {
        return ZPMC;
    }

    public void setZPMC(List<String> ZPMC) {
        this.ZPMC = ZPMC;
    }

    public List<String> getZPURL() {
        return ZPURL;
    }

    public void setZPURL(List<String> ZPURL) {
        this.ZPURL = ZPURL;
    }

    public Boolean getSCYJ() {
        return SCYJ;
    }

    public void setSCYJ(Boolean SCYJ) {
        this.SCYJ = SCYJ;
    }


}
