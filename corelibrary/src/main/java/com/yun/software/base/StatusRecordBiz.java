package com.yun.software.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 状态记录业务类
 *
 * @author LiBing
 */
public class StatusRecordBiz {
    /**
     * 共享参数
     */
    private SharedPreferences pref;

    public StatusRecordBiz(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 保存第一次登录标记
     */
    public void setFirstLoadTag(boolean tag) {
        pref.edit().putBoolean("FirstLoadTag", tag).commit();
    }

    /**
     * 获取第一次登录标记
     *
     * @return
     */
    public boolean getFirstLoadMain() {
        return pref.getBoolean("FirstLoadMain", true);
    }
    /**
     * 获取第一次选择城市
     *
     * @return
     */
    public boolean getFirstChoiceCity() {
        return pref.getBoolean("FirstChoiceCity", true);
    }

//    --
    /**
     * 是否是新注册用户
     */
    public void setIsHaveNew(boolean tag) {
        pref.edit().putBoolean("IsHaveNew", tag).commit();
    }

    /**
     * 是否是新注册用户
     *
     * @return
     */
    public boolean getIsHaveNew() {
        return pref.getBoolean("IsHaveNew", false);
    }

    /**
     * 是否显示新功能指引
     */
    public void setIsHaveGuide(boolean tag) {
        pref.edit().putBoolean("IsHaveGuide", tag).commit();
    }

    /**
     * 是否显示新功能指引
     *
     * @return
     */
    public boolean getIsHaveGuide() {
        return pref.getBoolean("IsHaveGuide", true);
    }
    /**
     * 保存第一次选择城市
     */
    public void setFirstChoiceCity(boolean tag) {
        pref.edit().putBoolean("FirstChoiceCity", tag).commit();
    }

    /**
     * 获取第一次选择成长信息
     *
     * @return
     */
    public boolean getFirstChoiceSex() {
        return pref.getBoolean("FirstChoiceSex", true);
    }

    /**
     * 设置默认地址
     */
    public void setDefaultAddress(String  tag) {
        pref.edit().putString("defaultaddress", tag).commit();
    }

    /**
     * 获取默认地址
     */
    public String getDefaultAddress() {
        return pref.getString("defaultaddress","0");
    }

    /**
     * 设置默认用户id
     */
    public void setCustomId(String customid) {
        pref.edit().putString("customid", customid).commit();
    }

    /**
     * 获取默认用户id
     */
    public String getCustomId() {
        return pref.getString("customid","0");
    }


    /**
     * 获取默认用户id
     */
    public String getUserName() {
        return pref.getString("userName","0");
    }
    /**
     * 设置默认用户名称
     */
    public void setUserName(String userName) {
        pref.edit().putString("userName", userName).commit();
    }
    /**
     * 设置默认用户id
     */
    public void setCustomToken(String customtoken) {
        pref.edit().putString("custom_token", customtoken).commit();
    }
    /**
     * 获取默认用户名称
     */
    public String getCustomToken() {
        return pref.getString("custom_token","");
    }
    /**
     * 设置Authrition
     */
    public String getAuthorization() {
        return pref.getString("authorization","0");
    }

    /**
     * 设置默认用户id
     */
    public void setAuthorization(String authorization) {
        pref.edit().putString("authorization", authorization).commit();
    }



    /**
     * 设置默认用户token
     */
    public void setTocken(String token) {
        pref.edit().putString("token", token).commit();
    }

    /**
     * 获取默认用户id
     */
    public String getTocken() {
        return pref.getString("token","0");
    }

    /**
     * 设置默认用户latitude
     */
    public void setlatitude(String latitude) {
        pref.edit().putString("latitude", latitude).commit();
    }

    /**
     * 获取默认用户latitude
     */
    public String getlatitude() {
        return pref.getString("latitude","39.915662");
    }
    /**
     * 设置默认用户lontitude
     */
    public void setlontitude(String lontitude) {
        pref.edit().putString("lontitude", lontitude).commit();
    }

    /**
     * 获取默认用户lontitude
     */
    public String getlontitude() {
        return pref.getString("lontitude","116.40493");
    }
    /**
     * 设置默认用户city
     */
    public void setcity(String city) {
        pref.edit().putString("city", city).commit();
    }
    /**
     * 获取默认用户city
     */
    public String getChoiceCity() {
        return pref.getString("choice_city","");
    }
    /**
     * 设置默认用户city
     */
    public void setChoiceCity(String city) {
        pref.edit().putString("choice_city", city).commit();
    }
    /**
     * 获取默认用户city
     */
    public String getcity() {
        return pref.getString("city","北京市");
    }
    /**
     * 设置默认用户addr
     */
    public void setaddress(String address) {
        pref.edit().putString("address", address).commit();
    }
    /**
     * 获取默认用户addr
     */
    public String getaddress() {
        return pref.getString("address","");
    }

    public void setScore(String score) {
        pref.edit().putString("score", score).commit();
    }
    public String getScore() {
        return pref.getString("score","0.0");
    }
}
