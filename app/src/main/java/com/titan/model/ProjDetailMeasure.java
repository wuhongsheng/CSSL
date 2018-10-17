package com.titan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanyw on 2017/11/3/003.
 * 项目措施
 */

public class ProjDetailMeasure implements Serializable {
    private List<subBean> GCCS;
    private List<subBean> ZWCS;
    private List<subBean> LSCS;
    private List<subBean> GLCS;
    private List<subBean> QTCS;
    private List<subBean> SGGCCS;
    private List<subBean> SGZWCS;
    private List<subBean> SGLSCS;
    private List<subBean> SGGLCS;
    private List<subBean> SZQTCS;

    public List<subBean> getSGGCCS() {
        return SGGCCS;
    }

    public void setSGGCCS(List<subBean> SGGCCS) {
        this.SGGCCS = SGGCCS;
    }

    public List<subBean> getSGZWCS() {
        return SGZWCS;
    }

    public void setSGZWCS(List<subBean> SGZWCS) {
        this.SGZWCS = SGZWCS;
    }

    public List<subBean> getSGLSCS() {
        return SGLSCS;
    }

    public void setSGLSCS(List<subBean> SGLSCS) {
        this.SGLSCS = SGLSCS;
    }

    public List<subBean> getSGGLCS() {
        return SGGLCS;
    }

    public void setSGGLCS(List<subBean> SGGLCS) {
        this.SGGLCS = SGGLCS;
    }

    public List<subBean> getSZQTCS() {
        return SZQTCS;
    }

    public void setSZQTCS(List<subBean> SZQTCS) {
        this.SZQTCS = SZQTCS;
    }

    public List<subBean> getGCCS() {
        return GCCS;
    }

    public void setGCCS(List<subBean> GCCS) {
        this.GCCS = GCCS;
    }

    public List<subBean> getZWCS() {
        return ZWCS;
    }

    public void setZWCS(List<subBean> ZWCS) {
        this.ZWCS = ZWCS;
    }

    public List<subBean> getLSCS() {
        return LSCS;
    }

    public void setLSCS(List<subBean> LSCS) {
        this.LSCS = LSCS;
    }

    public List<subBean> getGLCS() {
        return GLCS;
    }

    public void setGLCS(List<subBean> GLCS) {
        this.GLCS = GLCS;
    }

    public List<subBean> getQTCS() {
        return QTCS;
    }

    public void setQTCS(List<subBean> QTCS) {
        this.QTCS = QTCS;
    }



    public static class subBean implements Serializable {
        /**
         * CSNAME : 工程措施
         * JYSM : 开挖、填筑边坡挡土墙防护；
         * MS : 建设范围建立完善排水系统；弃渣场设置挡土墙、排水设施并进行土地整治；施工场地进行土地整治；绿化区域土地平整。开挖、填筑边坡挡土墙防护；
         * TZ : 投资
         * XCZP : ["XCZPMC": "盖板排水沟.png",
         *         "XCZPURL": "~/UpLoadFiles/长]现场照片
         * DOC : []文档
         * XCZF : []现场执法
         */

        private String ID;
        private String CSNAME;
        private String JYSM;
        private String MS;
        private String TZ;
        private List<Photo> XCZP;
        private List<Docment> DOC;
        private List<Censor> XCZF;

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

        public String getTZ() {
            return TZ;
        }

        public void setTZ(String TZ) {
            this.TZ = TZ;
        }

        public List<Photo> getXCZP() {
            return XCZP;
        }

        public void setXCZP(List<Photo> XCZP) {
            this.XCZP = XCZP;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public List<Censor> getXCZF() {
            return XCZF;
        }

        public void setXCZF(List<Censor> XCZF) {
            this.XCZF = XCZF;
        }

        public List<Docment> getDOC() {
            return DOC;
        }

        public void setDOC(List<Docment> DOC) {
            this.DOC = DOC;
        }
    }

    public static class Photo implements Serializable{

        /**
         * XCZPMC : 名称
         * XCZPURL : 地址
         */
        private String XCZPMC;
        private String XCZPURL;

        public String getXCZPMC() {
            return XCZPMC;
        }

        public void setXCZPMC(String XCZPMC) {
            this.XCZPMC = XCZPMC;
        }

        public String getXCZPURL() {
            return XCZPURL;
        }

        public void setXCZPURL(String XCZPURL) {
            this.XCZPURL = XCZPURL;
        }
    }

    public static class Docment implements Serializable{

        /**
         * DOCMC : 名称
         * DOCURL : 地址
         */

        private String DOCMC;
        private String DOCURL;

        public String getDOCMC() {
            return DOCMC;
        }

        public void setDOCMC(String DOCMC) {
            this.DOCMC = DOCMC;
        }

        public String getDOCURL() {
            return DOCURL;
        }

        public void setDOCURL(String DOCURL) {
            this.DOCURL = DOCURL;
        }
    }

    public static class Censor implements Serializable{

        /**
         * Reason :
         * CreateTime : 2017/11/28 18:27:45
         * CheckResult : 属实
         * CheckMan : jzd_admin
         */

        private String Reason;
        private String CreateTime;
        private String CheckResult;
        private String CheckMan;

        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getCheckResult() {
            return CheckResult;
        }

        public void setCheckResult(String CheckResult) {
            this.CheckResult = CheckResult;
        }

        public String getCheckMan() {
            return CheckMan;
        }

        public void setCheckMan(String CheckMan) {
            this.CheckMan = CheckMan;
        }
    }
//    private String CSNAME;//措施名称
//    private String JYSM;//简要说明
//    private String MS;//描述
//    private List<String> XCZPNAME;//现场照片名称
//    private List<String> XCZPURL;//现场照片对应连接
//    private List<String> DOCNAME;//设计方案及审批手续文档名称
//    private List<String> DOCURL;//设计方案及审批手续文档对应连接
//    private String XCZF;//现场执法
//    private List<String> ZFZP;//现场执法照片
//    private List<String> ZFZPURL;//执法照片url
//
//
//    public String getCSNAME() {
//        return CSNAME;
//    }
//
//    public void setCSNAME(String CSNAME) {
//        this.CSNAME = CSNAME;
//    }
//
//    public String getJYSM() {
//        return JYSM;
//    }
//
//    public void setJYSM(String JYSM) {
//        this.JYSM = JYSM;
//    }
//
//    public String getMS() {
//        return MS;
//    }
//
//    public void setMS(String MS) {
//        this.MS = MS;
//    }
//
//    public List<String> getXCZPNAME() {
//        return XCZPNAME;
//    }
//
//    public void setXCZPNAME(List<String> XCZPNAME) {
//        this.XCZPNAME = XCZPNAME;
//    }
//
//    public List<String> getXCZPURL() {
//        return XCZPURL;
//    }
//
//    public void setXCZPURL(List<String> XCZPURL) {
//        this.XCZPURL = XCZPURL;
//    }
//
//    public List<String> getDOCNAME() {
//        return DOCNAME;
//    }
//
//    public void setDOCNAME(List<String> DOCNAME) {
//        this.DOCNAME = DOCNAME;
//    }
//
//    public List<String> getDOCURL() {
//        return DOCURL;
//    }
//
//    public void setDOCURL(List<String> DOCURL) {
//        this.DOCURL = DOCURL;
//    }
//
//    public String getXCZF() {
//        return XCZF;
//    }
//
//    public void setXCZF(String XCZF) {
//        this.XCZF = XCZF;
//    }
//
//    public List<String> getZFZP() {
//        return ZFZP;
//    }
//
//    public void setZFZP(List<String> ZFZP) {
//        this.ZFZP = ZFZP;
//    }
//
//    public List<String> getZFZPURL() {
//        return ZFZPURL;
//    }
//
//    public void setZFZPURL(List<String> ZFZPURL) {
//        this.ZFZPURL = ZFZPURL;
//    }
}
