package com.titan.model;

import java.io.Serializable;
import java.util.List;

public class ProjDetailItemModel implements Serializable {

    public ProjDetailItemModel() {

    }

    public ProjDetailItemModel(String name, int type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    private String name;
    private int type;
    private String value;
    private SubContent subContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SubContent getSubContent() {
        return subContent;
    }

    public void setSubContent(SubContent subContent) {
        this.subContent = subContent;
    }

    public static class SubContent implements Serializable {
        //年度
        private String year;
        //对应的值
        private String yearValue;
        //投资/万元
        private String investment;
        //对应的值
        private String investmentValue;
        //说明
        private String instructions;
        //对应的值
        private String instructionsValue;
        //建设区域
        private String buildArea;
        //对应的值
        private String buildAreaValue;
        //长度/面积
        private String lengArea;
        //对应的值
        private String lengAreaValue;
        //挖方量
        private String wfl;
        //对应的值
        private String wflValue;
        //填方量
        private String tfl;
        //对应的值
        private String tflValue;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getYearValue() {
            return yearValue;
        }

        public void setYearValue(String yearValue) {
            this.yearValue = yearValue;
        }

        public String getInvestment() {
            return investment;
        }

        public void setInvestment(String investment) {
            this.investment = investment;
        }

        public String getInvestmentValue() {
            return investmentValue;
        }

        public void setInvestmentValue(String investmentValue) {
            this.investmentValue = investmentValue;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }

        public String getInstructionsValue() {
            return instructionsValue;
        }

        public void setInstructionsValue(String instructionsValue) {
            this.instructionsValue = instructionsValue;
        }

        public String getBuildArea() {
            return buildArea;
        }

        public void setBuildArea(String buildArea) {
            this.buildArea = buildArea;
        }

        public String getBuildAreaValue() {
            return buildAreaValue;
        }

        public void setBuildAreaValue(String buildAreaValue) {
            this.buildAreaValue = buildAreaValue;
        }

        public String getLengArea() {
            return lengArea;
        }

        public void setLengArea(String lengArea) {
            this.lengArea = lengArea;
        }

        public String getLengAreaValue() {
            return lengAreaValue;
        }

        public void setLengAreaValue(String lengAreaValue) {
            this.lengAreaValue = lengAreaValue;
        }

        public String getWfl() {
            return wfl;
        }

        public void setWfl(String wfl) {
            this.wfl = wfl;
        }

        public String getWflValue() {
            return wflValue;
        }

        public void setWflValue(String wflValue) {
            this.wflValue = wflValue;
        }

        public String getTfl() {
            return tfl;
        }

        public void setTfl(String tfl) {
            this.tfl = tfl;
        }

        public String getTflValue() {
            return tflValue;
        }

        public void setTflValue(String tflValue) {
            this.tflValue = tflValue;
        }
    }
}

