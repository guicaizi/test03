package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.SearchCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 首页主题适配器
 *
 * @author yangyajun
 */
public class SearchKeyAdapter extends BaseAdapter {
    private Context context;
    //	private String imgurl_test="http://www.zrytech.com/nopshop/File/Banners/1_20170713190223_thumb.jpg";
    private LayoutInflater inflater;
    /**
     * 主题集合
     */
    List<SearchCity> list;

    /**
     * @param context
     * @param list
     */
    public SearchKeyAdapter(Context context, List<SearchCity> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.setList(list);

    }

    /**
     * 设置数据集
     *
     * @param list
     */
    private void setList(List<SearchCity> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<SearchCity>();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SearchCity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.searkey_item, null);
        }
        holder = ViewHolder.getViewHolder(convertView);
        holder.tvCityname.setText(getItem(position).getCityName());

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_city_name)
        TextView tvCityname;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public static ViewHolder getViewHolder(View contentView) {
            ViewHolder viewHolder = (ViewHolder) contentView.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(contentView);
                contentView.setTag(viewHolder);

            }

            return viewHolder;

        }
    }

    /**
     * 更新数据
     *
     * @param list
     */
    public void updateData(List<SearchCity> list) {
        setList(list);
        notifyDataSetChanged();
    }



}
