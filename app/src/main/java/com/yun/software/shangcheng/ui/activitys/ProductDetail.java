package com.yun.software.shangcheng.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.MaxScollView;
import com.yun.software.shangcheng.ui.ViewWidget.MyRatingBar;
import com.yun.software.shangcheng.ui.ViewWidget.NoScrollListView;
import com.yun.software.shangcheng.ui.ViewWidget.RichText;
import com.yun.software.shangcheng.ui.ViewWidget.popView.ButtomDialog;
import com.yun.software.shangcheng.ui.ViewWidget.popView.DialogBuyProduct;
import com.yun.software.shangcheng.ui.adapter.CouponAdapterdetail;
import com.yun.software.shangcheng.ui.callback.GlideImageLoader;
import com.yun.software.shangcheng.ui.entity.Comment;
import com.yun.software.shangcheng.ui.entity.CouponEntity;
import com.yun.software.shangcheng.ui.entity.GoodAttribute;
import com.yun.software.shangcheng.ui.entity.GoodDetailInfor;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ExceptionUtil;
import com.yun.software.utils.ScreenUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/6/29 16:10
 */

public class ProductDetail extends BaseActivity {
    public static final String TAG = "ProductDetail";
    private static final int REQUEST_GOOD_COUPON = 1;
    private static final int REQUEST_GOOD_DETAIL_PINGLUN = 2;
    private static final int REQUEST_GOOD_DETAIL_INFOR = 3;
    private static final int REQUEST_GOOD_DETAIL_PRODUCTDETAI = 4;
    private static final int REQUEST_GOOD_DETAIL_PROPERTY = 5;
    private static final int REQUEST_GOOD_DETAIL_ADD_CAR = 6;
    private static final int REQUEST_GOOD_DETAIL_BUYNOW = 7;
    private static final int REQUEST_GOOD_DETAIL_ADDCOLLECTION = 8;
    private static final int REQUEST_GOOD_DETAIL_GETSTOCK_OR_PRICE = 9;
    private static final int REQUEST_GOOD_DETAIL_GETSTOCKLABEL = 10;
    private static final int REQUEST_MEAK_SURE_MESSURE = 11;
    private static final int REQUEST_GOOD_CAR_NUMBER = 12;
    private static final int REQUEST_GOOD_GET_COUPON = 13;

    /**
     * 返回按钮
     */
    @Bind(R.id.iv_product_back)
    ImageView ivBack;
    /**
     * banner图片展示
     */
    @Bind(R.id.banner)
    Banner banner;
    /**
     * 顶部布局======================= 顶部布局==================================
     */
    @Bind(R.id.sc_produce)
    MaxScollView sc_produce;
    /**
     * 商品名字
     */
    @Bind(R.id.tv_detail_name)
    TextView tvName;
    /**
     * 商品描述
     */
    @Bind(R.id.tv_detail_des)
    TextView tvDes;
    /**
     * 售价
     */
    @Bind(R.id.tv_detail_sale)
    TextView tvSale;
    /**
     * 会员价格
     */
    @Bind(R.id.tv_detial_newPrice)
    TextView tvNowPrice;
    @Bind(R.id.tv_vip)
    TextView tvVip;
    /**
     * 会员价格
     */
    @Bind(R.id.tv_detial_score)
    TextView tvDetailScore;
    /**
     * 普通价格
     */
    @Bind(R.id.tv_detial_oldprice)
    TextView tvOldPrice;
    /**
     * ==============================================评论========================================
     * 评论为null时候显示
     */
    @Bind(R.id.tv_empty)
    TextView tv_empty;
    /**
     * 第一评论
     */
    @Bind(R.id.lin_comment_one)
    LinearLayout lin_comment_one;
    /**
     * 评论描述
     */
    @Bind(R.id.tv_coment_item_des)
    TextView tv_comment_des;
    /**
     * 评论名字
     */
    @Bind(R.id.tv_comment_item_name)
    TextView tv_comment_name;
    /**
     * 评论时间
     */
    @Bind(R.id.tv_comment_item_time)
    TextView tv_comment_time;
    /**
     * 评论人物图像
     */
    @Bind(R.id.iv_comment_item_icon)
    ImageView iv_comment_icon;
    /**
     * 评论星星
     */
    @Bind(R.id.ratingbar_coment_item)
    MyRatingBar ratingBar;
    /**
     * 查看全部评论
     */
    @Bind(R.id.tv_see_allcoment)
    TextView tvAllComments;
    /**
     * =======================================优惠券布局====================================
     * 所有优惠券
     */
    @Bind(R.id.lin_all_coupon)
    LinearLayout linAllCoupon;
    /**
     * 店铺优惠券
     */
    @Bind(R.id.lin_diianpu)
    LinearLayout linDianpu;
    /**
     * 平台优惠券
     */
    @Bind(R.id.lin_pingtai)
    LinearLayout linPingtai;
    /**
     * 领取优惠券
     */
    @Bind(R.id.tv_get_coupon)
    TextView tvGetCoupon;
    /**
     * 购物车布局============================购物车布局===================================
     */
    @Bind(R.id.re_car)
    RelativeLayout re_car;
    /**
     * 购物车数目
     */
    @Bind(R.id.tv_produce_car_number)
    TextView tv_CarNumber;
    @Bind(R.id.tv_car_number)
    TextView tvCarNumber;

    /**
     * =======================================产品介绍 产品参数 猜你喜欢====================================
     * 产品介绍
     */
    @Bind(R.id.tv_produce_introduce)
    TextView tvProDescrible;
    /**
     * 产品参数
     */
    @Bind(R.id.tv_produce_paramter)
    TextView tvProParams;
    /**
     * 猜你喜欢
     */
    @Bind(R.id.tv_produce_like)
    TextView tvProLike;

    /**
     * 产品描述================================== 图片描述=====================================
     */
    @Bind(R.id.img_des)
    LinearLayout imgDes;

    /**
     * 产品参数 ================================= 参数描述描述=====================================
     */
    @Bind(R.id.re_params)
    RelativeLayout re_params;
    /**
     * 展示图
     */
    @Bind(R.id.lin_des_params)
    LinearLayout imgParamsDes;

    /**
     * 售后说明 ================================= 售后说明=====================================
     */
    @Bind(R.id.tv_coustomer_service)
    TextView tvService;


    /**
     * 收藏=======================================最下面底部布局===============================
     */
    @Bind(R.id.iv_line)
    ImageView ivLine;
    @Bind(R.id.tv_collcetion)
    RichText tv_collection;
    @Bind(R.id.re_collection)
    RelativeLayout reCollect;
    @Bind(R.id.re_good_car)
    RelativeLayout reGoodCar;
    @Bind(R.id.tv_good_car)
    RichText tvGoodCar;
    /**
     * 普通商品详情底部
     */
    @Bind(R.id.lin_detial_bottom)
    LinearLayout linDetialbottom;
    /**
     * 积分商品底部
     */
    @Bind(R.id.tv_score_buy)
    TextView tvScoreBuy;
    /**
     * 立即购买
     */
    @Bind(R.id.tv_buynow)
    TextView tvBuyNow;
    /**
     * 加入购物车
     */
    @Bind(R.id.tv_add_car)
    TextView tvAddCar;
    /**
     * 产品id
     */
    private String productid;
    /**
     * 详情信息
     */
    private GoodDetailInfor gDesInfor;
    /**
     * 评论列表
     */
    private List<Comment> commentlists;
    private List<GoodAttribute> attributeslists;
    private DialogBuyProduct dialogBuy;
    /**
     * 0加入购物车 1 直接购买
     */
    private String isbuy = "0";

    //优费券弹出窗
    private ButtomDialog buttomDialog;
    private List<CouponEntity> couponEntities;
    private boolean isCollect = false;
    /**
     * 0 updateone 1 updatetwo  2 makesureprice
     */
    private int type = 0;
    private List<String> choicIds;
    private List<String> choicIdstwo;
    private List<String> addCarlables;
    private int addcarNumber;
    /**
     * frompage 1 来自积分商城 0 来自普通商品
     */
    private int frompage = 0;
    private String couponid_choice;
    private boolean isFirstCInto = true;
    private double perScore;


    @Override
    public int getLayoutId() {
        return R.layout.activity_product_detial;
    }

    @Override
    public void initPresenter() {
        createLoadingview(this);
        commentInit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        commentInit();
    }

    public void commentInit() {
        Bundle bundle = getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if (bundle != null) {
            if (bundle.containsKey("productid")) {
                productid = bundle.getString("productid");

            }
            if (bundle.containsKey("frompage")) {

                frompage = bundle.getInt("frompage", 0);
            }
            MyLogUtils.i("kankan", "productid" + productid, "frompage" + frompage);
            if (frompage == 0) {
                ivLine.setVisibility(View.VISIBLE);
                linDetialbottom.setVisibility(View.VISIBLE);
                tvScoreBuy.setVisibility(View.GONE);
                if (StringUtils.isNotEmpty(biz.getCustomToken())) {
                    loadDate(REQUEST_GOOD_COUPON);
                }
            } else {
                linDetialbottom.setVisibility(View.GONE);
                tvScoreBuy.setVisibility(View.VISIBLE);
            }
            loadDate(REQUEST_GOOD_DETAIL_PROPERTY);

        }
        loadDate(REQUEST_GOOD_DETAIL_PROPERTY);
        loadDate(REQUEST_GOOD_DETAIL_PRODUCTDETAI);

    }

    @Override
    public void onResume() {
        banner.startAutoPlay();
        if (frompage == 0) {
            if (StringUtils.isNotEmpty(biz.getCustomToken())) {
                loadDate(REQUEST_GOOD_CAR_NUMBER);
            }

        }

        super.onResume();
    }

    @Override
    public void onPause() {
        banner.stopAutoPlay();
        super.onPause();
    }

    @Override
    public void initView() {
        banner.setIndicatorGravity(BannerConfig.CENTER);
        choicIds = new ArrayList<String>();
        dialogBuy = new DialogBuyProduct((Activity) mContext);
    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_GOOD_COUPON:
                    request(REQUEST_GOOD_COUPON, ApiConstants.GOOD_COUPON, "{}", myhttpListener, false, false);
                    break;
                case REQUEST_GOOD_DETAIL_PINGLUN:
                    /**
                     *产品评论
                     * {
                     "pageSize": 10,
                     "pageNumber": 1,
                     "param": {
                     "productId":"1"
                     }
                     }
                     */
                    Map map0 = new HashMap();
                    map0.put("pageSize", 10);
                    map0.put("pageNumber", 1);
                    Map map00 = new HashMap();
                    map00.put("productId", productid);
                    request(REQUEST_GOOD_DETAIL_PINGLUN, ApiConstants.GOOD_DETAIL_PINGLUN, StringUtils.commbingJson(map0, map00), myhttpListener, false, false);
                    break;
                case REQUEST_GOOD_DETAIL_PRODUCTDETAI:
                    /**
                     *产品详情信息
                     */
                    Map map1 = new HashMap();
                    map1.put("userId", biz.getCustomToken());
                    request(REQUEST_GOOD_DETAIL_PRODUCTDETAI, ApiConstants.GOOD_DETAIL_PRODUCTDETAI + productid, JSON.toJSONString(map1), myhttpListener, false, isFirstCInto);
                    isFirstCInto = false;
                    break;
                case REQUEST_GOOD_DETAIL_PROPERTY:
                    /**
                     *产品属性列表
                     */
                    request(REQUEST_GOOD_DETAIL_PROPERTY, ApiConstants.GOOD_DETAIL_PROPERTY + productid, "{}", myhttpListener, false, false);
                    break;
                case REQUEST_GOOD_GET_COUPON:
                    /**
                     *领取优惠券
                     */
                    Map map13 = new HashMap();
                    map13.put("couponId", couponid_choice);
                    map13.put("userId", biz.getCustomToken());
                    request(REQUEST_GOOD_GET_COUPON, ApiConstants.GOOD_GET_COUPON, JSON.toJSONString(map13), myhttpListener, false, false);
                    break;
                case REQUEST_GOOD_DETAIL_ADD_CAR:
                    /**
                     *加入购物车
                     *userId": "admin",
                     "productId": "1",
                     "count": 2,
                     "productLabels": [
                     "871675a3b5914659b2eb24c284e93a08",
                     "450699dd80e2438fae447c98cb6718c7",
                     "dad664aacd3443698695c32ced8de71c"]
                     }
                     */
                    Map map6 = new HashMap();
                    map6.put("userId", biz.getCustomToken());
                    map6.put("count", addcarNumber);
                    map6.put("productId", productid);
                    map6.put("buyNow", isbuy);
                    map6.put("productLabels", addCarlables);
                    request(REQUEST_GOOD_DETAIL_ADD_CAR, ApiConstants.GOOD_DETAIL_ADD_CAR, JSON.toJSONString(map6), myhttpListener, false, false);
                    break;
                case 7:
                    /**
                     *立即购买
                     * {
                     "userId": "admin",
                     "userRealName": "admin",
                     "productId": "1",
                     "count": 1,
                     "productLabels": [
                     "871675a3b5914659b2eb24c284e93a08",
                     "450699dd80e2438fae447c98cb6718c7",
                     "dad664aacd3443698695c32ced8de71c"]
                     }
                     }
                     */
                    request(REQUEST_GOOD_DETAIL_BUYNOW, ApiConstants.GOOD_DETAIL_BUYNOW, "", myhttpListener, false, false);
                    break;
                case 8:
                    /**
                     *加入收藏
                     * {
                     "userId": "admin",
                     "type": 2,		// 商品收藏:2	店铺收藏:1
                     "proxyId": "1"		// type=1为店铺id	type=2为商品id
                     }
                     */
                    Map map8 = new HashMap();
                    map8.put("userId", biz.getCustomToken());
                    map8.put("type", "2");
                    map8.put("proxyId", productid);
                    request(REQUEST_GOOD_DETAIL_ADDCOLLECTION, ApiConstants.GOOD_DETAIL_ADDCOLLECTION, JSON.toJSONString(map8), myhttpListener, false, false);
                    break;
                case 9:
                    /**
                     *根据产品ID和产品标签查价格和库存
                     * {
                     "productId": "1",
                     "productLabels": [
                     "871675a3b5914659b2eb24c284e93a08",
                     "450699dd80e2438fae447c98cb6718c7",
                     "dad664aacd3443698695c32ced8de71c"]
                     }
                     */
                    request(REQUEST_GOOD_DETAIL_GETSTOCK_OR_PRICE, ApiConstants.GOOD_DETAIL_GETSTOCK_OR_PRICE, "", myhttpListener, false, false);
                    break;
                case REQUEST_GOOD_DETAIL_GETSTOCKLABEL:
                    /**
                     *根据产品id和产品标签有库存的标签
                     * {
                     "productId": "1",
                     "productLabels": ["450699dd80e2438fae447c98cb6718c7"]
                     }
                     */
                    Map map10 = new HashMap();
                    map10.put("userId", biz.getCustomToken());
                    map10.put("productId", productid);
                    map10.put("productLabels", choicIds);
                    request(REQUEST_GOOD_DETAIL_GETSTOCKLABEL, ApiConstants.GOOD_DETAIL_GETSTOCKLABEL, JSON.toJSONString(map10), myhttpListener, false, false);
                    break;
                case REQUEST_MEAK_SURE_MESSURE:
                    /**
                     *根据产品id和产品标签有库存的标签
                     * {
                     "productId": "1",
                     "productLabels": ["450699dd80e2438fae447c98cb6718c7"]
                     }
                     */

                    Map map11 = new HashMap();
                    map11.put("userId", biz.getCustomToken());
                    map11.put("productId", productid);
                    map11.put("productLabels", choicIdstwo);
                    request(REQUEST_MEAK_SURE_MESSURE, ApiConstants.GOOD_DETAIL_GETSTOCKLABEL, JSON.toJSONString(map11), myhttpListener, false, false);

                    break;
                case REQUEST_GOOD_CAR_NUMBER:
                    /**
                     *获取购物车数目
                     * {
                     "productId": "1",
                     "productLabels": ["450699dd80e2438fae447c98cb6718c7"]
                     }
                     */

                    Map map12 = new HashMap();
                    map12.put("userId", biz.getCustomToken());
                    request(REQUEST_GOOD_CAR_NUMBER, ApiConstants.GET_GOOD_CAR_NUMBER, JSON.toJSONString(map12), myhttpListener, false, false);
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
                    case REQUEST_GOOD_DETAIL_PRODUCTDETAI:
                        //
                        MyLogUtils.i(TAG, "jsonstr==========产品详情" + jsonstr);
                        gDesInfor = JSON.parseObject(jsonstr, new TypeReference<GoodDetailInfor>() {
                        });
                        isCollect = gDesInfor.isFlag();
                        if (isCollect) {
                            tv_collection.setImageResource(R.drawable.img_collection_press);
                        } else {
                            tv_collection.setImageResource(R.drawable.img_collection_normal);
                        }
                        showBanners();
                        showinfor();
                        showImgDes();
                        showimgParams();
                        break;
                    case REQUEST_GOOD_DETAIL_PINGLUN:
                        commentlists = JSON.parseObject(StringUtils.getJsonListStr(jsonstr, "list"), new TypeReference<List<Comment>>() {
                        });
                        showComment();
                        break;
                    case REQUEST_GOOD_COUPON:
                        MyLogUtils.i(TAG, "jsonstr==========优费券" + jsonstr);
                        handlerCouPons(jsonstr);
                        break;
                    case REQUEST_GOOD_DETAIL_PROPERTY:
                        MyLogUtils.i(TAG, "jsonstr=======属性 " + jsonstr);
                        attributeslists = JSON.parseObject(jsonstr, new TypeReference<List<GoodAttribute>>() {
                        });

                        break;
                    case REQUEST_GOOD_DETAIL_ADD_CAR:
                        MyLogUtils.i(TAG, "jsonstr=======加入购物车 " + jsonstr);
                        if (isbuy.equals("0")) {
                            loadDate(REQUEST_GOOD_CAR_NUMBER);
                        } else {
                            List<String> ids = new ArrayList<>();
                            ids.add(jsonstr);
                            Bundle bundle = new Bundle();
                            bundle.putInt("frompage", dialogBuy.getType());
                            bundle.putStringArrayList("ids", (ArrayList<String>) ids);
                            enterPage(CommitOrderActivity.class, bundle);
                        }
                        break;
                    case REQUEST_GOOD_CAR_NUMBER:
                        int carnumber = StringUtils.toInt(jsonstr);
                        if (carnumber > 0) {
                            tvCarNumber.setVisibility(View.VISIBLE);
                            tvCarNumber.setText(jsonstr);
                        } else {
                            tvCarNumber.setVisibility(View.GONE);
                        }
                        MyLogUtils.i(TAG, "jsonstr=======购物车数目 " + jsonstr);
                        break;
                    case REQUEST_GOOD_DETAIL_ADDCOLLECTION:
                        MyLogUtils.i(TAG, "jsonstr=======加入收藏 " + jsonstr);
                        if (isCollect = !isCollect) {
                            tv_collection.setImageResource(R.drawable.img_collection_press);
                        } else {
                            tv_collection.setImageResource(R.drawable.img_collection_normal);
                        }
                        EventBus.getDefault().post(new MessageEvent("refresh_collect"));
                        break;
                    case REQUEST_GOOD_DETAIL_GETSTOCKLABEL:
                        List<String> ids = JSON.parseObject(StringUtils.getJsonListStr(jsonstr, "labelIds"), new TypeReference<List<String>>() {
                        });
                        List<String> price = JSON.parseObject(StringUtils.getJsonListStr(jsonstr, "price"), new TypeReference<List<String>>() {
                        });
                        MyLogUtils.i("qukankan", "you");
                        switch (type) {
                            case 0:
                                dialogBuy.updateTagStateOne(ids, price);
                                MyLogUtils.i("qukankan", "0");
                                break;
                            case 1:
                                MyLogUtils.i("qukankan", "1");
                                dialogBuy.updateTagState(ids, price);
                                break;
                        }
                        break;
                    case REQUEST_MEAK_SURE_MESSURE:
                        List<String> ids2 = JSON.parseObject(StringUtils.getJsonListStr(jsonstr, "labelIds"), new TypeReference<List<String>>() {
                        });
                        List<String> price2 = JSON.parseObject(StringUtils.getJsonListStr(jsonstr, "price"), new TypeReference<List<String>>() {
                        });
                        dialogBuy.makeSurePrice(price2);
                        break;
                    case REQUEST_GOOD_GET_COUPON:
                        MyLogUtils.i(TAG, "jsonstr==========领取优惠券" + jsonstr);
                        showShortToast("领取成功");
                        buttomDialog.dismiss();
                        break;
                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {
            if (REQUEST_GOOD_GET_COUPON != what) {
                finish();
            }
        }
    };

    /**
     * 基本信息描述
     */
    public void showinfor() {
        tvName.setText(gDesInfor.getInfo().getName());
        tvDes.setText(gDesInfor.getDescription());
        tvSale.setText("月销售" + gDesInfor.getInfo().getSold() + "笔");
        if (frompage == 0) {
            tvOldPrice.setVisibility(View.VISIBLE);
            tvNowPrice.setVisibility(View.VISIBLE);
            tvVip.setVisibility(View.VISIBLE);
            tvOldPrice.setText("￥" + gDesInfor.getInfo().getPrice());
            tvNowPrice.setText("￥" + gDesInfor.getInfo().getDiscountPrice());
        } else {
            tvDetailScore.setVisibility(View.VISIBLE);
            perScore = gDesInfor.getInfo().getScorePrice();
            tvDetailScore.setText(gDesInfor.getInfo().getScorePrice() + "分");
        }
    }

    /**
     * 广告图描述
     */
    public void showBanners() {
        banner.setImages(gDesInfor.getBanners())
                .setImageLoader(new GlideImageLoader())
                .start();
    }
        /**
         * 详情描述图片
         */
        public void showImgDes(){
                imgDes.removeAllViews();
                for (int index = 0; index < gDesInfor.getDetails().size(); index++) {
                    imgDes.setVisibility(View.VISIBLE);
                    View view = View.inflate(mContext, R.layout.gallery_item, null);
                    final int width = ScreenUtil.getWindowsWidth((Activity) mContext) - ScreenUtil.dip2px(mContext, 10);
                    final ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
                    Glide.with(this)//activty
                            .load(gDesInfor.getDetails().get(index))
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                                    // Do something with bitmap here.
                                    int loadHeight = bitmap.getHeight(); //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
                                    int loadwidht = bitmap.getWidth();
                                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                                    params.width = width;
                                    params.height = (width * loadHeight) / loadwidht;
                                    params.gravity = Gravity.CENTER_HORIZONTAL;
                                    imageView.setLayoutParams(params);
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                    imgDes.addView(view);
                }
            }

    /**
     * 详情参数描述
     */
    public void showimgParams() {
        imgParamsDes.removeAllViews();
        for (int index = 0; index < gDesInfor.getParams().size(); index++) {
            imgParamsDes.setVisibility(View.VISIBLE);
            View view = View.inflate(mContext, R.layout.gallery_item, null);
            final int width = ScreenUtil.getWindowsWidth((Activity) mContext) - ScreenUtil.dip2px(mContext, 10);
            final ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            Glide.with(this)//activty
                    .load(gDesInfor.getParams().get(index))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            // Do something with bitmap here.
                            int loadHeight = bitmap.getHeight(); //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
                            int loadwidht = bitmap.getWidth();
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                            params.width = width;
                            params.height = (width * loadHeight) / loadwidht;
                            params.gravity = Gravity.CENTER_HORIZONTAL;
                            imageView.setLayoutParams(params);
                            imageView.setImageBitmap(bitmap);
                        }
                    });
            imgParamsDes.addView(view);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
    //    /**
    //     * 详情参数描述
    //     */

    /**
     * 显示评论
     */
    public void showComment() {
        if (commentlists != null && commentlists.size() > 0) {
            lin_comment_one.setVisibility(View.VISIBLE);
            tv_empty.setVisibility(View.GONE);
            Comment comment = commentlists.get(0);
            if (StringUtils.isNotEmpty(comment.getRealName()))
                tv_comment_name.setText(comment.getRealName());
            if (StringUtils.isNotEmpty(comment.getContent()))
                ;
            tv_comment_des.setText(comment.getContent());
            if (StringUtils.isNotEmpty(comment.getCreateDate()))
                ;
            tv_comment_time.setText(comment.getCreateDate());
            ratingBar.setStarRating(comment.getScore());
            if (StringUtils.isNotEmpty(comment.getUserImg())) {
                GlidUtils.loadCircleImageView(this, comment.getUserImg(), iv_comment_icon);
            }
        } else {
            lin_comment_one.setVisibility(View.GONE);
            tv_empty.setVisibility(View.VISIBLE);
        }


    }

    /**
     * 优惠券
     */
    protected void handlerCouPons(String jsonstr) {

        Map<String, String> map = JSON.parseObject(jsonstr, new TypeReference<Map<String, String>>() {
        });
        String liststr = map.get("list");
        couponEntities = JSON.parseObject(liststr, new TypeReference<List<CouponEntity>>() {
        });
        if (couponEntities != null && couponEntities.size() > 0) {
            linAllCoupon.setVisibility(View.VISIBLE);
            linPingtai.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addLisener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvProParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sc_produce.post(new Runnable() {
                    @Override
                    public void run() {
                        int[] location = new int[2];
                        re_params.getLocationOnScreen(location);
                        int totalHeight = location[1];
                        MyLogUtils.i("kankan", "查看param高度" + totalHeight);
                        MyLogUtils.i("kankan", "查看sc_produce高度" + sc_produce.getChildAt(0).getMeasuredHeight());
                        sc_produce.smoothScrollTo(0, totalHeight);
                    }
                });
            }
        });

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BigImagePagerActivity.startImagePagerActivity((Activity) mContext, gDesInfor.getBanners(), position);

            }
        });
        dialogBuy.setSubmitlistener(new DialogBuyProduct.SubmitDateListener() {

            @Override
            public void choice(List<String> ids, int number, double choiceprice) {
                MyLogUtils.i("options", "选择id" + JSON.toJSONString(ids) + "购物车数目" + number + "\r\n");

                addCarlables = ids;
                addcarNumber = number;
                if (frompage == 1) {
                    if (StringUtils.isNotEmpty(biz.getScore())) {
                        double score = Double.valueOf(biz.getScore());
                        MyLogUtils.i("jifen", "我有的积分" + score + "需要积分" + choiceprice * number);
                        if (score < choiceprice * number) {
                            showShortToast("积分不足");
                            return;
                        }
                    }
                }
                loadDate(REQUEST_GOOD_DETAIL_ADD_CAR);

            }

            @Override
            public void choiceIcon(String url) {
                List<String> lists = new ArrayList<String>();
                lists.add(url);
                BigImagePagerActivity.startImagePagerActivity((Activity) mContext, lists, 0);
            }
        });
        tvBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tologin(new LogincallBack() {
                    @Override
                    public void loginsuccess() {
                        if (attributeslists == null || attributeslists.size() == 0) {
                            return;
                        }
                        if (dialogBuy != null) {
                            isbuy = "1";
                            dialogBuy.setDetail(attributeslists, gDesInfor);
                            dialogBuy.show();
                        }
                    }
                });

            }
        });
        tvAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tologin(new LogincallBack() {
                    @Override
                    public void loginsuccess() {
                        loadDate(REQUEST_GOOD_COUPON);
                        loadDate(REQUEST_GOOD_DETAIL_PROPERTY);
                        if (attributeslists == null || attributeslists.size() == 0) {
                            return;
                        }
                        if (dialogBuy != null) {
                            isbuy = "0";
                            dialogBuy.setDetail(attributeslists, gDesInfor);
                            dialogBuy.show();
                        }
                    }
                });
            }
        });
        dialogBuy.setOneChangeToTwoChangeLisener(new DialogBuyProduct.OneChangeToTwoChangeLisener() {
            @Override
            public void updateTwoTag(String attribteValueId) {
                choicIds.clear();
                choicIds.add(attribteValueId);
                type = 1;
                loadDate(REQUEST_GOOD_DETAIL_GETSTOCKLABEL);
            }

            @Override
            public void updateOneTag(String attribteValueID) {
                MyLogUtils.i("kankan", "更新==第一个");
                choicIds.clear();
                choicIds.add(attribteValueID);
                type = 0;
                loadDate(REQUEST_GOOD_DETAIL_GETSTOCKLABEL);
            }

            @Override
            public void makequrePrice(List<String> ids) {
                choicIdstwo = ids;
                loadDate(REQUEST_MEAK_SURE_MESSURE);
            }
        });
        reCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tologin(new LogincallBack() {
                    @Override
                    public void loginsuccess() {
                        loadDate(REQUEST_GOOD_DETAIL_ADDCOLLECTION);
                    }
                });

            }
        });
        reGoodCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tologin(new LogincallBack() {
                    @Override
                    public void loginsuccess() {
                        enterPage(GoodCarActivity.class);
                    }
                });

            }
        });
        tvScoreBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attributeslists == null || attributeslists.size() == 0) {
                    return;
                }

                if (dialogBuy != null) {
                    isbuy = "1";
                    dialogBuy.setDetail(attributeslists, gDesInfor, 1);
                    dialogBuy.show();
                }
            }
        });

        tvGetCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttomDialog == null)
                    buttomDialog = new ButtomDialog(mContext);
                buttomDialog.setViewListener(new ButtomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        initdialogView(v);
                    }
                }).setContentView(R.layout.activity_coupon_dialog);
                buttomDialog.show();
            }
        });
    }

    private CouponAdapterdetail CouponAdapter;

    private void initdialogView(View v) {
        try {

            CouponAdapter = new CouponAdapterdetail(mContext, couponEntities, CouponAdapterdetail.GET_COUPON);
            final NoScrollListView couponviewlist = v.findViewById(R.id.listview_coupon_one);
            TextView tvComplete = v.findViewById(R.id.tv_complete);
            couponviewlist.setAdapter(CouponAdapter);
            CouponAdapter.setAcceptLisener(new CouponAdapterdetail.AcceptLisener() {
                @Override
                public void clickAccept(final int position) {

                    couponid_choice = couponEntities.get(position).getId();
                    loadDate(REQUEST_GOOD_GET_COUPON);
                }
            });

            tvComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttomDialog.dismiss();
                }
            });
        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }
}
