package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/7/4 14:56
 */

public class CouponEntity {

    /**
     * flag : 0
     * coupon : ￥10
     * couponCondition : 满100元使用
     * description : 有效期2018.06.15-2018.07.31
     * id : 305d6f9889644dbeab7d60c451233e2d
     */

    private int flag;
    private String coupon;
    private String couponCondition;
    private String description;
    private String id;

    public static CouponEntity objectFromData(String str) {

        return new Gson().fromJson(str, CouponEntity.class);
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCouponCondition() {
        return couponCondition;
    }

    public void setCouponCondition(String couponCondition) {
        this.couponCondition = couponCondition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
