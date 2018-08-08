package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/8/1 18:00
 */

public class WxPayNeedParams {

    /**
     * appId : wx8c4774f266037b86
     * nonceStr : wqKjKok5QYYBLDRg
     * package : prepay_id=wx01182655630280170fc06ad02386350160
     * package1 : prepay_id=wx01182655630280170fc06ad02386350160
     * partnerId : 1510799461
     * paySign : 7285F0FB1AD00D8CE194ADED3C7B43F5
     * prepayId : wx01182655630280170fc06ad02386350160
     * signType : MD5
     * timeStamp : 1533119215
     */

    private String appId;
    private String nonceStr;
    private String packageX;
    private String package1;
    private String partnerId;
    private String paySign;
    private String prepayId;
    private String signType;
    private String timeStamp;

    public static WxPayNeedParams objectFromData(String str) {

        return new Gson().fromJson(str, WxPayNeedParams.class);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPackage1() {
        return package1;
    }

    public void setPackage1(String package1) {
        this.package1 = package1;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "WxPayNeedParams{" +
                "appId='" + appId + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", packageX='" + packageX + '\'' +
                ", package1='" + package1 + '\'' +
                ", partnerId='" + partnerId + '\'' +
                ", paySign='" + paySign + '\'' +
                ", prepayId='" + prepayId + '\'' +
                ", signType='" + signType + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
