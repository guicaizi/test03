package com.yun.software.shangcheng.ui.activitys;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.ClearEditText;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/2 17:27
 */

public class ChangePasswordActivity extends BaseActivity {
     public static final  String TAG="ChangePasswordActivity";
     public static int REQUEST_CHANGE_PASSWORD=0;

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_old_password)
    ClearEditText etOldPassword;
    @Bind(R.id.et_new_password)
    ClearEditText etNewPassword;
    @Bind(R.id.et_new_password_again)
    ClearEditText etNewPasswordAgain;
    @Bind(R.id.bt_regeist)
    Button btRegeist;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initPresenter() {
        tvTitle.setText("修改密码");

    }
    public void loadDate(int what){
         String oldps=etOldPassword.getText().toString();
         String newps=etNewPassword.getText().toString();
         String newpsag=etNewPasswordAgain.getText().toString();
         if(StringUtils.isEmpty(oldps)){
             showLongToast("请输入旧密码");
             return;
         }
        if(StringUtils.isEmpty(newps)){
            showLongToast("请输入新密码");
            return;
        }
        if(StringUtils.isEmpty(newpsag)){
            showLongToast("请再次新密码");
            return;
        }
        if(!newpsag.equals(newps)){
            showLongToast("两次新密码不一致，请重新输入");
            return;
        }
        try {
            Map map0 = new HashMap();
            map0.put("oldPassword",oldps);
            map0.put("password",newps);
            map0.put("confirm",newpsag);
            request(REQUEST_CHANGE_PASSWORD, ApiConstants.CHANGE_PASSWORD, JSON.toJSONString(map0), myhttpListener, false, false);
        }catch (Exception e){
            ExceptionUtil.handle(e);
        }
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            MyLogUtils.i(TAG,"修改结果"+jsonstr);
            Tools.showInfo(mContext,"修改成功");
            finish();

        }

        @Override
        public void onFailed(int what, String jsonstr) {

        }
    };
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
                loadDate(REQUEST_CHANGE_PASSWORD);
            }
        });


    }


}
