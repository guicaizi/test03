package com.yun.software.shangcheng.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseFragment;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.activitys.CommitOrderActivity;
import com.yun.software.shangcheng.ui.adapter.GoodCarAdapter;
import com.yun.software.shangcheng.ui.entity.GoodCarItem;
import com.yun.software.shangcheng.ui.lisenter.OnShoppingCartChangeListener;
import com.yun.software.shangcheng.ui.manager.GoodCarManager;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/12 16:02
 */
public class GoodCarFragment extends BaseFragment {
    private static final int REQUEST_GOOD_CAR_LIST = 1;
    private static final int REQUEST_GOOD_CAR_NUMBER = 2;
    private static final int REQUEST_GOOD_CAR_DELETE =3;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_edit)
    TextView tvEdit;
    @Bind(R.id.lin_submit)
    LinearLayout Sbmit;
    @Bind(R.id.my_car_list)
    PullToRefreshListView myCarList;
    @Bind(R.id.ivSelectAll)
    ImageView ivSelectAll;
    @Bind(R.id.lin_choice_all)
    LinearLayout linChoiceAll;
    @Bind(R.id.tvCountMoney)
    TextView tvCountMoney;
    @Bind(R.id.btnSubmit)
    TextView btnSubmit;
    @Bind(R.id.btndelete)
    TextView btndelete;
    @Bind(R.id.rlBottomBar)
    RelativeLayout rlBottomBar;
    @Bind(R.id.rlShoppingCartEmpty)
    RelativeLayout EmptyCar;
    GoodCarAdapter goodCarAdapter;
    private int frompage = 0;
    private int pageNumber = 1;
    private int total;
    private boolean isMoreData = false;
    private boolean isChoiceAll = false;
    private boolean isFistLoad=true;
    private boolean  limitOption=false;
    private GoodCarItem temCarItem;
    private List<GoodCarItem> caritemslist;
    private List<String> choiceids;


    public static GoodCarFragment getInstance(Bundle bundle) {

        GoodCarFragment goodCarFragment = new GoodCarFragment();
        if (bundle != null) {
            goodCarFragment.setArguments(bundle);
        }
        return goodCarFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_good_car;
    }

    @Override
    public void setDate() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("frompage")) {
                frompage = bundle.getInt("frompage", 0);
            }
        }
        if (frompage == 1) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
        caritemslist=new ArrayList<>();
        goodCarAdapter = new GoodCarAdapter(mContext, caritemslist);
        myCarList.setAdapter(goodCarAdapter);
        createLoadingview(myCarList.getRefreshableView());
        loadDate(REQUEST_GOOD_CAR_LIST);

    }

    @Override
    public void onResume() {
        super.onResume();
        limitOption=false;
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        loadDate(REQUEST_GOOD_CAR_LIST);
    }

    @Override
    protected void onInvisibleToUser() {
        super.onInvisibleToUser();
        tvEdit.setText("编辑");
        GoodCarAdapter.checkItem(false, ivSelectAll);
        goodCarAdapter.setEdit(false);
        Sbmit.setVisibility(View.VISIBLE);
        btndelete.setVisibility(View.GONE);
    }


    public void loadDate(int what) {
        switch (what) {
            case REQUEST_GOOD_CAR_LIST:
                Map map = new HashMap();
                Map map00 = new HashMap();
                map00.put("userId", biz.getCustomToken());
                map.put("pageNumber", pageNumber);
                map.put("pageSize", "10");
                map.put("param", map00);
                request(REQUEST_GOOD_CAR_LIST, ApiConstants.GOOD_CAR_LIST, JSON.toJSONString(map), myhttpListener, false,isFistLoad);
                break;
            /**
             *{"id":"06ad3b4fc69347e09ec9cc3d99301157","count":17}
             */
            case REQUEST_GOOD_CAR_NUMBER:
                Map map1 = new HashMap();
                map1.put("id", temCarItem.getId());
                map1.put("count", temCarItem.getCount());
                request(REQUEST_GOOD_CAR_NUMBER, ApiConstants.GOOD_UPDATE_NUMBER, JSON.toJSONString(map1), myhttpListener, false, false);
                break;
            case REQUEST_GOOD_CAR_DELETE:
                choiceids= GoodCarManager.getChoiceIds(caritemslist);
                if(choiceids.size()==0){
                    showShortToast("请选择要删除的物品！");
                    return;
                }
                Map map2 = new HashMap();
                map2.put("ids",choiceids);
                request(REQUEST_GOOD_CAR_DELETE, ApiConstants.GOOD_CAR_DELETE, JSON.toJSONString(map2), myhttpListener, false, false);
                break;
        }


    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            myCarList.onRefreshComplete();
            try {
                switch (what) {
                    case REQUEST_GOOD_CAR_LIST:
                        isFistLoad=false;
                        if(pageNumber==1){
                            caritemslist.clear();
                        }
                        if(StringUtils.isEmpty(jsonstr)){
                            EmptyCar.setVisibility(View.VISIBLE);
                            goodCarAdapter.updateData(caritemslist);
                            return;
                        }else{
                            EmptyCar.setVisibility(View.GONE);
                        }
                        MyLogUtils.i("jsontr===购物车列表", jsonstr);
                        List<GoodCarItem> goodCarItems = JSON.parseObject(StringUtils.getJsonListStr(jsonstr, "list"), new TypeReference<List<GoodCarItem>>() {
                        });
                        String total=StringUtils.getJsonListStr(jsonstr, "total");
                        if(Tools.checkList(goodCarItems)){
                            caritemslist.addAll(goodCarItems);
                        }
                        if(caritemslist.size()<Integer.valueOf(total)){
                            isMoreData=true;
                        }else{
                            isMoreData=false;
                        }
                        if(caritemslist.size()==0){
                            EmptyCar.setVisibility(View.VISIBLE);
                            goodCarAdapter.updateData(caritemslist);
                        }
                        goodCarAdapter.updateData(caritemslist);
                        break;
                    case REQUEST_GOOD_CAR_NUMBER:
                        MyLogUtils.i("更新购物车数目",jsonstr);
                        break;
                    case REQUEST_GOOD_CAR_DELETE:
                        pageNumber=1;
                        isMoreData=false;
                        loadDate(REQUEST_GOOD_CAR_LIST);
                        break;
                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {
            myCarList.onRefreshComplete();

        }
    };

    @Override
    protected void addLisener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        linChoiceAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChoiceAll = !isChoiceAll;
                GoodCarAdapter.checkItem(isChoiceAll, ivSelectAll);
                goodCarAdapter.ChoiceAll(isChoiceAll);
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEdit.getText().toString().equals("编辑")) {
                    tvEdit.setText("完成");
                    Sbmit.setVisibility(View.GONE);
                    btndelete.setVisibility(View.VISIBLE);
                    goodCarAdapter.setEdit(true);
                } else {
                    tvEdit.setText("编辑");
                    Sbmit.setVisibility(View.VISIBLE);
                    btndelete.setVisibility(View.GONE);
                    goodCarAdapter.setEdit(false);
                }
            }
        });

        goodCarAdapter.setOnShoppingCartChangeListener(new OnShoppingCartChangeListener() {
            @Override
            public void onTotalChange(String selectMoney) {

                tvCountMoney.setText(selectMoney);
            }

            @Override
            public void getIsSelectAll(boolean isSelectedAll) {
                GoodCarAdapter.checkItem(isSelectedAll, ivSelectAll);
            }

            @Override
            public void onItemChange(GoodCarItem goodCarItem) {
                    temCarItem=goodCarItem;
                    loadDate(REQUEST_GOOD_CAR_NUMBER);
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDate(REQUEST_GOOD_CAR_DELETE);


            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList choiceid=GoodCarManager.getChoiceIds(caritemslist);
                if(choiceid.size()==0){
                    showShortToast("请选择要下单的商品");
                    return;
                }
                if(limitOption){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("ids",GoodCarManager.getChoiceIds(caritemslist));
                enterPage(CommitOrderActivity.class,bundle);
                limitOption=true;

            }
        });

        myCarList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNumber=1;
                isMoreData=false;
                caritemslist.clear();
                GoodCarAdapter.checkItem(false, ivSelectAll);
                loadDate(REQUEST_GOOD_CAR_LIST);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(isMoreData){
                    pageNumber++;
                    loadDate(REQUEST_GOOD_CAR_LIST);
                }else{
                    showShortToast(R.string.no_more);
                    myCarList.onRefreshComplete();
                }

            }
        });


    }

}
