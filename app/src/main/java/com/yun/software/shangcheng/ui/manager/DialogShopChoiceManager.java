package com.yun.software.shangcheng.ui.manager;

import com.yun.software.buypop.tabGround.TagBean;
import com.yun.software.shangcheng.ui.entity.GoodAttribute;
import com.yun.software.shangcheng.ui.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanliang
 * on 2018/6/14 16:32
 */

public class DialogShopChoiceManager {
    private List<TagBean> oneTags;
    private List<TagBean> defaultOneTags;
    private List<TagBean> twoTags;
    private List<TagBean> defaultTwoTags;
    private List<String> oneTitles;
    private List<String> twoTitles;
    private List<GoodAttribute> allattrs;
    private List<GoodAttribute.LabelsBean> oneValues;
    private List<GoodAttribute.LabelsBean> twoValues;
    private boolean isTagoneCheck=false;
    private boolean isTagtwoCheck=false;
    private String oneTagChoiceid;
    private String towTagChoiceid;

    /**
     *默认图片展示
     */
    private String choiceDefaultImgDisplay;
    /**
     *购物车数目
     */
    private int carNumber=1;
    private String defaultPdName;
    private String defaultPdPrice;
    private String oneAttrName;
    private String twoAttrName;
    /**
     *选择属性一的值
     */
    private String choiceAttrOneValue;
    /**
     *选择属性二的值
     */
    private String choiceAttrTwoValue;
    /**
     *当前选择情况
     */
    private String nowChoiceStatestr;
    /**
     *选择后图片变化
     */
    private String choiceUrl;
    private String choiceUrltwo;

    private int type=0;

    public String getChoiceUrl() {
        return choiceUrl;
    }

    public void setChoiceUrl(String choiceUrl) {
        this.choiceUrl = choiceUrl;
    }

    public String getChoiceUrlTwo() {
        return choiceUrltwo;
    }

    public void setChoiceUrlTwo(String choiceUrltwo) {
        this.choiceUrltwo = choiceUrltwo;
    }
    public void setType(int type){
        this.type=type;
    }


    public String getNowChoiceStatestr() {

        if (StringUtils.isNotEmpty(getTwoAttrName())) {
            if (StringUtils.isEmpty(getChoiceAttrOneValue()) && StringUtils.isEmpty(getChoiceAttrTwoValue())) {
                return "请选择" + getOneAttrName() + "," + getTwoAttrName();
            } else if (StringUtils.isNotEmpty(getChoiceAttrOneValue()) && StringUtils.isEmpty(getChoiceAttrTwoValue())) {
                return getChoiceAttrOneValue()+ ",请选择" + getTwoAttrName();
            } else if (StringUtils.isNotEmpty(getChoiceAttrTwoValue()) && StringUtils.isEmpty(getChoiceAttrOneValue())) {
                return "请选择" + getOneAttrName()+ "," +getChoiceAttrTwoValue();
            } else {
               return getChoiceAttrOneValue() + "," + getChoiceAttrTwoValue();
            }
        } else {
            if (StringUtils.isNotEmpty(getOneAttrName())) {
                if (StringUtils.isEmpty(getChoiceAttrOneValue())) {
                   return  "请选择" +getTwoAttrName();
                } else {
                    return  getChoiceAttrOneValue();
                }
            } else {
               return " ";
            }
        }
    }

    public String getChoiceAttrOneValue() {
        return choiceAttrOneValue;
    }

    public void setChoiceAttrOneValue(String choiceAttrOneValue) {
        this.choiceAttrOneValue = choiceAttrOneValue;
    }

    public String getChoiceAttrTwoValue() {
        return choiceAttrTwoValue;
    }

    public void setChoiceAttrTwoValue(String choiceAttrTwoValue) {
        this.choiceAttrTwoValue = choiceAttrTwoValue;
    }

    public List<String> getOneTitles() {
        oneTitles=new ArrayList<>();
        for (TagBean tagBean : getOneTags()) {
            oneTitles.add(tagBean.getTitle());
        }
        return oneTitles;
    }

    public List<String> getTwoTitles() {
        twoTitles=new ArrayList<>();
        for (TagBean tagBean : getTwoTags()) {
            twoTitles.add(tagBean.getTitle());
        }
        return twoTitles;
    }

    public boolean isTagoneCheck() {
        return isTagoneCheck;
    }

    public void setTagoneCheck(boolean tagoneCheck) {
        isTagoneCheck = tagoneCheck;
    }

    public boolean isTagtwoCheck() {
        return isTagtwoCheck;
    }

    public void setTagtwoCheck(boolean tagtwoCheck) {
        isTagtwoCheck = tagtwoCheck;
    }


    public List<TagBean> getOneTags() {
        if(allattrs.size()>0){
            if(oneTags!=null&&oneTags.size()>0){
                return oneTags;
            }else{
                oneTags=new ArrayList<>();
                defaultOneTags=new ArrayList<>();
                List<GoodAttribute.LabelsBean> onevalues=allattrs.get(0).getLabels();
                if(oneTags!=null&&oneTags.size()>0)oneTags.clear();
                for (int i = 0; i < onevalues.size(); i++) {
                    //差价
//                    Double agio = (Double) onevalues.get(i).getPrice_adjustment();
                    //二级分类 标签
                    String typeTag =onevalues.get(i).getName();
                    //库存 这里默认使用最大值
                    int  stock = onevalues.get(i).getStock();
                    boolean  ishasStock=stock>0?true:false;
                    if (ishasStock){
                        oneTags.add(new TagBean(typeTag, 0, stock));
                        defaultOneTags.add(new TagBean(typeTag, 0, stock));
                    }
                    else{
                        oneTags.add(new TagBean(typeTag, 0, 0));
                        defaultOneTags.add(new TagBean(typeTag, 0, 0));
                    }

                }
            }
        }
        return oneTags;
    }

    public List<TagBean> getTwoTags() {
        if(allattrs.size()>1){
            if(twoTags!=null&&twoTags.size()>0){
                return twoTags;
            }else{
                twoTags=new ArrayList<>();
                defaultTwoTags=new ArrayList<>();

                List<GoodAttribute.LabelsBean> twovalues=allattrs.get(1).getLabels();
                for (int i = 0; i < twovalues.size(); i++) {
//                    Double agio = (Double) twovalues.get(i).getPrice_adjustment();
                    //二级分类 标签
                    String typeTag =twovalues.get(i).getName();
                    //库存 这里默认使用最大值
                    int  stock = twovalues.get(i).getStock();
                    boolean  ishasStock=stock>0?true:false;
                    if (ishasStock){
                        twoTags.add(new TagBean(typeTag, 0, stock));
                        defaultTwoTags.add(new TagBean(typeTag, 0, stock));
                    }

                    else{
                        twoTags.add(new TagBean(typeTag, 0, 0));
                        defaultTwoTags.add(new TagBean(typeTag, 0, 0));
                    }

                }
                return twoTags;
            }

        }
            return twoTags;
    }

    public void setTwoTags(List<TagBean> twoTags) {
        this.twoTags = twoTags;
    }

    public List<GoodAttribute> getAllattrs() {

        return allattrs;
    }

    public void setAllattrs(List<GoodAttribute> allattrs) {
        if(allattrs==null){
            this.allattrs=new ArrayList<GoodAttribute>();
        }else{
            this.allattrs=allattrs;
        }
    }
    public int getAllattributesize(){
        if(allattrs==null){
            return 0;
        }
        return allattrs.size();
    }


    public DialogShopChoiceManager() {


    }

    public String getChoiceDefaultImgDisplay() {
        return choiceDefaultImgDisplay;
    }

    public void setChoiceDefaultImgDisplay(String choiceDefaultImgDisplay) {
        this.choiceDefaultImgDisplay = choiceDefaultImgDisplay;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public void setOneTags(List<TagBean> oneTags) {
        this.oneTags = oneTags;
    }

    public String getDefaultPdName() {
        return defaultPdName;
    }

    public void setDefaultPdName(String defaultPdName) {
        this.defaultPdName = defaultPdName;
    }

    public String getDefaultPdPrice() {
        String defalutpaprices=null;
        if(type==0){
            defalutpaprices="￥"+defaultPdPrice;
        }else{
            defalutpaprices=defaultPdPrice+"分";
        }
        return defalutpaprices;
    }

    public void setDefaultPdPrice(String defaultPdPrice) {
        this.defaultPdPrice = defaultPdPrice;
    }


    public String getOneAttrName() {
        return oneAttrName;
    }

    public void setOneAttrName(String oneAttrName) {
        this.oneAttrName = oneAttrName;
    }

    public String getTwoAttrName() {
        return twoAttrName;
    }

    public void setTwoAttrName(String twoAttrName) {
        this.twoAttrName = twoAttrName;
    }

    public List<GoodAttribute.LabelsBean> getOneValues() {
        if(allattrs.size()>0){
            return allattrs.get(0).getLabels();
        }
        return new ArrayList<>();
    }

    public List<GoodAttribute.LabelsBean> getTwoValues() {
        if(allattrs.size()>1){
            return  allattrs.get(1).getLabels();
        }
        return new ArrayList<>();
    }
    /**
     *初始化tabtwo初始状态
     */
    public void initTabtwoState(List<String> alllists){

        for (int i = 0; i < getTwoTags().size(); i++) {
            getTwoTags().get(i).setAmount(0);
            for (int j = 0; j <alllists.size() ; j++) {
//                int id=StringUtils.toInt(alllists.get(j));
                if(getTwoValues().get(i).getId().equals(alllists.get(j))){
                    getTwoTags().get(i).setAmount(1000 );

                }
            }
        }
    }
    /**
     *初始化tabome初始状态
     */
    public  void initTagOneState(List<String> alllists){

        for (int i = 0; i < getOneTags().size(); i++) {
            getOneTags().get(i).setAmount(0);
            for (int j = 0; j <alllists.size() ; j++) {
                //   int id=StringUtils.toInt(alllists.get(j));
                if(getOneValues().get(i).getId().equals(alllists.get(j))){
                    getOneTags().get(i).setAmount(1000 );

                }
            }

        }
    }

    /**
     *设置标签一默认的库存
     */
    public void setdefautOneState() {
        for (int i = 0; i < defaultOneTags.size(); i++) {
            getOneTags().get(i).setAmount(defaultOneTags.get(i).getAmount());

        }

    }
    /**
     *设置标签二默认的库存
     */
    public void setdefautTwoState() {
        for (int i = 0; i < defaultTwoTags.size(); i++) {
            getTwoTags().get(i).setAmount(defaultTwoTags.get(i).getAmount());

        }

    }


    public String getattrSelect() {


        StringBuffer buffer = new StringBuffer();
        if (getAllattrs().size() == 0) {
            return "";
        }
        if (getAllattrs().size() > 1) {
            if (getOneValues() != null && getOneValues().size() > 0 && getTwoValues() != null && getTwoValues() .size() > 0) {
                for (int i = 0; i < getOneValues().size(); i++) {
                    if (getOneValues().get(i).getName().equals(getChoiceAttrOneValue())) {
                        buffer.append(getAllattrs().get(0).getId());
                        buffer.append(":");
                        buffer.append(getOneValues().get(i).getId());
                    }
                }
                for (int i = 0; i < getTwoValues() .size(); i++) {
                    if (getTwoValues() .get(i).getName().equals(getChoiceAttrTwoValue())) {
                        buffer.append(",");
                        buffer.append(getAllattrs().get(1).getId());
                        buffer.append(":");
                        buffer.append(getTwoValues() .get(i).getId());
                    }
                }
            } else {
                return "";
            }


        } else {
            if (getOneValues() != null && getOneValues().size() > 0) {
                for (int i = 0; i < getOneValues().size(); i++) {
                    if (getOneValues().get(i).getName().equals(getChoiceAttrOneValue())) {
                        buffer.append(getAllattrs().get(0).getId());
                        buffer.append(":");
                        buffer.append(getOneValues().get(i).getId());
                    }
                }
            } else {
                return "";
            }
        }

        return buffer.toString();

    }
    
    public String getAttributeXml(String key, String value) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        StringBuilder str = new StringBuilder();

//        <span class="brand-color">尺寸：<span class="col-brand"> 均码 </span></span>
        str.append("<span class=\"brand-color\">");
        str.append(key+"：");
        str.append("<span class=\"col-brand\">");
        str.append(" "+value+" ");
        str.append("</span></span>");
        return str.toString();
    }
    public String getAttributeTwoXml(String key, String value) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        str.append("<span>");
        str.append(key+"：");
        str.append("<span class=\"size\">");
        str.append(" "+value+" ");
        str.append("</span></span>");
        return str.toString();
    }
    public String  getChoiceStr(){
        if (StringUtils.isNotEmpty(getOneAttrName())) {
            String choiconeAttribute = getAttributeXml(getOneAttrName(), getChoiceAttrOneValue());
            if (StringUtils.isNotEmpty(getTwoAttrName())) {
                String choictwoAttribute = getAttributeTwoXml(getTwoAttrName(), getChoiceAttrTwoValue());
                return choiconeAttribute + choictwoAttribute;
            } else {
                return choiconeAttribute;
            }
        } else {
                 return "";

        }

    }

    public String getOneTagChoiceid() {
        return oneTagChoiceid;
    }

    public void setOneTagChoiceid(String oneTagChoiceid) {
        this.oneTagChoiceid = oneTagChoiceid;
    }

    public String getTwoTagChoiceid() {
        return towTagChoiceid;
    }

    public void setTwoTagChoiceid(String towTagChoiceid) {
        this.towTagChoiceid = towTagChoiceid;
    }
    /**
     *选择的id
     */ 
    public List<String> getChoiceIds(){
        List<String> ids=new ArrayList<String>();
        if(StringUtils.isNotEmpty(getOneTagChoiceid())){
            ids.add(getOneTagChoiceid());
        }
        if(StringUtils.isNotEmpty(getTwoTagChoiceid())){
            ids.add(getTwoTagChoiceid());
        }
        return ids;


    }

    public String getclaclutprice(List<String> prices) {
        String caclutestr="";

        if(prices.size()>1){
            if(type==0){
                caclutestr="￥ "+prices.get(0)+"~"+prices.get(prices.size()-1);
            }else{
                caclutestr=prices.get(0)+"~"+prices.get(prices.size()-1)+"分";
            }
        }else{
            if(type==0) {
                caclutestr="￥ " + prices.get(0);
            }else{
                caclutestr=prices.get(0)+"分";
            }
        }
        return caclutestr;
    }
    public static class  Builder{
        private List<GoodAttribute> allattrs;
        private int carNumber=1;
        private String defaultPdName;
        private String defaultPdPrice;
        private String oneAttrName;
        private String twoAttrName;
        private String choiceDefaultImgDisplay;
        private String choiceUrl;
        private static Builder builder;
        private Builder(){

        }
        public static Builder CreatBuilder(){
            builder=new Builder();
            return builder;
        }

        public Builder setAllattrs(List<GoodAttribute> allattrs) {
            this.allattrs = allattrs;
            return builder;
        }

        public Builder setCarNumber(int carNumber) {
            this.carNumber = carNumber;
            return builder;
        }

        public Builder setDefaultPdName(String defaultPdName) {
            this.defaultPdName = defaultPdName;
            return builder;
        }

        public Builder setDefaultPdPrice(String defaultPdPrice) {
            this.defaultPdPrice = defaultPdPrice;
            return builder;
        }

        public Builder setOneAttrName(String oneAttrName) {
            this.oneAttrName = oneAttrName;
            return builder;
        }

        public Builder setTwoAttrName(String twoAttrName) {
            this.twoAttrName = twoAttrName;
            return builder;
        }

        public Builder setChoiceDefaultImgDisplay(String choiceDefaultImgDisplay) {
            this.choiceDefaultImgDisplay = choiceDefaultImgDisplay;
            return builder;
        }

        public Builder setChoiceUrl(String choiceUrl) {
            this.choiceUrl = choiceUrl;
            return builder;
        }
        public DialogShopChoiceManager build(){
            DialogShopChoiceManager dialogShopChoiceManager=new DialogShopChoiceManager();
            dialogShopChoiceManager.setAllattrs(allattrs);
            dialogShopChoiceManager.setChoiceDefaultImgDisplay(choiceDefaultImgDisplay);
            dialogShopChoiceManager.setChoiceUrl(choiceDefaultImgDisplay);
            dialogShopChoiceManager.setDefaultPdPrice(defaultPdPrice);
            dialogShopChoiceManager.setDefaultPdName(defaultPdName);
            dialogShopChoiceManager.setOneAttrName(oneAttrName);
            dialogShopChoiceManager.setTwoAttrName(twoAttrName);
            return dialogShopChoiceManager;
        }
        
    }



}
