package com.yun.software.shangcheng.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mylhyl.circledialog.CircleDialog;
import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.NoScrollListView;
import com.yun.software.shangcheng.ui.ViewWidget.popView.ButtomDialog;
import com.yun.software.shangcheng.ui.adapter.CouponAdapterdetail;
import com.yun.software.shangcheng.ui.adapter.GoodCarAdapter;
import com.yun.software.shangcheng.ui.entity.Address;
import com.yun.software.shangcheng.ui.entity.CouponEntity;
import com.yun.software.shangcheng.ui.entity.GoodCarItem;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.entity.WxPayNeedParams;
import com.yun.software.shangcheng.ui.utils.DecimalUtil;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/13 15:29
 */

public class CommitOrderActivity extends BaseActivity {
    public static final String TAG = "CommitOrderActivity";
    private static final int REQUEST_ADDRESS = 1;
    private static final int REQUEST_GET_CIMMMIT_OREDER = 2;
    private static final int REQUEST_SUBMIT_ODERS = 3;
    private static final int REQUEST_GET_COUPON = 4;
    private static final int REQUEST_SUBMIT_SCORE_PAY = 5;
    private static final int REQUEST_SUBMIT_WX_PAY = 6;
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
    @Bind(R.id.tv_platfrom_coupon)
    TextView tvPlatfromCoupon;
    @Bind(R.id.re_platfrom_coupon)
    RelativeLayout rePlatfromCoupon;
    @Bind(R.id.v_switch_1)
    Switch vSwitch1;
    @Bind(R.id.sc_view)
    ScrollView scView;
    @Bind(R.id.tvCountMoney)
    TextView tvCountMoney;
    @Bind(R.id.btnSettle)
    TextView btnSettle;
    @Bind(R.id.rlBottomBar)
    RelativeLayout rlBottomBar;
    @Bind(R.id.tv_goods_total_money)
    TextView tvItemTotalMoney;
    @Bind(R.id.tv_total_goods)
    TextView tvItemTotalgoods;
    @Bind(R.id.et_leave)
    TextView etLeave;
    List<String> choiceids;
    GoodCarAdapter goodCarAdapter;
    List<GoodCarItem> carItemslist;
    private Address deFaultAddress;
    private List<CouponEntity> couponlist;
    private ButtomDialog buttomDialog;
    private String totalMoney;
    private String choiceCouponMoney;
    private String SubmitMoney;
    private String couponid = "";
    private int frompage = 0;
    private String sumitScoreOderid;
    private boolean limitOptions=false;
    private int  isAnonymous=0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm;
    }


    @Override
    public void initPresenter() {
        Bundle bundle = getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if (bundle != null) {
            if (bundle.containsKey("ids")) {
                choiceids = bundle.getStringArrayList("ids");
                MyLogUtils.i("CommitOrderActivity", "传过来id" + JSON.toJSONString(choiceids));
            }
            if (bundle.containsKey("frompage")) {
                frompage = bundle.getInt("frompage", 0);
            }
        }
        tvTitle.setText("确认订单");
        carItemslist = new ArrayList<>();
        couponlist = new ArrayList<CouponEntity>();
        goodCarAdapter = new GoodCarAdapter(mContext, carItemslist, frompage);
        goodCarAdapter.setIsshowredChoice(false);
        listOrder.setAdapter(goodCarAdapter);
        loadDate(REQUEST_ADDRESS);
        loadDate(REQUEST_GET_CIMMMIT_OREDER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(limitOptions){
            showLongToast("支付失败");
            finish();
        }
    }

    public void onMessageEventMainThread(MessageEvent messageEvent) {
        limitOptions=false;
        if (messageEvent.getMessage().equals("paydismiss")) {
            stopProgressDialog();


        } else if (messageEvent.getMessage().equals("paycancle")) {
            showLongToast("交易取消");
            Bundle bundle = new Bundle();
            bundle.putInt("orderid", 0);
            enterPage(AllOrderActivity.class, bundle);
            finish();

        } else if (messageEvent.getMessage().equals("paysuccess")) {
            MyLogUtils.i("WX下单数据", "支付成功");
            Bundle bundle = new Bundle();
            bundle.putInt("orderid", 1);
            enterPage(AllOrderActivity.class, bundle);
            finish();

        } else if (messageEvent.getMessage().equals("payerror")) {
            MyLogUtils.i("WX下单数据", "支付错误");
            finish();
        }
    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_ADDRESS:
                    Map map = new HashMap();
                    map.put("userId", biz.getCustomToken());
                    request(REQUEST_ADDRESS, ApiConstants.GOOD_DEFAULT_ADDRESS, JSON.toJSONString(map), myhttpListener, false, false);
                    break;
                case REQUEST_GET_CIMMMIT_OREDER:
                    Map map2 = new HashMap();
                    map2.put("ids", choiceids);
                    map2.put("userId", biz.getCustomToken());
                    request(REQUEST_GET_CIMMMIT_OREDER, ApiConstants.GOOD_COMMIT_ORDER, JSON.toJSONString(map2), myhttpListener, false, false);
                    break;
                /**
                 *{
                 "ids": [
                 "27eabcfeb6ca445fa7a60d39510d9ebd"
                 ],
                 "userId": "a8c953fba4a845679b32ab980df2c070",
                 "userRealName": "遇见感觉",
                 "transportName": "刘领取",
                 "transportPhone": "17671689546",
                 "transportAddress": "北京市 东城区西二旗",
                 "buyerMsg": "",
                 "isAnonymous": "0",
                 "couponId": ""
                 }
                 */
                case REQUEST_SUBMIT_ODERS:
                    if(StringUtils.isEmpty(tvConfirmAddress.getText().toString())){
                        ShowDialog();
                        limitOptions=false;
                        return;
                    }
                    Map map3 = new HashMap();
                    map3.put("ids", choiceids);
                    map3.put("userId", biz.getCustomToken());
                    map3.put("userRealName", biz.getUserName());
                    map3.put("transportName", deFaultAddress.getPersonName());
                    map3.put("transportPhone", deFaultAddress.getPhone());
                    map3.put("transportAddress", tvConfirmAddress.getText().toString());
                    map3.put("buyerMsg", etLeave.getText().toString());
                    map3.put("isAnonymous", isAnonymous);
                    map3.put("couponId", couponid);
                    request(REQUEST_SUBMIT_ODERS, ApiConstants.GOOD_SUBMIT_ODERS, JSON.toJSONString(map3), myhttpListener, false, false);
                    break;
                case REQUEST_GET_COUPON:
                    Map map0 = new HashMap();
                    Map map00 = new HashMap();
                    map0.put("pageSize", "100");
                    map0.put("pageNumber", "1");
                    map00.put("userId", biz.getCustomToken());
                    map00.put("orderField", "coupon");
                    map00.put("orderRule", "desc");
                    map00.put("totalPrice", totalMoney);
                    map0.put("param", map00);
                    request(REQUEST_GET_COUPON, ApiConstants.GOOD_MY_COUPON, StringUtils.commbingJson(map0, map00), myhttpListener, false, false);
                    break;
                case REQUEST_SUBMIT_SCORE_PAY:
                    Map map5 = new HashMap();
                    map5.put("userId", biz.getCustomToken());
                    map5.put("orderId", sumitScoreOderid);
                    request(REQUEST_SUBMIT_SCORE_PAY, ApiConstants.GOOD_SUBMIT_SCORE_PAY, JSON.toJSONString(map5), myhttpListener, false, false);
                    break;
                case REQUEST_SUBMIT_WX_PAY:
                    requestGet(REQUEST_SUBMIT_WX_PAY, ApiConstants.GOOD_SUBMIT_WX_PAY + "?outTradeNo=" + sumitScoreOderid, null, myhttpListener, false, false);
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
                    case REQUEST_ADDRESS:
                        MyLogUtils.i("jsonstr=success==默认地址", jsonstr);
                        deFaultAddress = JSON.parseObject(jsonstr, Address.class);
                        if (deFaultAddress.getStatus() == 1) {
                            tvConfirmName.setText("收货人：" + deFaultAddress.getPersonName());
                            tvConfirmAddress.setText(StringUtils.getAddress(deFaultAddress.getAreaCode(), deFaultAddress.getAddress()));
                            tvConfirmPhone.setText(deFaultAddress.getPhone());
                        } else {
                             ShowDialog();
                        }
                        break;
                    case REQUEST_GET_CIMMMIT_OREDER:
                        List<GoodCarItem> goodCarItems = JSON.parseObject(StringUtils.getJsonListStr(jsonstr, "detail"), new TypeReference<List<GoodCarItem>>() {
                        });
                        goodCarAdapter.updateData(goodCarItems);
                        totalMoney = StringUtils.getJsonListStr(jsonstr, "totalPrice");
                        if (frompage == 0) {
                            tvCountMoney.setText(StringUtils.formatMoney("合计：￥%s", totalMoney));
                        } else {
                            tvCountMoney.setText(StringUtils.formatMoney("合计消耗：%s分", totalMoney));
                        }
                        SubmitMoney = totalMoney;
                        tvItemTotalMoney.setText(StringUtils.formatMoney(null, totalMoney));
                        tvItemTotalgoods.setText(String.format("共%S件商品", goodCarItems.size()));
                        if (frompage == 0) {
                            loadDate(REQUEST_GET_COUPON);
                        }
                        break;
                    case REQUEST_GET_COUPON:
                        MyLogUtils.i(TAG, "优惠券数据:\r\n" + jsonstr);
                        handlerReciveCoupon(jsonstr);
                        break;
                    case REQUEST_SUBMIT_ODERS:
                        MyLogUtils.i(TAG, "提交订单数据:\r\n" + jsonstr);
                        sumitScoreOderid = jsonstr;
                        if (frompage == 0) {
                            loadDate(REQUEST_SUBMIT_WX_PAY);
                            //                            finish();
                        } else {
                            loadDate(REQUEST_SUBMIT_SCORE_PAY);

                        }
                        break;
                    case REQUEST_SUBMIT_SCORE_PAY:
                        MyLogUtils.i(TAG, "下单数据:\r\n" + jsonstr);

                        Bundle bundle = new Bundle();
                        bundle.putInt("orderid", 1);
                        enterPage(AllOrderActivity.class, bundle);
                        finish();
                        break;
                    case REQUEST_SUBMIT_WX_PAY:
                        MyLogUtils.i(TAG, "WX下单数据:\r\n" + jsonstr);
                        WxPayNeedParams wxPayNeedParams = JSON.parseObject(jsonstr, WxPayNeedParams.class);
                        MyLogUtils.i(TAG, "WX下单数据:\r\n" + wxPayNeedParams.toString());
                        Tools.toWakeWXay(wxPayNeedParams,CommitOrderActivity.this,false);
                        break;


                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {
            if(what==REQUEST_SUBMIT_ODERS||what==REQUEST_SUBMIT_WX_PAY){
                stopProgressDialog();
                limitOptions=false;
                finish();
            }

            MyLogUtils.i("jsonstr==f=默认地址", jsonstr);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle bundle = data.getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
            MyLogUtils.i("确认订单", "收到结果");
            if (bundle != null) {
                if (bundle.containsKey("address")) {
                    deFaultAddress = (Address) bundle.getSerializable("address");
                    MyLogUtils.i("确认订单", "address" + deFaultAddress.getAreaCode());
                    tvConfirmName.setText("收货人：" + deFaultAddress.getPersonName());
                    tvConfirmAddress.setText(StringUtils.getAddress(deFaultAddress.getAreaCode(), deFaultAddress.getAddress()));
                    tvConfirmPhone.setText(deFaultAddress.getPhone());
                }
            }
        } else {
            loadDate(REQUEST_ADDRESS);
        }

        //        MyLogUtils.i("返回值", );

    }

    @Override
    public void initView() {

    }

    /**
     * 我的优惠券
     */
    protected void handlerReciveCoupon(String jsonstr) {
        if (StringUtils.isEmpty(jsonstr)) {
            return;
        }
        List<CouponEntity> lists = JSON.parseObject(StringUtils.getJsonListStr(jsonstr, "list"), new TypeReference<List<CouponEntity>>() {
        });
        couponlist.addAll(lists);
        if (couponlist.size() > 0) {
            rePlatfromCoupon.setVisibility(View.VISIBLE);
            tvPlatfromCoupon.setText("-" + lists.get(0).getCoupon());
            choiceCouponMoney = lists.get(0).getCoupon().substring(1);
            couponid=lists.get(0).getId();
            MyLogUtils.i("kankan", "优费券金额" + choiceCouponMoney);
            SubmitMoney = DecimalUtil.subtract(totalMoney, choiceCouponMoney);
            tvCountMoney.setText(StringUtils.formatMoney("合计：￥%s", SubmitMoney));

        } else {
            rePlatfromCoupon.setVisibility(View.GONE);
        }

    }

    @Override
    public void addLisener() {
        reChoiceAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("frompage", 1);
                enterPageForResult(ManagerAddress.class, bundle, 22);
            }
        });
        rePlatfromCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initShowCoupon();
            }
        });
        btnSettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(limitOptions){
                    showShortToast("正在支付中...");
                    return;
                }
                limitOptions=true;
                startProgressDialog("正在支付....");
                loadDate(REQUEST_SUBMIT_ODERS);
            }
        });
        vSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //选中时 do some thing
                    isAnonymous=0;
                } else {
                    isAnonymous=1;
                    //非选中时 do some thing
                }

            }
        });

    }

    public void initShowCoupon() {
        if (buttomDialog == null)
            buttomDialog = new ButtomDialog(mContext);
        buttomDialog.setViewListener(new ButtomDialog.ViewListener() {
            @Override
            public void bindView(View v) {
                initdialogView(v);
            }
        }).setContentView(R.layout.chocie_coupon);
        buttomDialog.show();
    }

    private CouponAdapterdetail coupontypeAdater;
    private CouponEntity choiceCoupon;

    private void initdialogView(View v) {
        try {
            coupontypeAdater = new CouponAdapterdetail(mContext, couponlist, CouponAdapterdetail.USE_COUPON);
            NoScrollListView listViewone = (NoScrollListView) v.findViewById(R.id.ls_coupon_type);
            TextView tvComplete = (TextView) v.findViewById(R.id.tv_complete);
            listViewone.setAdapter(coupontypeAdater);
            tvComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttomDialog.dismiss();
                }
            });
            coupontypeAdater.setAcceptLisener(new CouponAdapterdetail.AcceptLisener() {
                @Override
                public void clickAccept(int position) {
                    SubmitMoney = totalMoney;
                    choiceCoupon = couponlist.get(position);
                    tvPlatfromCoupon.setText("-" + choiceCoupon.getCoupon());
                    choiceCouponMoney = choiceCoupon.getCoupon().substring(1);
                    MyLogUtils.i("kankan", "优费券金额" + choiceCouponMoney);
                    SubmitMoney = DecimalUtil.subtract(totalMoney, choiceCouponMoney);
                    tvCountMoney.setText(StringUtils.formatMoney("合计：￥%s", SubmitMoney));
                    couponid = choiceCoupon.getId();
                    buttomDialog.dismiss();
                }
            });
        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }
    private void ShowDialog(){
        new CircleDialog.Builder()
                .setCanceledOnTouchOutside(false)
                .setTitle("提示")
                .setWidth(0.7f)
                .setMaxHeight(0.3f)
                .setText("暂无收货地址,请前往添加?")
                .setPositive("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("frompage", 1);
                        enterPageForResult(ManagerAddress.class, bundle, 22);
                    }
                })
                .show(getSupportFragmentManager());
    }

}
