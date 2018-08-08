package com.yun.software.shangcheng.ui.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseFragment;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.adapter.VipsAdapter;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.entity.VipEntity;
import com.yun.software.shangcheng.ui.entity.WxPayNeedParams;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;
import com.yun.software.utils.SMSCodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/23 11:21
 */

public class VipApplyFragment extends BaseFragment {
    private static final int REQUEST_VIP_LIST =1 ;
    private static final int REQUEST_COUSTOM_INFOR =2 ;
    private static final int REQUEST_VER_CODE =3 ;
    private static final int REQUEST_WX_APYPARAMS =4;
    private static final int REQUEST_ODER_NUMBER =5;
     public static final  String TAG="VipApplyFragment";
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_icon)
    ImageView ivIcon;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_vip_state)
    TextView tvUserVipState;
    @Bind(R.id.tv_score)
    TextView tvScore;
    @Bind(R.id.list_vips)
    ListView listVips;
    @Bind(R.id.et_input_phone)
    EditText etInputPhone;
    @Bind(R.id.et_regist_code)
    EditText etRegistCode;
    @Bind(R.id.btn_get_regist_code)
    Button btnGetRegistCode;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    @Bind(R.id.btn_pay)
    Button btnPay;
    @Bind(R.id.sc_score)
    ScrollView scrollView;
    @Bind(R.id.tv_over_time)
    TextView tvOvertime;
    @Bind(R.id.lin_content)
    LinearLayout linContent;
    VipsAdapter vipsAdapter;
    private boolean operateLimitFlag = false;
    private String mobile;
    private boolean isfristRequest=true;
    private String submitphone,submitcode;
    private String submitOder;
    private int checkposition=0;
    List<VipEntity> vipEntities;
    private boolean isOperateLimitFlag=false;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_vip_apply;
    }

    @Override
    public void setDate() {
        vipEntities=new ArrayList<VipEntity>();
        vipsAdapter = new VipsAdapter(mContext, vipEntities);
        listVips.setAdapter(vipsAdapter);
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("会员");
        createLoadingview(linContent);
        loadDate(REQUEST_VIP_LIST);

    }

    public void onMessageEventMainThread(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("paydismiss")) {
            stopProgressDialog();


        } else if (messageEvent.getMessage().equals("paycancle")) {
            showLongToast("交易取消");



        } else if (messageEvent.getMessage().equals("paysuccess")) {
            showLongToast("购买成功");
            etInputPhone.setText("");
            etRegistCode.setText("");
            loadDate(REQUEST_COUSTOM_INFOR);


        } else if (messageEvent.getMessage().equals("payerror")) {
            showLongToast("购买失败");
        }
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        isOperateLimitFlag=false;
        loadDate(REQUEST_COUSTOM_INFOR);
    }

    @Override
    public void onResume() {
        super.onResume();
        isOperateLimitFlag=false;
    }

    @Override
    protected void addLisener() {
        btnGetRegistCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operateLimitFlag) {
                    return;
                }
                operateLimitFlag = true;
                mobile = etInputPhone.getText().toString();
                // 判断手机号不能为空
                if (StringUtils.isEmpty(mobile)) {
                    Tools.showInfo(mContext, R.string.mobile_null);
                    operateLimitFlag = false;
                    return;
                }
                // 判断手机号是否合法
                if (!StringUtils.isMobile(mobile)) {
                    Tools.showInfo(mContext, R.string.mobile_length_limit);
                    operateLimitFlag = false;
                    return;
                }
                loadDate(REQUEST_VER_CODE);
                SMSCodeUtil.startBtnTimer(btnGetRegistCode);
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOperateLimitFlag){
                    showShortToast("正在支付请稍后...");
                    return;
                }
                 submitphone=etInputPhone.getText().toString();
                 submitcode=etRegistCode.getText().toString();
                 if(StringUtils.isEmpty(submitphone)){
                     showShortToast("手机号不能为空");
                     return;
                 }
                 if(StringUtils.isEmpty(submitcode)){
                     showShortToast("验证码不能为空");
                     return;
                 }
                 isOperateLimitFlag=true;
                 startProgressDialog("正在支付中....");
                 loadDate(REQUEST_ODER_NUMBER);
            }
        });
        listVips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkposition=position;
                vipsAdapter.setCheckPostion(position);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SMSCodeUtil.cancelTimer(btnGetRegistCode);
    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_VIP_LIST:
                    Map map1=new HashMap();
                    request(REQUEST_VIP_LIST, ApiConstants.VIP_LIST_INFOR, JSON.toJSONString(map1), myhttpListener, false, false);
                case REQUEST_COUSTOM_INFOR:
                    Map map = new HashMap();
                    map.put("customerId",biz.getCustomToken());
                    request(REQUEST_COUSTOM_INFOR, ApiConstants.USERINFORTWO,JSON.toJSONString(map), myhttpListener, false, isfristRequest);
                    isfristRequest=false;
                    break;
                case REQUEST_VER_CODE:
                    Map map3 = new HashMap();
                    map3.put("phone",mobile);
                    map3.put("customerId",biz.getCustomToken());
                    request(REQUEST_VER_CODE, ApiConstants.VIP_VER_CODE,JSON.toJSONString(map3), myhttpListener, false, false);
                    break;
                case REQUEST_WX_APYPARAMS:
                    request(REQUEST_WX_APYPARAMS, ApiConstants.VIP_PAY+"?outTradeNo=" +submitOder,null, myhttpListener, false, false);
                    break;
                case REQUEST_ODER_NUMBER:
                    Map map5= new HashMap();
                    map5.put("customerId",biz.getCustomToken());
                    map5.put("memberId",vipEntities.get(checkposition).getId());
                    map5.put("phone",submitphone);
                    map5.put("verifyCode",submitcode);
                    request(REQUEST_ODER_NUMBER, ApiConstants.VIP_PAY_ODERNO,JSON.toJSONString(map5), myhttpListener, false, false);
                    break;
            }

        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            isfristRequest=false;
            try {
                switch (what) {
                    case REQUEST_VIP_LIST:
                        MyLogUtils.i(TAG,"vip列表"+jsonstr);
                        if(vipEntities.size()>0){
                            vipEntities.clear();
                        }
                        List<VipEntity> vips=JSON.parseObject(jsonstr,new TypeReference<List<VipEntity>>(){});
                        vipEntities.addAll(vips);
                        vipsAdapter.updateData(vipEntities);
                        tvTotalPrice.setText("总额：￥"+vipEntities.get(0).getPrice());
                        Tools.setListViewHeight(listVips);
                        break;
                    case REQUEST_COUSTOM_INFOR:
                         String icon= StringUtils.getJsonKeyStr(jsonstr,"icon");
                         String nickname= StringUtils.getJsonKeyStr(jsonstr,"nickName");
                         String score= StringUtils.getJsonKeyStr(jsonstr,"score");
                         String memberName= StringUtils.getJsonKeyStr(jsonstr,"memberName");
                         String expireTime= StringUtils.getJsonKeyStr(jsonstr,"expireTime");
                        GlidUtils.loadCircleImageView(mContext,icon,ivIcon);
                        tvUserName.setText(nickname);
                        tvScore.setText(String.format("我的积分:%S分",score));
                        MyLogUtils.i(TAG,"vip列表"+jsonstr);
                        if(StringUtils.isNotEmpty(memberName)){
                            tvUserVipState.setText("等级：普通会员");
                        }else{
                            tvUserVipState.setText("等级:普通用户");
                        }
                        if(StringUtils.isNotEmpty(expireTime)){
                            tvOvertime.setVisibility(View.VISIBLE);
                            tvOvertime.setText("有效期至："+expireTime.substring(0,expireTime.length()-3));
                        }
                        break;
                    case REQUEST_VER_CODE:
                        operateLimitFlag=false;
                        break;
                    case REQUEST_ODER_NUMBER:
                        submitOder=jsonstr;
                        loadDate(REQUEST_WX_APYPARAMS);
                        break;
                    case REQUEST_WX_APYPARAMS:
                        MyLogUtils.i(TAG, "WX下单数据:\r\n" + jsonstr);
                        WxPayNeedParams wxPayNeedParams = JSON.parseObject(jsonstr, WxPayNeedParams.class);
                        Tools.toWakeWXay(wxPayNeedParams,mContext,false);
                        break;

                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {
            isfristRequest=false;
            isOperateLimitFlag=false;

        }
    };

}
