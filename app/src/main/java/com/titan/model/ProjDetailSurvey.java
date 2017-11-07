package com.titan.model;

/**
 * Created by hanyw on 2017/11/3/003.
 * 项目概况
 */

public class ProjDetailSurvey {
    private String XMFZR;//项目总负责人
    private String XMDZ;//项目地址
    private String ZDMJ;//占地面积/hm²
    private String GCDZ;//工程投资/万元
    private String KGSJ;//开工时间
    private String WGSJ;//完工时间
    private String SCNL;//生产能力
    private String SCNX;//生产年限
    private String XMTSFL;//项目土石方量/万m³
    private String XMQZFA;//项目弃渣方案
    private String XMQTFA;//项目取土方案
    private String ZB;//坐标
    private Integer LRFS;//录入方式 0:度数,1:度分秒

    public String getXMFZR() {
        return XMFZR;
    }

    public void setXMFZR(String XMFZR) {
        this.XMFZR = XMFZR;
    }

    public String getXMDZ() {
        return XMDZ;
    }

    public void setXMDZ(String XMDZ) {
        this.XMDZ = XMDZ;
    }

    public String getZDMJ() {
        return ZDMJ;
    }

    public void setZDMJ(String ZDMJ) {
        this.ZDMJ = ZDMJ;
    }

    public String getGCDZ() {
        return GCDZ;
    }

    public void setGCDZ(String GCDZ) {
        this.GCDZ = GCDZ;
    }

    public String getKGSJ() {
        return KGSJ;
    }

    public void setKGSJ(String KGSJ) {
        this.KGSJ = KGSJ;
    }

    public String getWGSJ() {
        return WGSJ;
    }

    public void setWGSJ(String WGSJ) {
        this.WGSJ = WGSJ;
    }

    public String getSCNL() {
        return SCNL;
    }

    public void setSCNL(String SCNL) {
        this.SCNL = SCNL;
    }

    public String getSCNX() {
        return SCNX;
    }

    public void setSCNX(String SCNX) {
        this.SCNX = SCNX;
    }

    public String getXMTSFL() {
        return XMTSFL;
    }

    public void setXMTSFL(String XMTSFL) {
        this.XMTSFL = XMTSFL;
    }

    public String getXMQZFA() {
        return XMQZFA;
    }

    public void setXMQZFA(String XMQZFA) {
        this.XMQZFA = XMQZFA;
    }

    public String getXMQTFA() {
        return XMQTFA;
    }

    public void setXMQTFA(String XMQTFA) {
        this.XMQTFA = XMQTFA;
    }

    public String getZB() {
        return ZB;
    }

    public void setZB(String ZB) {
        this.ZB = ZB;
    }

    public Integer getLRFS() {
        return LRFS;
    }

    public void setLRFS(Integer LRFS) {
        this.LRFS = LRFS;
    }
}
