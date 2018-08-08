package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanliang
 * on 2018/7/17 10:18
 */

public class OderItemInfor implements Serializable {
    private static final long serialVersionUID = 66533L;
    /**
     * liInIn : [{"img":"http://119.23.172.36:8089/20180709/e43e65510c04450a97c80d27b897fa7f.jpeg","productLabels":{"颜色":"红色","尺寸":"大码"},"indentInfo":{"id":"32be5cc3295640ca84fbcfd346863177","indentId":"ba11cbb8f5cf4e5bb2ba7aa9fa461a4a","productId":"5bc155a1378a496b86ac31b5441df3ae","subProductId":"f29fc116f274454984013b7b9bb90532","productLabels":"{\"颜色\":\"红色\",\"尺寸\":\"大码\"}","productName":"测试商品","count":2,"price":1,"status":0,"commentStatus":null},"type":1},{"img":"http://119.23.172.36:8089/20180713/210864a9cfaa47408f74da917ace515a_300.png","productLabels":{"颜色":"玫瑰金","内存":"64G"},"indentInfo":{"id":"b8e02c5f831d4141b17a8d49941af6af","indentId":"ba11cbb8f5cf4e5bb2ba7aa9fa461a4a","productId":"1a294e0434be4365a1864a5f16cffbf3","subProductId":"1cde3d9077ff4c5197e13462f61ce77b","productLabels":"{\"颜色\":\"玫瑰金\",\"内存\":\"64G\"}","productName":"华为p20","count":8,"price":3099,"status":0,"commentStatus":null},"type":1}]
     * indent : {"id":"ba11cbb8f5cf4e5bb2ba7aa9fa461a4a","orderNo":"201807170956561032","totalPrice":24794,"userId":"a8c953fba4a845679b32ab980df2c070","couponId":null,"userRealName":"遇见感觉","shopId":null,"shopName":null,"tradeNo":null,"indentStatus":0,"transportStatus":null,"transportNo":null,"transportName":"晏亮","transportPhone":"17671689387","transportAddress":"湖北省 武汉市 东湖高新技术开发区光谷软件园","expressCompanyCode":null,"createDate":1531792616000,"buyerMsg":"","paymentType":null,"isAnonymous":0,"realPay":24794,"discusPay":0}
     */

    private IndentBean indent;
    private List<LiInInBean> liInIn;

    public static OderItemInfor objectFromData(String str) {

        return new Gson().fromJson(str, OderItemInfor.class);
    }

    public IndentBean getIndent() {
        return indent;
    }

    public void setIndent(IndentBean indent) {
        this.indent = indent;
    }

    public List<LiInInBean> getLiInIn() {
        return liInIn;
    }

    public void setLiInIn(List<LiInInBean> liInIn) {
        this.liInIn = liInIn;
    }

    public static class IndentBean implements Serializable {
        private static final long serialVersionUID = 66534L;
        /**
         * id : ba11cbb8f5cf4e5bb2ba7aa9fa461a4a
         * orderNo : 201807170956561032
         * totalPrice : 24794
         * userId : a8c953fba4a845679b32ab980df2c070
         * couponId : null
         * userRealName : 遇见感觉
         * shopId : null
         * shopName : null
         * tradeNo : null
         * indentStatus : 0
         * transportStatus : null
         * transportNo : null
         * transportName : 晏亮
         * transportPhone : 17671689387
         * transportAddress : 湖北省 武汉市 东湖高新技术开发区光谷软件园
         * expressCompanyCode : null
         * createDate : 1531792616000
         * buyerMsg :
         * paymentType : null
         * isAnonymous : 0
         * realPay : 24794
         * discusPay : 0
         * type:2
         */

        private String id;
        private String orderNo;
        private String totalPrice;
        private String userId;
        private Object couponId;
        private String userRealName;
        private Object shopId;
        private Object shopName;
        private Object tradeNo;
        private int indentStatus;
        private String transportStatus = "-1";
        private Object transportNo;
        private String transportName;
        private String transportPhone;
        private String transportAddress;
        private Object expressCompanyCode;
        private long createDate;
        private String buyerMsg;
        private Object paymentType;
        private int isAnonymous;
        private String realPay;
        private int discusPay;
        private int type;

        public static IndentBean objectFromData(String str) {

            return new Gson().fromJson(str, IndentBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getCouponId() {
            return couponId;
        }

        public void setCouponId(Object couponId) {
            this.couponId = couponId;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public Object getShopId() {
            return shopId;
        }

        public void setShopId(Object shopId) {
            this.shopId = shopId;
        }

        public Object getShopName() {
            return shopName;
        }

        public void setShopName(Object shopName) {
            this.shopName = shopName;
        }

        public Object getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(Object tradeNo) {
            this.tradeNo = tradeNo;
        }

        public int getIndentStatus() {
            return indentStatus;
        }

        public void setIndentStatus(int indentStatus) {
            this.indentStatus = indentStatus;
        }

        public String getTransportStatus() {
            return transportStatus;
        }

        public void setTransportStatus(String transportStatus) {
            this.transportStatus = transportStatus;
        }

        public Object getTransportNo() {
            return transportNo;
        }

        public void setTransportNo(Object transportNo) {
            this.transportNo = transportNo;
        }

        public String getTransportName() {
            return transportName;
        }

        public void setTransportName(String transportName) {
            this.transportName = transportName;
        }

        public String getTransportPhone() {
            return transportPhone;
        }

        public void setTransportPhone(String transportPhone) {
            this.transportPhone = transportPhone;
        }

        public String getTransportAddress() {
            return transportAddress;
        }

        public void setTransportAddress(String transportAddress) {
            this.transportAddress = transportAddress;
        }

        public Object getExpressCompanyCode() {
            return expressCompanyCode;
        }

        public void setExpressCompanyCode(Object expressCompanyCode) {
            this.expressCompanyCode = expressCompanyCode;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getBuyerMsg() {
            return buyerMsg;
        }

        public void setBuyerMsg(String buyerMsg) {
            this.buyerMsg = buyerMsg;
        }

        public Object getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(Object paymentType) {
            this.paymentType = paymentType;
        }

        public int getIsAnonymous() {
            return isAnonymous;
        }

        public void setIsAnonymous(int isAnonymous) {
            this.isAnonymous = isAnonymous;
        }

        public String getRealPay() {
            return realPay;
        }

        public void setRealPay(String realPay) {
            this.realPay = realPay;
        }

        public int getDiscusPay() {
            return discusPay;
        }

        public void setDiscusPay(int discusPay) {
            this.discusPay = discusPay;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class LiInInBean implements Serializable {
        private static final long serialVersionUID = 66535L;
        /**
         * img : http://119.23.172.36:8089/20180709/e43e65510c04450a97c80d27b897fa7f.jpeg
         * productLabels : {"颜色":"红色","尺寸":"大码"}
         * indentInfo : {"id":"32be5cc3295640ca84fbcfd346863177","indentId":"ba11cbb8f5cf4e5bb2ba7aa9fa461a4a","productId":"5bc155a1378a496b86ac31b5441df3ae","subProductId":"f29fc116f274454984013b7b9bb90532","productLabels":"{\"颜色\":\"红色\",\"尺寸\":\"大码\"}","productName":"测试商品","count":2,"price":1,"status":0,"commentStatus":null}
         * type : 1
         */

        private String img;
        private String productLabels;
        private IndentInfoBean indentInfo;
        private int type;

        public static LiInInBean objectFromData(String str) {

            return new Gson().fromJson(str, LiInInBean.class);
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getProductLabels() {
            return productLabels;
        }

        public void setProductLabels(String productLabels) {
            this.productLabels = productLabels;
        }

        public IndentInfoBean getIndentInfo() {
            return indentInfo;
        }

        public void setIndentInfo(IndentInfoBean indentInfo) {
            this.indentInfo = indentInfo;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }


        public static class IndentInfoBean implements Serializable {
            private static final long serialVersionUID = 66535L;
            /**
             * id : 32be5cc3295640ca84fbcfd346863177
             * indentId : ba11cbb8f5cf4e5bb2ba7aa9fa461a4a
             * productId : 5bc155a1378a496b86ac31b5441df3ae
             * subProductId : f29fc116f274454984013b7b9bb90532
             * productLabels : {"颜色":"红色","尺寸":"大码"}
             * productName : 测试商品
             * count : 2
             * price : 1
             * status : 0
             * commentStatus : null
             */

            private String id;
            private String indentId;
            private String productId;
            private String subProductId;
            private String productLabels;
            private String productName;
            private int count;
            private String price;
            private int status;
            private Object commentStatus;

            public static IndentInfoBean objectFromData(String str) {

                return new Gson().fromJson(str, IndentInfoBean.class);
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIndentId() {
                return indentId;
            }

            public void setIndentId(String indentId) {
                this.indentId = indentId;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getSubProductId() {
                return subProductId;
            }

            public void setSubProductId(String subProductId) {
                this.subProductId = subProductId;
            }

            public String getProductLabels() {
                return productLabels;
            }

            public void setProductLabels(String productLabels) {
                this.productLabels = productLabels;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getCommentStatus() {
                return commentStatus;
            }

            public void setCommentStatus(Object commentStatus) {
                this.commentStatus = commentStatus;
            }
        }
    }
}
