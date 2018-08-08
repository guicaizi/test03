package com.yun.software.shangcheng.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.utils.ExceptionUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/2 16:48
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.re_setting_myinfor)
    RelativeLayout reSettingMyinfor;
    @Bind(R.id.re_setting_address)
    RelativeLayout reSettingAddress;
    @Bind(R.id.re_setting_about)
    RelativeLayout reSettingAbout;
    @Bind(R.id.re_setting_feedback)
    RelativeLayout reSettingFeedback;
    @Bind(R.id.re_change_password)
    RelativeLayout reChangePassword;
    @Bind(R.id.btn_out_login)
    Button btnOutLogin;
    @Override
    public int getLayoutId() {
        return R.layout.activity_all_setting;
    }

    @Override
    public void initPresenter() {

            tvTitle.setText("设置");
    }

    @Override
    public void initView() {

    }
    public void loadDate(int what){
        try {
            switch (what){
                case 1:
                    break;
            }

        }catch (Exception e){
            ExceptionUtil.handle(e);
        }
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {

        }

        @Override
        public void onFailed(int what, String jsonstr) {

        }
    };
    @Override
    public void addLisener() {
        reSettingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *地址管理
                 */
                Bundle bundle = new Bundle();
                enterPage(ManagerAddress.class, bundle);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reSettingAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webUrl = "http://www.shixiangyoupin.com/shopmobile/#/aboutUs";
                Bundle bundle = new Bundle();
                bundle.putString("weburl", webUrl);
                enterPage(AllwebActivity.class, bundle);
            }
        });
        reChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPage(ChangePasswordActivity.class);
            }
        });
        btnOutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                EventBus.getDefault().post(new MessageEvent("tologin"));
            }
        });
    }
}
