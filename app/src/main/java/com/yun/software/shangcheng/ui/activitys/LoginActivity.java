package com.yun.software.shangcheng.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.AppApplication;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.registlogin.action.SingleCall;
import com.yun.software.shangcheng.ui.ViewWidget.ClearEditText;
import com.yun.software.shangcheng.ui.entity.MessageCode;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/2 17:07
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_LOGIN = 0x01;
    private static final int REQUEST_INFOR = 0x02;
    private static final int REQUEST_WXREGEST = 0x03;
    private int formpage = 0x00;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 用户头像
     */
    @Bind(R.id.iv_login_icon)
    ImageView ivLoginIcon;
    /**
     * 用户名称
     */
    @Bind(R.id.et_login_username)
    ClearEditText etLoginUsername;
    /**
     * 用户密码
     */
    @Bind(R.id.et_login_password)
    ClearEditText etLoginPassword;
    /**
     * 注册
     */
    @Bind(R.id.tv_login_regeist)
    TextView tvLoginRegeist;
    /**
     * 登录
     */
    @Bind(R.id.bt_login)
    Button btLogin;
    /**
     * 微信登录
     */
    @Bind(R.id.lin_login_weixin)
    LinearLayout linLoginWeixin;
    /**
     * 忘记密码
     */
    @Bind(R.id.tv_login_forget)
    TextView tvLoginForget;
    /**
     * 联系点击识别标记
     */
    private boolean operatelimitoption = false;
    String code;
    private boolean canClick = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_logins;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWXReturn(MessageCode messagecode) {
        MyLogUtils.i("options", "message" + messagecode.getCode());
        code = messagecode.getCode();
        if (code.equals("cancle")) {
            canClick = true;
        } else if (code.equals("dismiss")) {
            stopProgressDialog();
        }else{
            loadDate(REQUEST_WXREGEST);
        }
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void initPresenter() {
        Bundle bundle=getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if(bundle!=null&&bundle.containsKey("frompage")){
            formpage=bundle.getInt("frompage");

        }

    }

    @Override
    public void initView() {

    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_LOGIN:
                    String etusername = etLoginUsername.getText().toString();
                    String etpassword = etLoginPassword.getText().toString();
                    if (StringUtils.isEmpty(etusername)) {
                        Tools.showInfo(mContext, "请输入用户名");
                        return;
                    }
                    if (StringUtils.isEmpty(etpassword)) {
                        Tools.showInfo(mContext, "请输入用户密码");
                        return;
                    }
                    String totaluser=etusername+":"+etpassword;
                    String head=StringUtils.encodeHeadInfo(totaluser);

                    request(head,REQUEST_LOGIN, ApiConstants.LOGIN, "", myhttpListener, false, false);
                    //                    operatelimitoption=true;
                    break;

                case REQUEST_WXREGEST:
                    Map map1 = new HashMap();
                    map1.put("code", code);
                    request(REQUEST_WXREGEST, ApiConstants.WX_REGIST, JSON.toJSONString(map1), myhttpListener, false, false);
                    break;
                case REQUEST_INFOR:
                    Map map = new HashMap();
                    Map map00 = new HashMap();
                    map00.put("token", biz.getTocken());
                    map.put("param", map00);
                    request(ApiConstants.HEADER, REQUEST_INFOR, ApiConstants.USERINFOR, StringUtils.commbingJson(map, map00), myhttpListener, false, false);
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
                    case REQUEST_LOGIN:
                        String token1=StringUtils.getJsonKeyStr(jsonstr,"token");
                        ApiConstants.HEADER=token1;
                        biz.setAuthorization(token1);
                        Map<String,String> customer2= JSON.parseObject(StringUtils.getJsonKeyStr(jsonstr,"customer"), new TypeReference<Map<String, String>>() {
                        });
                        MyLogUtils.i(TAG,"token"+token1+"customer"+customer2);
                        MyLogUtils.i(TAG,"id"+customer2.get("id"));
                        biz.setCustomToken(customer2.get("id"));
                        if(formpage==1){
                            enterPage(MainActivity.class);
                        }else{
                            SingleCall.getInstance().doCall();
                        }
                        finish();
                        break;
                    case REQUEST_INFOR:
                        MyLogUtils.i(TAG, "个人信息返回：\r\n" + jsonstr);
                        Map<String, Object> infor = JSON.parseObject(jsonstr, Map.class);
                        biz.setCustomId(StringUtils.toString(infor.get("uid")));
                        biz.setCustomToken(StringUtils.toString(infor.get("roleId")));
                        finish();
                        break;
                    case REQUEST_WXREGEST:
                        MyLogUtils.i(TAG, "微信登陆返回：\r\n" + jsonstr);
                        String token=StringUtils.getJsonKeyStr(jsonstr,"token");
                        ApiConstants.HEADER=token;
                        biz.setAuthorization(token);
                        Map<String,String> customer= JSON.parseObject(StringUtils.getJsonKeyStr(jsonstr,"customer"), new TypeReference<Map<String, String>>() {
                        });
                        MyLogUtils.i(TAG,"token"+token+"customer"+customer);
                        MyLogUtils.i(TAG,"id"+customer.get("id"));
                        biz.setCustomToken(customer.get("id"));
                        biz.setUserName(customer.get("nickName"));
                        biz.setScore(customer.get("score"));
                        if(formpage==1){
                            enterPage(MainActivity.class);
                        }else{
                            SingleCall.getInstance().doCall();
                        }
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

    @Override
    public void addLisener() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operatelimitoption) {
                    Tools.showInfo(mContext, "正在登录，请稍等");
                    return;
                }
                loadDate(REQUEST_LOGIN);
            }
        });

        linLoginWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (operatelimitoption) {
                        Tools.showInfo(mContext, "正在登录微信，请稍后");
                        return;
                    }
                    if (!AppApplication.api.isWXAppInstalled()) {
                        Tools.showInfo(mContext, "您还未安装微信，请安装完微信后再试！");
                        return;
                    }
                    if (!AppApplication.api.isWXAppSupportAPI()) {
                        Tools.showInfo(mContext, "当前微信版本过低，请更换至最新版本。");
                        return;
                    }
                    startProgressDialog();
                    operatelimitoption = true;
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    AppApplication.api.sendReq(req);
                } catch (Exception e) {
                    ExceptionUtil.handle(e);
                    linLoginWeixin.performClick();
                }
            }
        });

        tvLoginRegeist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPage(RegistActivity.class);

            }
        });
        tvLoginForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPage(ForgetPassword.class);
            }
        });


    }


}
