package com.grandmagic.edustore.component;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.external.viewpagerindicator.PageIndicator;

/**
 * Created by zhangmengqi on 2016/11/8.
 */
public class CircleFrameLayout extends FrameLayout {
    public ViewPager mViewPager;
    public PageIndicator mPageIndicator;
    public static final int DELAY_MILLIS = 3000;
    public int mImageIndex = 0;

    public CircleFrameLayout(Context context) {
        super(context);
    }

    public CircleFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    public CircleFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, 0);
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            System.out.println("ACTION_DOWN");
            pushImageCycle();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
//            System.out.println("ACTION_UP");
            startImageCycle();
        }

        return false;
    }

    public void setPageIndicator(PageIndicator indicator) {
        this.mPageIndicator = indicator;
        mPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int mPreviousState = ViewPager.SCROLL_STATE_IDLE;
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // All of this is to inhibit any scrollable container from consuming our touch events as the user is changing pages
//                System.out.println("state:" + state);
                if (mPreviousState != ViewPager.SCROLL_STATE_IDLE && state == ViewPager.SCROLL_STATE_IDLE) {
                    mImageIndex = mViewPager.getCurrentItem();
                    startImageCycle();
                }

                mPreviousState = state;
            }
        });
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    /**
     * 开始轮播(手动控制自动轮播与否，便于资源控制)
     */
    public void startImageCycle() {
//        System.out.println("startImageCycle:" + mImageIndex);
        startImageTimerTask();
    }

    /**
     * 暂停轮播——用于节省资源
     */
    public void pushImageCycle() {
//        System.out.println(" pushImageCycle" + mImageIndex);
        stopImageTimerTask();
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
            PagerAdapter pagerAdapter = null;
            if (mViewPager != null) {
                 pagerAdapter = mViewPager.getAdapter();

            }
            if (pagerAdapter != null) {
                // 下标等于图片列表长度说明已滚动到最后一张图片,重置下标
                if ((++mImageIndex) == pagerAdapter.getCount()) {
                    mImageIndex = 0;
                }
                mPageIndicator.setCurrentItem(mImageIndex);
            } else {

            }
        }
    };
}
