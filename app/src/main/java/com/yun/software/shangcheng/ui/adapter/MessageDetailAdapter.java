package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.MessageType;
import com.yun.software.shangcheng.ui.utils.StringUtils;

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
public class MessageDetailAdapter extends BaseAdapter {
	private Context context;
//	private String imgurl_test="http://www.zrytech.com/nopshop/File/Banners/1_20170713190223_thumb.jpg";
	private LayoutInflater inflater;
	private int type=0;
	/**
	 * 主题集合
	 */
	List<MessageType> list;
	/**
	 * @param context
	 * @param list
	 */
	public MessageDetailAdapter(Context context, List<MessageType> list) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.setList(list);

	}
	public MessageDetailAdapter(Context context, List<MessageType> list, int type) {
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
	private void setList(List<MessageType> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<MessageType>();
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MessageType getItem(int position) {
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
			convertView = inflater.inflate(R.layout.message_item_detail, null);
		}
		holder = ViewHolder.getViewHolder(convertView);
		MessageType mt=list.get(position);
		holder.tvMsgDes.setText(StringUtils.StringFilter(mt.getContent()));
		holder.tvMsgTime.setText(mt.getReadtime());
//
		return convertView;
	}
	static class ViewHolder {
        @Bind(R.id.tv_msg_time)
        TextView tvMsgTime;
        @Bind(R.id.tv_msg_des)
        TextView tvMsgDes;
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
	public void updateData(List<MessageType> list) {
		setList(list);
		notifyDataSetChanged();
	}

}
