package com.yun.software.shangcheng.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.yun.software.shangcheng.ui.adapter.GoodItemAdapter;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.entity.OderItemInfor;
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

public class ApplyReturnMoneyDetailActivity extends BaseActivity {
    public final static int REQUEST_RETURN_MONEY=0;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.list_order)
    NoScrollListView listOrder;
    @Bind(R.id.tv_oder_number)
    TextView tvOderNumber;
    @Bind(R.id.tv_shop_total_price)
    TextView tvShopTotalPrice;
    @Bind(R.id.tv_count_price)
    TextView tvCountPrice;
    @Bind(R.id.tv_all_oder_price)
    TextView tvAllOderPrice;
    @Bind(R.id.tv_aready_pay)
    TextView tvAreadyPay;
    @Bind(R.id.re_resone_one)
    RelativeLayout reResoneOne;
    @Bind(R.id.re_reson_two)
    RelativeLayout reResonTwo;
    @Bind(R.id.re_reson_thress)
    RelativeLayout reResonThress;
    @Bind(R.id.iv_reson_one)
    ImageView ivResonOne;
    @Bind(R.id.iv_reson_two)
    ImageView ivResonTwo;
    @Bind(R.id.iv_reson_thress)
    ImageView ivResonThress;
    @Bind(R.id.sc_view)
    ScrollView scView;
    @Bind(R.id.tv_apply_money)
    TextView tvApplyMoney;        
    OderItemInfor oderItemInfor;
    OderItemInfor.IndentBean indentBean;
    GoodItemAdapter goodItemAdapter;
    private String[] resons={"预约不上","商家营业但不待","其它理由"};
    private int resontTag=-1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_return_money;
    }

    @Override
    public void initPresenter() {
        tvTitle.setText("申请退货");
        goodItemAdapter = new GoodItemAdapter(mContext, null);
        listOrder.setAdapter(goodItemAdapter);
        Bundle bundle = getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if (bundle != null && bundle.containsKey("oderdetail")) {
            oderItemInfor = (OderItemInfor) bundle.getSerializable("oderdetail");
            setdefautlDate();
        }
    }
    public void loadDate(int what){
        try {
            switch (what){
                case REQUEST_RETURN_MONEY:
                    Map map=new HashMap();
                    map.put("indentId",oderItemInfor.getIndent().getId());
                    map.put("reason",resons[resontTag]);
                    request(REQUEST_RETURN_MONEY, ApiConstants.RETURN_MONEY, JSON.toJSONString(map), myhttpListener, false, false);
                    break;
            }

        }catch (Exception e){
            ExceptionUtil.handle(e);
        }
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            try {
                switch (what) {
                    case REQUEST_RETURN_MONEY:
                        EventBus.getDefault().post(new MessageEvent("tuihuanhuo"));
                        finish();

                        break;}
            }catch (Exception e){
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {

        }
    };
    @Override
    public void initView() {
    }

    public void setdefautlDate() {
        indentBean = oderItemInfor.getIndent();
        tvShopTotalPrice.setText("￥" + indentBean.getTotalPrice());
        tvCountPrice.setText("￥" + indentBean.getDiscusPay());
        tvAllOderPrice.setText("￥" + indentBean.getRealPay());
        tvAreadyPay.setText("￥" + indentBean.getRealPay());
        tvOderNumber.setText(indentBean.getOrderNo());
        goodItemAdapter.updateData(oderItemInfor.getLiInIn());
    }

    @Override
    public void addLisener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reResoneOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choice(0);
            }
        });
        reResonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choice(1);
            }
        });
        reResonThress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choice(2);
            }
        });
        tvApplyMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *申请退款
                 */
                loadDate(REQUEST_RETURN_MONEY);
            }
        });
    }

    public void Choice(int tag) {
        resontTag=tag;
        Tools.checkItem(false,ivResonOne);
        Tools.checkItem(false,ivResonTwo);
        Tools.checkItem(false,ivResonThress);
        switch (tag) {
            case 0:
                Tools.checkItem(true,ivResonOne);
                break;
            case 1:
                Tools.checkItem(true,ivResonTwo);
                break;
            case 2:
                Tools.checkItem(true,ivResonThress);
                break;
        }
        resontTag=tag;
    }
}
