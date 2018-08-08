package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/6/27 16:47
 */

public class MainCategory {

    /**
     * id : 394f795df5c84ee7a8a51ef62ca10104
     * name : 衣服
     * pid : null
     * userId : null
     * createDate : null
     * description : 我们都是好看的衣服
     * img : http://119.23.172.36:8089/20180712/2e27381823cc4f22b3f06d2087c893c6.jpg
     * pname : null
     * children : null
     * weight : null
     */

    private String id;
    private String name;
    private Object pid;
    private Object userId;
    private Object createDate;
    private String description;
    private String img;
    private Object pname;
    private Object children;
    private Object weight;

    public static MainCategory objectFromData(String str) {

        return new Gson().fromJson(str, MainCategory.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Object getPname() {
        return pname;
    }

    public void setPname(Object pname) {
        this.pname = pname;
    }

    public Object getChildren() {
        return children;
    }

    public void setChildren(Object children) {
        this.children = children;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }
}
