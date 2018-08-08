package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.GoodCategoryInfor;
import com.yun.software.shangcheng.ui.utils.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class CategoryChildAdapter extends BaseAdapter {

    private Context context;
    private List<GoodCategoryInfor.ChildrenBeanX> childitems;
    private String imgUrls;
    private int onePosion;

    public CategoryChildAdapter(Context context, List<GoodCategoryInfor.ChildrenBeanX> childitems) {
        this.context = context;
        setList(childitems);
    }

    @Override
    public int getCount() {
        return childitems.size();
    }
    /**
     * 设置数据集
     *
     * @param list
     */
    private void setList(List<GoodCategoryInfor.ChildrenBeanX> list) {
        if (list != null) {
            this.childitems = list;
        } else {
            this.childitems = new ArrayList<GoodCategoryInfor.ChildrenBeanX>();
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
//    http://www.zrytech.com/NopShop/api/Banner/GetShopBannerPictures

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GoodCategoryInfor.ChildrenBeanX ChildrenBeanX = childitems.get(position);
        List<GoodCategoryInfor.ChildrenBeanX.ChildrenBean> dataList = ChildrenBeanX.getChildren();
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_child, null);
            viewHold = new ViewHold();
            viewHold.gridView = (NoScrollGridView) convertView.findViewById(R.id.gridView);
            viewHold.blank = (TextView) convertView.findViewById(R.id.blank);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        CategoryChildItemAdapter adapter = new CategoryChildItemAdapter(context, dataList);
        viewHold.blank.setText(ChildrenBeanX.getName());
        viewHold.gridView.setAdapter(adapter);
        viewHold.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positiontwo, long id) {
                childLisener.childclick(onePosion,position,positiontwo);

            }
        });
        return convertView;
    }

    private static class ViewHold {
        private NoScrollGridView gridView;
        private TextView blank;
    }
    /**
     * 更新数据
     *
     * @param list
     */
    public void updateData(List<GoodCategoryInfor.ChildrenBeanX> list,int onePosiont) {
        this.onePosion=onePosiont;
        setList(list);
        notifyDataSetChanged();
    }
    public interface  ChildLisener{
        public void childclick(int onepostion,int postion,int twoposition);

    }
    private ChildLisener childLisener;
    public void setChildLisener( ChildLisener childLisener){
            this.childLisener=childLisener;


    }
}
