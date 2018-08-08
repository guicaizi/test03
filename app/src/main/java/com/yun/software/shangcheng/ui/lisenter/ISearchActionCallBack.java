package com.yun.software.shangcheng.ui.lisenter;


import com.yun.software.shangcheng.ui.entity.SearchCity;

/**
 * Created by yanliang
 * on 2018/3/27 18:10
 */

public interface ISearchActionCallBack {


    void BackAciton();
    void SearchAciton(String string);
    void SearchAcitonChange(String string);
    void ChoiceCity(SearchCity city);
    void ClickAlearm();

}
