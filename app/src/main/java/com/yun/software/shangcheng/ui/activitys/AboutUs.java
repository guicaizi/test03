package com.yun.software.shangcheng.ui.activitys;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.utils.ExceptionUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/8/3 17:33
 */

public class AboutUs extends BaseActivity {
    public static final int REQUEST_ABOUST_US = 1;
    @Bind(R.id.tv_about_us)
    TextView tvAboutUs;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }



    @Override
    public void initPresenter() {

        tvTitle.setText("关于我们");
        Map map = new HashMap();
        map.put("code", 1);
        request(REQUEST_ABOUST_US, ApiConstants.GOOD_ABOUT_US, JSON.toJSONString(map), myhttpListener, false, false);

    }

    @Override
    public void initView() {


    }
    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            try {
                switch (what) {
                    case REQUEST_ABOUST_US:
                        String content = StringUtils.getJsonKeyStr(jsonstr, "content");
                        MyLogUtils.i("kankan","jsonstr=="+jsonstr);
                        tvAboutUs.setText(Html.fromHtml(content));

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
    public void addLisener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
