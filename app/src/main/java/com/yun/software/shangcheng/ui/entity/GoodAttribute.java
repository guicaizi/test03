package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by yanliang
 * on 2018/7/2 14:01
 */

public class GoodAttribute {

    /**
     * id : 65f051c98b6f4052900177f0cbcf06b7
     * shopId : null
     * productId : 1
     * name : 款式
     * status : 0
     * labels : [{"id":"1e96a9b7e5004f0ba77129bb0aedbf7d","propertyId":"65f051c98b6f4052900177f0cbcf06b7","name":"B","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505812811152&di=9e6461f749e8a5dc2d289e16d2a6ee84&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F16%2F20151216120350_FZJi8.jpeg","status":1,"imgSwitch":1,"stock":295},{"id":"6474ea21154040429d998d9a96b45dc9","propertyId":"65f051c98b6f4052900177f0cbcf06b7","name":"E","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505812811152&di=9e6461f749e8a5dc2d289e16d2a6ee84&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F16%2F20151216120350_FZJi8.jpeg","status":1,"imgSwitch":1,"stock":286},{"id":"7cf3145ea2e04356916bedce86b04dca","propertyId":"65f051c98b6f4052900177f0cbcf06b7","name":"G","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505812811152&di=9e6461f749e8a5dc2d289e16d2a6ee84&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F16%2F20151216120350_FZJi8.jpeg","status":1,"imgSwitch":1,"stock":299},{"id":"871675a3b5914659b2eb24c284e93a08","propertyId":"65f051c98b6f4052900177f0cbcf06b7","name":"C","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505812811152&di=9e6461f749e8a5dc2d289e16d2a6ee84&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F16%2F20151216120350_FZJi8.jpeg","status":1,"imgSwitch":1,"stock":0},{"id":"a462372976474819887ecabf6da2fede","propertyId":"65f051c98b6f4052900177f0cbcf06b7","name":"A","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505812811152&di=9e6461f749e8a5dc2d289e16d2a6ee84&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F16%2F20151216120350_FZJi8.jpeg","status":1,"imgSwitch":1,"stock":0},{"id":"c72e2815c3ee413981222457072915e2","propertyId":"65f051c98b6f4052900177f0cbcf06b7","name":"D","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505812811152&di=9e6461f749e8a5dc2d289e16d2a6ee84&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F16%2F20151216120350_FZJi8.jpeg","status":1,"imgSwitch":1,"stock":0},{"id":"ef361eed5f7a4900b6cd079229a119d5","propertyId":"65f051c98b6f4052900177f0cbcf06b7","name":"L","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505812811152&di=9e6461f749e8a5dc2d289e16d2a6ee84&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F16%2F20151216120350_FZJi8.jpeg","status":1,"imgSwitch":1,"stock":0}]
     */

    private String id;
    private Object shopId;
    private String productId;
    private String name;
    private int status;
    private String price;
    private String defalueimg;
    private List<LabelsBean> labels;

    public static GoodAttribute objectFromData(String str) {

        return new Gson().fromJson(str, GoodAttribute.class);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDefalueimg() {
        return defalueimg;
    }

    public void setDefalueimg(String defalueimg) {
        this.defalueimg = defalueimg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getShopId() {
        return shopId;
    }

    public void setShopId(Object shopId) {
        this.shopId = shopId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<LabelsBean> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelsBean> labels) {
        this.labels = labels;
    }

    public static class LabelsBean {
        /**
         * id : 1e96a9b7e5004f0ba77129bb0aedbf7d
         * propertyId : 65f051c98b6f4052900177f0cbcf06b7
         * name : B
         * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505812811152&di=9e6461f749e8a5dc2d289e16d2a6ee84&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201512%2F16%2F20151216120350_FZJi8.jpeg
         * status : 1
         * imgSwitch : 1
         * stock : 295
         */

        private String id;
        private String propertyId;
        private String name;
        private String img;
        private int status;
        private int imgSwitch;
        private int stock;

        public static LabelsBean objectFromData(String str) {

            return new Gson().fromJson(str, LabelsBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getImgSwitch() {
            return imgSwitch;
        }

        public void setImgSwitch(int imgSwitch) {
            this.imgSwitch = imgSwitch;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }
}
