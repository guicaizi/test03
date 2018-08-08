package com.yun.software.shangcheng.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseFragment;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.activitys.SearchShoperActivity;
import com.yun.software.shangcheng.ui.adapter.CouponAdapter;
import com.yun.software.shangcheng.ui.entity.CouponEntity;
import com.yun.software.shangcheng.ui.utils.ListViewEmptyUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/4 13:58
 */

public class MyCoupotionsFramgent extends BaseFragment {
    public static final String TAG = "GoodCollectionFramgent";
    private static final int REQUEST_GET_COUPON = 1;
    Map<String, Object> handleproducts;
    @Bind(R.id.my_list)
    PullToRefreshListView myCollectList;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    private List<CouponEntity> couponlist;
    CouponAdapter couponadapter;
    // 是否有更多数据
    protected boolean isMoreNew;
    private int pageNoNew = 1;
    private ListViewEmptyUtils emptyUtils;
    private boolean isFirstRequest=true;


    public static MyCoupotionsFramgent getInstance(Bundle bundle) {

        MyCoupotionsFramgent mAgentWebFragment = new MyCoupotionsFramgent();
        if (bundle != null) {
            mAgentWebFragment.setArguments(bundle);
        }
        return mAgentWebFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_comment_refrsh_list;
    }

    @Override
    public void setDate() {
        tvTitle.setText("我的优惠券");
        couponlist = new ArrayList<CouponEntity>();
        couponadapter = new CouponAdapter(mContext, null,1);
        myCollectList.setAdapter(couponadapter);
        myCollectList.setMode(PullToRefreshBase.Mode.BOTH);
        emptyUtils = new ListViewEmptyUtils(mContext, myCollectList);
        createLoadingview(myCollectList.getRefreshableView());
        isMoreNew = true;
        loadDate(REQUEST_GET_COUPON);

    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_GET_COUPON:
                    Map map0 = new HashMap();
                    Map map00 = new HashMap();
                    map0.put("pageSize", "10");
                    map0.put("pageNumber", pageNoNew);
                    map00.put("userId", biz.getCustomToken());
                    map0.put("param", map00);
                    request(REQUEST_GET_COUPON, ApiConstants.GOOD_MY_COUPON, StringUtils.commbingJson(map0, map00), myhttpListener, false, isFirstRequest);
                    isFirstRequest=false;
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
                    case REQUEST_GET_COUPON:
                        myCollectList.onRefreshComplete();
                        MyLogUtils.i(TAG, "优惠券数据:\r\n" + jsonstr);
                        handlerCollcet(jsonstr);
                        //                            handlerHotSale(jsonstr);
                        break;
                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }
        @Override
        public void onFailed(int what, String jsonstr) {
            myCollectList.onRefreshComplete();
        }
    };

    @Override
    protected void addLisener() {
        myCollectList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNoNew = 1;
                isMoreNew = true;
                loadDate(REQUEST_GET_COUPON);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (isMoreNew) {
                    // 联网加载数据
                    pageNoNew++;
                    loadDate(REQUEST_GET_COUPON);
                } else {
                    showShortToast("没有更多数据了！");
                    myCollectList.onRefreshComplete();
                }
            }
        });
        myCollectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("keyword","");
                enterPage(SearchShoperActivity.class, bundle);
            }
        });
        couponadapter.setAcceptLisener(new CouponAdapter.AcceptLisener() {
            @Override
            public void clickAccept(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("keyword","");
                enterPage(SearchShoperActivity.class, bundle);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    /**
     * 优惠券列表
     */
    protected void handlerCollcet(String jsonstr) {
        if (StringUtils.isEmpty(jsonstr)) {
            isMoreNew = false;
            return;
        }
        Map<String ,String> map= JSON.parseObject(jsonstr,new TypeReference<Map<String,String>>(){});
        String liststr=map.get("list");
        if(pageNoNew==1){
            couponlist.clear();
        }
        List<CouponEntity> hotlist = JSON.parseObject(liststr, new TypeReference<List<CouponEntity>>() {
        });
        if(hotlist!=null&&hotlist.size()>0){
            if (hotlist.size() == 10) {
                isMoreNew = true;
            } else {
                isMoreNew = false;
            }
            couponlist.addAll(hotlist);
        }
        if(couponlist.size()==0){
            emptyUtils.setEmptyTextAndImage("亲，暂时没有优惠券，快去领取吧！", R.drawable.no_data);
        }
        couponadapter.updateData(couponlist);


    }



}
