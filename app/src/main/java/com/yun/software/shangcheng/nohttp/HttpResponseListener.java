/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yun.software.shangcheng.nohttp;

import android.app.Activity;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.dialog.WaitDialog;
import com.yun.software.shangcheng.loadManager.LoadingAndRetryManager;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.Tools;

import org.greenrobot.eventbus.EventBus;

/**
 * Created in Nov 4, 2015 12:02:55 PM.
 *
 * @author Yan Zhenjie.
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {

    private Activity mActivity;
    /**
     * Dialog.
     */
    private WaitDialog mWaitDialog;
    /**
     * Request.
     */
    private Request<?> mRequest;
    /**
     * 结果回调.
     */
    private HttpListener callback;
    protected LoadingAndRetryManager mLoadingAndRetryManager;
    private boolean islooding = false;

    /**
     * @param activity     context用来实例化dialog.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     * @param canCancel    是否允许用户取消请求.
     * @param isLoading    是否显示dialog.
     */
    public HttpResponseListener(LoadingAndRetryManager mLoadingAndRetryManager, Activity activity, Request<?> request, HttpListener httpCallback, boolean canCancel, boolean isLoading) {
        this.mActivity = activity;
        this.mRequest = request;
        this.mLoadingAndRetryManager = mLoadingAndRetryManager;
        //        if (activity != null && isLoading) {
        //            mWaitDialog = new WaitDialog(activity);
        //            mWaitDialog.setCancelable(canCancel);
        //            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        //                @Override
        //                public void onCancel(DialogInterface dialog) {
        //                    mRequest.cancel();
        //                }
        //            });
        //        }
        this.islooding = isLoading;
        this.callback = httpCallback;
    }

    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {
        if (mLoadingAndRetryManager != null && !mActivity.isFinishing() && islooding)
            mLoadingAndRetryManager.showLoading();
        //            mWaitDialog.show();
    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
        if (mLoadingAndRetryManager != null && !mActivity.isFinishing() && islooding)
            //            mWaitDialog.dismiss();
            mLoadingAndRetryManager.showContent();
    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        if (response == null) {
            callback.onFailed(what, "无返回值");
            return;
        }
        if (callback != null) {
            HttpResult listHttpResult = (HttpResult) response.get();
            //            MyLogUtils.i("httpResult收到数据","======================================\r\n"+((JSONObject) response.get()).toJSONString());
            //            HttpResult listHttpResult = JSON.parseObject(jsonObject.toJSONString(), HttpResult.class);
            //            HttpResult listHttpResult = JSON.parseObject(jsonObject.toJSONString(), HttpResult.class);
            //            if("")
            MyLogUtils.i("zjhd", "请求回调结果" + listHttpResult.toString());
            if (listHttpResult == null) {
                Tools.showInfo(mActivity, "无返回值");
                callback.onFailed(what, "无返回值");
                return;
            }
            if (listHttpResult.getCode().equals("1000")) {
                if (mLoadingAndRetryManager != null && !mActivity.isFinishing() && islooding)
                    mLoadingAndRetryManager.showContent();
                callback.onSucceed(what, listHttpResult.getData());
            } else {
                if (mLoadingAndRetryManager != null && !mActivity.isFinishing() && islooding) {
                    mLoadingAndRetryManager.showRetry();
                }
                callback.onFailed(what, listHttpResult.getMessage());

                if (listHttpResult.getMessage().equals("token expired")) {
                    EventBus.getDefault().post(new MessageEvent("tologin"));
                    Tools.showInfo(mActivity, "账号信息已过期，请重新登录。");
                } else {
                    Tools.showInfo(mActivity, listHttpResult.getMessage());
                }

            }
        }
    }

    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {
        if (mLoadingAndRetryManager != null && !mActivity.isFinishing() && islooding)
            mLoadingAndRetryManager.showRetry();
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            Tools.showInfo(mActivity, R.string.error_please_check_network);
        } else if (exception instanceof TimeoutError) {// 请求超时
            Tools.showInfo(mActivity, R.string.error_timeout);
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            Tools.showInfo(mActivity, R.string.error_not_found_server);
        } else if (exception instanceof URLError) {// URL是错的
            Tools.showInfo(mActivity, R.string.error_url_error);
        } else if (exception instanceof NotFoundCacheError) {
            Tools.showInfo(mActivity, R.string.download_error_storage);
        } else {
            Tools.showInfo(mActivity, R.string.error_unknow);
        }
        Logger.e("错误：" + exception.getMessage());
        if (callback != null)
            callback.onFailed(what, exception.getMessage());
        //            callback.onFailed(what, response);
    }

}
