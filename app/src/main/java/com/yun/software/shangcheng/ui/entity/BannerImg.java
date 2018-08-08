package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/6/27 11:31
 */

public class BannerImg {


    /**
     * link : http://image.uczzd.cn/15469369297265720538.jpeg?id=0&from=export
     * src : http://image.uczzd.cn/15469369297265720538.jpeg?id=0&from=export
     */

    private String link;
    private String src;

    public static BannerImg objectFromData(String str) {

        return new Gson().fromJson(str, BannerImg.class);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "BannerImg{" +
                "link='" + link + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
