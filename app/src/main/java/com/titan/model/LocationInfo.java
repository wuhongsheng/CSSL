package com.titan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanyw on 2018/3/1.
 * 位置信息
 */

public class LocationInfo implements Serializable{
    //设备当前位置
    private Double[] localPoint;
    //项目坐标集合
    private List<Double[]> coordinateList;
    //详细地址
    private String address;
    //获取国家
    private String country ;
    //获取省份
    private String province ;
    //获取城市
    private String city ;
    //获取区县
    private String district ;
    //获取街道信息
    private String street ;


    public Double[] getLocalPoint() {
        return localPoint;
    }

    public void setLocalPoint(Double[] localPoint) {
        this.localPoint = localPoint;
    }

    public List<Double[]> getCoordinateList() {
        return coordinateList;
    }

    public void setCoordinateList(List<Double[]> coordinateList) {
        this.coordinateList = coordinateList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
