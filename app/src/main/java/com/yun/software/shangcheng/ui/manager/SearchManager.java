package com.yun.software.shangcheng.ui.manager;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;

import com.yun.software.shangcheng.ui.entity.TagState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanliang
 * on 2018/7/18 15:43
 */

public class SearchManager {
    List<TagState> list;
    private ObjectAnimator animator;
    private int lastTag = -1;
    private String[] type={"","sold","price"};

    public SearchManager() {
        list = new ArrayList<>();
    }

    public void setAllTags(List<TagState> list) {
        if(list==null){
            return;
        }
        this.list.clear();
        this.list.addAll(list);

    }

    public void setTag(int tag) {
        TagState tagStatedefaut = list.get(tag);
        if (lastTag == tag) {
            if (tagStatedefaut.getStateSort() == TagState.Statecategory.ASC) {
                tagStatedefaut.setStateSort(TagState.Statecategory.DESC);
            } else if (tagStatedefaut.getStateSort() == TagState.Statecategory.DESC) {
                tagStatedefaut.setStateSort(TagState.Statecategory.ASC);
            }
            notifyDataSetChanged();
            startAnimation(tagStatedefaut);
        } else {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setStateLine(TagState.Statecategory.NOLINE);
            }
            list.get(tag).setStateLine(TagState.Statecategory.LINE);
            if(tag!=0){
                if(tagStatedefaut.getStateSort()==(TagState.Statecategory.NONE)){
                    tagStatedefaut.setStateSort(TagState.Statecategory.ASC);
                }
            }
            notifyDataSetChanged();
        }

        lastTag = tag;
    }

    public TagState getTag() {
        return list.get(lastTag);
    }
    public String getOderFiled(){
        return type[lastTag];
    }


    private void notifyDataSetChanged() {

        for (int i = 0; i < list.size(); i++) {
            TagState tagStatedefaut = list.get(i);
            if (tagStatedefaut.getStateLine() == TagState.Statecategory.NOLINE) {
                tagStatedefaut.line.setVisibility(View.INVISIBLE);
                tagStatedefaut.tvRight.setTextColor(Color.parseColor("#323232"));
            } else {
                tagStatedefaut.line.setVisibility(View.VISIBLE);
                tagStatedefaut.tvRight.setTextColor(Color.parseColor("#ff0000"));
            }

        }


    }

    public void startAnimation(TagState tagState) {
        if (tagState.indectorView == null) {
            return;
        }
        if (animator != null) {
            animator = null;
        }
        if (tagState.getStateSort() == TagState.Statecategory.ASC) {
            animator = ObjectAnimator.ofFloat(tagState.indectorView, "rotation", -180, 0f);
            animator.setDuration(500);
            animator.start();


        } else if (tagState.getStateSort() == TagState.Statecategory.DESC) {
            animator = ObjectAnimator.ofFloat(tagState.indectorView, "rotation", 0, 180f);
            animator.setDuration(500);
            animator.start();
        } else {

        }
    }
    public void clear(){
        if (animator != null) {
            animator = null;
        }
    }
}
