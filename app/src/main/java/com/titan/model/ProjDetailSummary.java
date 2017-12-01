package com.titan.model;

import java.util.List;
import java.util.Map;

/**
 * Created by hanyw on 2017/11/3/003.
 * 项目概况
 */

public class ProjDetailSummary {
    /**
     * XMFZR : 223
     * XMDZ : 123
     * ZDMJ : 111
     * GCDZ : 122
     * KGSJ : 2017/12/7 0:00:00
     * WGSJ : 2018/1/4 0:00:00
     * SCNL :
     * SCNX :
     * XMTSFL_WFL : 23
     * XMTSFL_TFL : 23
     * XMQZFA : 123
     * XMQTFA : 123
     * ZB : 0,0;
     * LRFS :
     * QTL : 12
     * SHSBSSMJ : 123
     * STLSMJ : 123
     * YCSTLSWH : 123
     * SBZTZ : 123
     * FNDSSJH : [{"ND":"","CSGCL":"","TZ":"","BZ":""}]
     */

    private String XMFZR;// 项目总负责人
    private String XMDZ;// 项目地址
    private String ZDMJ;// 占地面积/hm²
    private String GCDZ;// 工程投资/万元
    private String KGSJ;// 开工时间
    private String WGSJ;// 完工时间
    private String SCNL;// 生产能力
    private String SCNX;// 生产年限
    private String XMTSFL;// 项目土石方量/万m³
    private String XMTSFL_WFL;// 项目土石方量/万m³ 挖方量
    private String XMTSFL_TFL;// 项目土石方量/万m³ 填方量
    private String XMQZFA;// 项目弃渣方案
    private String XMQTFA;// 项目取土方案
    private String ZB;// 坐标
    private String LRFS;// 录入方式 0:度数,1:度分秒
    private String QTL;//弃土（石、渣）量/万m³
    private String SHSBSSMJ;//损坏水保设施面积/km²
    private String STLSMJ;//造成水土流失面积/km²
    private String YCSTLSWH;//预测水土流失危害
    private String SBZTZ;//时水土保持总投资
    private List<Map<String, FNDSSJHBean>> FNDSSJH;//分年度实施计划

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

    public String getXMTSFL_WFL() {
        return XMTSFL_WFL;
    }

    public void setXMTSFL_WFL(String XMTSFL_WFL) {
        this.XMTSFL_WFL = XMTSFL_WFL;
    }

    public String getXMTSFL_TFL() {
        return XMTSFL_TFL;
    }

    public void setXMTSFL_TFL(String XMTSFL_TFL) {
        this.XMTSFL_TFL = XMTSFL_TFL;
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

    public String getLRFS() {
        return LRFS;
    }

    public void setLRFS(String LRFS) {
        this.LRFS = LRFS;
    }

    public String getQTL() {
        return QTL;
    }

    public void setQTL(String QTL) {
        this.QTL = QTL;
    }

    public String getSHSBSSMJ() {
        return SHSBSSMJ;
    }

    public void setSHSBSSMJ(String SHSBSSMJ) {
        this.SHSBSSMJ = SHSBSSMJ;
    }

    public String getSTLSMJ() {
        return STLSMJ;
    }

    public void setSTLSMJ(String STLSMJ) {
        this.STLSMJ = STLSMJ;
    }

    public String getYCSTLSWH() {
        return YCSTLSWH;
    }

    public void setYCSTLSWH(String YCSTLSWH) {
        this.YCSTLSWH = YCSTLSWH;
    }

    public String getSBZTZ() {
        return SBZTZ;
    }

    public void setSBZTZ(String SBZTZ) {
        this.SBZTZ = SBZTZ;
    }

    public String getXMTSFL() {
        return XMTSFL;
    }

    public void setXMTSFL(String XMTSFL) {
        this.XMTSFL = XMTSFL;
    }

    public List<Map<String, FNDSSJHBean>> getFNDSSJH() {
        return FNDSSJH;
    }

    public void setFNDSSJH(List<Map<String, FNDSSJHBean>> FNDSSJH) {
        this.FNDSSJH = FNDSSJH;
    }

    public static class FNDSSJHBean {
        /**
         * ND :   年度
         * CSGCL :措施工程量/km²
         * TZ :   投资/万元
         * BZ :   备注
         */

        private String ND;
        private String CSGCL;
        private String TZ;
        private String BZ;

        public String getND() {
            return ND;
        }

        public void setND(String ND) {
            this.ND = ND;
        }

        public String getCSGCL() {
            return CSGCL;
        }

        public void setCSGCL(String CSGCL) {
            this.CSGCL = CSGCL;
        }

        public String getTZ() {
            return TZ;
        }

        public void setTZ(String TZ) {
            this.TZ = TZ;
        }

        public String getBZ() {
            return BZ;
        }

        public void setBZ(String BZ) {
            this.BZ = BZ;
        }
    }


//    private String XMFZR;// 项目总负责人
//    private String XMDZ;// 项目地址
//    private String ZDMJ;// 占地面积/hm²
//    private String GCDZ;// 工程投资/万元
//    private String KGSJ;// 开工时间
//    private String WGSJ;// 完工时间
//    private String SCNL;// 生产能力
//    private String SCNX;// 生产年限
//    private String XMTSFL;// 项目土石方量/万m³
//    private String XMQZFA;// 项目弃渣方案
//    private String XMQTFA;// 项目取土方案
//    private String ZB;// 坐标
//    private String LRFS;// 录入方式 0:度数,1:度分秒
//
//    public String getXMFZR() {
//        return XMFZR;
//    }
//
//    public void setXMFZR(String XMFZR) {
//        this.XMFZR = XMFZR;
//    }
//
//    public String getXMDZ() {
//        return XMDZ;
//    }
//
//    public void setXMDZ(String XMDZ) {
//        this.XMDZ = XMDZ;
//    }
//
//    public String getZDMJ() {
//        return ZDMJ;
//    }
//
//    public void setZDMJ(String ZDMJ) {
//        this.ZDMJ = ZDMJ;
//    }
//
//    public String getGCDZ() {
//        return GCDZ;
//    }
//
//    public void setGCDZ(String GCDZ) {
//        this.GCDZ = GCDZ;
//    }
//
//    public String getKGSJ() {
//        return KGSJ;
//    }
//
//    public void setKGSJ(String KGSJ) {
//        this.KGSJ = KGSJ;
//    }
//
//    public String getWGSJ() {
//        return WGSJ;
//    }
//
//    public void setWGSJ(String WGSJ) {
//        this.WGSJ = WGSJ;
//    }
//
//    public String getSCNL() {
//        return SCNL;
//    }
//
//    public void setSCNL(String SCNL) {
//        this.SCNL = SCNL;
//    }
//
//    public String getSCNX() {
//        return SCNX;
//    }
//
//    public void setSCNX(String SCNX) {
//        this.SCNX = SCNX;
//    }
//
//    public String getXMTSFL() {
//        return XMTSFL;
//    }
//
//    public void setXMTSFL(String XMTSFL) {
//        this.XMTSFL = XMTSFL;
//    }
//
//    public String getXMQZFA() {
//        return XMQZFA;
//    }
//
//    public void setXMQZFA(String XMQZFA) {
//        this.XMQZFA = XMQZFA;
//    }
//
//    public String getXMQTFA() {
//        return XMQTFA;
//    }
//
//    public void setXMQTFA(String XMQTFA) {
//        this.XMQTFA = XMQTFA;
//    }
//
//    public String getLRFS() {
//        return LRFS;
//    }
//
//    public void setLRFS(String LRFS) {
//        this.LRFS = LRFS;
//    }
//
//    public String getZB() {
//        return ZB;
//    }
//
//    public void setZB(String ZB) {
//        this.ZB = ZB;
//    }
}
