package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by yanliang
 * on 2018/7/2 11:21
 */

public class Comment {

    /**
     * realName : 飞翔的小土豆
     * score : 5
     * imgs : ["","http://localhost:8080/20170922/07cbff3ee6584b3db2f99fdc047486a4.png"]
     * userImg : http://image.uczzd.cn/15469369297265720538.jpeg?id=0&from=export
     * content : 好好
     * createDate : 2017-19-22
     */

    private String realName;
    private int score;
    private String userImg;
    private String content;
    private String createDate;
    private List<String> imgs;

    public static Comment objectFromData(String str) {

        return new Gson().fromJson(str, Comment.class);
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
