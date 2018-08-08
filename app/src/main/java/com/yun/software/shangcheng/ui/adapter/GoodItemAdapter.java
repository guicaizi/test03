package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.OderItemInfor;
import com.yun.software.shangcheng.ui.lisenter.OnShoppingCartChangeListener;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by handsome on 2016/4/8.
 */
public class GoodItemAdapter extends BaseAdapter {

    private Context context;
    //	private String imgurl_test="http://www.zrytech.com/nopshop/File/Banners/1_20170713190223_thumb.jpg";
    private LayoutInflater inflater;
    private boolean isshowredChoice = true;
    /**
     * 主题集合
     */
    List<OderItemInfor.LiInInBean> list;
    private boolean isEdit = false;
    private OnShoppingCartChangeListener mChangeListener;

    /**
     * @param context
     * @param list
     */
    public GoodItemAdapter(Context context, List<OderItemInfor.LiInInBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.setList(list);

    }

    /**
     * 设置数据集
     *
     * @param list
     */
    private void setList(List<OderItemInfor.LiInInBean> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<OderItemInfor.LiInInBean>();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public OderItemInfor.LiInInBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setIsshowredChoice(boolean isshowredChoice) {
        this.isshowredChoice = isshowredChoice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_elv_child_test, null);
        }
        holder = ViewHolder.getViewHolder(convertView);
        OderItemInfor.LiInInBean liInInBean = list.get(position);
        GlidUtils.loadImageNormal(context, liInInBean.getImg(), holder.ivGoods);
        holder.tvGoodName.setText(liInInBean.getIndentInfo().getProductName());
        holder.tvGoodParams.setText(StringUtils.getJsonListStrXML(liInInBean.getProductLabels()));
        holder.tvNum.setText("X " + liInInBean.getIndentInfo().getCount());
        holder.tvPriceNew.setText("￥ " + liInInBean.getIndentInfo().getPrice());
        holder.tvNum.setVisibility(View.VISIBLE);
        holder.linGoodEdit.setVisibility(View.GONE);
        holder.reCheckGood.setVisibility(View.GONE);
        return convertView;
    }


    static class ViewHolder {
        /**
         * 选择圆框
         */
        @Bind(R.id.re_checkGood)
        RelativeLayout reCheckGood;
        /**
         * 选择商品图片
         */
        @Bind(R.id.ivGoods)
        ImageView ivGoods;
        /**
         * 选择商品图片
         */
        @Bind(R.id.ivCheckGood)
        ImageView ivCheckGood;
        /**
         * 商品名称
         */
        @Bind(R.id.tv_good_name)
        TextView tvGoodName;
        /**
         * 商品参数
         */
        @Bind(R.id.tv_good_params)
        TextView tvGoodParams;
        /**
         * 购物车数目
         */
        @Bind(R.id.tvNum)
        TextView tvNum;
        /**
         * 减少
         */
        @Bind(R.id.tvreduce)
        TextView tvReduce;
        /**
         * 增加
         */
        @Bind(R.id.tvNum2)
        TextView tvNum2;
        @Bind(R.id.tvadd)
        TextView tvAdd;
        /**
         * 编辑布局
         */
        @Bind(R.id.lin_good_edit)
        LinearLayout linGoodEdit;
        /**
         * 当前价格
         */
        @Bind(R.id.tvPriceNew)
        TextView tvPriceNew;

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
    public void updateData(List<OderItemInfor.LiInInBean> list) {
        setList(list);
        notifyDataSetChanged();
    }


}
