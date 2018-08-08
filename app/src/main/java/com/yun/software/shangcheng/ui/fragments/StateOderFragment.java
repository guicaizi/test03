package com.yun.software.shangcheng.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseFragment;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.UIAlertView;
import com.yun.software.shangcheng.ui.activitys.ApplyReturnMoneyDetailActivity;
import com.yun.software.shangcheng.ui.activitys.OrderDetailDescript;
import com.yun.software.shangcheng.ui.adapter.ListStateoderAdapter;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.entity.OderItemInfor;
import com.yun.software.shangcheng.ui.entity.WxPayNeedParams;
import com.yun.software.shangcheng.ui.utils.ListViewEmptyUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/17 11:32
 */

public class StateOderFragment extends BaseFragment {
     public static final  String TAG="StateOderFragment";
    private static final int REQUEST_GET_ODERS = 1;
    private static final int REQUEST_RECIVE_GOODS =2;
    private static final int REQUEST_CANCLE_ORDER =3;
    private static final int REQUEST_SUBMIT_WX_PAY =4;
    @Bind(R.id.my_list_oder)
    PullToRefreshListView myListOder;
    @Bind(R.id.re_nodate)
    RelativeLayout reNodate;
    ListStateoderAdapter stateoderAdapter;
    String type = "0";
    private int pageNumber;
    private List<OderItemInfor> oderItemInfors;
    private boolean isMoreNew=false;
    private ListViewEmptyUtils emptyUtils;
    private int tempostion=0;
    private boolean isFirstRequest=true;
    private boolean limitOpreation=false;
    @Override
    protected int getLayoutResource() {
        return R.layout.frament_state_order;
    }

    @Override
    public void setDate() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("type")) {
                type = bundle.getString("type");
            }
        }
        oderItemInfors = new ArrayList<>();
        stateoderAdapter = new ListStateoderAdapter(mContext, oderItemInfors);
        myListOder.setAdapter(stateoderAdapter);
        createLoadingview(myListOder.getRefreshableView());
        emptyUtils = new ListViewEmptyUtils(mContext, myListOder);
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        pageNumber=1;
        loadDate(REQUEST_GET_ODERS);
    }


    @Override
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        super.onMessageEventMainThread(messageEvent);
        limitOpreation=false;
        if(messageEvent.getMessage().equals("refresh_oder_list")){
            if(type.equals("2")){
                pageNumber=1;
                loadDate(REQUEST_GET_ODERS);
            }
        }else if (messageEvent.getMessage().equals("paydismiss")) {
            stopProgressDialog();


        } else if (messageEvent.getMessage().equals("paycancle")) {
            showLongToast("交易取消");
        } else if (messageEvent.getMessage().equals("paysuccess")) {
            if(type.equals("2")){
                pageNumber=1;
                loadDate(REQUEST_GET_ODERS);
            }

        } else if (messageEvent.getMessage().equals("payerror")) {
            showLongToast("交易失败");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(limitOpreation){
            stopProgressDialog();
        }
    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_GET_ODERS:
                    Map map00 = new HashMap();
                    map00.put("type", type);
                    map00.put("userId", biz.getCustomToken());
                    Map map0 = new HashMap();
                    map0.put("pageNumber", pageNumber);
                    map0.put("pageSize", "10");
                    map0.put("param", map00);
                    request(REQUEST_GET_ODERS, ApiConstants.GOOD_ODER_LIST, JSON.toJSONString(map0), myhttpListener, false, isFirstRequest);
                    isFirstRequest=false;
                    break;
                case REQUEST_RECIVE_GOODS:
                    Map map = new HashMap();
                    map.put("id", oderItemInfors.get(tempostion).getIndent().getId());
                    request(REQUEST_RECIVE_GOODS, ApiConstants.GOOD_RECIVE_STATE, JSON.toJSONString(map), myhttpListener, false, false);
                    break;
                case REQUEST_CANCLE_ORDER:
                    Map map3 = new HashMap();
                    map3.put("id", oderItemInfors.get(tempostion).getIndent().getId());
                    request(REQUEST_CANCLE_ORDER, ApiConstants.GOOD_CANCLE_ORDER, JSON.toJSONString(map3), myhttpListener, false, false);
                    break;
                case REQUEST_SUBMIT_WX_PAY:
                    requestGet(REQUEST_SUBMIT_WX_PAY, ApiConstants.GOOD_SUBMIT_WX_PAY + "?outTradeNo=" +  oderItemInfors.get(tempostion).getIndent().getId(), null, myhttpListener, false, false);
                    break;
            }
        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            myListOder.onRefreshComplete();
            try {
                switch (what) {
                    case REQUEST_GET_ODERS:
                        if (pageNumber == 1) {
                            oderItemInfors.clear();
                        }
                        List<OderItemInfor> lists = JSON.parseObject(jsonstr, new TypeReference<List<OderItemInfor>>() {
                        });

                        if(lists!=null&&lists.size()>0){
                            if(lists.size()==10){
                                isMoreNew=true;
                            }else{
                                isMoreNew=false;
                            }
                            oderItemInfors.addAll(lists);
                        }
                        stateoderAdapter.updateData(oderItemInfors);
                        if(oderItemInfors.size()==0){
                            emptyUtils.setEmptyTextAndImage("暂无订单", R.drawable.no_data);
                        }

                        break;
                    case REQUEST_RECIVE_GOODS:
                        pageNumber=1;
                        isMoreNew=false;
                        loadDate(REQUEST_GET_ODERS);

                        break;
                    case REQUEST_CANCLE_ORDER:
                        pageNumber=1;
                        isMoreNew=false;
                        loadDate(REQUEST_GET_ODERS);
                        break;
                    case REQUEST_SUBMIT_WX_PAY:
                        MyLogUtils.i(TAG, "WX下单数据:\r\n" + jsonstr);
                        WxPayNeedParams wxPayNeedParams = JSON.parseObject(jsonstr, WxPayNeedParams.class);
                        Tools.toWakeWXay(wxPayNeedParams,mContext);
                        break;
                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {
            limitOpreation=false;
            myListOder.onRefreshComplete();
        }
    };

    @Override
    protected void addLisener() {
        myListOder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OderItemInfor oderItemInfor = oderItemInfors.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("frompage",type);
                bundle.putSerializable("oderdetail", oderItemInfor);
                enterPage(OrderDetailDescript.class, bundle);
            }
        });
        myListOder.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNumber = 1;
                isMoreNew = true;
                loadDate(REQUEST_GET_ODERS);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (isMoreNew) {
                    // 联网加载数据
                    pageNumber++;
                    loadDate(REQUEST_GET_ODERS);
                } else {
//                    Tools.showInfo(mContext, R.string.no_more);
                    showShortToast(R.string.no_more);
                    myListOder.onRefreshComplete();
                }

            }
        });
        stateoderAdapter.setCommentListener(new ListStateoderAdapter.CommentListener() {
            @Override
            public void itemPay(int position) {
                if(limitOpreation){
                    showShortToast("正在支付请稍后...");
                    return;
                }
                tempostion=position;
                limitOpreation=true;
                loadDate(REQUEST_SUBMIT_WX_PAY);
            }

            @Override
            public void itemCancle(int position) {
                tempostion=position;
                showDelDialog("您确定取消该条订单吗?", REQUEST_CANCLE_ORDER);
            }

            @Override
            public void itemclick(int shopposition, int goodposition) {

            }
            @Override
            public void itemSure(int position) {
                tempostion=position;
                showDelDialog("确认收货?", REQUEST_RECIVE_GOODS);
            }
            /**
             * 退款 退货
             */
            @Override
            public void itemBack(int position) {
                OderItemInfor oderItemInfor = oderItemInfors.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("oderdetail", oderItemInfor);
                enterPage(ApplyReturnMoneyDetailActivity.class, bundle);
            }
            @Override
            public void itemComment(int position) {

            }
        });

    }

    private void showDelDialog(String des, final int showType) {
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
                                           if (showType == REQUEST_CANCLE_ORDER) {
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
