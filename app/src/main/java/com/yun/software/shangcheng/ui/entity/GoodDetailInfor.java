package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by yanliang
 * on 2018/7/2 10:06
 */

public class GoodDetailInfor {

    /**
     * info : {"id":"1900819c1196459d9a5937f9c98b3e07","shopId":null,"kindId":"35220e7cd10344df8739cbfbd3babc25","name":"ceshi","logo":"http://119.23.172.36:8089/20180709/5cbe5276cbdf41ce839b1d4d1386966b.jpeg","price":0,"discountPrice":0,"sold":0,"stock":0,"status":1,"createUser":"15623885110","createDate":1530898399000,"boutique":1,"hot":1,"goodCount":0,"middleCount":0,"badCount":0,"productScore":0,"type":1,"scorePrice":0}
     * description : asdasd
     * banners : ["http://119.23.172.36:8089/20180709/18edca18b272463cb73d2f303805817d.png"]
     * details : ["http://119.23.172.36:8089/20180709/d3381a40298f4ead80f9b076b930c760.jpeg","http://119.23.172.36:8089/20180709/ba94f3de8a0a4fe6ad3f0cd5402f19f8.jpeg"]
     * params : ["http://119.23.172.36:8089/20180709/77ced72e45514f258f57f3b03a37112b.jpeg"]
     * flag : false
     */

    private InfoBean info;
    private String description;
    private boolean flag;
    private List<String> banners;
    private List<String> details;
    private List<String> params;

    public static GoodDetailInfor objectFromData(String str) {

        return new Gson().fromJson(str, GoodDetailInfor.class);
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public static class InfoBean {
        /**
         * id : 1900819c1196459d9a5937f9c98b3e07
         * shopId : null
         * kindId : 35220e7cd10344df8739cbfbd3babc25
         * name : ceshi
         * logo : http://119.23.172.36:8089/20180709/5cbe5276cbdf41ce839b1d4d1386966b.jpeg
         * price : 0
         * discountPrice : 0
         * sold : 0
         * stock : 0
         * status : 1
         * createUser : 15623885110
         * createDate : 1530898399000
         * boutique : 1
         * hot : 1
         * goodCount : 0
         * middleCount : 0
         * badCount : 0
         * productScore : 0
         * type : 1
         * scorePrice : 0
         */

        private String id;
        private Object shopId;
        private String kindId;
        private String name;
        private String logo;
        private double price;
        private double discountPrice;
        private int sold;
        private int stock;
        private int status;
        private String createUser;
        private long createDate;
        private int boutique;
        private int hot;
        private int goodCount;
        private int middleCount;
        private int badCount;
        private int productScore;
        private int type;
        private double scorePrice;

        public static InfoBean objectFromData(String str) {

            return new Gson().fromJson(str, InfoBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getShopId() {
            return shopId;
        }

        public void setShopId(Object shopId) {
            this.shopId = shopId;
        }

        public String getKindId() {
            return kindId;
        }

        public void setKindId(String kindId) {
            this.kindId = kindId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
        }

        public int getSold() {
            return sold;
        }

        public void setSold(int sold) {
            this.sold = sold;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public int getBoutique() {
            return boutique;
        }

        public void setBoutique(int boutique) {
            this.boutique = boutique;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public int getGoodCount() {
            return goodCount;
        }

        public void setGoodCount(int goodCount) {
            this.goodCount = goodCount;
        }

        public int getMiddleCount() {
            return middleCount;
        }

        public void setMiddleCount(int middleCount) {
            this.middleCount = middleCount;
        }

        public int getBadCount() {
            return badCount;
        }

        public void setBadCount(int badCount) {
            this.badCount = badCount;
        }

        public int getProductScore() {
            return productScore;
        }

        public void setProductScore(int productScore) {
            this.productScore = productScore;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getScorePrice() {
            return scorePrice;
        }

        public void setScorePrice(double scorePrice) {
            this.scorePrice = scorePrice;
        }
    }
}
