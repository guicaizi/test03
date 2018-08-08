package com.yun.software.shangcheng.registlogin.options;

import android.app.Activity;
import android.content.Context;

import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.registlogin.action.Valid;
import com.yun.software.shangcheng.ui.activitys.LoginActivity;
import com.yun.software.shangcheng.ui.utils.StringUtils;


/**
 * Created by jinyabo on 8/12/2017.
 */

public class LoginValid implements Valid {
    private BaseActivity context;

    public LoginValid(Context context) {
        this.context = (BaseActivity) context;
    }

    /**
     * check whether it login in or not
     * @return
     */
    @Override
    public boolean check() {
        return StringUtils.isNotEmpty(context.biz.getCustomToken());
    }


    /**
     * if check() return false. then doValid was called
     */
    @Override
    public void doValid() {
        LoginActivity.start((Activity) context);
    }
}
