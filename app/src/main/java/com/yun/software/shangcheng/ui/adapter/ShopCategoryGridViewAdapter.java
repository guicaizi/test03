package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.MainCategory;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 首页主题适配器
 * 
 * @author yanliang
 * 
 */
public class ShopCategoryGridViewAdapter extends BaseAdapter {
	private Context context;
	private String imgurl_test="http://www.zrytech.com/nopshop/File/Banners/1_20170713190223_thumb.jpg";
	private LayoutInflater inflater;
	/**
	 * 主题集合
	 */
	List<MainCategory> list;
	/**
	 * @param context
	 * @param list
	 */
	public ShopCategoryGridViewAdapter(Context context, List<MainCategory> list) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.setList(list);
	}

	/**
	 * 设置数据集
	 * 
	 * @param list
	 */
	private void setList(List<MainCategory> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<MainCategory>();
		}
	}
	@Override
	public int getCount() {
		if(list.size()>4){
			return 4;
		}else{
			return list.size();
		}

	}
	@Override
	public MainCategory getItem(int position) {
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
			convertView = inflater.inflate(R.layout.category_gridview_item, null);
		}
		holder = ViewHolder.getViewHolder(convertView);
		MainCategory mainCategory=list.get(position);
		if(position==3){
			GlidUtils.loadCircleImageView(context,"https://gw.alicdn.com/tfs/TB11MUWSpXXXXa2apXXXXXXXXXX-183-144.png?getAvatar=1_.webp",holder.ivCategory);
			holder.tvCategory.setText("全部");
		}else{
			GlidUtils.loadCircleImageView(context,mainCategory.getImg(),holder.ivCategory);
			holder.tvCategory.setText(mainCategory.getName());
		}


		return convertView;
	}
	static class ViewHolder {
		/**
		 * 主题名称
		 */
		@Bind(R.id.tv_category_item)
		TextView tvCategory;
		/**
		 * 主题图标
		 */
		@Bind(R.id.iv_category_item)
		ImageView ivCategory;
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
	public void updateData(List<MainCategory> list) {
		setList(list);
		notifyDataSetChanged();
	}
}
