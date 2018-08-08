package com.yun.software.shangcheng.ui.manager;

import android.widget.TextView;

import com.yun.software.shangcheng.ui.entity.GoodCarItem;
import com.yun.software.shangcheng.ui.utils.DecimalUtil;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanliang
 * on 2018/7/13 10:52
 */

public class GoodCarManager {
   public static boolean getGoodsIsAllCheck(List<GoodCarItem> list){
       for (int i = 0; i < list.size(); i++) {
           if(!list.get(i).isCheck()){
               return false;
           }
       }

       return true;
   }
    /**
     * 增减数量，操作通用，数据不通用
     */
    public static void addOrReduceGoodsNum(boolean isPlus, GoodCarItem goods, TextView tvNum) {
        int currentNum= goods.getCount();
        int  num = 1;
        if (isPlus) {
            num = currentNum + 1;
        } else {
            int i =currentNum;
            if (i > 1) {
                num =currentNum-1;
            } else {
                num =1;
            }
        }

        tvNum.setText(String.valueOf(num));
        goods.setCount(num);

    }
    /**
     * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
     *
     * @return 0=选中的商品数量；1=选中的商品总价
     */
    public static String getShoppingCount(List<GoodCarItem> listGoods) {
        MyLogUtils.i("money","调用了");
        String selectedMoney = "0";
        for (int i = 0; i < listGoods.size(); i++) {

                boolean isSelectd = listGoods.get(i).isCheck();
                if (isSelectd) {
                    String price = String.valueOf(listGoods.get(i).getPrice());
                    String num = String.valueOf(listGoods.get(i).getCount());;
                    String countMoney = DecimalUtil.multiply(price, num);
                    selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
                }

        }
        Double cny = Double.parseDouble(selectedMoney);
        DecimalFormat df = new DecimalFormat("0.00");//格式化
        String CNY=df.format(cny);
        String countMoney = String.format("合计：￥%s", CNY);
        return countMoney;
    }

    public static ArrayList<String> getChoiceIds(List<GoodCarItem> caritemslist) {
        ArrayList<String> ids=new ArrayList<>();

        for (int i = 0; i < caritemslist.size(); i++) {
            if(caritemslist.get(i).isCheck()){
                ids.add(caritemslist.get(i).getId());
            }
        }

        return ids;
    }



}
