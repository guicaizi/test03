package com.yun.software.shangcheng.ui.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.ui.fragments.GoodCarFragment;

/**
 * Created by yanliang
 * on 2018/7/12 16:02
 */

public class GoodCarActivity extends BaseActivity {
    private FrameLayout mFrameLayout;
    private FragmentManager mFragmentManager;
    @Override
    public int getLayoutId() {
        return R.layout.activity_all_collection;
    }
    @Override
    public void initPresenter() {
        mFrameLayout = (FrameLayout) this.findViewById(R.id.container_framelayout);
        mFragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putInt("frompage",1);
        ft.add(R.id.container_framelayout, GoodCarFragment.getInstance(bundle), GoodCarFragment.class.getName());
        ft.commit();

    }

    @Override
    public void initView() {

    }

    @Override
    public void addLisener() {

    }

}
