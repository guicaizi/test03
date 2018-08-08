package com.yun.software.shangcheng.ui.ViewWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by yanliang
 * on 2017/8/8 14:21
 */

public class ScrollviewPager extends ScrollView {

        // 滑动距离及坐标
        private float xDistance, yDistance, xLast, yLast;

        public ScrollviewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDistance = yDistance = 0f;
                    xLast = ev.getX();
                    yLast = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float curX = ev.getX();
                    final float curY = ev.getY();

                    xDistance += Math.abs(curX - xLast);
                    yDistance += Math.abs(curY - yLast);
                    xLast = curX;
                    yLast = curY;

                    if(xDistance > yDistance){
                        return false;
                    }
            }

            return super.onInterceptTouchEvent(ev);
        }
    }

