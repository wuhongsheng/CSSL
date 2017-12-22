package com.titan.model;

import java.io.Serializable;

/**
 * Created by hanyw on 2017/12/8/008.
 * 项目统计
 */

public class Statistics implements Serializable {

    /**
     * ThreeDown : {"ThreeDownPass":0,"ThreeDownNoPass":1}
     * ThreeEight : {"ThreeEightPass":1,"ThreeEightNoPass":1}
     * EIGHTUP : {"EIGHTUPPass":0,"EIGHTUPNoPass":0}
     */

    private ThreeDownBean ThreeDown;
    private ThreeEightBean ThreeEight;
    private EIGHTUPBean EIGHTUP;

    public ThreeDownBean getThreeDown() {
        return ThreeDown;
    }

    public void setThreeDown(ThreeDownBean ThreeDown) {
        this.ThreeDown = ThreeDown;
    }

    public ThreeEightBean getThreeEight() {
        return ThreeEight;
    }

    public void setThreeEight(ThreeEightBean ThreeEight) {
        this.ThreeEight = ThreeEight;
    }

    public EIGHTUPBean getEIGHTUP() {
        return EIGHTUP;
    }

    public void setEIGHTUP(EIGHTUPBean EIGHTUP) {
        this.EIGHTUP = EIGHTUP;
    }

    public static class ThreeDownBean {
        /**
         * ThreeDownPass : 0
         * ThreeDownNoPass : 1
         */

        private int ThreeDownPass;
        private int ThreeDownNoPass;

        public int getThreeDownPass() {
            return ThreeDownPass;
        }

        public void setThreeDownPass(int ThreeDownPass) {
            this.ThreeDownPass = ThreeDownPass;
        }

        public int getThreeDownNoPass() {
            return ThreeDownNoPass;
        }

        public void setThreeDownNoPass(int ThreeDownNoPass) {
            this.ThreeDownNoPass = ThreeDownNoPass;
        }
    }

    public static class ThreeEightBean {
        /**
         * ThreeEightPass : 1
         * ThreeEightNoPass : 1
         */

        private int ThreeEightPass;
        private int ThreeEightNoPass;

        public int getThreeEightPass() {
            return ThreeEightPass;
        }

        public void setThreeEightPass(int ThreeEightPass) {
            this.ThreeEightPass = ThreeEightPass;
        }

        public int getThreeEightNoPass() {
            return ThreeEightNoPass;
        }

        public void setThreeEightNoPass(int ThreeEightNoPass) {
            this.ThreeEightNoPass = ThreeEightNoPass;
        }
    }

    public static class EIGHTUPBean {
        /**
         * EIGHTUPPass : 0
         * EIGHTUPNoPass : 0
         */

        private int EIGHTUPPass;
        private int EIGHTUPNoPass;

        public int getEIGHTUPPass() {
            return EIGHTUPPass;
        }

        public void setEIGHTUPPass(int EIGHTUPPass) {
            this.EIGHTUPPass = EIGHTUPPass;
        }

        public int getEIGHTUPNoPass() {
            return EIGHTUPNoPass;
        }

        public void setEIGHTUPNoPass(int EIGHTUPNoPass) {
            this.EIGHTUPNoPass = EIGHTUPNoPass;
        }
    }
}
