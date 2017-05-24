package com.grandmagic.edustore.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by zhangmengqi on 2017/5/23.
 */

public class Label extends AppCompatTextView {
    private int mColor = 0xffffa500; //暂时只需要一种颜色
    private Paint mPaint;
    private static final float sroke_width = 4;
    private static final float s = sroke_width / 2;
    public Label(Context context) {
        super(context);
        init();
    }

    public Label(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(sroke_width);
        setPadding(5,5,5,5);

        setTextColor(mColor);
        mPaint.setColor(mColor);  //暂时只需要一种颜色
    }

    public void setColor(int color) {
        mColor = color;
        postInvalidateDelayed(100);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mColor != -1) {
//            setTextColor(mColor);
//            mPaint.setColor(mColor);  暂时只需要一种颜色，到时候需要多种颜色的时候再放开
            //  画TextView的4个边
            canvas.drawLine(s, s, this.getWidth() - s, s, mPaint);
            canvas.drawLine(s, s, s, this.getHeight() - s, mPaint);
            canvas.drawLine(this.getWidth() - s, s, this.getWidth() - s, this.getHeight() - s, mPaint);
            canvas.drawLine(s, this.getHeight() - s, this.getWidth() - s, this.getHeight() - s, mPaint);

        }

        super.onDraw(canvas);
    }
}
