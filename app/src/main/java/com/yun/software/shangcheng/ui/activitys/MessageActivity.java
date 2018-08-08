package com.yun.software.shangcheng.ui.activitys;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.adapter.MessageAdapter;
import com.yun.software.shangcheng.ui.entity.MessageType;
import com.yun.software.shangcheng.ui.utils.ListViewEmptyUtils;
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
 * on 2018/7/27 15:48
 */

public class MessageActivity extends BaseActivity {
    public static final int REQUEST_MESSAGE_TYPE = 0;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.my_list)
    PullToRefreshListView myList;
    MessageAdapter messageAdapter;
    private ListViewEmptyUtils emptyUtils;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_comment_refrsh_list;
    }

    @Override
    public void initPresenter() {
        tvTitle.setText("消息中心");
        messageAdapter = new MessageAdapter(mContext, null);
        myList.setAdapter(messageAdapter);
        myList.setMode(PullToRefreshBase.Mode.DISABLED);
        emptyUtils = new ListViewEmptyUtils(mContext, myList);
        createLoadingview(myList.getRefreshableView());

    }


    @Override
    public void initView() {
       loadDate(REQUEST_MESSAGE_TYPE);
    }

    public void loadDate(int what) {
        try {
            switch (what) {
                case REQUEST_MESSAGE_TYPE:
                    Map map = new HashMap();
                    map.put("msgtype", 1);
                    request(REQUEST_MESSAGE_TYPE, ApiConstants.GOOD_MESSAGE_TYPE, JSON.toJSONString(map), myhttpListener, false, true);
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
                    case REQUEST_MESSAGE_TYPE:
                        MyLogUtils.i("kankan", "jsonstr消息类型" + jsonstr);
                        if(StringUtils.isNotEmpty(jsonstr)){
                            MessageType messageType=  JSON.parseObject(jsonstr,MessageType.class);
                            List<MessageType> messageTypes=new ArrayList<>();
                            messageTypes.add(messageType);
                            messageAdapter.updateData(messageTypes);
                        }else{
                            MessageType messageType=new MessageType();
                            messageType.setTitle("物流通知");
                            messageType.setContent("暂无信息");
                            messageType.setCreatetime("");
                            List<MessageType> messageTypes=new ArrayList<>();
                            messageTypes.add(messageType);
                            messageAdapter.updateData(messageTypes);
//                            emptyUtils.setEmptyTextAndImage("暂无消息！", R.drawable.no_data);
                        }

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
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enterPage(MessageDetailActivity.class);
            }
        });

    }


}
