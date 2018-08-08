package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/7/23 15:36
 */

public class VipEntity {

    /**
     * createTime : 2018-07-10 15:39:26
     * createUser : admin
     * description : 购买即享一小时会员时间
     * effectiveTime : 1
     * id : 2
     * name : 年度会员
     * price : 0.01
     */

    private String createTime;
    private String createUser;
    private String description;
    private int effectiveTime;
    private int id;
    private String name;
    private double price;

    public static VipEntity objectFromData(String str) {

        return new Gson().fromJson(str, VipEntity.class);
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(int effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
