package com.yun.software.shangcheng.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.UIAlertView;
import com.yun.software.shangcheng.ui.adapter.AddressAdapter;
import com.yun.software.shangcheng.ui.entity.Address;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/3 11:21
 */

public class ManagerAddress extends BaseActivity {
    public static final String TAG = "ManagerAddress";
    private static final int REQUEST_ADDRESS_INFOR = 1;
    private static final int REQUEST_DELETE_ADDRESS = 2;
    private static final int REQUEST_SETDEFALUT_ADDRESS = 3;
    private static final int REQUEST_EDIT_ADDRESS = 4;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.lv_address)
    ListView lv_address;
    @Bind(R.id.tv_add_address)
    TextView tvAddAddress;
    private AddressAdapter adapter;
    private List<Address> addressList;
    /**
     *选择默认地址
     */
    private int choiceDefPosition;
    private Address choiceDefAddress;
    private int frompage=0;
    @Override
    public int getLayoutId() {

        return R.layout.activity_address;
    }

    @Override
    public void initPresenter() {
        tvTitle.setText("管理收货地址");
        addressList = new ArrayList<Address>();
        adapter = new AddressAdapter(this, addressList);
        lv_address.setAdapter(adapter);
        Bundle bundle=getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if(bundle!=null){
            if(bundle.containsKey("frompage")){
                frompage=bundle.getInt("frompage",0);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDate(REQUEST_ADDRESS_INFOR);
    }

    @Override
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        super.onMessageEventMainThread(messageEvent);
        if(messageEvent.getMessage().equals("change_address_success")){
            finish();
        }
    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_ADDRESS_INFOR:
                    Map map0 = new HashMap();
                    Map map00 = new HashMap();
                    map0.put("param", map00);
                    map00.put("userId", biz.getCustomToken());
                    request(REQUEST_ADDRESS_INFOR, ApiConstants.ADDRESS_MANAGER, StringUtils.commbingJson(map0, map00), myhttpListener, false, false);
                    break;
                case REQUEST_DELETE_ADDRESS:
                    Map map1 = new HashMap();
                    map1.put("id",choiceDefAddress.getId());
                    request(REQUEST_DELETE_ADDRESS, ApiConstants.ADDRESS_DELETE+choiceDefAddress.getId(), StringUtils.commbingJson(map1), myhttpListener, false, false);
                    break;
                case REQUEST_EDIT_ADDRESS:
                    Map map2=new HashMap();
                    map2.put("id",choiceDefAddress.getId());
                    map2.put("status","1");
                    map2.put("userId",biz.getCustomToken());
                    map2.put("personName",choiceDefAddress.getPersonName());
                    map2.put("phone",choiceDefAddress.getPhone());
                    map2.put("address",choiceDefAddress.getAddress());
                    map2.put("areaCode",choiceDefAddress.getAreaCode());
                    map2.put("postalCode",choiceDefAddress.getPostalCode());
                    MyLogUtils.i("请求", JSON.toJSONString(map2));
                    request(REQUEST_EDIT_ADDRESS, ApiConstants.ADDRESS_EDIT, JSON.toJSONString(map2), myhttpListener, false, false);
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
                    case REQUEST_ADDRESS_INFOR:
                        MyLogUtils.i(TAG, "地址管理信息:\r\n" + jsonstr);
                        addressList= JSON.parseObject(jsonstr, new TypeReference<List<Address>>() {});
                        adapter.updateData(addressList);
                        break;
                    case REQUEST_DELETE_ADDRESS:
                        MyLogUtils.i(TAG, "删除地址:\r\n" + jsonstr);
                        addressList.remove(choiceDefPosition);
                        adapter.notifyDataSetChanged();
                        break;
                    case REQUEST_EDIT_ADDRESS:
                        MyLogUtils.i(TAG, "编辑信息:\r\n" + jsonstr);
                        loadDate(REQUEST_ADDRESS_INFOR);
                        break;
                }

            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }

        }


        @Override
        public void onFailed(int what, String jsonstr) {


        }
    };

    @Override
    public void initView() {

    }

    @Override
    public void addLisener() {
        adapter.setAddessChangeLisener(new AddressAdapter.AddressChangeLisener() {
            @Override
            public void deleteAddress(final int position) {
                final Address address = addressList.get(position);

                final UIAlertView delDialog = new UIAlertView(mContext, "温馨提示","确定删除" + address.getPersonName()+ "这条地址吗？",
                        "取消", "确定");
                delDialog.show();

                delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {

                                               @Override
                                               public void doLeft() {
                                                   delDialog.dismiss();
                                               }

                                               @Override
                                               public void doRight() {
                                                   choiceDefPosition=position;
                                                   choiceDefAddress=address;
                                                   loadDate(REQUEST_DELETE_ADDRESS);
                                                   delDialog.dismiss();
                                               }
                                           }
                );
            }

            @Override
            public void editAddress(int position) {
                Address address=addressList.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("address",address);
                enterPage(AddOrEditAddress.class,bundle);
            }

            @Override
            public void setDefalutAddress(int position) {
                choiceDefPosition=position;
                choiceDefAddress=addressList.get(position);
                loadDate(REQUEST_EDIT_ADDRESS);
//                deleteId=String.valueOf(addressList.get(position).getId());

            }
        });
        lv_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(frompage==1){
                    choiceDefAddress=addressList.get(position);
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("address",choiceDefAddress);
                    intent.putExtra(AppConfig.START_ACTIVITY_PUTEXTRA,bundle);
                    setResult(3,intent);
                    finish();
                }

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enterPage(AddOrEditAddress.class);

            }
        });

    }
}
