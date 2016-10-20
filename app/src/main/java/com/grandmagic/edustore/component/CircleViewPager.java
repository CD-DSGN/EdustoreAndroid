package com.grandmagic.edustore.component;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhangmengqi on 2016/9/14.
 */
public class CircleViewPager extends ViewPager {

    public static final int DELAY_MILLIS = 3000;
    private int mImageIndex = 0;
    private Context mContext;

    public CircleViewPager(Context context) {
        super(context);
        init();
        mContext = context;
    }

    public CircleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mContext = context;
    }

    private void init() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        startImageCycle();
                        break;
                    default:
                        // 停止图片滚动
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });

    }

    private void startImageTimerTask() {
        stopImageTimerTask();
        // 图片每3秒滚动一次
        mHandler.postDelayed(mImageTimerTask, DELAY_MILLIS);
    }

    private void stopImageTimerTask() {
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();
    private Runnable mImageTimerTask = new Runnable() {

        @Override
        public void run() {
            PagerAdapter pagerAdapter = CircleViewPager.this.getAdapter();
            if (pagerAdapter != null) {
                // 下标等于图片列表长度说明已滚动到最后一张图片,重置下标
                if ((++mImageIndex) == pagerAdapter.getCount()) {
                    mImageIndex = 0;
                }
                CircleViewPager.this.setCurrentItem(mImageIndex);
            } else {

            }
        }
    };

    /**
     * 开始轮播(手动控制自动轮播与否，便于资源控制)
     */
    public void startImageCycle() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播——用于节省资源
     */
    public void pushImageCycle() {
        stopImageTimerTask();
    }

}
