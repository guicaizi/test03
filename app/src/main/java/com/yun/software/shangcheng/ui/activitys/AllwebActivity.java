package com.yun.software.shangcheng.ui.activitys;


import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.base.BaseWebActivity;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;

/**
 * Created by yanliang
 * on 2018/7/4 10:08
 */
    public class AllwebActivity extends BaseWebActivity{
    public static final  String TAG="AllwebActivity";

//    @Override
    public String getUrl() {
        String weburl = null;
        Bundle bundle =getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if(bundle!=null){
            if(bundle.containsKey("weburl")){
                weburl=bundle.getString("weburl");
                MyLogUtils.i(TAG,"传递过来的网址"+weburl);

            }
        }

        return weburl;
    }



    @Override
    protected void onStart() {
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new JavaScriptinterface(this));
        super.onStart();
    }

    public class JavaScriptinterface {
        Context context;

        public JavaScriptinterface(Context c) {
            context = c;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void callAndroid(String jsReturn) {
            MyLogUtils.i("options", "web调用Android返回字段===》" + jsReturn);
             finish();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();



        //测试Cookies
        /*try {

            String targetUrl="";
            Log.i("Info","cookies:"+ AgentWebConfig.getCookiesByUrl(targetUrl="http://www.jd.com"));
            AgentWebConfig.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {
                    Log.i("Info","onResume():"+value);
                }
            });

            String tagInfo=AgentWebConfig.getCookiesByUrl(targetUrl);
            Log.i("Info","tag:"+tagInfo);
            AgentWebConfig.syncCookie("http://www.jd.com","ID=IDHl3NVU0N3ltZm9OWHhubHVQZW1BRThLdGhLaFc5TnVtQWd1S2g1REcwNVhTS3RXQVFBQEBFDA984906B62C444931EA0");
            String tag=AgentWebConfig.getCookiesByUrl(targetUrl);
            Log.i("Info","tag:"+tag);
            AgentWebConfig.removeSessionCookies();
            Log.i("Info","removeSessionCookies:"+AgentWebConfig.getCookiesByUrl(targetUrl));
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }
}
