package com.titan.cssl.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hanyw on 2017/11/1/001.
 * 项目时间
 */

public class ProjTime implements Serializable{
    /**
     * 年
     */
    private int year;
    /**
     * 月
     */
    private int month;
    /**
     * 日
     */
    private int day;
    /**
     * 小时
     */
    private int hour;
    /**
     * 分钟
     */
    private int minute;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public ArrayList<String> toListString(){
        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(this.getYear()==0?"":this.getYear()));
        list.add(String.valueOf(this.getMonth()==0?"":this.getMonth()));
        list.add(String.valueOf(this.getDay()==0?"":this.getDay()));
        list.add(String.valueOf(this.getHour()==0?"":this.getHour()));
        list.add(String.valueOf(this.getMinute()==0?"":this.getMinute()));
        return list;
    }
}
