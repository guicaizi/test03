package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/3/28 10:35
 */

public class SearchCity {


    /**
     * ID : 1
     * FullName : 北京市(北京市)
     * CityName : 北京市
     */

    private int ID;
    private String FullName;
    private String CityName;

    public static SearchCity objectFromData(String str) {

        return new Gson().fromJson(str, SearchCity.class);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }
}
