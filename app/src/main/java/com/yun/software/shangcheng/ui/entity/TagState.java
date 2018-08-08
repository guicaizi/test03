package com.yun.software.shangcheng.ui.entity;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yanliang
 * on 2018/7/18 15:44
 */

public class TagState {
    Statecategory stateSort;
    Statecategory stateLine;
    public ImageView line;
    public TextView tvRight;
    public ImageView indectorView;

    /**
     *
     * @param stateSort
     * @param stateLine
     * @param line
     * @param tvRight
     */
    public TagState(Statecategory stateSort, Statecategory stateLine, ImageView line, TextView tvRight,ImageView indectorView) {
        this.stateSort = stateSort;
        this.stateLine = stateLine;
        this.line = line;
        this.indectorView=indectorView;
        this.tvRight = tvRight;
    }

    public Statecategory getStateSort() {
        return stateSort;
    }

    public void setStateSort(Statecategory stateSort) {
        this.stateSort = stateSort;
    }

    public Statecategory getStateLine() {
        return stateLine;
    }

    public void setStateLine(Statecategory stateLine) {
        this.stateLine = stateLine;
    }

    public  enum Statecategory {
        DESC("desc"), ASC("asc"),LINE("line"),NOLINE("noline"),NONE("");
        private String name;

        private Statecategory(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
