package com.yun.software.shangcheng.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.Address;
import com.yun.software.shangcheng.ui.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by handsome on 2016/4/8.
 */
public class AddressAdapter extends BaseAdapter implements View.OnClickListener {


    private List<Address> addressList;
    private LayoutInflater mInflater;
    private Context context;

    //选中条目
    private int position;

    public AddressAdapter(Context context, List<Address> addressList) {
        setList(addressList
        );
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_address, parent, false);
        }
        ViewHolder holder = getViewHolder(convertView);
        Address address = addressList.get(position);
        holder.tv_realname.setText(address.getPersonName());
        holder.tv_address.setText(StringUtils.getAddress(address.getAreaCode(),address.getAddress()));
        holder.tv_phone.setText(address.getPhone());
        holder.lin_checkIcon.setOnClickListener(this);
        holder.lin_checkIcon.setTag(position);
        //删除和编辑按钮
        holder.ly_edit.setOnClickListener(this);
        holder.ly_edit.setTag(position);
        holder.ly_delete.setOnClickListener(this);
        holder.ly_delete.setTag(position);
        boolean isdefault = address.getStatus()==1?true:false;
        if (isdefault) {
            holder.tv_isdefault.setText("默认地址");
            holder.iv_isdefault.setImageResource(R.drawable.ic_check);
        } else {
            holder.tv_isdefault.setText("设为默认");
            holder.iv_isdefault.setImageResource(R.drawable.ic_uncheckone);
        }
        return convertView;
    }

    /**
     * 获得控件管理对象
     *
     * @param view
     * @return
     */
    private ViewHolder getViewHolder(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_checkicon:
                position = (int) v.getTag();
                addesslisener.setDefalutAddress(position);
//                selectDefaultAddress(position);
//                break;
                break;
            case R.id.ly_edit:
                position = (int) v.getTag();
                addesslisener.editAddress(position);
//                activityController.startEditAddressActivityWithAddress(context, addressList.get(position));
                break;
            case R.id.ly_delete:
                position = (int) v.getTag();
                addesslisener.deleteAddress(position);
//                deleteAddress(position);
                break;
        }
    }
    /**
     * 设置数据集
     *
     * @param list
     */
    private void setList(List<Address> list) {
        if (list != null) {
            this.addressList= list;
        } else {
            this.addressList = new ArrayList<Address>();
        }
    }

    /**
     * 更新数据
     *
     * @param list
     */
    public void updateData(List<Address> list) {
        setList(list);
        notifyDataSetChanged();
    }
    /**
     * 控件管理类
     */
    private class ViewHolder {
        private TextView tv_realname, tv_phone, tv_address, tv_isdefault;
        private LinearLayout ly_edit, ly_delete,lin_checkIcon;
        private ImageView iv_isdefault;

        ViewHolder(View view) {
            tv_realname = (TextView) view.findViewById(R.id.tv_realname);
            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_isdefault = (TextView) view.findViewById(R.id.tv_isdefault);
            ly_edit = (LinearLayout) view.findViewById(R.id.ly_edit);
            ly_delete = (LinearLayout) view.findViewById(R.id.ly_delete);
            lin_checkIcon=(LinearLayout)view.findViewById(R.id.lin_checkicon);
            iv_isdefault=(ImageView)view.findViewById(R.id.iv_check_img);
        }
    }


    /**
     * 设置默认地址
     *
     * @param position
     */
    private void selectDefaultAddress(int position) {
        for (int i = 0; i < addressList.size(); i++) {
            Address address = addressList.get(i);
            address.setStatus(i == position?1:0);
            //升级数据库
//            addressController.update(address);
        }
        notifyDataSetChanged();
    }

    public  interface  AddressChangeLisener{
        void deleteAddress(int position);
        void editAddress(int position);
        void setDefalutAddress(int position);

    }
    AddressChangeLisener addesslisener;
    public void setAddessChangeLisener(AddressChangeLisener addesslisener){

        this.addesslisener=addesslisener;

    }
//
//    /**
//     * 删除地址
//     *
//     * @param position
//     */
//    private void deleteAddress(int position) {
//        final Address address = addressList.get(position);
//
//        final UIAlertView delDialog = new UIAlertView(context, "温馨提示","确定删除" + address.realname + "这条地址吗？",
//                "取消", "确定");
//        delDialog.show();
//
//        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
//
//                                       @Override
//                                       public void doLeft() {
//                                           delDialog.dismiss();
//                                       }
//
//                                       @Override
//                                       public void doRight() {
//                                           addressList.remove(address);
//                                           notifyDataSetChanged();
//                                           delDialog.dismiss();
//                                       }
//                                   }
//        );
//
//    }
}
