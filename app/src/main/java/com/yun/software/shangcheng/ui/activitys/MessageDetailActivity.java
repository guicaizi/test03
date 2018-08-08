package com.yun.software.shangcheng.ui.activitys;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.adapter.MessageDetailAdapter;
import com.yun.software.shangcheng.ui.entity.MessageType;
import com.yun.software.shangcheng.ui.utils.ListViewEmptyUtils;
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
 * on 2018/7/27 15:48
 */

public class MessageDetailActivity extends BaseActivity {
    public static final int REQUEST_MESSAGE_TYPE = 0;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.my_list)
    PullToRefreshListView myList;
    MessageDetailAdapter messageAdapter;
    private int pageNumber=1;
    List<MessageType> messageTypes;
    private ListViewEmptyUtils emptyUtils;
    private boolean ismoreDate=false;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_comment_refrsh_list;
    }

    @Override
    public void initPresenter() {
        tvTitle.setText("消息中心");
        messageTypes=new ArrayList<>();
        messageAdapter = new MessageDetailAdapter(mContext, messageTypes);
        emptyUtils = new ListViewEmptyUtils(mContext, myList);
        myList.setAdapter(messageAdapter);

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
                    map.put("isRead", "");
                    map.put("msgtype", "");
                    map.put("pageNumber", pageNumber);
                    map.put("pageSize", 10);
                    request(REQUEST_MESSAGE_TYPE, ApiConstants.GOOD_MESSAGE_LIST, JSON.toJSONString(map), myhttpListener, false, false);
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
                        myList.onRefreshComplete();
                        MyLogUtils.i("kankan", "jsonstr消息类型" + jsonstr);
                        if(pageNumber==1){
                            messageTypes.clear();
                        }
//
                        List<MessageType> messagelists= JSON.parseObject(StringUtils.getJsonListStr(jsonstr, "list"), new TypeReference<List<MessageType>>() {
                        });
                        if(Tools.checkList(messagelists)){
                            if(messagelists.size()==10){
                                ismoreDate=true;
                            }else{
                                ismoreDate=false;
                            }
                            messageTypes.addAll(messagelists);
                        }
                        if(messageTypes.size()==0){
                            emptyUtils.setEmptyTextAndImage("暂无消息", R.drawable.no_data);
                        }
//                        messageTypes.add(messageType);
                        messageAdapter.updateData(messageTypes);
                        break;
                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
        }
        @Override
        public void onFailed(int what, String jsonstr) {
            myList.onRefreshComplete();
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
        myList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNumber = 1;
                ismoreDate = true;
                loadDate(REQUEST_MESSAGE_TYPE);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (ismoreDate) {
                    // 联网加载数据
                    pageNumber++;
                    loadDate(REQUEST_MESSAGE_TYPE);
                } else {
                    myList.onRefreshComplete();
                }
            }
        });

    }


}
