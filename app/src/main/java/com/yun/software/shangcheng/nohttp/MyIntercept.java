package com.yun.software.shangcheng.nohttp;

import com.yanzhenjie.nohttp.rest.Interceptor;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestHandler;
import com.yanzhenjie.nohttp.rest.Response;



/**
 * Created by yanliang
 * on 2018/6/27 13:44
 */

public class MyIntercept implements Interceptor {

    @Override
    public <T> Response<T> intercept(RequestHandler requestHandler, Request<T> request) {
        return null;
    }




}
