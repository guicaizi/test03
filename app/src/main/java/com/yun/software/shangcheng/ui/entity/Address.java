package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by yanliang
 * on 2018/7/3 14:46
 */

public class Address implements Serializable{
    private static final long serialVersionUID = 888336895632L;
    /**
     * address : 和平大道100号
     * areaCode : 广东省 阳江市 江城区
     * createDate : 1530597440000
     * id : 66074f77ad86462ebc329853f7ffd740
     * personName : 收货人1
     * phone : 18312345678
     * postalCode : 420106
     * status : 1
     * userId : admin
     */

    private String address;
    private String areaCode;
    private long createDate;
    private String id;
    private String personName;
    private String phone;
    private String postalCode;
    private int status;
    private String userId;

    public static Address objectFromData(String str) {

        return new Gson().fromJson(str, Address.class);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
