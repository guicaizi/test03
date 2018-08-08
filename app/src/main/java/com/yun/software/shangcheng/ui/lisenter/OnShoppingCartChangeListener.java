package com.yun.software.shangcheng.ui.lisenter;

import com.yun.software.shangcheng.ui.entity.GoodCarItem;

/**
 * Created by Administrator on 2015/11/3.
 */
public interface OnShoppingCartChangeListener {
     void onTotalChange(String selectMoney);
     void  getIsSelectAll(boolean isSelectedAll);
      void onItemChange(GoodCarItem goodCarItem);
}
