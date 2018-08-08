package com.yun.software.shangcheng.ui.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串操作工具
 *
 * @author LiBing
 */
public class StringUtils {

    /**
     * 产生时间戳
     *
     * @return
     */
    public static String createTimestamp() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp;
    }


    /**
     * 产生接口验证参数
     *
     * @param timestamp
     * @return
     */
    public static String createSign(String timestamp) {
        String sign = "";
        String toDigest = timestamp +"yanzheng";
        sign = MD5.getMD5(toDigest);
        return sign;
    }

    public  static String encodeHeadInfo(String totalstr ) {

        String headInfo=getEncripytion(totalstr);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append( String.format ("\\u%04x", (int)c) );
            } else {
                stringBuffer.append(c);
            }
        }
        String str=stringBuffer.toString();
        return str.substring(0,str.length()-6);
    }
    public static String getEncripytion(String totalstr){

        StringBuffer buffer=new StringBuffer();
        buffer.append("Basic");
        buffer.append(" ");
        buffer.append(Base64.encodeToString(totalstr.getBytes(),Base64.DEFAULT));
        return buffer.toString();
    };
    /**
     * 将对象数据转换成Int
     *
     * @param obj
     * @return
     */
    public static int toInt(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        String str = String.valueOf(obj);
        return (int) Float.parseFloat(str);
    }
    /**
     * 将对象数据转换成String
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return "";
        }
        return String.valueOf(obj);
    }
    public static String getText(View v) {
        String text = "";
        if (v instanceof EditText) {
            text = ((EditText) v).getText().toString().trim();
        } else if (v instanceof TextView) {
            text = ((TextView) v).getText().toString().trim();
        } else if (v instanceof Button) {
            text = ((Button) v).getText().toString().trim();
        }
        return text;
    }

    /**
     * 将对象类小数型转换成String 整型
     *
     * @param obj
     * @return
     */
    public static String toStringInt(Object obj) {
        return toString(toInt(obj));
    }
    /**
     * 将对象类小数型转换成String 整型
     *
     * @return
     */
    public static String toStringMoney(Double db) {
        if(db!=null){
            DecimalFormat df = new DecimalFormat("0.00");//格式化
            String CNY=df.format(db);
            return CNY;
        }else{
            return "";
        }

    }

    /**
     * 将对象数据转换成Float
     *
     * @param obj
     * @return
     */
    public static float toFloat(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        String str = String.valueOf(obj);
        return Float.parseFloat(str);
    }
    public static double toDouble(String str) {

        return Double.parseDouble(str);
    }
    /**
     * 判断字符串是否null或为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {

        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串不是null或为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String commbingJson(Map map,Map params) {
        Map map0 =map;
        map0.put("param",params);
        return JSON.toJSONString(map0);
    }
    public static String commbingJson(Map map) {
        return JSON.toJSONString(map);
    }

        /**
         * 判断是否是11位手机号码
         *
         * @param mobile
         * @return
         */
    public static boolean isMobile(String mobile) {
        if (isEmpty(mobile)) {
            return false;
        }
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(14[5,7])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }


    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String str ="[1-9]{6}19[0-9]{2}"
                + "((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))"
                + "[0-9]{3}[0-9x]{1}";
        Pattern pattern = Pattern.compile(str);
        return pattern.matcher(idCard).matches()?true:false;
    }
    /**
     * str1 时间 比str2 时间大
     */
    public static boolean twoTimeCompress(String str1, String str2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date, date2;
        boolean bool = false;
        try {
            date = sdf.parse(str1);
            date2 = sdf.parse(str2);
//            long str = Math.max(date.getTime(), date2.getTime());
            long str =date.getTime()>=date2.getTime()?date.getTime():date2.getTime();
            if (str == date.getTime()) {
                bool = true;
                if (date.getTime() == date2.getTime()) {
                    bool = false;
                }
            } else {
                bool = false;
                if (date.getTime() == date2.getTime()) {
                    bool = true;
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bool;
    }
    /**
     * 显示时间差值
     *
     * @param laseTimeMillis
     * @return
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static long showTimMargin(long laseTimeMillis) {
        long second = 0;
        try {
            if (laseTimeMillis == 0) {
                return second;
            }
            long currentTime = System.currentTimeMillis();
            long passtime = currentTime - laseTimeMillis;
            second = passtime / 1000;
            return second;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return second;
    }
    public static String getJson(Context context,String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static String getJsonListStr(String jsonstr,String liststr) {
        if(isEmpty(jsonstr)){
            return "";
        }
        Map map= JSON.parseObject(jsonstr,Map.class);
        return StringUtils.toString(map.get(liststr));
    }
    public static String getJsonKeyStr(String jsonstr,String key) {
        if(isEmpty(jsonstr)){
            return "";
        }
        Map map= JSON.parseObject(jsonstr,Map.class);
        return StringUtils.toString(map.get(key));
    }

    public static String getJsonListStrXML(String jsonstr) {
        if(isEmpty(jsonstr)){
            return "";
        }
        Map<String,String> map= JSON.parseObject(jsonstr,Map.class);
        StringBuffer buffer=new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            buffer.append(entry.getKey());
            buffer.append(":");
            buffer.append(entry.getValue());
            buffer.append(" ");
        }
        return buffer.toString();
    }

    public static String formatMoney(String str,String totalprive) {
        Double cny = Double.parseDouble(totalprive);
        DecimalFormat df = new DecimalFormat("0.00");//格式化
        String CNY=df.format(cny);
        if(isEmpty(str)){
            return CNY;
        }
        String countMoney = String.format(str, CNY);
        return countMoney;
    }

    public static String formateTime(long createDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return  sdf.format(createDate);
    }

    public static String getAddress(String areaCode,String address) {
        if(TextUtils.isEmpty(areaCode)){
            return "";
        }
        List<Map<String, String>> lists = JSON.parseObject(areaCode, new TypeReference<List<Map<String, String>>>() {
        });
        StringBuffer bufferAddress=new StringBuffer();
        for (Map<String, String> mapitem : lists) {
            bufferAddress.append(mapitem.get("name"));
        }
        if(!TextUtils.isEmpty(address)){
            bufferAddress.append(address);
        }

        return bufferAddress.toString();
    }
    public static String  getArressid(String areaCode,int postion) {
        if(TextUtils.isEmpty(areaCode)){
            return "";
        }
        List<Map<String, String>> lists = JSON.parseObject(areaCode, new TypeReference<List<Map<String, String>>>() {
        });
        List<String> ids=new ArrayList<>();
        for (Map<String, String> mapitem : lists) {
            ids.add(mapitem.get("id"));
        }
        if(postion<ids.size()){
            return ids.get(postion);
        }else{
            return "0";
        }
    }
    public static String StringFilter(String str) throws PatternSyntaxException {
        str=str.replaceAll("【","[").replaceAll("】","]").replaceAll("！","!");//替换中文标号
        String regEx="[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


}
