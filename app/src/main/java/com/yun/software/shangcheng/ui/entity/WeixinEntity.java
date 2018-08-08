package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yanliang
 * on 2017/10/10 17:31
 */

public class WeixinEntity {


    /**
     * appId : wx1fdaa3130803324b
     * timeStamp : 1507628214
     * nonceStr : 5C936263F3428A40227908D5A3847C0B
     * package : prepay_id=
     * paySign : C367DB8CFC35EF57F7B4F725D1AB7CD8
     * out_trade_no : {Id=77.0, FirstName=可乐, LastName=null, Email=null, Company=null, CountryId=null, CountryName=null, StateProvinceId=16.0, City=null, Address1=只要你, Address2=null, ZipPostalCode=null, PhoneNumber=15690849387, FaxNumber=null, CustomAttributes=null, CreatedOnUtc=null, StateProvinceName=null, IsDefaultAddress=false, FullAddress=河南省 郑州市 管城回族区只要你, ShortAddress=河南省 郑州市 管城回族区, CountyId=1510.0, CityId=155.0, CustomerId=null}
     * total_fee : 1
     * time : 2017-10-10
     * result : null
     * prepayId :
     * mchid : L4ktvnjoV39VsS37X9CIAkholCjjSpyR
     */

    private String appId;
    private String timeStamp;
    private String nonceStr;
    @SerializedName("package")
    private String packageX;
    private String paySign;
    private String out_trade_no;
    private String total_fee;
    private String time;
    private Object result;
    private String prepayId;
    private String mchid;

    public static WeixinEntity objectFromData(String str) {

        return new Gson().fromJson(str, WeixinEntity.class);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    @Override
    public String toString() {
        return "WeixinEntity{" +
                "appId='" + appId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", packageX='" + packageX + '\'' +
                ", paySign='" + paySign + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", time='" + time + '\'' +
                ", result=" + result +
                ", prepayId='" + prepayId + '\'' +
                ", mchid='" + mchid + '\'' +
                '}';
    }
}
