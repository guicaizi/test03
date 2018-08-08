package com.yun.software.shangcheng.ui.activitys;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.ClearEditText;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;
import com.yun.software.utils.SMSCodeUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/3 10:40
 */

public class ForgetPassword extends BaseActivity {
    private static final int REQUEST_SMS_INFOR = 1;
    private static final int REQUEST_REGEIST = 2;
    private static final int REQUEST_FORGET = 4;
    /**
     * 验证码开始计时请求
     */
    private static final int FLAG_REQUEST_START_TIMER = 3;
    Map<String, Object> handlesms;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_register_mobile)
    EditText etRegisterMobile;
    @Bind(R.id.btn_register_obtain_verification_code)
    Button btnRegisterObtainVerificationCode;
    @Bind(R.id.tv_register_verification_code_title)
    TextView tvRegisterVerificationCodeTitle;
    @Bind(R.id.et_register_verification_code)
    ClearEditText etRegisterVerificationCode;
    @Bind(R.id.rl_verification)
    RelativeLayout rlVerification;
    @Bind(R.id.et_login_password)
    ClearEditText etLoginPassword;
    @Bind(R.id.et_login_password_two)
    ClearEditText etLoginPasswordTwo;
    @Bind(R.id.bt_regeist)
    Button btRegeist;
    /**
     * 电话号码， 验证码， 密码
     */
    private String mobile, verCode, password,passwordtwo;

    private boolean operateLimitFlag = false;
    private boolean isforgetpassword =false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initPresenter() {
            isforgetpassword=true;
            btRegeist.setText("确定");
            tvTitle.setText("找回密码");

    }

    @Override
    public void initView() {

    }

    @Override
    public void addLisener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btRegeist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitRegist();
            }
        });
        btnRegisterObtainVerificationCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mobile = etRegisterMobile.getText().toString();
                // 判断手机号不能为空
                if (StringUtils.isEmpty(mobile)) {
                    Tools.showInfo(mContext, R.string.mobile_null);
                    return;
                }
                // 判断手机号是否合法
                if (!StringUtils.isMobile(mobile)) {
                    Tools.showInfo(mContext, R.string.mobile_length_limit);
                    return;
                }
                SMSCodeUtil.startBtnTimer(btnRegisterObtainVerificationCode);
                loadDate(REQUEST_SMS_INFOR);
            }
        });

    }
    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_SMS_INFOR:
                    Map map3 = new HashMap();
                    map3.put("phone",mobile);
                    request(REQUEST_SMS_INFOR, ApiConstants.REGIST_CODE, JSON.toJSONString(map3), myhttpListener, false, false);
                    break;
            }

        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }
    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            try {
                switch (what) {
                    case REQUEST_SMS_INFOR:
                        break;
                    case REQUEST_FORGET:
                        showShortToast("已为您找回密码！请用新密码登录。");
                        finish();
                        break;

                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {


        }
    };
    public void commitRegist(){
        mobile  = etRegisterMobile.getText().toString();
        password = etLoginPassword.getText().toString();
        passwordtwo=etLoginPasswordTwo.getText().toString();
        // 判断手机号不能为空
        if (StringUtils.isEmpty(mobile)) {
            Tools.showInfo(mContext, R.string.mobile_null);
            operateLimitFlag = false;
            return;
        }
        // 判断手机号是否合法
        if (!StringUtils.isMobile(mobile)) {
            Tools.showInfo(mContext,
                    R.string.mobile_length_limit);
            operateLimitFlag = false;
            return;
        }
        verCode = etRegisterVerificationCode.getText().toString();
        // 验证码不能为空
        if (StringUtils.isEmpty(verCode)) {
            Tools.showInfo(mContext,
                    R.string.verification_code_null);
            operateLimitFlag = false;
            return;
        }

        // 密码不能为空
        if (StringUtils.isEmpty(password)) {
            Tools.showInfo(mContext, R.string.password_length_limit);
            operateLimitFlag = false;
            return;
        }
        // 密码是否合法
        if (password.length() < 6 || password.length() > 16) {
            Tools.showInfo(mContext, R.string.password_length_limit);
            operateLimitFlag = false;
            return;
        }
        if (!password.equals(passwordtwo)) {
            Tools.showInfo(mContext, "两次密码输入不一致");
            operateLimitFlag = false;
            return;
        }
        if(isforgetpassword){
            Map<String, String> maps = new HashMap<String, String>();
            maps.put("phone",mobile);
            maps.put("verifyCode",verCode);
            maps.put("password",password);
            maps.put("confirm",passwordtwo);
            request(REQUEST_FORGET, ApiConstants.REGIST_FOURGET_PASSWORD,JSON.toJSONString(maps), myhttpListener, false, false);

        }



    }
}
