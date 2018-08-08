package com.yun.software.shangcheng.ui.activitys;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.ui.ViewWidget.ClearEditText;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/18 15:06
 */

public class CategoryDetail extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_search)
    ClearEditText etSearch;
    @Bind(R.id.img_shop_car)
    ImageView imgShopCar;
    @Bind(R.id.tv_dafault_sore)
    TextView tvDafaultSore;
    @Bind(R.id.tv_sale)
    TextView tvSale;
    @Bind(R.id.lin_sale)
    LinearLayout linSale;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.lin_price)
    LinearLayout linPrice;
    @Bind(R.id.tv_produce_categoryname)
    TextView tvProduceCategoryname;
    @Bind(R.id.lin_classfiy)
    LinearLayout linClassfiy;
    @Bind(R.id.my_search_list)
    PullToRefreshGridView mySearchList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shoperp_classfiy_one;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void addLisener() {

    }


}
