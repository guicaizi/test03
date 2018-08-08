package com.yun.software.shangcheng.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yun.software.shangcheng.base.AppApplication;
import com.yun.software.shangcheng.ui.entity.MessageCode;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLogUtils.i("options","lala1");
        AppApplication.api.handleIntent(getIntent(), this);
        EventBus.getDefault().post(new MessageCode("code","dismiss"));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MyLogUtils.i("options","lala2");
        setIntent(intent);
        AppApplication.api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        MyLogUtils.i("options","lala3");
    }

    String code;
    String errcode;
    String state;
    @Override
    public void onResp(BaseResp baseResp) {
        MyLogUtils.i("options", "baseResp.errcode：" + baseResp.errCode + "-----baseResp.errstr：" + baseResp.errStr + "-----baseResp.transction：" + baseResp.transaction + "-----baseResp.openid：" + baseResp.openId);
        if(-2==baseResp.errCode){
//            Tools.showInfo(this,"取消授权");
            Bundle bundle = new Bundle();
            bundle.putString("code",baseResp.errCode+"");
            EventBus.getDefault().post(new MessageCode("code","cancle"));
            finish();

        }else{
            if(StringUtils.isNotEmpty(baseResp.openId)){
//                LogUtil.i(TAG,"成功------------------");
            }else{
                SendAuth.Resp resp= (SendAuth.Resp) baseResp;
                code=resp.code;
                errcode= StringUtils.toString(resp.errCode);
                state=resp.state;
                Bundle bundle = new Bundle();
                bundle.putInt("errCode", resp.errCode);
                bundle.putString("errStr",resp.errStr);
                bundle.putString("state",resp.state);
                bundle.putString("code",code);
                MyLogUtils.i("options","code"+code+"errcode"+errcode+"state"+state);
                EventBus.getDefault().post(new MessageCode("code",code));
                finish();
            }
        }
        finish();
    }
    //在UI线程中执行

}
