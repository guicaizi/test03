package com.yun.software.shangcheng.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseFragment;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.RichText;
import com.yun.software.shangcheng.ui.activitys.AllOrderActivity;
import com.yun.software.shangcheng.ui.activitys.CommentActivity;
import com.yun.software.shangcheng.ui.activitys.MessageActivity;
import com.yun.software.shangcheng.ui.activitys.ScoreShoperActivity;
import com.yun.software.shangcheng.ui.activitys.SettingActivity;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ExceptionUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/6/26 14:41
 */

public class ShopMyFragment extends BaseFragment {
    public static final String TAG = "ShopMyFragment";
    private static final int REQUEST_INFOR = 0;
    private static final int REQUEST_ODER_STATE = 1;
    @Bind(R.id.lin_my)
    LinearLayout  linMy;
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
    /**
     * 待付款
     */
    @Bind(R.id.rt_center_pay)
    RichText rtCenterPay;
    @Bind(R.id.tv_pay_number)
    TextView tvPayNumber;
    /**
     * 待收货
     */
    @Bind(R.id.rt_center_send)
    RichText rtCenterSend;
    @Bind(R.id.tv_send_number)
    TextView tvSendNumber;
    /**
     * 待评论
     */
    @Bind(R.id.rt_center_comment)
    RichText rtCenterComment;
    @Bind(R.id.tv_comment_number)
    TextView tvCommentNumber;
    /**
     * 待收货
     */
    @Bind(R.id.rt_center_receive)
    RichText rtCenterReceive;
    @Bind(R.id.tv_receive_number)
    TextView tvReceiveNumber;
    /**
     * 全部订单
     */
    @Bind(R.id.rt_center_all)
    RichText rtCenterAll;
    @Bind(R.id.tv_all_number)
    TextView tvallNumber;
    @Bind(R.id.tv_over_time)
    TextView tvOvertime;
    @Bind(R.id.re_center_collect)
    RelativeLayout reCenterCollect;
    @Bind(R.id.re_center_scoreshoper)
    RelativeLayout reCenterScoreshoper;
    @Bind(R.id.re_center_coupon)
    RelativeLayout reCenterCoupon;
    @Bind(R.id.re_center_addressManager)
    RelativeLayout reCenterAddressManager;
    @Bind(R.id.re_center_about)
    RelativeLayout reCenterAbout;
    @Bind(R.id.re_center_message)
    RelativeLayout reCenterMessage;
    private boolean isfrsitcomin=true;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_shop_my;
    }

    @Override
    public void setDate() {
        tvTitle.setText("个人中心");
        ivBack.setVisibility(View.GONE);
        createLoadingview(linMy);

    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        loadDate(REQUEST_INFOR);
        loadDate(REQUEST_ODER_STATE);
    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_INFOR:
                    Map map = new HashMap();
                    map.put("customerId",biz.getCustomToken());
                    request(REQUEST_INFOR, ApiConstants.USERINFORTWO,JSON.toJSONString(map), myhttpListener, false, isfrsitcomin);
                    isfrsitcomin=false;
                    break;
                case REQUEST_ODER_STATE:
                    Map map1 = new HashMap();
                    map1.put("userId", biz.getCustomToken());
                    request(REQUEST_ODER_STATE, ApiConstants.ODER_STATE, JSON.toJSONString(map1), myhttpListener, false, false);
                    break;
            }

        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            switch (what) {
                case REQUEST_INFOR:
                    String icon= StringUtils.getJsonKeyStr(jsonstr,"icon");
                    String nickname= StringUtils.getJsonKeyStr(jsonstr,"nickName");
                    String score= StringUtils.getJsonKeyStr(jsonstr,"score");
                    String memberName= StringUtils.getJsonKeyStr(jsonstr,"memberName");
                    String expireTime= StringUtils.getJsonKeyStr(jsonstr,"expireTime");
                    GlidUtils.loadCircleImageView(mContext,icon,ivIcon);
                    tvUserName.setText(nickname);
                    biz.setScore(score);
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
                case REQUEST_ODER_STATE:
                    Map<String, String> map = JSON.parseObject(jsonstr, new TypeReference<Map<String, String>>() {
                    });
                    //待付款
                    int pendingPayment = StringUtils.toInt(map.get("pendingPayment"));
                    if (pendingPayment > 0) {
                        tvPayNumber.setVisibility(View.VISIBLE);
                        tvPayNumber.setText(pendingPayment + "");
                    } else {
                        tvPayNumber.setVisibility(View.GONE);
                    }
                    //待发货
                    int pendingDelivery = StringUtils.toInt(map.get("pendingDelivery"));
                    if (pendingDelivery > 0) {
                        tvSendNumber.setVisibility(View.VISIBLE);
                        tvSendNumber.setText(pendingDelivery + "");
                    } else {
                        tvSendNumber.setVisibility(View.GONE);
                    }

                    // 待收货
                    int takeDelivery = StringUtils.toInt(map.get("takeDelivery"));
                    if (takeDelivery > 0) {
                        tvReceiveNumber.setVisibility(View.VISIBLE);
                        tvReceiveNumber.setText(takeDelivery + "");
                    } else {
                        tvReceiveNumber.setVisibility(View.GONE);
                    }
                    //退换货
                    int pendingComment = StringUtils.toInt(map.get("returns"));
                    if (pendingComment > 0) {
                        tvCommentNumber.setVisibility(View.VISIBLE);
                        tvCommentNumber.setText(pendingComment + "");
                    } else {
                        tvCommentNumber.setVisibility(View.GONE);
                    }
                    // 全部
                    int total = StringUtils.toInt(map.get("finished"));
                    if (total > 0) {
                        tvallNumber.setVisibility(View.VISIBLE);
                        tvallNumber.setText(total + "");
                    } else {
                        tvallNumber.setVisibility(View.GONE);
                    }
                    break;
            }
        }
        @Override
        public void onFailed(int what, String jsonstr) {


        }
    };

    @Override
    protected void addLisener() {
        reCenterCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CommentActivity.class).putExtra(CommentActivity.TYPE_KEY, 0));

            }
        });
        reCenterScoreshoper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                enterPage(Modle.class);
                //enterPage(LoginActivity.class);
                enterPage(ScoreShoperActivity.class);
            }
        });
        reCenterCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(mContext, CommentActivity.class).putExtra(CommentActivity.TYPE_KEY, 1));
            }
        });
        reCenterAddressManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPage(SettingActivity.class);
            }
        });

        rtCenterPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("orderid", 0);
                enterPage(AllOrderActivity.class, bundle);
            }
        });
        rtCenterSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("orderid", 1);
                enterPage(AllOrderActivity.class, bundle);
            }
        });
        rtCenterReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("orderid", 2);
                enterPage(AllOrderActivity.class, bundle);
            }
        });
        rtCenterComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("orderid", 3);
                enterPage(AllOrderActivity.class, bundle);
            }
        });
        rtCenterAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("orderid", 4);
                enterPage(AllOrderActivity.class, bundle);
            }
        });
        reCenterMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                enterPage(MessageActivity.class, bundle);
            }
        });
    }
}



