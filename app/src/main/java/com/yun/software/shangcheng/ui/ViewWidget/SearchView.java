package com.yun.software.shangcheng.ui.ViewWidget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.adapter.SearchKeyAdapter;
import com.yun.software.shangcheng.ui.entity.SearchCity;
import com.yun.software.shangcheng.ui.lisenter.ISearchActionCallBack;

import java.util.List;


/**
 * Created by yanliang
 * on 2018/3/27 17:53
 */

public class SearchView extends LinearLayout {
   private  Context context;
   private ClearEditText etSearch;
   private NoScrollListView listView;
   private ImageView searchBack;
   private ISearchActionCallBack mCallBack;
   private SearchKeyAdapter searchKeyAdapter;
   private List<SearchCity> lists;
   private ImageView alreamView;
    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();


    }

    /**
     * 关注b
     * 作用：初始化搜索框
     */
    private void init() {

        // 1. 初始化UI组件->>关注c
        initView();

//        queryData("");

        etSearch.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    // 1. 点击搜索按键后，根据输入的搜索字段进行查询
                    // 注：由于此处需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
                    if (!(mCallBack == null)){
                        mCallBack.SearchAciton(etSearch.getText().toString());
                    }
//                    Toast.makeText(context, "需要搜索的是" + etSearch.getText(), Toast.LENGTH_SHORT).show();

                    // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
//                    boolean hasData = hasData(etSearch.getText().toString().trim());
//                    // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
//                    if (!hasData) {
//                        insertData(et_search.getText().toString().trim());
//                        queryData("");
//                    }
                }
                return false;
            }
        });


        /**
         * 搜索框的文本变化实时监听
         */
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                // 每次输入后，模糊查询数据库 & 显示
                // 注：若搜索框为空,则模糊搜索空字符 = 显示所有的搜索历史
                String tempName = etSearch.getText().toString();
                if (!(mCallBack == null)){
                    mCallBack.SearchAcitonChange(etSearch.getText().toString());
                }

            }
        });
        searchBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(mCallBack == null)){
                    mCallBack.BackAciton();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.ChoiceCity(lists.get(position));
            }
        });
        alreamView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.ClickAlearm();
            }
        });
    }

    private void initView(){

        // 1. 绑定R.layout.search_layout作为搜索框的xml文件
        LayoutInflater.from(context).inflate(R.layout.search_layout,this);

        // 2. 绑定搜索框EditText
        etSearch = (ClearEditText) findViewById(R.id.et_search);
        listView = (NoScrollListView) findViewById(R.id.listView);
        searchKeyAdapter=new SearchKeyAdapter(context,null);
        listView.setAdapter(searchKeyAdapter);
        alreamView=(ImageView) findViewById(R.id.img_alerme);
        // 6. 返回按键
        searchBack = (ImageView) findViewById(R.id.iv_back);
    }


    /**
     * 点击键盘中搜索键后的操作，用于接口回调
     */
    public void setOnClickSearch(ISearchActionCallBack mCallBack){
        this.mCallBack = mCallBack;

    }


    public void updateListView(List<SearchCity> list) {
        this.lists=list;
        searchKeyAdapter.updateData(list);

    }
}
