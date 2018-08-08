package com.yun.software.baserx;

public class HttpResult<T> {
    String suc;
    T data;
    String msg;
    String count;
    String statusCode;

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "suc='" + suc + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", count='" + count + '\'' +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}
