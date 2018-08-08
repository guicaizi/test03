package com.yun.software.shangcheng.ui.activitys;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.ui.adapter.ViewPageFragmentAdapter;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.fragments.StateOderFragment;
import com.yun.software.utils.PagerSlidingTabStrip;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/6 16:22
 */

public class AllOrderActivity extends BaseActivity {
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.pager_tabstrip)
    PagerSlidingTabStrip pagerTabstrip;
    @Bind(R.id.pager)
    ViewPager pager;
    private ViewPageFragmentAdapter mTabsAdapter;
    private int orderid=0;
    @Override
    public int getLayoutId() {
        return  R.layout.activity_order_detial;
    }

    @Override
    public void initPresenter() {
        tvTitle.setText("我的订单");
        mTabsAdapter = new ViewPageFragmentAdapter(getSupportFragmentManager(),
                pagerTabstrip, pager);
        onSetupTabAdapter(mTabsAdapter);
        pager.setOffscreenPageLimit(1);
        Bundle bundle = getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if (bundle !=null) {
            if(bundle.containsKey("orderid")){
                orderid = bundle.getInt("orderid",0);
            }
        }
        mTabsAdapter.setindexTab(orderid);
    }

    @Override
    public void initView() {

    }

    @Override
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        super.onMessageEventMainThread(messageEvent);
       if (messageEvent.getMessage().equals("paysuccess")) {
           mTabsAdapter.setindexTab(1);
       }else if(messageEvent.getMessage().equals("tuihuanhuo")){
           mTabsAdapter.setindexTab(3);
       }
    }

    @Override
    public void addLisener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = getResources().getStringArray(
                R.array.news_viewpage_arrays);
        adapter.addTab(title[0], "type", StateOderFragment.class,
                getBundle("2"));
        adapter.addTab(title[1], "type", StateOderFragment.class,
                getBundle("3"));
        adapter.addTab(title[2], "type", StateOderFragment.class,
                getBundle("4"));
        adapter.addTab(title[3], "type", StateOderFragment.class,
                getBundle("5"));
        adapter.addTab(title[4], "type", StateOderFragment.class,
                getBundle("1"));
    }
    private Bundle getBundle(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("type", id);
        return bundle;
    }
}
