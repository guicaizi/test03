package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.VipEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 首页主题适配器
 *
 * @author yangyajun
 *
 */
public class VipsAdapter extends BaseAdapter {
	private Context context;
//	private String imgurl_test="http://www.zrytech.com/nopshop/File/Banners/1_20170713190223_thumb.jpg";
	private LayoutInflater inflater;
	private int type=0;
	private  int checkPostion=0;
	/**
	 * 主题集合
	 */
	List<VipEntity> list;
	/**
	 * @param context
	 * @param list
	 */
	public VipsAdapter(Context context, List<VipEntity> list) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.setList(list);

	}
	public VipsAdapter(Context context, List<VipEntity> list, int type) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.setList(list);
		this.type=type;

	}


	/**
	 * 设置数据集
	 *
	 * @param list
	 */
	private void setList(List<VipEntity> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<VipEntity>();
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public VipEntity getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setCheckPostion(int checkPostion){
		this.checkPostion=checkPostion;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.vip_list_view_item, null);
		}
		holder = ViewHolder.getViewHolder(convertView);
		if(checkPostion==position){
			holder.linBg.setBackgroundResource(R.drawable.round_strock_yello_shape);
		}else{
			holder.linBg.setBackgroundResource(R.drawable.round_strock_gray_shape_noraml);
		}
		holder.tvPrice.setText("￥"+getItem(position).getPrice());
		holder.tvVipName.setText(getItem(position).getName());
		holder.tvVipTime.setText(getItem(position).getDescription());

		return convertView;
	}
	static class ViewHolder {

        @Bind(R.id.tv_vip_name)
        TextView tvVipName;
        @Bind(R.id.tv_vip_time)
        TextView tvVipTime;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.lin_bg)
		LinearLayout linBg;


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
	public void updateData(List<VipEntity> list) {
		setList(list);
		notifyDataSetChanged();
	}

}
