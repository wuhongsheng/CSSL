package com.titan.model;

import java.util.List;

/**
 * Created by hanyw on 2017/11/3/003.
 * 项目措施
 */

public class ProjDetailMeasure {
    private String CSNAME;//措施名称
    private String JYSM;//简要说明
    private String MS;//描述
    private List<String> XCZPNAME;//现场照片名称
    private List<String> XCZPURL;//现场照片对应连接
    private List<String> DOCNAME;//设计方案及审批手续文档名称
    private List<String> DOCURL;//设计方案及审批手续文档对应连接
    private String ZJYJ;//专家意见,以审批提交时间作为显示名称和查询参数,时间格式为:yyyy-MM-dd HH:mm


    public String getCSNAME() {
        return CSNAME;
    }

    public void setCSNAME(String CSNAME) {
        this.CSNAME = CSNAME;
    }

    public String getJYSM() {
        return JYSM;
    }

    public void setJYSM(String JYSM) {
        this.JYSM = JYSM;
    }

    public String getMS() {
        return MS;
    }

    public void setMS(String MS) {
        this.MS = MS;
    }

    public List<String> getXCZPNAME() {
        return XCZPNAME;
    }

    public void setXCZPNAME(List<String> XCZPNAME) {
        this.XCZPNAME = XCZPNAME;
    }

    public List<String> getXCZPURL() {
        return XCZPURL;
    }

    public void setXCZPURL(List<String> XCZPURL) {
        this.XCZPURL = XCZPURL;
    }

    public List<String> getDOCNAME() {
        return DOCNAME;
    }

    public void setDOCNAME(List<String> DOCNAME) {
        this.DOCNAME = DOCNAME;
    }

    public List<String> getDOCURL() {
        return DOCURL;
    }

    public void setDOCURL(List<String> DOCURL) {
        this.DOCURL = DOCURL;
    }

    public String getZJYJ() {
        return ZJYJ;
    }

    public void setZJYJ(String ZJYJ) {
        this.ZJYJ = ZJYJ;
    }
}
