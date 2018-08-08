package com.yun.software.shangcheng.nohttp;

import com.google.gson.Gson;

public class HttpResult {


    /**
     * code : 1000
     * message : success
     * data : [{"link":"http://image.uczzd.cn/15469369297265720538.jpeg?id=0&from=export","src":"http://image.uczzd.cn/15469369297265720538.jpeg?id=0&from=export"},{"link":"http://img2.cache.netease.com/photo/0011/2015-07-11/AU85O1ML5QJ40011.jpg","src":"http://img2.cache.netease.com/photo/0011/2015-07-11/AU85O1ML5QJ40011.jpg"}]
     */

    private String code;
    private String message;
    private String data;
//
    public static HttpResult objectFromData(String str) {

        return new Gson().fromJson(str, HttpResult.class);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
