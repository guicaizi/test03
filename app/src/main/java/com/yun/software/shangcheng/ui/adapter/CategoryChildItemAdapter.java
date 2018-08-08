package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.GoodCategoryInfor;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;

import java.util.List;

/**
 * author：wangzihang
 * date： 2017/8/8 19:15
 * desctiption：
 * e-mail：wangzihang@xiaohongchun.com
 */

public class CategoryChildItemAdapter extends BaseAdapter {

    private Context context;
    private List<GoodCategoryInfor.ChildrenBeanX.ChildrenBean> childitems;

    public CategoryChildItemAdapter(Context context, List<GoodCategoryInfor.ChildrenBeanX.ChildrenBean> childitems) {
        this.context = context;
        this.childitems = childitems;
    }


    @Override
    public int getCount() {
        if (childitems != null) {
            return childitems.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return childitems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodCategoryInfor.ChildrenBeanX.ChildrenBean subcategory = childitems.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_child_category, null);
            viewHold = new ViewHold();
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.item_home_name);
            viewHold.iv_icon = (ImageView) convertView.findViewById(R.id.item_album);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(subcategory.getName());
        GlidUtils.loadRoundImageView(context,subcategory.getImg(),viewHold.iv_icon,3);
        return convertView;


    }

    private static class ViewHold {
        private TextView tv_name;
        private ImageView iv_icon;
    }

}
