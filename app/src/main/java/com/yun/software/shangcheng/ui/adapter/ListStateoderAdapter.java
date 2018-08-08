package com.yun.software.shangcheng.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun.software.baseApp.AppConfig;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.ViewWidget.CacheHolder;
import com.yun.software.shangcheng.ui.ViewWidget.NestListView;
import com.yun.software.shangcheng.ui.entity.OderItemInfor;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by flyer on 2016/12/20.
 */

public class ListStateoderAdapter extends BaseAdapter implements NestListView.BindViewGroupListener {


    private List<OderItemInfor> oderitems;
    private Context mContext;
    private String type;


    public ListStateoderAdapter( Context context,List<OderItemInfor> list) {
        mContext = context;
        setList(list);
    }
    @Override
    public int getCount() {
        return oderitems.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_order_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        OderItemInfor oderall = oderitems.get(position);
        OderItemInfor.IndentBean beans=oderall.getIndent();
        List<OderItemInfor.LiInInBean> goods = oderall.getLiInIn();
        viewHolder.re_sure.setVisibility(View.GONE);
        viewHolder.tv_sure.setVisibility(View.GONE);
        viewHolder.tv_back.setVisibility(View.GONE);
        viewHolder.re_bottom.setVisibility(View.GONE);
        viewHolder.tv_order_state.setVisibility(View.GONE);
        if(beans.getIndentStatus()==0){
            if(beans.getTransportStatus().equals("3")){

            }else{
                viewHolder.re_bottom.setVisibility(View.VISIBLE);
                viewHolder.tv_order_state.setVisibility(View.VISIBLE);
            }


       }else if(beans.getIndentStatus()==1&&beans.getTransportStatus().equals("0")){
            viewHolder.re_sure.setVisibility(View.VISIBLE);
            if(beans.getType()==1){
                viewHolder.tv_back.setVisibility(View.VISIBLE);
                viewHolder.tv_back.setText("申请退货");
            }else{
                viewHolder.re_sure.setVisibility(View.GONE);
                viewHolder.tv_back.setVisibility(View.GONE);
            }

       }else if(beans.getIndentStatus()==1&&beans.getTransportStatus().equals("1")){
           //待收货
           viewHolder.re_sure.setVisibility(View.VISIBLE);
           viewHolder.tv_sure.setVisibility(View.VISIBLE);
            if(beans.getType()==1){
                viewHolder.tv_back.setVisibility(View.VISIBLE);
            }else{
                viewHolder.tv_back.setVisibility(View.GONE);
            }
       }else if(beans.getIndentStatus()==2&&beans.getTransportStatus().equals("3")){
            viewHolder.re_sure.setVisibility(View.GONE);
            viewHolder.tv_sure.setVisibility(View.GONE);
            viewHolder.tv_back.setVisibility(View.GONE);
            viewHolder.re_bottom.setVisibility(View.GONE);
            viewHolder.tv_order_state.setVisibility(View.GONE);
        }

        int size = goods.size();
        viewHolder.tv_shop_name.setText("时享优品");
        viewHolder.tv_shop_name.setOnClickListener(listener);
        viewHolder.tv_shop_name.setTag(position);
        viewHolder.tv_shop_goods_number.setText("共" + size + "件商品");
        if(beans.getType()==1){
            viewHolder.tv_shop_goods_money.setText(StringUtils.formatMoney("￥%S(包邮)",beans.getRealPay()));
        }else{
            viewHolder.tv_shop_goods_money.setText(StringUtils.formatMoney("%S分(包邮)",beans.getRealPay()));
        }

        viewHolder.mNestListView.setBindViewGroupListener(this);
        viewHolder.mNestListView.setReuse(true);
        viewHolder.mNestListView.createNestListView(position, size);
        viewHolder.tv_state_order_cancle.setTag(position);
        viewHolder.tv_state_order_cancle.setOnClickListener(listener);
        viewHolder.tv_state_order_pay.setTag(position);
        viewHolder.tv_state_order_pay.setOnClickListener(listener);
        viewHolder.tv_wuliu.setText("物流单号  "+beans.getOrderNo());

        viewHolder.tv_sure.setTag(position);
        viewHolder.tv_sure.setOnClickListener(listener);
        viewHolder.tv_back.setOnClickListener(listener);
        viewHolder.tv_back.setTag(position);
        viewHolder.tv_wuliu.setTag(position);
        viewHolder.tv_wuliu.setOnClickListener(listener);
        viewHolder.tv_comment.setTag(position);
        viewHolder.tv_comment.setOnClickListener(listener);
        return convertView;
    }
    @Override
    public View getNestView(int positionInList, int positionInNestList, View convertView) {
        NestListViewHolder nestListHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.nest_list_item, null);
            // 缓存所有 convertView 下的小 view 的实例对象，避免重复 findViewById
            nestListHolder = new NestListViewHolder(convertView);
            convertView.setTag(nestListHolder);
        } else {
            nestListHolder = (NestListViewHolder) convertView.getTag();
        }
        OderItemInfor.IndentBean indentBean=oderitems.get(positionInList).getIndent();
        OderItemInfor.LiInInBean goods=oderitems.get(positionInList).getLiInIn().get(positionInNestList);
        OderItemInfor.LiInInBean.IndentInfoBean gooditem=goods.getIndentInfo();
        String labels=goods.getProductLabels();
        String pdtDesc= StringUtils.getJsonListStrXML(labels);
        String priceNew =gooditem.getPrice();
        int num = gooditem.getCount();
        String goodIcon=goods.getImg();
        nestListHolder.tvItemChild.setText(gooditem.getProductName());
        if(StringUtils.isNotEmpty(pdtDesc)){
            nestListHolder.tvGoodsParam.setText(pdtDesc);
        }
        if(indentBean.getType()==1){
            nestListHolder.tvPriceNew.setText("￥"+priceNew);
        }else{
            nestListHolder.tvPriceNew.setText(priceNew+"分");
        }
        nestListHolder.tvNum.setText("X " + num);
        GlidUtils.loadRoundImageView(mContext,goodIcon,nestListHolder.ivGoods,3);
        return convertView;
    }
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_state_order_pay:
                    int position= (int) v.getTag();
                    commentListener.itemPay(position);
                    break;
                case R.id.tv_state_order_cacle:
                    int position1= (int) v.getTag();
                    commentListener.itemCancle(position1);
                    break;
                case R.id.re_click_item:
//                    int[] itemPosion=(int[])v.getTag();
//                    commentListener.itemclick(itemPosion[0],itemPosion[1]);
                    break;
                case R.id.tv_sure:
                    int position3= (int) v.getTag();
                    MyLogUtils.i("http","请求参数确认收货1");
                    commentListener.itemSure(position3);
                    break;
                case R.id.tv_back_good:
                    int position8= (int) v.getTag();
                    commentListener.itemBack(position8);
                    break;
                case R.id.tv_comment:
                    int position4= (int) v.getTag();
//                    commentListener.itemComment(position4);
                    break;
                case R.id.tv_shop_name:
                    int position5= (int) v.getTag();
                    String venderId=oderitems.get(position5).getIndent().getId()+"";
                    Bundle bundle=new Bundle();
                    bundle.putString("VendorId",venderId);
//                    enterPage(ShoperDetailActivity.class,bundle);
                    break;
                case R.id.tv_wuliu:
//                    int position6= (int) v.getTag();
//                    String webUrl= ApiConstants.FORMAL_HOST+"Mobile/Order/LogisticsTracking?"+"tradeNo="
//                    +oderitems.get(position6).getTrackingNumber()+"&orderId="+oderitems.get(position6).getOrderId()+"&token="+biz.getTocken()+"&customerId="+biz.getCustomId();
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putString("weburl", webUrl);
//                    enterPage(WebActivity.class, bundle2);
//                    http://www.touhanggo.com/Mobile/Order/LogisticsTracking?tradeNo=02152255555665&orderId=88
                    break;

            }
        }
    };



    private static class NestListViewHolder extends CacheHolder {
        ImageView ivGoods;
        TextView tvItemChild;
        TextView tvGoodsParam;
        TextView tvPriceNew;
        TextView tvNum;
        TextView tvPriceOld;
        LinearLayout llGoodInfo;
        RelativeLayout reClickItem;
        TextView tvComment;

        NestListViewHolder(View convertView) {
            setConvertView(convertView);
            ivGoods=getView(R.id.ivGoods);
            tvItemChild=getView(R.id.tvItemChild);
            tvGoodsParam=getView(R.id.tvGoodsParam);
            tvPriceNew=getView(R.id.tvPriceNew);
            tvNum=getView(R.id.tvNum);
            reClickItem=getView(R.id.re_click_item);
            tvPriceOld=getView(R.id.tvPriceOld);
            llGoodInfo=getView(R.id.llGoodInfo);
            tvComment=getView(R.id.tv_comment);
    }
    }


    private static class ViewHolder extends CacheHolder {
        /**
         *合计商品数目
         */
        TextView tv_shop_goods_number;
        /**
         *合计金额
         */
        TextView tv_shop_goods_money;
        /**
         *店铺名称
         */
        TextView tv_shop_name;
        /**
         *购买商品列表
         */
        NestListView mNestListView;
        /**
         *付款
         */
        TextView tv_state_order_pay;
        /**
         *取消订单
         */
        TextView tv_state_order_cancle;
        /**
         *付款状态
         */
        TextView tv_order_state;
        /**
         *取消订单付款 布局
         */
        RelativeLayout re_bottom;
        /**
         *评论布局
         */
        RelativeLayout re_comment;
        /**
         *评论
         */
        /**
         *评论下横线
         */
        ImageView img_tag;
        /**
         *物流信息
         */
        TextView tv_wuliu;
        /**
         *确认收货
         */
        TextView tv_sure;
        /**
         *申请退货
         */
        TextView tv_back;
        /**
         *物流 确认收货布局
         */
        RelativeLayout re_sure;

        TextView tv_comment;
        ViewHolder(View convertView) {
            setConvertView(convertView);
            tv_shop_goods_money=getView(R.id.tv_goods_total_money);
            tv_shop_goods_number=getView(R.id.tv_total_goods);
            tv_shop_name=getView(R.id.tv_shop_name);
            mNestListView=getView(R.id.list_item_content_nlv);
            tv_state_order_cancle=getView(R.id.tv_state_order_cacle);
            tv_state_order_pay=getView(R.id.tv_state_order_pay);
            tv_order_state=getView(R.id.tv_order_state_item);
            re_bottom=getView(R.id.re_order_bottom);
            img_tag=getView(R.id.iv_tag);
            tv_wuliu=getView(R.id.tv_wuliu);
            tv_sure=getView(R.id.tv_sure);
            tv_back=getView(R.id.tv_back_good);
            re_sure=getView(R.id.re_sure);
            tv_comment=getView(R.id.tv_comment);
            re_comment=getView(R.id.re_comment);
        }
    }
    /**
     * 设置数据集
     */
    private void setList(List<OderItemInfor> list) {
        if (list != null) {
            this.oderitems = list;
        } else {
            this.oderitems= new ArrayList<OderItemInfor>();
        }
    }
    /**
     * 更新数据
     */
    public void updateData(List<OderItemInfor> list) {
        setList(list);
        notifyDataSetChanged();
    }
    CommentListener commentListener;
    public void setCommentListener(CommentListener commentListener){
              this.commentListener=commentListener;
    }
    public  interface  CommentListener{
        void itemPay(int position);
        void itemCancle(int position);
        void itemclick(int shopposition, int goodposition);
        void itemSure(int position);
        void itemBack(int position);
        void itemComment(int position);
    }
    public void enterPage(Class<?> cla, Bundle bundle) {
        if (cla == null) {
            return;
        }
        Intent intent = new Intent(mContext, cla);
        if (bundle != null) {
            intent.putExtra(AppConfig.START_ACTIVITY_PUTEXTRA, bundle);
        }
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
