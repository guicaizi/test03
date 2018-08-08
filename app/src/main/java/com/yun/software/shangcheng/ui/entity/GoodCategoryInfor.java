package com.yun.software.shangcheng.ui.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by yanliang
 * on 2018/6/28 10:57
 */

public class GoodCategoryInfor {

    /**
     * createDate : null
     * createDateName :
     * id : 1da0d2a792684aa597451a3d349094be
     * name : 手机
     * pid : null
     * userId : null
     * description : null
     * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg
     * pname : null
     * children : [{"createDate":null,"createDateName":"","id":"2","name":"华为","pid":null,"userId":null,"description":null,"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg","pname":null,"children":[{"createDate":null,"createDateName":"","id":"10","name":"荣耀P7","pid":null,"userId":null,"description":null,"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg","pname":null,"children":null},{"createDate":null,"createDateName":"","id":"11","name":"荣耀P8","pid":null,"userId":null,"description":null,"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg","pname":null,"children":null},{"createDate":null,"createDateName":"","id":"12","name":"荣耀P9","pid":null,"userId":null,"description":null,"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg","pname":null,"children":null}]}]
     */

    private Object createDate;
    private String createDateName;
    private String id;
    private String name;
    private Object pid;
    private Object userId;
    private Object description;
    private String img;
    private Object pname;
    private List<ChildrenBeanX> children;

    public static GoodCategoryInfor objectFromData(String str) {

        return new Gson().fromJson(str, GoodCategoryInfor.class);
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateName() {
        return createDateName;
    }

    public void setCreateDateName(String createDateName) {
        this.createDateName = createDateName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Object getPname() {
        return pname;
    }

    public void setPname(Object pname) {
        this.pname = pname;
    }

    public List<ChildrenBeanX> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBeanX> children) {
        this.children = children;
    }

    public static class ChildrenBeanX {
        /**
         * createDate : null
         * createDateName :
         * id : 2
         * name : 华为
         * pid : null
         * userId : null
         * description : null
         * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg
         * pname : null
         * children : [{"createDate":null,"createDateName":"","id":"10","name":"荣耀P7","pid":null,"userId":null,"description":null,"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg","pname":null,"children":null},{"createDate":null,"createDateName":"","id":"11","name":"荣耀P8","pid":null,"userId":null,"description":null,"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg","pname":null,"children":null},{"createDate":null,"createDateName":"","id":"12","name":"荣耀P9","pid":null,"userId":null,"description":null,"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg","pname":null,"children":null}]
         */

        private Object createDate;
        private String createDateName;
        private String id;
        private String name;
        private Object pid;
        private Object userId;
        private Object description;
        private String img;
        private Object pname;
        private List<ChildrenBean> children;

        public static ChildrenBeanX objectFromData(String str) {

            return new Gson().fromJson(str, ChildrenBeanX.class);
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public String getCreateDateName() {
            return createDateName;
        }

        public void setCreateDateName(String createDateName) {
            this.createDateName = createDateName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getPid() {
            return pid;
        }

        public void setPid(Object pid) {
            this.pid = pid;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getPname() {
            return pname;
        }

        public void setPname(Object pname) {
            this.pname = pname;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * createDate : null
             * createDateName :
             * id : 10
             * name : 荣耀P7
             * pid : null
             * userId : null
             * description : null
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505729345506&di=9fd4f26d1a7fa522305c369133ec9d7f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01da915922a1bdb5b3086ed43ffcc7.jpg%40900w_1l_2o_100sh.jpg
             * pname : null
             * children : null
             */

            private Object createDate;
            private String createDateName;
            private String id;
            private String name;
            private Object pid;
            private Object userId;
            private Object description;
            private String img;
            private Object pname;
            private Object children;

            public static ChildrenBean objectFromData(String str) {

                return new Gson().fromJson(str, ChildrenBean.class);
            }

            public Object getCreateDate() {
                return createDate;
            }

            public void setCreateDate(Object createDate) {
                this.createDate = createDate;
            }

            public String getCreateDateName() {
                return createDateName;
            }

            public void setCreateDateName(String createDateName) {
                this.createDateName = createDateName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getPid() {
                return pid;
            }

            public void setPid(Object pid) {
                this.pid = pid;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public Object getPname() {
                return pname;
            }

            public void setPname(Object pname) {
                this.pname = pname;
            }

            public Object getChildren() {
                return children;
            }

            public void setChildren(Object children) {
                this.children = children;
            }
        }
    }
}
