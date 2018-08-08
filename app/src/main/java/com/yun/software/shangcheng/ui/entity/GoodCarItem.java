package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/7/12 17:36
 */

public class GoodCarItem {

    /**
     * id : 06ad3b4fc69347e09ec9cc3d99301157
     * valid : true
     * productId : 4123a7ede4fa47b49c55645645ba255e
     * subProductId : 3bf710b100454045b7dc30ff9253e4ae
     * productName : 飞科剃须刀
     * logo : http://119.23.172.36:8089/20180709/47fd9a616ffd4f63873a8d33c47ca3df.jpeg
     * label : 颜色:蓝色,尺寸:大
     * count : 2
     * price : 99
     * type : 1
     *
     */

    private String id;
    private boolean valid;
    private String productId;
    private String subProductId;
    private String productName;
    private String logo;
    private String label;
    private int count;
    private double price;
    private int type;
    private boolean isCheck=false;

    public static GoodCarItem objectFromData(String str) {

        return new Gson().fromJson(str, GoodCarItem.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSubProductId() {
        return subProductId;
    }

    public void setSubProductId(String subProductId) {
        this.subProductId = subProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
