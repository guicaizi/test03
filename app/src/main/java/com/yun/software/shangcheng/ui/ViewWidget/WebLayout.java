package com.yun.software.shangcheng.ui.ViewWidget;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.just.agentweb.IWebLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yun.software.shangcheng.R;

/**
 * Created by yanlian on 2018/10/1.
 */


public class WebLayout implements IWebLayout {

    private Activity mActivity;
    private final TwinklingRefreshLayout mTwinklingRefreshLayout;
    private WebView mWebView = null;

    public WebLayout(Activity activity) {
        this.mActivity = activity;
        mTwinklingRefreshLayout = (TwinklingRefreshLayout) LayoutInflater.from(activity).inflate(R.layout.fragment_twk_web, null);
        mTwinklingRefreshLayout.setPureScrollModeOn();
        mWebView = (WebView) mTwinklingRefreshLayout.findViewById(R.id.webView);
    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mTwinklingRefreshLayout;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return mWebView;
    }



}
