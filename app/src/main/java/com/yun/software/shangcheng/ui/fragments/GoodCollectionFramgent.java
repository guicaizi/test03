package com.yun.software.shangcheng.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseFragment;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.activitys.ProductDetail;
import com.yun.software.shangcheng.ui.adapter.GoodinforAdapter;
import com.yun.software.shangcheng.ui.entity.GoodInfor;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.utils.ListViewEmptyUtils;
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
 * on 2018/7/4 13:58
 */

public class GoodCollectionFramgent extends BaseFragment {
    public static final String TAG = "GoodCollectionFramgent";
    private static final int REQUEST_GET_PRODUCT = 1;
    Map<String, Object> handleproducts;
    @Bind(R.id.my_collection_list)
    PullToRefreshGridView myCollectList;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    private List<GoodInfor> productsList;
    GoodinforAdapter malladapter;
    // 是否有更多数据
    protected boolean isMoreNew;
    private int pageNoNew = 1;
    private ListViewEmptyUtils emptyUtils;
    private boolean isFirstRequest=true;
    public static GoodCollectionFramgent getInstance(Bundle bundle) {

        GoodCollectionFramgent mAgentWebFragment = new GoodCollectionFramgent();
        if (bundle != null) {
            mAgentWebFragment.setArguments(bundle);
        }
        return mAgentWebFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_goodcollection;
    }

    @Override
    public void setDate() {
        tvTitle.setText("我的收藏");
        productsList = new ArrayList<GoodInfor>();
        malladapter = new GoodinforAdapter(mContext, null);
        myCollectList.setAdapter(malladapter);
        myCollectList.setMode(PullToRefreshBase.Mode.BOTH);
        emptyUtils = new ListViewEmptyUtils(mContext, myCollectList);
        createLoadingview(myCollectList.getRefreshableView());

        isMoreNew = true;
        loadDate(REQUEST_GET_PRODUCT);

    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_GET_PRODUCT:
                    Map map0 = new HashMap();
                    Map map00 = new HashMap();
                    map0.put("pageSize", "10");
                    map0.put("pageNumber", pageNoNew);
                    map00.put("userId", biz.getCustomToken());
                    map0.put("param", map00);
                    request(REQUEST_GET_PRODUCT, ApiConstants.GOOD_COLLECTION, StringUtils.commbingJson(map0, map00), myhttpListener, false, isFirstRequest);
                    isFirstRequest=false;
                    break;
            }

        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }

    @Override
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        super.onMessageEventMainThread(messageEvent);
        if(messageEvent.getMessage().equals("refresh_collect")){
            pageNoNew = 1;
            isMoreNew = false;
            loadDate(REQUEST_GET_PRODUCT);

        }
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            try {
                switch (what) {
                    case REQUEST_GET_PRODUCT:
                        myCollectList.onRefreshComplete();
                        MyLogUtils.i(TAG, "收藏数据:\r\n" + jsonstr);
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
        myCollectList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                pageNoNew = 1;
                isMoreNew = false;
                loadDate(REQUEST_GET_PRODUCT);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                if (isMoreNew) {
                    // 联网加载数据
                    pageNoNew++;
                    loadDate(REQUEST_GET_PRODUCT);
                } else {
                    Tools.showInfo(mContext, R.string.no_more);
                    myCollectList.onRefreshComplete();
                }
            }
        });
        myCollectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 进入更多规格页面
                Bundle bundle = new Bundle();
                bundle.putSerializable("productid", productsList.get(i).getId());
                enterPage(ProductDetail.class, bundle);
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
     * 热销数据
     */
    protected void handlerCollcet(String jsonstr) {

        if (StringUtils.isEmpty(jsonstr)) {
            isMoreNew = false;
            return;
        }
        if(pageNoNew==1){
            productsList.clear();
        }
        List<GoodInfor> hotlist = JSON.parseObject(jsonstr, new TypeReference<List<GoodInfor>>() {
        });


        if(Tools.checkList(hotlist)){
            if (hotlist.size() == 10) {
                isMoreNew = true;
            } else {
                isMoreNew = false;
            }
            productsList.addAll(hotlist);
        }
          if(productsList.size()==0){
              emptyUtils.setEmptyTextAndImage("亲，快去收藏你喜欢的商品吧！", R.drawable.no_data);
          }

        malladapter.updateData(productsList);


    }



}
