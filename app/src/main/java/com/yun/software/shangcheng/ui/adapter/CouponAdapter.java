package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.ViewWidget.DashView;
import com.yun.software.shangcheng.ui.ViewWidget.VoucherView;
import com.yun.software.shangcheng.ui.entity.CouponEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 首页主题适配器
 *
 * @author yangyajun
 */
public class CouponAdapter extends BaseAdapter {
    private Context context;
    //	private String imgurl_test="http://www.zrytech.com/nopshop/File/Banners/1_20170713190223_thumb.jpg";
    private LayoutInflater inflater;
    /**
     * 主题集合
     */
    List<CouponEntity> list;
    private int type = 0;

    /**
     * @param context
     * @param list
     */
    public CouponAdapter(Context context, List<CouponEntity> list, int type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.type = type;
        this.setList(list);

    }

    /**
     * 设置数据集
     *
     * @param list
     */
    private void setList(List<CouponEntity> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<CouponEntity>();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CouponEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.coupon_item, null);
        }

        holder = ViewHolder.getViewHolder(convertView);
        if (type == 1) {
            holder.voucherView.setmBitmap(Color.parseColor("#E47070"));
            holder.tvPrice.setTextColor(Color.parseColor("#ffffff"));
            holder.tvPriceTime.setTextColor(Color.parseColor("#ffffff"));
            holder.tvModdle.setTextColor(Color.parseColor("#ffffff"));
            holder.tvAccept.setTextColor(Color.parseColor("#ffffff"));
            holder.dashView.setDashColor(Color.parseColor("#ffffff"));

        } else {
            holder.voucherView.setmBitmap(Color.parseColor("#E47070"));
            holder.tvPrice.setTextColor(Color.parseColor("#ffffff"));
            holder.tvPriceTime.setTextColor(Color.parseColor("#ffffff"));
            holder.tvModdle.setTextColor(Color.parseColor("#ffffff"));
            holder.tvAccept.setTextColor(Color.parseColor("#ffffff"));
            holder.dashView.setDashColor(Color.parseColor("#ffffff"));
        }
        CouponEntity couponEntity = getItem(position);
        holder.tvPrice.setText(couponEntity.getCoupon());
        holder.tvModdle.setText(couponEntity.getCouponCondition());
        holder.tvPriceTime.setText(couponEntity.getDescription());
        holder.tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acceptLisener!=null){
                    acceptLisener.clickAccept(position);
                }

            }
        });
        holder.tvAccept.setText("去使用");

        //        holder.tvCityname.setText(getItem(position).getCityName());

        return convertView;
    }

    public interface AcceptLisener {
        void clickAccept(int position);

    }

    private AcceptLisener acceptLisener;

    public void setAcceptLisener(AcceptLisener acceptLisener) {
        this.acceptLisener = acceptLisener;
    }

    static class ViewHolder {
        //        @Bind(R.id.tv_city_name)
        //        TextView tvCityname;
        @Bind(R.id.voucherView)
        VoucherView voucherView;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_price_moddle)
        TextView tvModdle;
        @Bind(R.id.tv_price_time)
        TextView tvPriceTime;
        @Bind(R.id.tv_accept)
        TextView tvAccept;
        @Bind(R.id.dashview)
        DashView dashView;

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
    public void updateData(List<CouponEntity> list) {
        setList(list);
        notifyDataSetChanged();
    }


}
