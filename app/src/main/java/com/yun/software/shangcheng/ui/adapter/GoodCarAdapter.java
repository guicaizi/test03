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
import com.yun.software.shangcheng.ui.entity.GoodCarItem;
import com.yun.software.shangcheng.ui.lisenter.OnShoppingCartChangeListener;
import com.yun.software.shangcheng.ui.manager.GoodCarManager;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by handsome on 2016/4/8.
 */
public class GoodCarAdapter extends BaseAdapter {

    private Context context;
    //	private String imgurl_test="http://www.zrytech.com/nopshop/File/Banners/1_20170713190223_thumb.jpg";
    private LayoutInflater inflater;
    private boolean isshowredChoice=true;
    /**
     * 主题集合
     */
    List<GoodCarItem> list;
    private boolean isEdit=false;
    int frompage=0;
    private OnShoppingCartChangeListener mChangeListener;

    /**
     * @param context
     * @param list
     */
    public GoodCarAdapter(Context context, List<GoodCarItem> list) {
          this(context, list,0);

    }
    public GoodCarAdapter(Context context, List<GoodCarItem> list,int frompage) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.setList(list);
        this.frompage=frompage;

    }

        /**
         * 设置数据集
         *
         * @param list
         */
    private void setList(List<GoodCarItem> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<GoodCarItem>();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GoodCarItem getItem(int position) {
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
        GlidUtils.loadImageNormal(context,getItem(position).getLogo(),holder.ivGoods);
        holder.tvGoodName.setText(getItem(position).getProductName());
        holder.tvGoodParams.setText(getItem(position).getLabel());
        holder.tvNum2.setText(getItem(position).getCount()+"");
        holder.tvNum.setText("X "+getItem(position).getCount());
        if(frompage==0){
            holder.tvPriceNew.setText("￥ "+getItem(position).getPrice());
        }else{
            holder.tvPriceNew.setText(getItem(position).getPrice()+"分");
        }
        checkItem(getItem(position).isCheck(),holder.ivCheckGood);
        if(isEdit){
            holder.tvNum.setVisibility(View.GONE);
            holder.linGoodEdit.setVisibility(View.VISIBLE);
        }else{
            holder.tvNum.setVisibility(View.VISIBLE);
            holder.linGoodEdit.setVisibility(View.GONE);
        }
        if(!isshowredChoice){
            holder.reCheckGood.setVisibility(View.GONE);
        }
        holder.reCheckGood.setTag(position);
        holder.reCheckGood.setOnClickListener(mylistener);
        holder.tvAdd.setTag(position);
        holder.tvAdd.setOnClickListener(mylistener);
        holder.tvReduce.setTag(position);
        holder.tvReduce.setOnClickListener(mylistener);
        return convertView;

    }
    private   View.OnClickListener  mylistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.re_checkGood:
                    int postion= (int) v.getTag();
                    if(getItem(postion).isCheck()){
                        getItem(postion).setCheck(false);
                    }else{
                        getItem(postion).setCheck(true);
                    }
                    if (mChangeListener != null) {
                        mChangeListener.getIsSelectAll(GoodCarManager.getGoodsIsAllCheck(list));
                    }
                    setTotalMoneyChange();
                    notifyDataSetChanged();
                    break;
                case R.id.tvreduce:
                     int postion2= (int) v.getTag();
                     GoodCarManager.addOrReduceGoodsNum(false,list.get(postion2),((TextView) (((View) (v.getParent())).findViewById(R.id.tvNum2))));
                     setItemChange(getItem(postion2));
                     setTotalMoneyChange();
                     break;
                case R.id.tvadd:
                    int postion3= (int) v.getTag();
                    GoodCarManager.addOrReduceGoodsNum(true,list.get(postion3),((TextView) (((View) (v.getParent())).findViewById(R.id.tvNum2))));
                    setItemChange(getItem(postion3));
                    setTotalMoneyChange();
                    break;

            }



        }
    };

    public static  boolean checkItem(boolean isSelect, ImageView ivCheck) {
        if (isSelect) {
            ivCheck.setImageResource(R.drawable.ic_check);
        } else {
            ivCheck.setImageResource(R.drawable.ic_uncheckone);
        }
        return isSelect;
    }
    public void setEdit(boolean isEdit){
        this.isEdit=isEdit;
        notifyDataSetChanged();
    }

    public void setTotalMoneyChange(){
        if (mChangeListener != null) {
            mChangeListener.onTotalChange(GoodCarManager.getShoppingCount(list));
        }
    }
    public void setItemChange(GoodCarItem goodCarItem){
        if (mChangeListener != null) {
            mChangeListener.onItemChange(goodCarItem);
        }
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
         *商品名称
         */
        @Bind(R.id.tv_good_name)
        TextView tvGoodName;
        /**
         *商品参数
         */
        @Bind(R.id.tv_good_params)
        TextView tvGoodParams;
        /**
         *购物车数目
         */
        @Bind(R.id.tvNum)
        TextView tvNum;
        /**
         *减少
         */
        @Bind(R.id.tvreduce)
        TextView tvReduce;
        /**
         *增加
         */
        @Bind(R.id.tvNum2)
        TextView tvNum2;
        @Bind(R.id.tvadd)
        TextView tvAdd;
        /**
         *编辑布局
         */
        @Bind(R.id.lin_good_edit)
        LinearLayout linGoodEdit;
        /**
         *当前价格
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
    public void updateData(List<GoodCarItem> list) {
        setList(list);
        setTotalMoneyChange();
        notifyDataSetChanged();
    }
    public void ChoiceAll(boolean flag) {
        for (GoodCarItem goodCarItem : list) {
            goodCarItem.setCheck(flag);

        }
        setTotalMoneyChange();
        notifyDataSetChanged();
    }
    public void setOnShoppingCartChangeListener(OnShoppingCartChangeListener changeListener) {
        this.mChangeListener = changeListener;
    }
}
