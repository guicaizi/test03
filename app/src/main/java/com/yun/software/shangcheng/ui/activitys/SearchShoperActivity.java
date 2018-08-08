package com.yun.software.shangcheng.ui.activitys;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.ClearEditText;
import com.yun.software.shangcheng.ui.adapter.GoodinforAdapter;
import com.yun.software.shangcheng.ui.entity.GoodInfor;
import com.yun.software.shangcheng.ui.entity.TagState;
import com.yun.software.shangcheng.ui.manager.SearchManager;
import com.yun.software.shangcheng.ui.utils.ListViewEmptyUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

;

/**
 * Created by yanliang
 * on 2017/8/9 12:01
 */

public class SearchShoperActivity extends BaseActivity {
    private static final int REQUEST_GET_PRODUCT = 1;
    Map<String, Object> handleproducts;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_search)
    ClearEditText etSearch;
    @Bind(R.id.img_shop_car)
    ImageView imgShopCar;
    /**
     *默认排序
     */
    @Bind(R.id.iv_defalut)
    ImageView ivDefalut;
    @Bind(R.id.tv_deult)
    TextView  tvDefalut;
    @Bind(R.id.lin_defalut)
    LinearLayout linDefalut;
    @Bind(R.id.re_defalut)
    RelativeLayout reDefalut;
    /**
     *销量
     */
    @Bind(R.id.iv_inditator_sale)
    ImageView ivInditatorSale;
    @Bind(R.id.iv_sale)
    ImageView ivSale;
    @Bind(R.id.tv_sale)
    TextView tvSale;
    @Bind(R.id.re_sale)
    RelativeLayout reSale;
    /**
     *价格
     */
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.iv_price)
    ImageView ivPrice;
    @Bind(R.id.iv_inditator_price)
    ImageView ivInditatorPrice;
    @Bind(R.id.re_price)
    RelativeLayout rePrice;
    /**
     *全部分类
     */
    @Bind(R.id.all_category)
    TextView allCategory;
    /**
     *列表
     */
    @Bind(R.id.my_search_list)
    PullToRefreshGridView mySearchList;

    private List<GoodInfor> productsList;
    GoodinforAdapter malladapter;
    // 是否有更多数据
    protected boolean isMoreNew;
    private int pageNoNew = 1;
    private String keywordl="";
    private ListViewEmptyUtils emptyUtils;
    SearchManager searchManager;
    String kindid="";
    @Override
    public int getLayoutId() {
        return R.layout.activity_shoperp_search;
    }

    @Override
    public void initPresenter() {
        Bundle bundle = getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if (bundle != null) {
            if (bundle.containsKey("keyword"));
            keywordl = bundle.getString("keyword", "");
            etSearch.setText(keywordl);
            if (bundle.containsKey("kindid")) {
                kindid = bundle.getString("kindid");
               String  cname=bundle.getString("name");
               allCategory.setText(cname);
            }

        }
    }

    @Override
    public void initView() {
        searchManager=new SearchManager();
        initDate();
        productsList = new ArrayList<GoodInfor>();
        malladapter = new GoodinforAdapter(mContext, null);
        mySearchList.setAdapter(malladapter);
        emptyUtils = new ListViewEmptyUtils(mContext, mySearchList);
        isMoreNew = false;
        createLoadingview(mySearchList.getRefreshableView());
        loadDate(REQUEST_GET_PRODUCT);
    }

    public void initDate(){
        List<TagState> listtags=new ArrayList<>();
        TagState  tagStatedefalut=new TagState(TagState.Statecategory.NONE,TagState.Statecategory.LINE,ivDefalut,tvDefalut,null);
        TagState  tagStatedeSale= new TagState(TagState.Statecategory.NONE,TagState.Statecategory.NOLINE,ivSale,tvSale,ivInditatorSale);
        TagState  tagStatedePrice=new TagState(TagState.Statecategory.NONE,TagState.Statecategory.NOLINE,ivPrice,tvPrice,ivInditatorPrice);
        listtags.add(tagStatedefalut);
        listtags.add(tagStatedeSale);
        listtags.add(tagStatedePrice);
        searchManager.setAllTags(listtags);
        searchManager.setTag(0);
    }

    @Override
    public void addLisener() {
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String key = etSearch.getText().toString();
                bundle.putString("keyword", key);
                enterPage(SearchShoperActivity.class, bundle);
            }
        });
        reDefalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNoNew=1;
                searchManager.setTag(0);
                loadDate(REQUEST_GET_PRODUCT);

            }
        });
        reSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 pageNoNew=1;
                 searchManager.setTag(1);
                loadDate(REQUEST_GET_PRODUCT);

            }
        });
        rePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNoNew=1;
                searchManager.setTag(2);
                loadDate(REQUEST_GET_PRODUCT);

            }
        });
        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPage(GoodCategorys.class);
            }
        });


        mySearchList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                pageNoNew = 1;
                isMoreNew = true;
                loadDate(REQUEST_GET_PRODUCT);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                if (isMoreNew) {
                    // 联网加载数据
                    pageNoNew++;
                    loadDate(REQUEST_GET_PRODUCT);
                } else {
                    showShortToast(R.string.no_more);
                    mySearchList.onRefreshComplete();
                }
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1,
                                          KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    keywordl = etSearch.getText().toString();
                    pageNoNew = 1;
                    loadDate(REQUEST_GET_PRODUCT);
                    hiddenKeyBoard();
                }
                return true;
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1,
                                          KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    Bundle bundle = new Bundle();
                    String key = etSearch.getText().toString();
                    bundle.putString("keyword", key);
                    enterPage(SearchShoperActivity.class, bundle);
                }
                return true;
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mySearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("productid", productsList.get(i).getId());
                enterPage(ProductDetail.class, bundle);
            }
        });
        imgShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   enterPage(GoodCarActivity.class);
            }
        });

    }
    @Override
    protected void onDestroy() {
        if(searchManager!=null){
            searchManager.clear();
        }
        super.onDestroy();
    }

    public void loadDate(int requestCode) {
        try {
            //  {"pageIndex":1,"pageSize":10,"keyWord":"","orderBy":1,"VendorId":"0","CategoryId":"0","CustomerId":"780","UserId":"780"}
            switch (requestCode) {
                case REQUEST_GET_PRODUCT:
                     Map map0 = new HashMap();
                     Map map00 = new HashMap();
                     map00.put("name", keywordl);
                     map00.put("kindId", kindid);
                     map00.put("orderRule",searchManager.getTag().getStateSort().getName());
                     map00.put("orderField",searchManager.getOderFiled());
                     map00.put("type","1");
                     map0.put("pageSize", "10");
                     map0.put("pageNumber", pageNoNew);
                     map0.put("param", map00);
                     MyLogUtils.i("httprequest", "请求数据" + JSON.toJSONString(map0));
                    request(REQUEST_GET_PRODUCT, ApiConstants.FIRST_PAGER_ALLSALE, JSON.toJSONString(map0), myhttpListener, false, true);
                    break;

            }
        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            if (mySearchList != null && mySearchList.isRefreshing()) {
                mySearchList.onRefreshComplete();
            }
            switch (what) {
                case REQUEST_GET_PRODUCT:
                    if(pageNoNew==1){
                        productsList.clear();
                    }
                    List<GoodInfor> hotlist = JSON.parseObject(StringUtils.getJsonListStr(jsonstr,"list"), new TypeReference<List<GoodInfor>>(){});

                   if(hotlist!=null&&hotlist.size()>0){
                       if(hotlist.size()==10){
                           isMoreNew=true;
                       }else{
                           isMoreNew=false;
                       }
                       productsList.addAll(hotlist);
                   }
                    malladapter.updateData(productsList);
                    if (productsList.size() == 0) {
                        emptyUtils.setEmptyTextAndImage("暂无此类商品", R.drawable.no_data);
                    }
                    break;
            }
        }
        @Override
        public void onFailed(int what, String jsonstr) {
            if (mySearchList != null && mySearchList.isRefreshing()) {
                mySearchList.onRefreshComplete();
            }
        }


    };





}
