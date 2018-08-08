package com.yun.software.shangcheng.ui.activitys;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.ClearEditText;
import com.yun.software.shangcheng.ui.ViewWidget.NoScrollListView;
import com.yun.software.shangcheng.ui.adapter.CategoryChildAdapter;
import com.yun.software.shangcheng.ui.adapter.MyTabAdapter;
import com.yun.software.shangcheng.ui.entity.GoodCategoryInfor;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by yanliang
 * on 2018/6/28 10:03
 */

public class GoodCategorys extends BaseActivity {
    private static final int REQUEST_CATEGORY=1;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.lin_content)
    LinearLayout linContent;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.et_search)
    ClearEditText etSearch;
    @Bind(R.id.img_alerme)
    ImageView imgAlerme;
    @Bind(R.id.tv_message_number)
    TextView tvMessageNumber;
    @Bind(R.id.verticalview)
    VerticalTabLayout verticalview;
    @Bind(R.id.iv_topIcon)
    ImageView ivTopIcon;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.sc_grid)
    NoScrollListView sclist;
    CategoryChildAdapter categoryChildAdapter;
//    CategoryChildItemAdapter categoryChildItemAdapter;
    List<GoodCategoryInfor> catelists;
    List<GoodCategoryInfor.ChildrenBeanX> childrenBeanXlist;
    MyTabAdapter tabAdapter;
    private int firstPosition=0;

    @Override
    public int getLayoutId() {
        return R.layout.acitivity_goods_category;
    }

    @Override
    public void initPresenter() {
        ivBack.setVisibility(View.VISIBLE);
        catelists=new ArrayList<>();
        childrenBeanXlist=new ArrayList<>();
//        tabAdapter=new MyTabAdapter(catelists);
        categoryChildAdapter=new CategoryChildAdapter(mContext,childrenBeanXlist);
//        verticalview.setTabAdapter(tabAdapter);
        sclist.setAdapter(categoryChildAdapter);
        createLoadingview(linContent);
        loadDate(REQUEST_CATEGORY);

    }

    @Override
    public void initView() {

    }
    public void loadDate(int what){
        try {
            switch (what){
                case REQUEST_CATEGORY:
                    request(REQUEST_CATEGORY, ApiConstants.FIRST_PAGER_ALL_CATEGORY,"", myhttpListener, false, true);
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
                switch (what){
                    case REQUEST_CATEGORY:
                        /**
                         *处理分类别彪
                         */
                      handCategory(jsonstr);
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
    
    
    protected  void handCategory(String jsonstr){
        MyLogUtils.i("httpResult===gooodcategory",jsonstr);
        if(catelists.size()>0){
            catelists.clear();
        }
        List<GoodCategoryInfor> stu = JSON.parseObject(jsonstr, new TypeReference<List<GoodCategoryInfor>>(){});
        catelists.addAll(stu);
        verticalview.setTabAdapter(new MyTabAdapter(catelists));
        ivTopIcon.setVisibility(View.VISIBLE);
        GlidUtils.loadRoundImageView(mContext,catelists.get(firstPosition).getImg(),ivTopIcon,3);
        //                        tabAdapter.notifyAll();
        if(firstPosition==0){
            categoryChildAdapter.updateData(catelists.get(firstPosition).getChildren(),0);

        }else{
            verticalview.setTabSelected(firstPosition);
        }
        MyLogUtils.i("httpResult===gooodcategory","大小"+stu.size());
        
    }
    @Override
    public void addLisener() {
        verticalview.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                MyLogUtils.i("options","position"+position+"icon"+catelists.get(position).getName());
                categoryChildAdapter.updateData(catelists.get(position).getChildren(),position);
                GlidUtils.loadRoundImageView(mContext,catelists.get(position).getImg(),ivTopIcon,3);
            }
            @Override
            public void onTabReselected(TabView tab, int position) {
            }
        });
        categoryChildAdapter.setChildLisener(new CategoryChildAdapter.ChildLisener() {
            @Override
            public void childclick(int onepostion, int postion, int twoposition) {
                String kindid=  catelists.get(onepostion).getChildren().get(postion).getChildren().get(twoposition).getId();
                String name=  catelists.get(onepostion).getChildren().get(postion).getChildren().get(twoposition).getName();
                Bundle bundle = new Bundle();
                bundle.putString("kindid",kindid);
                bundle.putString("name",name);
                enterPage(SearchShoperActivity.class,bundle);


            }
        });
        imgAlerme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               tologin(new BaseActivity.LogincallBack() {
                    @Override
                    public void loginsuccess() {
                        enterPage(MessageActivity.class);
                    }
                });

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
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
