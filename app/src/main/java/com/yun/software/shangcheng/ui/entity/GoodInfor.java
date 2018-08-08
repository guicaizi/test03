package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/6/27 15:20
 */

public class GoodInfor {

    /**
     * badCount : 0
     * boutique : 1
     * createDate : 1529495667000
     * createDateName :
     * discountPrice : 0
     * goodCount : 0
     * hot : 1
     * id : 1
     * kindId : 2f17c6829001456d90893de051fe936e
     * logo : http://img.shu163.com/uploadfiles/wallpaper/2010/6/2010093006114284.jpg
     * middleCount : 0
     * name : 飞科剃须刀
     * price : 100
     * productScore : 0
     * shopId : ce426b842d0844118d703b6aacc84018
     * sold : 0
     * status : 0
     * stock : 0
     *
     */

    private int badCount;
    private int boutique;
    private long createDate;
    private String createDateName;
    private int discountPrice;
    private int goodCount;
    private int hot;
    private String id;
    private String kindId;
    private String logo;
    private int middleCount;
    private String name;
    private double price;
    private int productScore;
    private String shopId;
    private int sold;
    private int status;
    private int stock;
    private String scorePrice;

    public static GoodInfor objectFromData(String str) {

        return new Gson().fromJson(str, GoodInfor.class);
    }

    public int getBadCount() {
        return badCount;
    }

    public void setBadCount(int badCount) {
        this.badCount = badCount;
    }

    public int getBoutique() {
        return boutique;
    }

    public void setBoutique(int boutique) {
        this.boutique = boutique;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateName() {
        return createDateName;
    }

    public void setCreateDateName(String createDateName) {
        this.createDateName = createDateName;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getMiddleCount() {
        return middleCount;
    }

    public void setMiddleCount(int middleCount) {
        this.middleCount = middleCount;
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

    public int getProductScore() {
        return productScore;
    }

    public void setProductScore(int productScore) {
        this.productScore = productScore;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getScorePrice() {
        return scorePrice;
    }

    public void setScorePrice(String scorePrice) {
        this.scorePrice = scorePrice;
    }
}
