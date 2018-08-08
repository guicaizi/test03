package com.yun.software.shangcheng.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yun.software.base.StatusRecordBiz;
import com.yun.software.baseApp.AppConfig;
import com.yun.software.commonwidget.LoadingDialog;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.loadManager.LoadingAndRetryManager;
import com.yun.software.shangcheng.loadManager.OnLoadingAndRetryListener;
import com.yun.software.shangcheng.nohttp.FastJsonRequest;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.nohttp.HttpResponseListener;
import com.yun.software.shangcheng.nohttp.HttpResult;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ToastUitl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * des:基类fragment
 * Created by xsf
 * on 2016.07.12:38
 */

/***************使用例子*********************/
public abstract  class BaseFragment extends Fragment {
    protected View rootView;
    //    public RxManager mRxManager;
    public BaseActivity mContext;
    public StatusRecordBiz biz;
    protected boolean mIsLoadedData;
    protected LoadingAndRetryManager mLoadingAndRetryManager;


    /**
     * 用来标记取消。
     */
    private Object object = new Object();

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     */
    public void request(int what, String url, String jsonBody, HttpListener callback,
                        boolean canCancel, boolean isLoading) {
        request(null, what, url, jsonBody, callback, canCancel, isLoading);
    }

    public  void requestGet(int what,String url,String jsonBody, HttpListener callback,
                            boolean canCancel, boolean isLoading) {
        MyLogUtils.i("http","请求地址："+url);
        Request<HttpResult> request= new FastJsonRequest(url, RequestMethod.GET);
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        if(StringUtils.isNotEmpty(jsonBody)){
            request.setDefineRequestBodyForJson(jsonBody);
        }
        request.setCancelSign(object);
        request.addHeader("Authorization",ApiConstants.HEADER);
        request.addHeader("from","APP");
        mQueue.add(what, request, new HttpResponseListener<HttpResult>(mLoadingAndRetryManager,mContext, request, callback, canCancel, isLoading));
    }

    public void request(String header, int what, String url, String jsonBody, HttpListener callback,
                        boolean canCancel, boolean isLoading) {
        MyLogUtils.i("http", "请求地址：" + url + "\r\n请求参数" + jsonBody);
        Request<HttpResult> request = new FastJsonRequest(url, RequestMethod.POST);
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        if (StringUtils.isNotEmpty(jsonBody)) {
            request.setDefineRequestBodyForJson(jsonBody);
        }
        request.setCancelSign(object);
        request.addHeader("from","APP");
        request.addHeader("Authorization", ApiConstants.HEADER);
        mQueue.add(what, request, new HttpResponseListener<HttpResult>(mLoadingAndRetryManager, mContext, request, callback, canCancel, isLoading));
    }

    public void createLoadingview(final Object view) {


        mLoadingAndRetryManager = LoadingAndRetryManager.generate(view, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                BaseFragment.this.setRetryEvent(retryView);
            }
        });
        mLoadingAndRetryManager.showLoading();
    }

    public void setRetryEvent(View retryView) {

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        //        mRxManager=new RxManager();
        ButterKnife.bind(this, rootView);
        mContext = (BaseActivity) getActivity();
        biz = new StatusRecordBiz(mContext);
        mQueue = NoHttp.newRequestQueue(1);
        setDate();
        addLisener();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            handleOnVisibilityChangedToUser(isVisibleToUser);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(true);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(false);
        }
    }

    /**
     * 处理对用户是否可见
     *
     * @param isVisibleToUser 可见
     */
    private void handleOnVisibilityChangedToUser(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            // 对用户可见
            if (!mIsLoadedData) {
                mIsLoadedData = true;
                onLazyLoadOnce();
            }
            onVisibleToUser();
        } else {
            // 对用户不可见
            onInvisibleToUser();
        }
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    protected void onLazyLoadOnce() {
    }

    /**
     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
     */
    protected void onVisibleToUser() {


    }

    /**
     * 对用户不可见时触发该方法
     */
    protected void onInvisibleToUser() {
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void setDate();

    //初始化view
    protected abstract void addLisener();

    /**
     * 进入页面
     *
     * @param cla 启动的页面
     */
    public void enterPage(Class<?> cla) {
        if (cla == null) {
            return;
        }
        enterPage(cla, null);
    }

    /**
     * 进入页面
     *
     * @param cla    要启动的页面
     * @param bundle 要传递的参数
     */
    public void enterPage(Class<?> cla, Bundle bundle) {
        if (cla == null) {
            return;
        }
        enterPageForResult(cla, bundle, 0);

    }

    /**
     * 进入页面
     *
     * @param cla
     * @param requestCode 请求码
     */
    public void enterPageForResult(Class<?> cla, int requestCode) {
        if (cla == null) {
            return;
        }
        enterPageForResult(cla, null, requestCode);
    }

    /**
     * 进入页面
     *
     * @param cla
     * @param bundle
     * @param requestCode 请求码
     */
    public void enterPageForResult(Class<?> cla, Bundle bundle, int requestCode) {
        if (cla == null) {
            return;
        }
        Intent intent = new Intent(mContext, cla);
        if (bundle != null) {
            intent.putExtra(AppConfig.START_ACTIVITY_PUTEXTRA, bundle);
        }
        if (requestCode > 0) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
        getActivity().overridePendingTransition(com.yun.software.R.anim.fade_in, com.yun.software.R.anim.fade_out);
    }

    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, true);
    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUitl.showShort(resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUitl.showLong(resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUitl.showLong(text);
    }

    public void showToastWithImg(String text, int res) {
        ToastUitl.showToastWithImg(text, res);
    }

    protected void cancelAll() {
        mQueue.cancelAll();
    }

    protected void cancelBySign(Object object) {
        mQueue.cancelBySign(object);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mQueue.cancelBySign(object);
        mQueue.stop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        ButterKnife.unbind(this);
        stopProgressDialog();
        //        mRxManager.clear();
    }
    /**
     *消息订阅
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainThread(MessageEvent messageEvent) {



    }


}