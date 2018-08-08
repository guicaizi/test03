package com.yun.software.shangcheng.base;


import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.durban.Durban;
import com.yanzhenjie.durban.util.DurbanUtils;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yanzhenjie.nohttp.rest.Interceptor;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestHandler;
import com.yanzhenjie.nohttp.rest.Response;
import com.yun.software.baseApp.BaseApplication;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.loadManager.LoadingAndRetryManager;
import com.yun.software.shangcheng.ui.callback.GlideImageLoader;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.utils.LogUtils;

import java.util.Locale;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    public static IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.logInit(false);

        api = WXAPIFactory.createWXAPI(this, "wx8c4774f266037b86");
        api.registerApp("wx8c4774f266037b86");

        /**
         *腾讯bugly
         */
        CrashReport.initCrashReport(getApplicationContext(), "2eb711b045", true);
        Locale locale = Durban.getDurbanConfig().getLocale();
        DurbanUtils.applyLanguageForContext(this, locale);
        Album.initialize(new AlbumConfig.Build()
                        .setImageLoader(new GlideImageLoader()) // Use glide loader.
                        .setLocale(Locale.getDefault())
                        .build());

//        System.loadLibrary("live-openh264");
//        System.loadLibrary("QuCore-ThirdParty");
//        System.loadLibrary("QuCore");
        Logger.setDebug(true);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("NoHttpSample");// 设置NoHttp打印Log的tag。
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
        NoHttp.initialize(InitializationConfig.newBuilder(this)
                // 设置全局连接超时时间，单位毫秒，默认10s。
                .connectionTimeout(5000)
                // 设置全局服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout( 5000)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .cacheStore(
                        new DBCacheStore(this).setEnable(true) // 如果不使用缓存，设置setEnable(false)禁用。
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现。
                .cookieStore(
                        new DBCookieStore(this).setEnable(false) // 如果不维护cookie，设置false禁用。
                )
                // 配置网络层，URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                .networkExecutor(new OkHttpNetworkExecutor())
                .interceptor(new Interceptor() {
                    @Override
                    public <T> Response<T> intercept(RequestHandler requestHandler, Request<T> request) {
                        Headers headers=request.getHeaders();
                        headers.add("APPId","yanliang");
                        MyLogUtils.i("header", request.getHeaders().toJSONString());


                        return requestHandler.handle(request);
                    }
                })
                .build()
        );
    }

//    private void initDBHelper() {
//        DBHelper.init(getApplicationContext());
//    }
}
