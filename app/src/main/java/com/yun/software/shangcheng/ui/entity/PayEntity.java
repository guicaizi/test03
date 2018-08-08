package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2017/10/13 11:44
 */

public class PayEntity {

    /**
     * OnlineServerId : 9
     * StoreId : 1
     * Qty : 1
     * BarberShopId : 9
     * ServerCardNo :
     * CreateBy : 9616
     * BuyTel : 17671689387
     */
    private String OnlineServerId;
    private int StoreId;
    private String Qty;
    private String BarberShopId;
    private String ServerCardNo;
    private String CreateBy;
    private String BuyTel;
    private String CustomerId;
    private String UserId;
    private String PayType;


    public static PayEntity objectFromData(String str) {

        return new Gson().fromJson(str, PayEntity.class);
    }

    public String getOnlineServerId() {
        return OnlineServerId;
    }

    public void setOnlineServerId(String OnlineServerId) {
        this.OnlineServerId = OnlineServerId;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int StoreId) {
        this.StoreId = StoreId;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qty) {
        this.Qty = Qty;
    }

    public String getBarberShopId() {
        return BarberShopId;
    }

    public void setBarberShopId(String BarberShopId) {
        this.BarberShopId = BarberShopId;
    }

    public String getServerCardNo() {
        return ServerCardNo;
    }

    public void setServerCardNo(String ServerCardNo) {
        this.ServerCardNo = ServerCardNo;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String CreateBy) {
        this.CreateBy = CreateBy;
    }

    public String getBuyTel() {
        return BuyTel;
    }

    public void setBuyTel(String BuyTel) {
        this.BuyTel = BuyTel;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

}
