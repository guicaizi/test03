package com.yun.software.shangcheng.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.base.BaseFragment;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.ClearEditText;
import com.yun.software.shangcheng.ui.ViewWidget.DragHorizontalScrollView;
import com.yun.software.shangcheng.ui.activitys.BigImagePagerActivity;
import com.yun.software.shangcheng.ui.activitys.GoodCategorys;
import com.yun.software.shangcheng.ui.activitys.MessageActivity;
import com.yun.software.shangcheng.ui.activitys.ProductDetail;
import com.yun.software.shangcheng.ui.activitys.SearchShoperActivity;
import com.yun.software.shangcheng.ui.adapter.GoodinforAdapter;
import com.yun.software.shangcheng.ui.adapter.ShopCategoryGridViewAdapter;
import com.yun.software.shangcheng.ui.callback.GlideImageLoader;
import com.yun.software.shangcheng.ui.entity.BannerImg;
import com.yun.software.shangcheng.ui.entity.GoodInfor;
import com.yun.software.shangcheng.ui.entity.MainCategory;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.NoScrollGridView;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;


/**
 * Created by yanliang
 * on 2018/6/26 15:29
 */

public class ShopMainIndexFragment extends BaseFragment {
    private static final int REQUEST_BANNES = 1;
    private static final int REQUEST_CATEGORY = 2;
    private static final int REQUEST_JINPING = 3;
    private static final int REQUEST_HOT_SALE = 4;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.et_search)
    ClearEditText etSearch;
    @Bind(R.id.img_alerme)
    ImageView imgAlerme;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.tv_message_number)
    TextView tvMessageNumber;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.shop_category_gridview)
    NoScrollGridView shopCategoryGridview;
    @Bind(R.id.lin_add_shop_img)
    LinearLayout linAddShopImg;
    @Bind(R.id.scrollview_state)
    DragHorizontalScrollView scrollviewState;
    @Bind(R.id.lin_pop)
    LinearLayout linPop;
    @Bind(R.id.gv_gridview)
    NoScrollGridView gvGridview;
    @Bind(R.id.sv_mell_fresh)
    PullToRefreshScrollView svMellFresh;
    GoodinforAdapter goodinforAdapter;
    List<GoodInfor> listgoods;
    // 是否有更多数据
    protected boolean isMoreNew = false;
    private boolean isFirstinto=true;
    private int pageNoNew = 1;
    private ShopCategoryGridViewAdapter shopCategoryGridViewAdapter;
    //banner 图片集合
    private List<BannerImg> stu;
    private List<MainCategory> mainCategories;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_shop_main;
    }

    @Override
    public void onResume() {
        banner.startAutoPlay();
        super.onResume();
    }

    @Override
    public void onPause() {
        banner.stopAutoPlay();
        super.onPause();
    }

    @Override
    public void setRetryEvent(View retryView) {
        super.setRetryEvent(retryView);
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMoreNew = false;
                pageNoNew = 1;
                loadDate(REQUEST_BANNES);
                loadDate(REQUEST_CATEGORY);
                loadDate(REQUEST_JINPING);
                loadDate(REQUEST_HOT_SALE);
            }
        });
    }

    @Override
    public void setDate() {
        banner.setIndicatorGravity(BannerConfig.CENTER);
        listgoods = new ArrayList<>();
        goodinforAdapter = new GoodinforAdapter(mContext, listgoods);
        shopCategoryGridViewAdapter = new ShopCategoryGridViewAdapter(mContext, null);
        shopCategoryGridview.setAdapter(shopCategoryGridViewAdapter);
        gvGridview.setAdapter(goodinforAdapter);
        svMellFresh.setMode(PullToRefreshBase.Mode.BOTH);
        createLoadingview(svMellFresh.getRefreshableView());
        loadDate(REQUEST_BANNES);
        loadDate(REQUEST_CATEGORY);
        loadDate(REQUEST_JINPING);
        loadDate(REQUEST_HOT_SALE);
    }
    // 精品为1

    public void loadDate(int what) {
        switch (what) {
            case REQUEST_BANNES:
                Map map = new HashMap();
                map.put("type", "0");
                request(REQUEST_BANNES, ApiConstants.FIRST_PAGER_BANNERS, JSON.toJSONString(map), myhttpListener, false, false);
                break;
            case REQUEST_CATEGORY:
                request(REQUEST_CATEGORY, ApiConstants.FIRST_PAGER_CATEGORY, "", myhttpListener, false, false);
                break;
            case REQUEST_JINPING:
                Map map0 = new HashMap();
                Map map00 = new HashMap();
                map00.put("boutique", "1");
                map00.put("hot", "");
                map00.put("kindId", "");
                map00.put("name", "");
                map00.put("orderField", "");
                map00.put("orderRule", "");
                map00.put("type", "1");
                map0.put("pageSize", 6);
                map0.put("pageNumber", 1);
                map0.put("param", map00);
                MyLogUtils.i("httprequest", "请求数据" + JSON.toJSONString(map0));
                request(REQUEST_JINPING, ApiConstants.FIRST_PAGER_ALLSALE, JSON.toJSONString(map0), myhttpListener, false, false);
                break;
            case REQUEST_HOT_SALE:
                Map map1 = new HashMap();
                Map map11 = new HashMap();
                map11.put("boutique", "");
                map11.put("hot", "");
                map11.put("kindId", "");
                map11.put("name", "");
                map11.put("orderField", "");
                map11.put("orderRule", "");
                map11.put("type", "1");
                map1.put("pageSize","10");
                map1.put("pageNumber", pageNoNew);
                map1.put("param", map11);
                MyLogUtils.i("httprequest", "请求数据" + JSON.toJSONString(map1));
                request(REQUEST_HOT_SALE, ApiConstants.FIRST_PAGER_ALLSALE, JSON.toJSONString(map1), myhttpListener, false, isFirstinto);
                isFirstinto=false;
                break;

        }
    }

    @Override
    protected void addLisener() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ArrayList<String> imgs = new ArrayList<>();
                for (BannerImg bannerImg : stu) {
                    imgs.add(bannerImg.getSrc());
                }
                BigImagePagerActivity.startImagePagerActivity((Activity) mContext, imgs, position);


            }
        });
        shopCategoryGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 3) {
                    enterPage(GoodCategorys.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("kindid", mainCategories.get(i).getId());
                    bundle.putString("name", mainCategories.get(i).getName());
                    enterPage(SearchShoperActivity.class,bundle);
                }
            }
        });
        /**
         * 下拉刷新
         */
        //
        svMellFresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                isMoreNew = true;
                pageNoNew = 1;
                loadDate(REQUEST_HOT_SALE);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (isMoreNew) {
                    // 联网加载数据
                    pageNoNew++;
                    loadDate(REQUEST_HOT_SALE);
                } else {
                    if (svMellFresh.isRefreshing()) {
                        showShortToast("没有更多数据了！");
                        svMellFresh.onRefreshComplete();
                    }
                }

            }
        });

        gvGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 进入更多规格页面
                Bundle bundle = new Bundle();
                bundle.putSerializable("productid", listgoods.get(i).getId());
                enterPage(ProductDetail.class, bundle);
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
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String key = etSearch.getText().toString();
                bundle.putString("keyword", key);
                enterPage(SearchShoperActivity.class, bundle);
            }
        });
        imgAlerme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.tologin(new BaseActivity.LogincallBack() {
                    @Override
                    public void loginsuccess() {
                        enterPage(MessageActivity.class);
                    }
                });

            }
        });
    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            if (svMellFresh != null && svMellFresh.isRefreshing()) {
                svMellFresh.onRefreshComplete();
            }
            switch (what) {
                case REQUEST_BANNES:
                    MyLogUtils.i("httprequest===广告图", jsonstr);
                    HanlderBinner(jsonstr);
                    break;
                case REQUEST_JINPING:
                    Map map = JSON.parseObject(jsonstr, Map.class);
                    MyLogUtils.i("httprequest===jinping", StringUtils.toString(map.get("list")));
                    HanlderJinPing(StringUtils.toString(map.get("list")));
                    break;
                case REQUEST_HOT_SALE:
                    Map map1 = JSON.parseObject(jsonstr, Map.class);
                    MyLogUtils.i("httprequest===sale", StringUtils.toString(map1.get("list")));
                    handlerHotSale(StringUtils.toString(map1.get("list")));
                    break;
                case REQUEST_CATEGORY:
                    MyLogUtils.i("httpResult===category", jsonstr);
                    HanlderCategory(jsonstr);
                    break;
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {
            if (svMellFresh != null && svMellFresh.isRefreshing()) {
                svMellFresh.onRefreshComplete();
            }
        }


    };

    /**
     * 轮播图数据
     */
    protected void HanlderBinner(String jsonstr) {
        MyLogUtils.i("http请求结果", "结果" + jsonstr);
        stu = JSON.parseObject(jsonstr, new TypeReference<List<BannerImg>>() {
        });
        banner.setImages(stu)
                .setImageLoader(new GlideImageLoader())
                .start();

    }

    /**
     * 分类数据
     */
    protected void HanlderCategory(String jsonstr) {
        MyLogUtils.i("http请求结果", "结果" + jsonstr);

        mainCategories = JSON.parseObject(jsonstr, new TypeReference<List<MainCategory>>() {
        });
        shopCategoryGridViewAdapter.updateData(mainCategories);


    }

    /**
     * 精品数据
     */
    protected void HanlderJinPing(String jsonstr) {
        final List<GoodInfor> propularList = JSON.parseObject(jsonstr, new TypeReference<List<GoodInfor>>() {
        });
        if (propularList.size() > 0) {
            if (linAddShopImg == null)
                return;
            linAddShopImg.removeAllViews();
            linPop.setVisibility(View.VISIBLE);
            for (int i = 0; i < propularList.size(); i++) {
                View view = View.inflate(mContext, R.layout.hot_icon_item, null);
                int width = ScreenUtil.getWindowsWidth((Activity) mContext) / 4;
                RelativeLayout re_hot = (RelativeLayout) view.findViewById(R.id.re_hot);
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
                TextView tv_price = (TextView) view.findViewById(R.id.tv_hot_price);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) re_hot.getLayoutParams();
                params.width = width - ScreenUtil.dip2px(mContext, 5);
                params.setMargins(ScreenUtil.dip2px(mContext, 5), 0, 0, 0);
                re_hot.setLayoutParams(params);
                GlidUtils.loadRoundImageView(mContext, propularList.get(i).getLogo(), imageView, 5);
                tv_price.setText("￥" + propularList.get(i).getPrice());
                linAddShopImg.addView(view);
                final int postion=i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("productid",propularList.get(postion).getId());
                        enterPage(ProductDetail.class, bundle);
                    }
                });
            }
        }
    }


    /**
     * 热销数据
     */
    protected void handlerHotSale(String jsonstr) {
        if (StringUtils.isEmpty(jsonstr)) {
            isMoreNew = false;
            return;
        }

        List<GoodInfor> hotlist = JSON.parseObject(jsonstr, new TypeReference<List<GoodInfor>>() {
        });
        if (hotlist.size() == 10) {
            isMoreNew = true;
        } else {
            isMoreNew = false;
        }
        listgoods.addAll(hotlist);
        goodinforAdapter.updateData(listgoods);


    }

}
