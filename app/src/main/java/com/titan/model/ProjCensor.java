package com.titan.model;

import java.util.List;

/**
 * Created by hanyw on 2017/11/8/008.
 * 现场执法
 */

public class ProjCensor {
    private String ID;//措施id
    private List<photo> ZPMC;//照片名称
    private String SCYJ;//审查意见; 属实;不属实
    private String USERID;//用户id

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<photo> getZPMC() {
        return ZPMC;
    }

    public void setZPMC(List<photo> ZPMC) {
        this.ZPMC = ZPMC;
    }

    public String getSCYJ() {
        return SCYJ;
    }

    public void setSCYJ(String SCYJ) {
        this.SCYJ = SCYJ;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public static class photo{
        private String NAME;
        private String DATA;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getDATA() {
            return DATA;
        }

        public void setDATA(String DATA) {
            this.DATA = DATA;
        }

    }
}
