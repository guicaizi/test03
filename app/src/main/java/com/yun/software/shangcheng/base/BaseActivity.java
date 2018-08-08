package com.yun.software.shangcheng.base;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yun.software.BuildConfig;
import com.yun.software.base.StatusRecordBiz;
import com.yun.software.baseApp.AppConfig;
import com.yun.software.baseApp.AppManager;
import com.yun.software.commonwidget.LoadingDialog;
import com.yun.software.commonwidget.StatusBarCompat;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.loadManager.LoadingAndRetryManager;
import com.yun.software.shangcheng.loadManager.OnLoadingAndRetryListener;
import com.yun.software.shangcheng.nohttp.FastJsonRequest;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.nohttp.HttpResponseListener;
import com.yun.software.shangcheng.nohttp.HttpResult;
import com.yun.software.shangcheng.registlogin.action.Action;
import com.yun.software.shangcheng.registlogin.action.SingleCall;
import com.yun.software.shangcheng.registlogin.options.LoginValid;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ExceptionUtil;
import com.yun.software.utils.ToastUitl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;


/**
 * 基类
 */
public abstract class BaseActivity extends AppCompatActivity implements Action {
    /**
     *小项目不推荐使用mvp ，后期可自行扩展
     */

    /**
     * 用来标记取消。
     */
    private Object object = new Object();

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    public Context mContext;

    private boolean isConfigChange=false;
    public StatusRecordBiz biz;
    private LogincallBack mlogincallBack;
    public LoadingAndRetryManager mLoadingAndRetryManager;
    /**
     * 发起请求。
     *
     * @param what      what.
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     */
    public  void request(int what,String url,String jsonBody, HttpListener callback,
                         boolean canCancel, boolean isLoading) {
      request(null,what,url,jsonBody,callback,canCancel,isLoading);
    }
    public  void request(String header,int what,String url,String jsonBody, HttpListener callback,
                         boolean canCancel, boolean isLoading) {
        MyLogUtils.i("http","请求地址："+url+"\r\n请求参数"+jsonBody);
        Request<HttpResult> request= new FastJsonRequest(url, RequestMethod.POST);
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        if(StringUtils.isNotEmpty(jsonBody)){
            request.setDefineRequestBodyForJson(jsonBody);
        }
        request.setCancelSign(object);
        if(StringUtils.isNotEmpty(header)){
            request.addHeader("authorization",header);
        }
        request.addHeader("Authorization",ApiConstants.HEADER);
        request.addHeader("from","APP");
        mQueue.add(what, request, new HttpResponseListener<HttpResult>(mLoadingAndRetryManager,this, request, callback, canCancel, isLoading));
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
        mQueue.add(what, request, new HttpResponseListener<HttpResult>(mLoadingAndRetryManager,this, request, callback, canCancel, isLoading));
    }
    public  void requestUpload(int what,String url,String filepath, HttpListener callback,OnUploadListener mProgressHandler) {
        Request<HttpResult> request= new FastJsonRequest(url, RequestMethod.POST);
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        BasicBinary binary = new FileBinary(new File(filepath));
        request.setCancelSign(object);
        request.addHeader("Authorization",ApiConstants.HEADER);
        request.addHeader("from","APP");
        binary.setUploadListener(what, mProgressHandler);
        request.add("file", binary);
        mQueue.add(what, request, new HttpResponseListener<HttpResult>(mLoadingAndRetryManager,this, request, callback, false, false));
    }


    public void createLoadingview(final Object view){
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(view, new OnLoadingAndRetryListener()
        {
            @Override
            public void setRetryEvent(View retryView)
            {
                BaseActivity.this.setRetryEvent(retryView);
            }
        });
        mLoadingAndRetryManager.showLoading();
    }
    /**
     *消息订阅
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainThread(MessageEvent messageEvent) {

    }

    public void setRetryEvent(View retryView){

        
    }
    /**
     * Log tag
     */
    protected static String TAG_LOG = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            try {
                isConfigChange=false;
                doBeforeSetcontentView();
                setContentView(getLayoutId());
                ButterKnife.bind(this);
                mContext = this;
                mQueue = NoHttp.newRequestQueue(3);
                TAG_LOG = this.getClass().getSimpleName();
                biz=new StatusRecordBiz(mContext);
                this.initPresenter();
                this.initView();
                this.addLisener();
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }


    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        //设置昼夜主题
//        initTheme();
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // 设置竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        // 默认着色状态栏
//        SetStatusBarColor();
////          SetTranslanteBar();
    }

    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();
    //初始化view
    public abstract void initView();
    //初始化viewN
    public abstract void addLisener();


    /**
     * 设置主题
     */
    private void initTheme() {
//        ChangeModeController.setTheme(this, R.style.DayTheme, R.style.NightTheme);
    }
    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(){
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, com.yun.software.R.color.alpha_80_black));
    }
    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color){
        StatusBarCompat.setStatusBarColor(this,color);
    }
    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar(){
        StatusBarCompat.translucentStatusBar(this);
    }


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
        overridePendingTransition(com.yun.software.R.anim.fade_in, com.yun.software.R.anim.fade_out);
    }

    public void finishActivity(int flag) {
        super.finish();
    }
    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, true);
    }

    /**
     * 停止浮动加载进度条
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
    /**
     * 带图片的toast
     * @param text
     * @param res
     */
    public void showToastWithImg(String text, int res) {
        ToastUitl.showToastWithImg(text,res);
    }

    /**
     * 网络访问错误提醒
     */
    public void showNetErrorTip() {
        ToastUitl.showToastWithImg(getText(com.yun.software.R.string.net_error).toString(), com.yun.software.R.drawable.ic_wifi_off);
    }
    public void showNetErrorTip(String error) {
        ToastUitl.showToastWithImg(error, com.yun.software.R.drawable.ic_wifi_off);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //debug版本不统计crash
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if(!BuildConfig.LOG_DEBUG) {
            //友盟统计
//            MobclickAgent.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //debug版本不统计crash
        if(!BuildConfig.LOG_DEBUG) {
            //友盟统计
//            MobclickAgent.onPause(this);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isConfigChange=true;
    }

    @Override
    protected void onDestroy() {
        mQueue.cancelBySign(object);
        mQueue.stop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if(!isConfigChange){
            AppManager.getAppManager().finishActivity(this);
        }
        ButterKnife.unbind(this);
        super.onDestroy();


    }
    protected void cancelAll() {
        mQueue.cancelAll();
    }

    protected void cancelBySign(Object object) {
        mQueue.cancelBySign(object);
    }

    public void tologin(LogincallBack logincallBack){
        mlogincallBack=logincallBack;
        SingleCall.getInstance()
                .addAction(this)
                .addValid(new LoginValid(this))
                .doCall();

    }

    @Override
    public void call() {
        if(mlogincallBack!=null){
            mlogincallBack.loginsuccess();
        }
    }
    public interface LogincallBack{
        void loginsuccess();
    }


    /**
     * 隐藏软键盘
     */
    public void hiddenKeyBoard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 带参数的显示软键盘
     */
    public void openkeybord(final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }

        }, 300);
    }

}
