package com.yun.software.shangcheng.ui.adapter;

import android.graphics.Color;

import com.yun.software.shangcheng.ui.entity.GoodCategoryInfor;

import java.util.List;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by yanliang
 * on 2017/10/27 14:04
 */

public class MyTabAdapter implements TabAdapter {
    List<GoodCategoryInfor> list;

    public MyTabAdapter(List<GoodCategoryInfor> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return null;
    }


    @Override
    public QTabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public QTabView.TabTitle getTitle(int position) {
        return new TabView.TabTitle.Builder()
                //                .setContent(list.get(position).getCategoryName())
                .setContent(list.get(position).getName())
                .setTextColor(Color.parseColor("#44525C"), Color.parseColor("#798193"))
                .build();
    }

    @Override
    public int getBackground(int position) {
        return  -1;
        // 设置 标签背景色
        // return  R.drawable.circle_red_shape;

    }
}
