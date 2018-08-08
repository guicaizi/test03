package com.yun.software.shangcheng.ui.utils;

import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by yanliang
 * on 2018/4/19 14:17
 */

public class WeakRefHander extends android.os.Handler {

    private final WeakReference<Callback> mRef;
    private final int mLoopTime;
    private int NO_LOOP = -1;
    private int what =0;

    /**
     * 循环
     *
     * @param loopAction
     * @param loopTime
     */
    public WeakRefHander(Callback loopAction, int loopTime) {
        super();
        this.mRef = new WeakReference<>(loopAction);
        this.mLoopTime = loopTime;

    }

    /**
     * 不循环
     *
     * @param loopAction
     */
    public WeakRefHander(Callback loopAction) {
        super();
        mRef = new WeakReference<>(loopAction);
        mLoopTime = NO_LOOP;
    }

    @Override
    public void handleMessage(Message msg) {
        Callback action = mRef.get();
        if (action != null) {
            action.handleMessage(msg);
            if (mLoopTime != NO_LOOP) {
                sendEmptyMessageDelayed(what, mLoopTime);
            }
        }
    }

    public void start() {
        removeMessages(0);
        sendEmptyMessageDelayed(0, 0);
    }

    public void start(int what, long delay) {
        this.what = what;
        removeMessages(what);
        sendEmptyMessageDelayed(what, delay);
    }

    public void stop() {
        removeMessages(what);
    }

    public void clear() {
        removeMessages(what);
        mRef.clear();
    }
}