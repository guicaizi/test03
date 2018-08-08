package com.yun.software.shangcheng.ui.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bigkoo.pickerview.OptionsPickerView;
import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.entity.Address;
import com.yun.software.shangcheng.ui.entity.ChinaAddress;
import com.yun.software.shangcheng.ui.entity.MessageEvent;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/3 15:11
 */

public class AddOrEditAddress extends BaseActivity {
    public static final String TAG = "AddorEditAddress";
    private static final int REQUEST_ALLADDRESS_JSON = 1;
    private static final int REQUEST_EDIT_ADDRESS = 2;
    private static final int REQUEST_ADD_ADDRESS = 3;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_address_tag)
    TextView tvAddressTag;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_mobile)
    EditText etMobile;
    @Bind(R.id.tv_choice_address)
    TextView tvChoiceAddress;
    @Bind(R.id.et_address_detail)
    EditText etAddressDetail;
    @Bind(R.id.tv_save)
    TextView tvSave;
    private List<ChinaAddress> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String id = "0";
    private boolean isEditeAddress = false;
    private boolean isLoaded = false;
    String commitName;
    String commitPhone;
    String commitAddress;
    String commitdesAddress = "";
    String provinceId = "0";
    String cityId = "0";
    String countryId = "0";
    boolean isfromEdit = false;
    private boolean limitOptions = false;
    // 选择项
    int temoptions1 = 0, temoptions2 = 0, temoptions3 = 0;
    Address address;
    int frompage = 0;
    private List<Map<String, String>> submitArealist = new ArrayList<>();

    public int getLayoutId() {
        return R.layout.activity_addoredit_address;
    }

    @Override
    public void initPresenter() {
        Bundle bundle = getIntent().getBundleExtra(AppConfig.START_ACTIVITY_PUTEXTRA);
        if (bundle != null) {
            if (bundle.containsKey("address")) {
                isfromEdit = true;
                address = (Address) bundle.getSerializable("address");
                id = address.getId();
                commitPhone = address.getPhone();
                commitdesAddress = address.getAddress();
                commitAddress = address.getAreaCode();
                commitName = address.getPersonName();
                tvChoiceAddress.setText(StringUtils.getAddress(address.getAreaCode(), ""));
                etAddressDetail.setText(commitdesAddress);
                etMobile.setText(commitPhone);
                etName.setText(commitName);
                provinceId=StringUtils.getArressid(address.getAreaCode(),0);
                cityId=StringUtils.getArressid(address.getAreaCode(),1);
                countryId=StringUtils.getArressid(address.getAreaCode(),2);
                MyLogUtils.i("kankan","省会id"+provinceId+"城市id"+cityId+"县区id"+countryId);
                isEditeAddress = true;
            }
            if (bundle.containsKey("frompage")) {
                frompage = bundle.getInt("frompage", 0);
            }
        }
        if (isEditeAddress) {
            tvTitle.setText("编辑收货地址");
        } else {
            tvTitle.setText("添加收货地址");
        }

        loadDate(REQUEST_ALLADDRESS_JSON);
    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_ALLADDRESS_JSON:
                    String jsonstr = StringUtils.getJson(mContext, ApiConstants.addressname);
                    final Map<String, String> maps = JSON.parseObject(jsonstr, new TypeReference<Map<String, String>>() {
                    });
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handleraddressJson(maps);
                        }
                    }).start();
                    break;
                case REQUEST_ADD_ADDRESS:
                    Map map = new HashMap();
                    map.put("status", "0");
                    map.put("userId", biz.getCustomToken());
                    map.put("personName", commitName);
                    map.put("phone", commitPhone);
                    map.put("address", commitdesAddress);
                    map.put("areaCode", commitAddress);
                    map.put("postalCode", "0");
                    MyLogUtils.i("请求", JSON.toJSONString(map));
                    request(REQUEST_ADD_ADDRESS, ApiConstants.ADDRESS_ADD, JSON.toJSONString(map), myhttpListener, false, false);
                    break;
                case REQUEST_EDIT_ADDRESS:
                    Map map2 = new HashMap();
                    map2.put("id", address.getId());
                    map2.put("status", address.getStatus());
                    map2.put("userId", biz.getCustomToken());
                    map2.put("personName", commitName);
                    map2.put("phone", commitPhone);
                    map2.put("address", commitdesAddress);
                    map2.put("areaCode", commitAddress);
                    map2.put("postalCode", "0");
                    MyLogUtils.i("请求", JSON.toJSONString(map2));
                    request(REQUEST_EDIT_ADDRESS, ApiConstants.ADDRESS_EDIT, JSON.toJSONString(map2), myhttpListener, false, false);
                    break;


            }

        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }

    private void handleraddressJson(Map<String, String> map) {
        try {
            if (map != null && !"".equals(map)) {
                String list = map.get("data");
                options1Items = JSON.parseObject(list, new TypeReference<List<ChinaAddress>>() {
                });

                for (int i = 0; i < options1Items.size(); i++) {
                    if (options1Items.get(i).getId().equals(provinceId)) {
                        temoptions1 = i;
                    }
                    ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
                    ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                    if (options1Items == null) {
                        return;
                    }
                    if (options1Items.get(i).getChild() != null && options1Items.get(i).getChild().size() > 0) {
                        for (int c = 0; c < options1Items.get(i).getChild().size(); c++) {//遍历该省份的所有城市
                            if (cityId.equals(options1Items.get(i).getChild().get(c).getId())) {
                                temoptions2 = c;
                            }
                            ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                            String CityName = options1Items.get(i).getChild().get(c).getName();
                            CityList.add(CityName);//添加城市
                            //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                            if (options1Items.get(i).getChild().get(c).getChild() == null
                                    || options1Items.get(i).getChild().get(c).getChild().size() == 0) {
                                City_AreaList.add("");
                            } else {
                                for (int d = 0; d < options1Items.get(i).getChild().get(c).getChild().size(); d++) {//该城市对应地区所有数据
                                    String AreaName = options1Items.get(i).getChild().get(c).getChild().get(d).getName();
                                    if (countryId.equals(options1Items.get(i).getChild().get(c).getChild().get(d).getId())) {
                                        temoptions3 = d;
                                    }
                                    City_AreaList.add(AreaName);//添加该城市所有地区数据
                                }
                            }
                            Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                        }
                    } else {
                        CityList.add("");
                        Province_AreaList.add(new ArrayList<String>());

                    }
                    /**
                     * 添加城市数据
                     */
                    options2Items.add(CityList);

                    /**
                     * 添加地区数据
                     */
                    options3Items.add(Province_AreaList);
                }

                isLoaded = false;

            } else {
                Tools.showInfo(mContext, R.string.network_not_work);
            }
        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }


    }

    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            limitOptions = false;
            try {
                switch (what) {
                    case REQUEST_ADD_ADDRESS:
                        MyLogUtils.i(TAG, "添加收货地址\r\n" + jsonstr);
                        finish();
                        break;
                    case REQUEST_EDIT_ADDRESS:
                        MyLogUtils.i(TAG, "修改地址\r\n" + jsonstr);
                        EventBus.getDefault().post(new MessageEvent("change_address_success"));
                        finish();
                        break;
                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }

        }

        @Override
        public void onFailed(int what, String jsonstr) {
            limitOptions = false;

        }
    };

    @Override
    public void initView() {

    }

    @Override
    public void addLisener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvChoiceAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLoaded) {
                    hiddenKeyBoard();
                    ShowPickerView();
                } else {
                    Tools.showInfo(mContext, "正在解析数据");
                }
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commitCheck()) {
                    if (limitOptions) {
                        Tools.showInfo(mContext, "正在提交，请稍等。");
                        return;
                    }
                    limitOptions = true;
                    if (isEditeAddress) {
                        loadDate(REQUEST_EDIT_ADDRESS);
                    } else {
                        loadDate(REQUEST_ADD_ADDRESS);
                    }

                }
            }
        });


    }

    public boolean commitCheck() {
        commitName = etName.getText().toString();
        commitPhone = etMobile.getText().toString();
        commitdesAddress = etAddressDetail.getText().toString();
        if (StringUtils.isEmpty(commitName)) {
            Tools.showInfo(mContext, "请填写收货人!");
            return false;
        }
        if (StringUtils.isEmpty(commitPhone)) {
            Tools.showInfo(mContext, "请填写联系电话!");
            return false;
        }
        if (!StringUtils.isMobile(commitPhone)) {
            Tools.showInfo(mContext, "手机号格式不正确!");
            return false;
        }
        if (StringUtils.isEmpty(commitAddress)) {
            Tools.showInfo(mContext, "请选择收货地址！");
            return false;
        }
        if (StringUtils.isEmpty(commitdesAddress)) {
            Tools.showInfo(mContext, "请填写详细地址！");
            return false;
        }
        return true;
    }

    private void ShowPickerView() {// 弹出选择器

        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                MyLogUtils.i("kankan","options3"+options3);
                  submitArealist.clear();
                  temoptions1 = options1;
                  temoptions2 = options2;
                  temoptions3 = options3;
                //返回的分别是三个级别的选中位置
                if (StringUtils.isEmpty(options2Items.get(options1).get(options2))) {
                    cityId = "0";
                    countryId = "0";
                    String tx = options1Items.get(options1).getPickerViewText();
                    tvChoiceAddress.setText(tx);
                    Map<String, String> map = new HashMap();
                    map.put("name", options1Items.get(options1).getName());
                    map.put("id", options1Items.get(options1).getId());
                    submitArealist.add(map);
                    provinceId = options1Items.get(options1).getId();
                } else {
                    Map<String, String> map = new HashMap();
                    map.put("name", options1Items.get(options1).getName());
                    map.put("id", options1Items.get(options1).getId());
                    Map<String, String> map1 = new HashMap();
                    map1.put("name", options1Items.get(options1).getChild().get(options2).getName());
                    map1.put("id", options1Items.get(options1).getChild().get(options2).getId());
                    Map<String, String> map2 = new HashMap();
                    submitArealist.add(map);
                    submitArealist.add(map1);
                    MyLogUtils.i("kankan","大小"+options1Items.get(options1).getChild().get(options2).getChild());
                    if(options1Items.get(options1).getChild().get(options2).getChild()!=null){
                        map2.put("name", options1Items.get(options1).getChild().get(options2).getChild().get(options3).getName());
                        map2.put("id", options1Items.get(options1).getChild().get(options2).getChild().get(options3).getId());
                        countryId = options1Items.get(options1).getChild().get(options2).getChild().get(options3).getId();
                        submitArealist.add(map2);
                    }
                    String tx = options1Items.get(options1).getPickerViewText() +
                            options2Items.get(options1).get(options2) +
                            options3Items.get(options1).get(options2).get(options3);
                    tvChoiceAddress.setText(tx);
                    provinceId = options1Items.get(options1).getId();
                    cityId = options1Items.get(options1).getChild().get(options2).getId();
                    MyLogUtils.i(TAG, provinceId, cityId, countryId);
                }
                if (submitArealist.size() > 0) {
                    commitAddress = JSON.toJSONString(submitArealist);
                }
                MyLogUtils.i("kankan", "提交地址" + commitAddress);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.setSelectOptions(temoptions1, temoptions2, temoptions3);

        pvOptions.show();
    }
}
