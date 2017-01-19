package com.grandmagic.edustore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.grandmagic.edustore.EcmobileApp;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.TEACHERCOMMENTS;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class BigImageViewAct extends Activity {
    public static final String IMAGE_ARRAY = "imagepatharray";
    ViewPager viewPager;
    LinearLayout linearLayout;
    private int position;
    private List<TEACHERCOMMENTS.Img> imagelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image_view);
        initdata();
        initview();
    }

    private void initdata() {
        position = getIntent().getIntExtra("position", 0);
        imagelist = (List<TEACHERCOMMENTS.Img>) getIntent().getSerializableExtra(IMAGE_ARRAY);
    }

    List<PhotoView> photoViews;

    private void initview() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        linearLayout = (LinearLayout) findViewById(R.id.dotlayout);
        photoViews = new ArrayList<>();
        for (TEACHERCOMMENTS.Img s : imagelist) {
            PhotoView photoView = new PhotoView(BigImageViewAct.this);
            photoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageLoader.getInstance().displayImage(s.img, photoView, EcmobileApp.options);
            photoViews.add(photoView);
            ImageView pointView = new ImageView(this);
            pointView.setPadding(5, 0, 5, 0);
            pointView.setImageResource(R.drawable.ic_page_indicator);
            linearLayout.addView(pointView);
        }
        viewPager.setAdapter(new ImgPagerAdapter());
        viewPager.setCurrentItem(position);
        ((ImageView) linearLayout.getChildAt(position)).setImageResource(R.drawable.ic_page_indicator_focused);
        findViewById(R.id.top_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    ((ImageView) linearLayout.getChildAt(i)).setImageResource(i == position ? R.drawable.ic_page_indicator_focused : R.drawable.ic_page_indicator);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    class ImgPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return photoViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(photoViews.get(position));
            return photoViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(photoViews.get(position));
        }
    }
}
