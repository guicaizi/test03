package com.yun.software.shangcheng.ui.activitys;

import android.widget.ImageView;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.ClearEditText;
import com.yun.software.shangcheng.ui.utils.NoScrollGridView;
import com.yun.software.utils.ExceptionUtil;

import butterknife.Bind;
import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * Created by yanliang
 * on 2018/6/28 10:03
 */

public class Modle extends BaseActivity {
    private static final int REQUEST_GET_PRODUCT =1 ;
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
    @Bind(R.id.sc_grid)
    NoScrollGridView scGrid;

    @Override
    public int getLayoutId() {
        return R.layout.acitivity_goods_category;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }
    public void loadDate(int what){
        try {
            switch (what){
                case REQUEST_GET_PRODUCT:
//                    request(REQUEST_GOOD_DETAIL_PINGLUN, ApiConstants.GOOD_DETAIL_PINGLUN, StringUtils.commbingJson(map0,map00), myhttpListener, false, false);
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
                switch (what) {
                    case REQUEST_GET_PRODUCT:





                        break;}
            }catch (Exception e){
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {

        }
    };

    @Override
    public void addLisener() {

    }
}
