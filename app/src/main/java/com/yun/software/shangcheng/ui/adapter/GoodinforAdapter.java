package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.GoodInfor;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;

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
public class GoodinforAdapter extends BaseAdapter {
	private Context context;
//	private String imgurl_test="http://www.zrytech.com/nopshop/File/Banners/1_20170713190223_thumb.jpg";
	private LayoutInflater inflater;
	private int type=0;
	/**
	 * 主题集合
	 */
	List<GoodInfor> list;
	/**
	 * @param context
	 * @param list
	 */
	public GoodinforAdapter(Context context, List<GoodInfor> list) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.setList(list);

	}
	public GoodinforAdapter(Context context, List<GoodInfor> list,int type) {
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
	private void setList(List<GoodInfor> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<GoodInfor>();
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public GoodInfor getItem(int position) {
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
			convertView = inflater.inflate(R.layout.mall_item, null);
		}
		holder = ViewHolder.getViewHolder(convertView);
		GoodInfor GoodInfor=list.get(position);
		GlidUtils.loadRoundImageView(context,GoodInfor.getLogo(),holder.ivMallImg,3,R.drawable.loading88);
		holder.tvSell.setText("已售"+GoodInfor.getSold()+"件");
		if(type==0){
			holder.tvPrice.setText("￥"+GoodInfor.getPrice());
		}else{
			holder.tvPrice.setText("积分："+GoodInfor.getScorePrice());
		}

		holder.tv_GoodInfor_name.setText(GoodInfor.getName());
		return convertView;
	}
	static class ViewHolder {
		/**
		 * 出售量
		 */
		@Bind(R.id.tv_mall_sell)
		TextView tvSell;
		/**
		 * 出售量
		 */
		@Bind(R.id.tv_mall_price)
		TextView tvPrice;
		/**
		 * 主题图标
		 */
		@Bind(R.id.iv_mall_item_top)
		ImageView ivMallImg;
		/**
		  * 名称
		  */
        @Bind(R.id.tv_product_name)
		TextView tv_GoodInfor_name;

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
	public void updateData(List<GoodInfor> list) {
		setList(list);
		notifyDataSetChanged();
	}

}
