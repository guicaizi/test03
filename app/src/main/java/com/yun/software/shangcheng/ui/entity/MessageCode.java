package com.yun.software.shangcheng.ui.entity;

/**
 * @author 许英俊 2017/5/3
 */
public class MessageCode {

    private String message;
    private String code;

    public MessageCode(String message, String code) {
        this.message = message;
        this.code=code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
