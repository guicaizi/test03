package com.yun.software.shangcheng.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.NoScrollListView;
import com.yun.software.shangcheng.ui.ViewWidget.UIAlertView;
import com.yun.software.shangcheng.ui.adapter.GoodItemAdapter;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.entity.OderItemInfor;
import com.yun.software.shangcheng.ui.entity.WxPayNeedParams;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/17 15:18
 */

public class OrderDetailDescript extends BaseActivity {
     public static final  String TAG="OrderDetailDescript";
    private static final int REQUEST_CANCLE_ORDER = 1;
    private static final int REQUEST_RECIVE_GOODS = 2;
    private static final int REQUEST_SUBMIT_WX_PAY =3;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_confirm_name)
    TextView tvConfirmName;
    @Bind(R.id.tv_confirm_phone)
    TextView tvConfirmPhone;
    @Bind(R.id.tv_confirm_address)
    TextView tvConfirmAddress;
    @Bind(R.id.re_choice_address)
    RelativeLayout reChoiceAddress;
    @Bind(R.id.list_order)
    NoScrollListView listOrder;
    @Bind(R.id.tv_tag)
    TextView tvTag;
    @Bind(R.id.tv_leave)
    TextView tvLeave;
    @Bind(R.id.tv_shop_total_price)
    TextView tvShopTotalPrice;
    @Bind(R.id.tv_count_price)
    TextView tvCountPrice;
    @Bind(R.id.tv_all_oder_price)
    TextView tvAllOderPrice;
    @Bind(R.id.tv_aready_pay)
    TextView tvAreadyPay;
    @Bind(R.id.tv_oder_number)
    TextView tvOderNumber;
    @Bind(R.id.tv_creattime)
    TextView tvCreattime;
    @Bind(R.id.sc_view)
    ScrollView scView;
    @Bind(R.id.img_left_tag)
    ImageView imgLeftTag;
    @Bind(R.id.tv_state_order_cacle)
    TextView tvStateOrderCacle;
    @Bind(R.id.tv_state_order_sure)
    TextView tvStateOrderSure;
    @Bind(R.id.tv_state_order_contact)
    TextView tvStateOrderContact;
    @Bind(R.id.tv_state_order_pay)
    TextView tvStateOrderPay;
    @Bind(R.id.tv_apply_return_money)
    TextView tvApplyReturnMoney;
    @Bind(R.id.re_bottom_detail)
    LinearLayout reBottomDetail;
    @Bind(R.id.tv_pay_state)
    TextView tvpayState;
    OderItemInfor oderItemInfor;
    OderItemInfor.IndentBean indentBean;
    GoodItemAdapter goodItemAdapter;
    private boolean limitOpreation=false;
    private String type;
    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_sure;
    }

    @Override
    public void initPresenter() {
        goodItemAdapter = new GoodItemAdapter(mContext, null);
        listOrder.setAdapter(goodItemAdapter);
        Bundle bundle = getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if (bundle != null && bundle.containsKey("oderdetail")) {
            oderItemInfor = (OderItemInfor) bundle.getSerializable("oderdetail");
            type =  bundle.getString("frompage");
            setdefautlDate();
        }
        tvTitle.setText("确认订单");


    }
    @Override
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        super.onMessageEventMainThread(messageEvent);
        limitOpreation=false;
         if (messageEvent.getMessage().equals("paydismiss")) {
            stopProgressDialog();
        } else if (messageEvent.getMessage().equals("paycancle")) {
            showLongToast("交易取消");
        } else if (messageEvent.getMessage().equals("paysuccess")) {
             showLongToast("交易成功");
             finish();
        } else if (messageEvent.getMessage().equals("payerror")) {
            showLongToast("交易失败");
        } else if(messageEvent.getMessage().equals("tuihuanhuo")){
             finish();
         }
    }
    @Override
    public void initView() {

    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_CANCLE_ORDER:
                    Map map = new HashMap();
                    map.put("id", oderItemInfor.getIndent().getId());
                    request(REQUEST_CANCLE_ORDER, ApiConstants.GOOD_CANCLE_ORDER, JSON.toJSONString(map), myhttpListener, false, false);
                    break;
                case REQUEST_RECIVE_GOODS:
                    Map map2 = new HashMap();
                    map2.put("id", oderItemInfor.getIndent().getId());
                    request(REQUEST_RECIVE_GOODS, ApiConstants.GOOD_RECIVE_STATE, JSON.toJSONString(map2), myhttpListener, false, false);
                    break;
                case REQUEST_SUBMIT_WX_PAY:
                    requestGet(REQUEST_SUBMIT_WX_PAY, ApiConstants.GOOD_SUBMIT_WX_PAY + "?outTradeNo=" + oderItemInfor.getIndent().getId(), null, myhttpListener, false, false);
                    break;
            }

        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            limitOpreation=false;
            try {
                switch (what) {
                    case REQUEST_CANCLE_ORDER:
                        finish();
                        EventBus.getDefault().post(new MessageEvent("refresh_oder_list"));
                        break;
                    case REQUEST_RECIVE_GOODS:
                        finish();
                        EventBus.getDefault().post(new MessageEvent("refresh_oder_list"));
                        break;
                    case REQUEST_SUBMIT_WX_PAY:
                        MyLogUtils.i(TAG, "WX下单数据:\r\n" + jsonstr);
                        WxPayNeedParams wxPayNeedParams = JSON.parseObject(jsonstr, WxPayNeedParams.class);
                        Tools.toWakeWXay(wxPayNeedParams,OrderDetailDescript.this);
                    break;
                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {
               limitOpreation=false;
        }
    };


    public void setdefautlDate() {
        indentBean = oderItemInfor.getIndent();
        tvConfirmAddress.setText(indentBean.getTransportAddress());
        tvConfirmName.setText(indentBean.getTransportName());
        tvConfirmPhone.setText(indentBean.getTransportPhone());
        if (StringUtils.isNotEmpty(indentBean.getBuyerMsg())) {
            tvLeave.setText(indentBean.getBuyerMsg());
        }
        if (indentBean.getIndentStatus() == 0) {
            tvpayState.setText("需付款");
        } else {
            tvpayState.setText("已付款");
        }
        if (indentBean.getType() == 1) {
            tvShopTotalPrice.setText("￥" + indentBean.getTotalPrice());
            tvCountPrice.setText("￥" + indentBean.getDiscusPay());
            tvAllOderPrice.setText("￥" + indentBean.getRealPay());
            tvAreadyPay.setText("￥" + indentBean.getRealPay());
        } else {
            tvShopTotalPrice.setText(indentBean.getTotalPrice() + "分");
            tvCountPrice.setText(indentBean.getDiscusPay() + "分");
            tvAllOderPrice.setText(indentBean.getRealPay() + "分");
            tvAreadyPay.setText(indentBean.getRealPay() + "分");
        }

        tvOderNumber.setText(indentBean.getOrderNo());
        tvCreattime.setText(StringUtils.formateTime(indentBean.getCreateDate()));
        goodItemAdapter.updateData(oderItemInfor.getLiInIn());
        int state = oderItemInfor.getIndent().getIndentStatus();
        if(type.equals("1")){
            //已完成
            return;
        }else if(type.equals("2")){
            //待付款
            tvStateOrderCacle.setVisibility(View.VISIBLE);
            tvStateOrderPay.setVisibility(View.VISIBLE);
        }else if(type.equals("3")){
            //待发货
            if (indentBean.getTransportStatus().equals("0")) {
                if (indentBean.getType() == 1) {
                    tvApplyReturnMoney.setVisibility(View.VISIBLE);
                }

            }

        }else if(type.equals("4")){
            //已经发货
            if (indentBean.getType() == 1) {
                tvApplyReturnMoney.setVisibility(View.VISIBLE);
            }
            tvStateOrderSure.setVisibility(View.VISIBLE);
        }else if(type.equals("5")){
            return;
        }
    }

    @Override
    public void addLisener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvApplyReturnMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("oderdetail", oderItemInfor);
                enterPage(ApplyReturnMoneyDetailActivity.class, bundle);

            }
        });
        tvStateOrderCacle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDelDialog("您确定取消该条订单吗?", REQUEST_CANCLE_ORDER);
            }
        });
        tvStateOrderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDelDialog("确认收货?", REQUEST_RECIVE_GOODS);
            }
        });
        tvStateOrderPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(limitOpreation){
                    showShortToast("正在支付请稍后...");
                    return;
                }
                limitOpreation=true;
                loadDate(REQUEST_SUBMIT_WX_PAY);
            }
        });

    }

    private void showDelDialog(String des, final int type) {
        final UIAlertView delDialog = new UIAlertView(mContext, null, des,
                "取消", "确定");
        delDialog.show();
        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {

                                       @Override
                                       public void doLeft() {
                                           delDialog.dismiss();
                                       }

                                       @Override
                                       public void doRight() {
                                           if (type == REQUEST_CANCLE_ORDER) {
                                               loadDate(REQUEST_CANCLE_ORDER);
                                           } else {
                                               loadDate(REQUEST_RECIVE_GOODS);
                                           }

                                           delDialog.dismiss();
                                       }
                                   }
        );
    }
}
