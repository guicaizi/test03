package com.yun.software.shangcheng.ui.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yun.software.baseApp.AppManager;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.fragments.GoodCarFragment;
import com.yun.software.shangcheng.ui.fragments.ModleFragment;
import com.yun.software.shangcheng.ui.fragments.ShopMainIndexFragment;
import com.yun.software.shangcheng.ui.fragments.ShopMyFragment;
import com.yun.software.shangcheng.ui.fragments.VipApplyFragment;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.utils.DoubleClickExitHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    private static final int REQUEST_BANNES=1;
    public static final String TAG = "MainActivityMain";
    private DoubleClickExitHelper mDoubleClickExit;
    @Bind(R.id.Container)
    FrameLayout layout_content;
    @Bind(R.id.v_line)
    View vLine;
    @Bind(R.id.gr_check)
    RadioGroup main_radio;
    String tag = null;
    @Bind(R.id.Generic_Rb01)
    RadioButton rb01;
    @Bind(R.id.Generic_Rb02)
    RadioButton rb02;
    @Bind(R.id.Generic_Rb03)
    RadioButton rb03;
    @Bind(R.id.Generic_Rb04)
    RadioButton rb04;
    @Bind(R.id.Generic_Rb05)
    RadioButton rb05;
    @Bind(R.id.iv_beautiful)
    ImageView ivBeautiful;
    @Bind(R.id.iv_first)
    ImageView ivFirst;
    @Bind(R.id.iv_four)
    ImageView ivFour;
    @Bind(R.id.iv_five)
    ImageView ivFive;
    private int index = 0;
    private int lastindex = -1;
    //点击次数
    private int clicktag = 1;
    private long firstTime = 0;
    private boolean isInit = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //        super.onSaveInstanceState(outState);
//        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            main_radio.check(R.id.Generic_Rb01);
            isInit = true;
        }
    }
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        if ("first".equals(messageEvent.getMessage())) {
            main_radio.check(R.id.Generic_Rb01);
        }else if("tologin".equals(messageEvent.getMessage())){
            AppManager.getAppManager().finishAllActivity();
            biz.setCustomToken("");
            biz.setAuthorization("");
            ApiConstants.HEADER="";;
            Bundle bundle=new Bundle();
            bundle.putInt("frompage",1);
            enterPage(LoginActivity.class,bundle);


        }

    }
    @Override
    public void initPresenter() {
        ApiConstants.HEADER=biz.getAuthorization();
        mDoubleClickExit = new DoubleClickExitHelper(this);
        setBottomScaleSize(rb01);
        setBottomScaleSize(rb02);
        setBottomScaleSize(rb03);
        setBottomScaleSize(rb04);
        setBottomScaleSize(rb05);
        rb03.setEnabled(false);

    }
    public void setBottomScaleSize(RadioButton bottomScaleSize) {
        Drawable[] drawables = bottomScaleSize.getCompoundDrawables();
        drawables[1].setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.x22),
                getResources().getDimensionPixelSize(R.dimen.x22));
        bottomScaleSize.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }


    @Override
    public void initView() {

    }

    @Override
    public void addLisener() {
        ivBeautiful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tologin(new LogincallBack() {
                    @Override
                    public void loginsuccess() {
                        MyLogUtils.i("kankan","成功改了");
                        main_radio.check(R.id.Generic_Rb03);
                    }
                });

            }
        });

        ivFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tologin(new LogincallBack() {
                    @Override
                    public void loginsuccess() {
                        main_radio.check(R.id.Generic_Rb04);
                    }
                });

            }
        });
        ivFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tologin(new LogincallBack() {
                    @Override
                    public void loginsuccess() {
                        main_radio.check(R.id.Generic_Rb05);
                    }
                });
            }
        });
        main_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Generic_Rb01:
                        index = 0;
                        break;
                    case R.id.Generic_Rb02:
                        index = 1;
                        break;
                    case R.id.Generic_Rb03:
                        index = 2;
                        break;
                    case R.id.Generic_Rb04:
                        index = 3;
                        break;
                    case R.id.Generic_Rb05:
                        index = 4;
                        break;
                }
                Fragment fragment = (Fragment) fragments.instantiateItem(layout_content, index);
                //默认选择第0个fragment
                fragments.setPrimaryItem(layout_content, 0, fragment);
                //fragment提交更新事务
                fragments.finishUpdate(layout_content);
                lastindex = index;
            }


        });
    }

    FragmentStatePagerAdapter fragments = new FragmentStatePagerAdapter(
            getSupportFragmentManager()) {

        //几个fragment就返回几个
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = null;
            switch (arg0) {
                case 0:
                    fragment = new ShopMainIndexFragment();
                    break;
                case 1:
                    fragment = new ModleFragment();
                    break;
                case 2:
                    fragment = new VipApplyFragment();
                    break;
                case 3:
                    fragment = new GoodCarFragment();
                    break;
                case 4:
                    fragment = new ShopMyFragment();
                    break;
            }
            return fragment;
        }
    };



}
