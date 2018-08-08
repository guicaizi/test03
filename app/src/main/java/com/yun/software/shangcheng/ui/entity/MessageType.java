package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/7/27 15:56
 */

public class MessageType  {

    /**
     * id : 53
     * customerId : a8c953fba4a845679b32ab980df2c070
     * title : 发货通知
     * content : 您的订单已发货，订单号：b43c4ec5e7704b53a13abf0f077a9c5c，物流单号：213889102887 (中通)
     * isRead : true
     * msgtype : 1
     * senderId : admin
     * createtime : 2018-07-23 14:02:09
     * readtime : 2018-07-25 14:35:39
     * isDelete : false
     */

    private int id;
    private String customerId;
    private String title;
    private String content;
    private boolean isRead;
    private int msgtype;
    private String senderId;
    private String createtime;
    private String readtime;
    private boolean isDelete;

    public static MessageType objectFromData(String str) {

        return new Gson().fromJson(str, MessageType.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public int getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(int msgtype) {
        this.msgtype = msgtype;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getReadtime() {
        return readtime;
    }

    public void setReadtime(String readtime) {
        this.readtime = readtime;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
