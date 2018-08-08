package com.yun.software.shangcheng.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yun.software.shangcheng.base.AppApplication;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;

import org.greenrobot.eventbus.EventBus;



/**
 * 微信支付的回调页面
 * 
 * @author mulinrui
 * 
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppApplication.api.handleIntent(getIntent(), this);
		MyLogUtils.i(TAG,"onCreat");
		EventBus.getDefault().post(new MessageEvent("paydismiss"));
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		MyLogUtils.i(TAG,"pay2");
		AppApplication.api.handleIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq baseReq) {
		MyLogUtils.i(TAG,"pay3");
	}

	@Override
	public void onResp(BaseResp baseResp) {
		MyLogUtils.i(TAG,"onPayFinish, errCode = " + baseResp.errCode);
		if(-2==baseResp.errCode){
			EventBus.getDefault().post(new MessageEvent("paycancle"));

			finish();
		}else if(0==baseResp.errCode){
			EventBus.getDefault().post(new MessageEvent("paysuccess"));
			finish();
		}else{
			EventBus.getDefault().post(new MessageEvent("payerror"));
			finish();
		}
		finish();
	}



}