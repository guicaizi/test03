package com.yun.software.shangcheng.ui.ViewWidget.popView;


import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yun.software.buypop.tabGround.MyTabLable;
import com.yun.software.buypop.tabGround.TagBean;
import com.yun.software.buypop.tabGround.TagContainerLayout;
import com.yun.software.buypop.tabGround.TagContainerLayout.ViewColor;
import com.yun.software.buypop.tabGround.TagFactory;
import com.yun.software.buypop.tabGround.TagView;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.ViewWidget.NumberButton;
import com.yun.software.shangcheng.ui.activitys.ProductDetail;
import com.yun.software.shangcheng.ui.entity.GoodAttribute;
import com.yun.software.shangcheng.ui.entity.GoodDetailInfor;
import com.yun.software.shangcheng.ui.manager.DialogShopChoiceManager;
import com.yun.software.shangcheng.ui.utils.DecimalUtil;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;

import java.util.List;


public class DialogBuyProduct extends AbsDialog {
    private Activity mContext;
    private SubmitDateListener submitlistener;
    private ImageView iv_close;
    // 存放一级分类下所有二级子类  属性一
    private List<TagBean> tagBeans;
    //存放一级分类下所有二级子类  属性二
    private List<TagBean> tagBeans2;
    private TagContainerLayout oneTagContainer;
    private TagContainerLayout twoTagContainer;
    private TextView tvAttributeNameOne;
    private TextView tvattributeNameTwo;
    //要显示的 商品名称
    private TextView tvShowName;
    //要显示的 商品价位
    private TextView tvShowMoney;
    private TextView tv_des;
    private ImageView iv_icon;
    private TextView tv_commit;
    private NumberButton numberButton;
    private ViewColor mBanViewColor = new ViewColor();
    private ViewColor mDefaultViewColor = new ViewColor();
    private ViewColor mClickViewColor = new ViewColor();
    private ProductDetail detail;
    //二级标签 状态工厂
    private MyTabLable tagOneFactory;
    private MyTabLable twoAttributeFactory;
    private DialogShopChoiceManager dsch;
    private int type;
    public DialogBuyProduct(Activity context) {
        super(context, R.style.dialog_normal);
        mContext=context;
        setContentView(R.layout.dialog_bug_produce);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
    /**
     *
     */
    public void setSubmitlistener(SubmitDateListener submitlistener) {
        this.submitlistener = submitlistener;
    }

    public interface SubmitDateListener {
        void choice(List<String> ids, int number,double choiceprice);
        void choiceIcon(String url);
    }
    @Override
    protected void initView() {
        iv_close = (ImageView) findViewById(R.id.iv_close);
        oneTagContainer = (TagContainerLayout) findViewById(R.id.one_tag_container);
        twoTagContainer = (TagContainerLayout) findViewById(R.id.two_tag_container);
        tvAttributeNameOne = (TextView) findViewById(R.id.color_label);
        tvattributeNameTwo = (TextView) findViewById(R.id.size_label);
        tvShowName = (TextView) findViewById(R.id.tv_dialog_name);
        tvShowMoney = (TextView) findViewById(R.id.tv_dialog_money);
        tv_des = (TextView) findViewById(R.id.tv_dialog_size);
        iv_icon = (ImageView) findViewById(R.id.iv_dialog_icon);
        tv_commit = (TextView) findViewById(R.id.tv_tag_commit);
        numberButton = (NumberButton) findViewById(R.id.number_button);
        mDefaultViewColor = new ViewColor(ContextCompat.getColor(mContext, R.color.backGroundColor), 0, ContextCompat.getColor(mContext, R.color.text_color_black));
        mClickViewColor = new ViewColor(ContextCompat.getColor(mContext, R.color.clickBackGroundColor), 0, ContextCompat.getColor(mContext, R.color.clickTextColor));
    }
    @Override
    protected void initData() {
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
    }
    public void setDetail(List<GoodAttribute> gab, GoodDetailInfor gDesinfor) {
        setDetail(gab,gDesinfor,0);
    }

    public int getType() {
        return type;
    }

    public void setDetail(List<GoodAttribute> gab, GoodDetailInfor gDesinfor, int type) {
//        this.detail = detial;
//        attrs = detial.getAttributes();
        this.type=type;
        dsch=new DialogShopChoiceManager();
        dsch.setType(type);
        dsch.setAllattrs(gab);
        if(type==0){
            dsch.setDefaultPdPrice(DecimalUtil.add(String.valueOf(gDesinfor.getInfo().getPrice()),"0.0"));
        }else{
            MyLogUtils.i("kankan","默认价格"+String.valueOf(gDesinfor.getInfo().getScorePrice()));
            dsch.setDefaultPdPrice(DecimalUtil.add(String.valueOf(gDesinfor.getInfo().getScorePrice()),"0.0"));
        }
        dsch.setChoiceDefaultImgDisplay(gDesinfor.getInfo().getLogo());
        dsch.setDefaultPdName(gDesinfor.getInfo().getName());
        numberButton.setCurrentNumber(dsch.getCarNumber());
        tvShowName.setText(dsch.getDefaultPdName());
        tvShowMoney.setText(dsch.getDefaultPdPrice());

        tv_des.setText(dsch.getNowChoiceStatestr());
        GlidUtils.loadRoundImageView(mContext, dsch.getChoiceDefaultImgDisplay(), iv_icon, 3);
        if(dsch.getAllattributesize()>0){
            tvAttributeNameOne.setVisibility(View.VISIBLE);
            oneTagContainer.setVisibility(View.VISIBLE);
            dsch.setOneAttrName(gab.get(0).getName());
            dsch.setChoiceUrl(gDesinfor.getInfo().getLogo());
            initOneTag();
            if (dsch.getAllattributesize()>1) {
                dsch.setChoiceUrlTwo(gDesinfor.getInfo().getLogo());
                dsch.setTwoAttrName(gab.get(1).getName());
                initTwoTag();
                tvattributeNameTwo.setVisibility(View.VISIBLE);
                twoTagContainer.setVisibility(View.VISIBLE);
            } else {
                tvattributeNameTwo.setVisibility(View.GONE);
                twoTagContainer.setVisibility(View.GONE);
            }
        }else{
            tvAttributeNameOne.setVisibility(View.GONE);
            oneTagContainer.setVisibility(View.GONE);
            tvattributeNameTwo.setVisibility(View.GONE);
            twoTagContainer.setVisibility(View.GONE);
        }
    }
    @Override
    protected void setListener() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isNotEmpty(dsch.getOneAttrName())) {
                    if (StringUtils.isEmpty(dsch.getChoiceAttrOneValue())) {
                        Tools.showInfo(mContext, "请选择" + dsch.getOneAttrName());
                        return;
                    }
                }
                if (StringUtils.isNotEmpty(dsch.getTwoAttrName())) {
                    if (StringUtils.isEmpty(dsch.getChoiceAttrTwoValue())) {
                        Tools.showInfo(mContext, "请选择" + dsch.getTwoAttrName());
                        return;
                    }
                }
                    try {
                        String perMoney=tvShowMoney.getText().toString().replace("分","").replace("￥","");
                        submitlistener.choice(dsch.getChoiceIds(),numberButton.getNumber(),Double.valueOf(perMoney));
                    }catch (Exception e){
                        String perMoney=tvShowMoney.getText().toString().replace("分","").replace("￥","");
                        submitlistener.choice(dsch.getChoiceIds(),numberButton.getNumber(),0);
                    }


                dismiss();
            }
        });
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitlistener.choiceIcon(dsch.getChoiceUrl());
            }
        });
    }
    private void initOneTag() {
        tvAttributeNameOne.setText(dsch.getOneAttrName());
        oneTagContainer.setTitles(dsch.getOneTitles());
        tagOneFactory = new MyTabLable(dsch.getOneTags(), oneTagContainer.getAllChildViews(), mBanViewColor, mDefaultViewColor, mDefaultViewColor);
        oneTagContainer.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(TagView view, int position, String text) {
                TagFactory.ClickStatus clickStatus = tagOneFactory.onColorTagClick(position);
                if (clickStatus == TagFactory.ClickStatus.CLICK) {
                    TagBean tagbean = dsch.getOneTags().get(position);
//                    choiceTag1 = tagbean.getTitle();
                    dsch.setChoiceAttrOneValue(tagbean.getTitle());
                    //计算二级类型产品最后的价格
//                    String absolutetTotoal = DecimalUtil.add(dsch.getDefaultPdPrice(), String.valueOf(tagbean.getPrice()));
//                    tvShowMoney.setText("￥" + dsch.getDefaultPdPrice());
                    tv_des.setText(dsch.getNowChoiceStatestr());
                    String  choiceUrl = StringUtils.toString(dsch.getOneValues().get(position).getImg());
                    //标签一选中
                    dsch.setChoiceUrl(choiceUrl);
                    dsch.setTagoneCheck(true);
                    //设置当前选择id
                    dsch.setOneTagChoiceid(dsch.getOneValues().get(position).getId());
                    if(!dsch.isTagtwoCheck()){
                        GlidUtils.loadRoundImageView(mContext, dsch.getChoiceUrl(), iv_icon, 3);
                    }else{
                        oneTwoLisener.makequrePrice(dsch.getChoiceIds());
                    }
                     oneTwoLisener.updateTwoTag(dsch.getOneValues().get(position).getId());
                } else if (clickStatus == TagFactory.ClickStatus.UNCLICK) {
                    dsch.setOneTagChoiceid("");
                    dsch.setTagoneCheck(false);
                    dsch.setChoiceAttrOneValue("");
                    tv_des.setText(dsch.getNowChoiceStatestr());
                    updateDefautStatetwo();
                    dsch.setChoiceUrl(dsch.getChoiceDefaultImgDisplay());
                    if(dsch.getChoiceIds().size()==0){
                        tvShowMoney.setText(dsch.getDefaultPdPrice());
                    }else{
                        oneTwoLisener.makequrePrice(dsch.getChoiceIds());
                    }


                    if(dsch.isTagtwoCheck()){
                        GlidUtils.loadRoundImageView(mContext,dsch.getChoiceUrlTwo(), iv_icon, 3);
                    }else{
                        GlidUtils.loadRoundImageView(mContext,dsch.getChoiceUrl(), iv_icon, 3);
                    }

                }
            }
            @Override
            public void onTagLongClick(int position, String text) {
            }
            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }

    private void initTwoTag() {
        tvattributeNameTwo.setText(dsch.getTwoAttrName());
        twoTagContainer.setTitles(dsch.getTwoTitles());
        twoAttributeFactory = new MyTabLable(dsch.getTwoTags(), twoTagContainer.getAllChildViews(), mBanViewColor, mDefaultViewColor, mDefaultViewColor);
        twoTagContainer.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(TagView view, int position, String text) {
                TagFactory.ClickStatus clickStatus = twoAttributeFactory.onColorTagClick(position);
                TagBean tagbean = dsch.getTwoTags().get(position);
                if (clickStatus == TagFactory.ClickStatus.CLICK) {
                    dsch.setChoiceAttrTwoValue(tagbean.getTitle());
                    tv_des.setText(dsch.getNowChoiceStatestr());
                    dsch.setTagtwoCheck(true);
                    dsch.setTwoTagChoiceid(dsch.getTwoValues().get(position).getId());
                    dsch.setChoiceUrlTwo(dsch.getTwoValues().get(position).getImg());
                    if(dsch.isTagoneCheck()){
                        oneTwoLisener.makequrePrice(dsch.getChoiceIds());
                    }
                    oneTwoLisener.updateOneTag(dsch.getTwoValues().get(position).getId());
                    GlidUtils.loadRoundImageView(mContext,dsch.getChoiceUrlTwo(), iv_icon, 3);
                } else if (clickStatus == TagFactory.ClickStatus.UNCLICK) {
                    dsch.setChoiceAttrTwoValue("");
                    dsch.setTagtwoCheck(false);
                    tv_des.setText(dsch.getNowChoiceStatestr());
                    dsch.setTwoTagChoiceid("");
                    dsch.setChoiceUrlTwo(dsch.getChoiceDefaultImgDisplay());
                    updateDefautStateOne();
                    if(dsch.getChoiceIds().size()==0){
                        tvShowMoney.setText(dsch.getDefaultPdPrice());
                    }else{
                        oneTwoLisener.makequrePrice(dsch.getChoiceIds());
                    }
                    if(dsch.isTagoneCheck()){
                        GlidUtils.loadRoundImageView(mContext,dsch.getChoiceUrl(), iv_icon, 3);
                    }else{
                        GlidUtils.loadRoundImageView(mContext,dsch.getChoiceUrlTwo(), iv_icon, 3);
                    }

                }
            }
            @Override
            public void onTagLongClick(int position, String text) {
            }
            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }
    /**
     *联动回调状态二
     */
    public void updateTagState(List<String> alllists,List<String> prices){
        if(dsch.getAllattributesize()>1){
            dsch.initTabtwoState(alllists);
            twoAttributeFactory.initTags();
        }
        if(!dsch.isTagtwoCheck()){
            MyLogUtils.i("kankan","标签联动二"+JSON.toJSONString(prices));
            tvShowMoney.setText(dsch.getclaclutprice(prices));
        }

    }
    /**
     *联动回调一
     */
    public void updateTagStateOne(List<String> alllists,List<String> prices) {
        MyLogUtils.i("jsonstr","更新第一个标签"+ JSON.toJSONString(alllists));
        dsch.initTagOneState(alllists);
        if(!dsch.isTagoneCheck()){
              tvShowMoney.setText(dsch.getclaclutprice(prices));
        }
        tagOneFactory.initTags();
    }

    public void makeSurePrice(List<String> prices){
        tvShowMoney.setText(dsch.getclaclutprice(prices));
    }

    public  void updateDefautStateOne(){
        dsch.setdefautOneState();
        tagOneFactory.initTags();

    }
    public  void updateDefautStatetwo(){
        dsch.setdefautTwoState();
        twoAttributeFactory.initTags();

    }





    public void setOneChangeToTwoChangeLisener(OneChangeToTwoChangeLisener oneTwoLisener){
        this.oneTwoLisener=oneTwoLisener;
    }

   /**
    *联动回调
    */
    private OneChangeToTwoChangeLisener oneTwoLisener;

    public interface  OneChangeToTwoChangeLisener{
        public void updateTwoTag(String attribteValueId);
        public void updateOneTag(String attribteValueID);
        public void makequrePrice(List<String> ids);

    }

}
