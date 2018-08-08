/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yun.software.shangcheng.api;

public class ApiConstants {
    /**
     * 服务器地址.
     */
//    public static final String SERVER = "http://119.23.172.36:10001";
    public static final String SERVER = "http://shixiangyoupin.com:8080";
    public static final  String addressname = "alladdress.json";
//    public static final String SERVER = "http://192.168.1.144:8080";
    //临时web测试网址
    public static final  String  FORMAL_HOST="http://www.zrytech.com/NopShop/";
    public static  String TOKEN ="";
    public static  String HEADER ="";
    //首页
    /**
     * ========================================首页======================================
     * 首页广告图
     */
    public static final String FIRST_PAGER_BANNERS = SERVER + "/device/banner/list";
    /**
     *
     * 精品 热销
     */
    public static final String FIRST_PAGER_ALLSALE = SERVER + "/device/product/list";
    /**
     * 首页分类
     */
    public static final String FIRST_PAGER_CATEGORY = SERVER + "/device/kind/listRoot";
    /**
     * 首页全部分类
     */
    public static final String FIRST_PAGER_ALL_CATEGORY = SERVER + "/device/kind/menu";
    //
    /**
     * 0 优惠券================================================详情++++++++++++++++++++++++
     */
    public static final String GOOD_COUPON = SERVER + "/device/Coupon/list";
    /**
     * 1产品图片详情
     */
    public static final String GOOD_DETAIL_BANNERS = SERVER + "/device/product/getDetail/";
    /**
     * 2详情评论
     */
    public static final String GOOD_DETAIL_PINGLUN = SERVER + "/device/comment/commentList";
    /**
     * 3产品信息
     */
    public static final String GOOD_DETAIL_INFOR = SERVER + "/device/product/get/";
    public static final String GOOD_GET_COUPON = SERVER + "/device/userCoupon/insert";

    /**
     * 4产品详情信息
     *url：/device/productDetail/get/
     */
    public static final String GOOD_DETAIL_PRODUCTDETAI = SERVER + "/device/productDetail/get/";
    /**
     * 5 品属性列表
     *url：/device/property/list/1
     */
    public static final String GOOD_DETAIL_PROPERTY = SERVER + "/device/property/list/";
    /**
     * 6 加入购物车
     *url：/device/productDetail/get/1
     */
    public static final String GOOD_DETAIL_ADD_CAR = SERVER + "/device/shopCar/add";
    /**
     * 7 立即购买
     *url：/device/productDetail/get/1
     */
    public static final String GOOD_DETAIL_BUYNOW = SERVER + "/device/shopCar/createIndent";
    /**
     * 8 加入收藏
     *url：/device/collection/addCollection
     */
    public static final String GOOD_DETAIL_ADDCOLLECTION = SERVER + "/device/collection/addCollection";
    /**
     * 9 根据产品ID和产品标签查价格和库存
     *url：/device/productDetail/get/1
     */
    public static final String GOOD_DETAIL_GETSTOCK_OR_PRICE = SERVER + "/device/subProduct/get";
    /**
     * 10 根据产品id和产品标签有库存的标签
     *url：/device/productDetail/get/1
     */
    public static final String GOOD_DETAIL_GETSTOCKLABEL = SERVER + "/device/subProduct/getStockLabel";
    /**
     * 10 获取购物车数目
     *url：/device/shopCar/total
     */
    public static final String GET_GOOD_CAR_NUMBER = SERVER + "/device/shopCar/total";

    /**
     * ===========================================个人中心=================================================
     * 1个人信息
     *url：/device/user/get
     */
    public static final String USERINFOR = SERVER + "/device/user/get";
    public static final String USERINFORTWO = SERVER + "/device/customer/get";
    /**
     *登录
     */
    public static final String LOGIN = SERVER + "/device/customer/login";
    /**
     *2修改密码
     */
    public static final String CHANGE_PASSWORD = SERVER + "/device/customer/modify";
    /**
     *3订单状态
     */
    public static final String ODER_STATE = SERVER + "/device/indent/getCount";
    /**
     *4我的收藏
     */
    public static final String GOOD_COLLECTION = SERVER + "/device/collection/getCollection";
    /**
     *5我的优惠券
     */
    public static final String GOOD_MY_COUPON = SERVER + "/device/userCoupon/list";

    /**
     *6我的物流消息
     */
    public static final String GOOD_MESSAGE_TYPE = SERVER + "/device/message/lastOne";
    /**
     *7我的物流消息
     */
    public static final String GOOD_MESSAGE_LIST = SERVER + "/device/message/list";
    /**
     *关于我们
     */
    public static final String GOOD_ABOUT_US = SERVER + "/device/dict/getByCode";




    /**
     * ================================地址管理==================================================
     *1地址管理
     */
    public static final String ADDRESS_MANAGER = SERVER + "/device/userAddress/find";
    /**
     *2删除地址
     */
    public static final String ADDRESS_DELETE = SERVER + "/device/userAddress/delete/";
    /**
     *3添加地址
     */
    public static final String ADDRESS_ADD = SERVER + "/device/userAddress/create";
    /**
     *4修改地址
     */
    public static final String ADDRESS_EDIT = SERVER + "/device/userAddress/update";

    /**
     * ================================购物车==================================================
     *购物车列表
     */
    public static final String GOOD_CAR_LIST = SERVER + "/device/shopCar/list";
    /**
     *更新购物车
     */
    public static final String GOOD_UPDATE_NUMBER = SERVER + "/device/shopCar/updateCount";
    /**
     *删除产品
     */
    public static final String GOOD_CAR_DELETE = SERVER + "/device/shopCar/delete";
    /**
     *
     * * ================================确认订单==============================================
     *查询订单列表
     */
    public static final String GOOD_COMMIT_ORDER = SERVER + "/device/shopCar/shopCarDetail";
    /**
     *获取默认地址
     */
    public static final String GOOD_DEFAULT_ADDRESS = SERVER + "/device/userAddress/get";
    /**
     *提交订单
     * device/indent/create/by/shopCarIds
     */
    public static final String GOOD_SUBMIT_ODERS = SERVER + "/device/indent/create/by/shopCarIds";
    public static final String GOOD_SUBMIT_SCORE_PAY= SERVER + "/device/UserScore/pay";
    public static final String GOOD_SUBMIT_WX_PAY= SERVER + "/pay/wxpay/shop/order";
    /**
     *
     *====================================订单状态==========================================
     * 订单列表
     */
    public static final String GOOD_ODER_LIST = SERVER + "/device/indent/listByAndroid";
    public static final String GOOD_CANCLE_ORDER = SERVER + "/device/indent/updateOneIndentStatus";
    public static final String GOOD_RECIVE_STATE = SERVER + "/device/indent/updateOneTransportStatus";
    /**
     *会员中心
     */
    public static final String VIP_LIST_INFOR = SERVER + "/device/member/list";
    public static final String VIP_VER_CODE = SERVER + "/device/member/verifyCode";
    public static final String VIP_PAY = SERVER + "/pay/wxpay/openvip/order";
    public static final String VIP_PAY_ODERNO = SERVER + "/device/member/buy";
    public static final String RETURN_MONEY = SERVER + "/device/indent/apply";


    /**
     *======================================登录 注册流程==================================
     * 微信注册
     */
    public static final String WX_REGIST = SERVER + "/wechat/login";
    /**
     * 手机号注册
     */
    public static final String GOOD_REGIST = SERVER + "/device/customer/register";
    /**
     * 上传接口
     */
    public static final String GOOD_IMAGE_UPLOAD = SERVER + "/image/uploadByAndroid";

    /**
     *用户注册
     */
    public static final String REGIST_CODE = SERVER + "/device/customer/verifyCode";
    /**
     *忘记密码
     */
    public static final String REGIST_FOURGET_PASSWORD = SERVER + "/device/customer/forget";



    
    

}

