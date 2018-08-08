package com.yun.software.shangcheng.ui.ViewWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by yanliang
 * on 2017/9/6 16:49
 */

public class MyImageView extends ImageView {

    private Paint mPaint;

    /**
     * 圆的宽度
     */
    private int mCircleWidth = 2;
    public MyImageView(Context context) {
        this(context, null);
}

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);//取消锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(Color.parseColor("#EBEBEB"));
        RectF oval = new RectF( 0, 0,
                getWidth(), getHeight());

        canvas.drawArc(oval,196,148,false,mPaint);
        super.onDraw(canvas);
    }
}
