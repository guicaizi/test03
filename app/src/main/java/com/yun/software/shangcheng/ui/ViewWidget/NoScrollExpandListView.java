package com.yun.software.shangcheng.ui.ViewWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class NoScrollExpandListView extends ExpandableListView {

	public NoScrollExpandListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}
