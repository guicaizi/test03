package com.yun.software.shangcheng.ui.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.adapter.GoodinforAdapter;
import com.yun.software.shangcheng.ui.callback.GlideImageLoader;
import com.yun.software.shangcheng.ui.entity.BannerImg;
import com.yun.software.shangcheng.ui.entity.GoodInfor;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.NoScrollGridView;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/19 10:56
 */

public class ScoreShoperActivity extends BaseActivity {
    private static final int REQUEST_BANNERS=0 ;
    private static final int REQUEST_SCOR_GOODS=1 ;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.gv_gridview)
    NoScrollGridView gvGridview;
    @Bind(R.id.sv_mell_fresh)
    PullToRefreshScrollView svMellFresh;
    private List<BannerImg> stu;
    private int pageNoNew = 1;
    protected boolean isMoreNew = false;
    GoodinforAdapter goodinforAdapter;
    List<GoodInfor> listgoods;
    @Override
    public int getLayoutId() {
        return R.layout.activity_score_shoper;
    }

    @Override
    public void initPresenter() {
        tvTitle.setText("积分商城");
        banner.setIndicatorGravity(BannerConfig.CENTER);
        listgoods = new ArrayList<>();
        goodinforAdapter = new GoodinforAdapter(mContext, listgoods,1);
        gvGridview.setAdapter(goodinforAdapter);
        svMellFresh.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        createLoadingview(svMellFresh.getRefreshableView());
        loadDate(REQUEST_BANNERS);
        loadDate(REQUEST_SCOR_GOODS);

    }
    public void loadDate(int what){
        try {
            switch (what){
                case REQUEST_BANNERS:
                    Map map = new HashMap();
                    map.put("type", "1");
                    request(REQUEST_BANNERS, ApiConstants.FIRST_PAGER_BANNERS, JSON.toJSONString(map), myhttpListener, false, false);

                    break;
                case REQUEST_SCOR_GOODS:
                    Map map1 = new HashMap();
                    Map map11 = new HashMap();
                    map11.put("orderField", "");
                    map11.put("orderRule", "");
                    map11.put("kindId", "");
                    map11.put("name", "");
                    map11.put("boutique", "");
                    map11.put("hot", "");
                    map11.put("type", "2");
                    map1.put("pageSize", 10);
                    map1.put("pageNumber", pageNoNew);
                    map1.put("param", map11);
                    MyLogUtils.i("httprequest", "请求数据" + JSON.toJSONString(map1));
                    boolean showloding = pageNoNew == 1 ? true : false;
                    request(REQUEST_SCOR_GOODS, ApiConstants.FIRST_PAGER_ALLSALE, JSON.toJSONString(map1), myhttpListener, false, showloding);

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
                    case REQUEST_BANNERS:
                        MyLogUtils.i("httprequest===广告图", jsonstr);
                        HanlderBinner(jsonstr);
                        break;
                    case REQUEST_SCOR_GOODS:
                        Map map1 = JSON.parseObject(jsonstr, Map.class);
                        MyLogUtils.i("httprequest===sale", StringUtils.toString(map1.get("list")));
                        handlerScoreList(StringUtils.toString(map1.get("list")));
                        break;
                }
            }catch (Exception e){
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {

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
     * 积分商品
     */
    protected void handlerScoreList(String jsonstr) {
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
    @Override
    public void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void addLisener() {
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

        /**
         * 下拉刷新
         */
        //
        svMellFresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                isMoreNew = true;
                pageNoNew = 1;
                loadDate(REQUEST_SCOR_GOODS);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (isMoreNew) {
                    // 联网加载数据
                    pageNoNew++;
                    loadDate(REQUEST_SCOR_GOODS);
                } else {
                    showShortToast("没有更多数据了");
                    if (svMellFresh.isRefreshing()) {
                        svMellFresh.onRefreshComplete();
                    }
                }

            }
        });
        gvGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // 进入更多规格页面
                Bundle bundle = new Bundle();
                bundle.putString("productid", listgoods.get(i).getId());
                bundle.putInt("frompage", 1);
                enterPage(ProductDetail.class, bundle);
            }
        });

    }


}
