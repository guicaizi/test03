package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

/**
 * Created by yanliang
 * on 2018/7/3 16:38
 */

public class UserInfor {

    /**
     * img : http://image.uczzd.cn/15469369297265720538.jpeg?id=0&from=export
     * password : 123456
     * realName : 飞翔的小土豆
     * roleId : df9332d994904aef883481eea25d9111
     * sex : -1
     * sid : 786e2cee2f8c4cb49a3d30d5ac03c266
     * since : 1530603532868
     * status : 0
     * uid : admin
     * username : 15623885110
     */

    private String img;
    private String password;
    private String realName;
    private String roleId;
    private int sex;
    private String sid;
    private long since;
    private int status;
    private String uid;
    private String username;

    public static UserInfor objectFromData(String str) {

        return new Gson().fromJson(str, UserInfor.class);
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getSince() {
        return since;
    }

    public void setSince(long since) {
        this.since = since;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
