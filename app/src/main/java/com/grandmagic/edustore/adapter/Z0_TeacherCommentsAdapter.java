package com.grandmagic.edustore.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grandmagic.BeeFramework.Utils.ScreenUtils;
import com.grandmagic.BeeFramework.adapter.BeeBaseAdapter;
import com.grandmagic.edustore.EcmobileApp;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.BigImageViewAct;
import com.grandmagic.edustore.protocol.TEACHERCOMMENTS;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chenggaoyuan on 2016/11/29.
 */
public class Z0_TeacherCommentsAdapter extends BeeBaseAdapter {

    private ArrayList<TEACHERCOMMENTS> teacherCommentsArrayList;

    private ImageLoader mImageLoader = ImageLoader.getInstance();

    public Z0_TeacherCommentsAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
        this.teacherCommentsArrayList = dataList;
    }

    public class CommentsCellHolder extends BeeCellHolder {
        ImageView teacherImg;
        TextView teacher_name;
        TextView comments;
        TextView publish_time;
        LinearLayout imagelayout;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        CommentsCellHolder cell = new CommentsCellHolder();
        cell.teacherImg = (ImageView) cellView.findViewById(R.id.teacher_img);
        cell.teacher_name = (TextView) cellView.findViewById(R.id.teacher_name);
        cell.comments = (TextView) cellView.findViewById(R.id.comments);
        cell.publish_time = (TextView) cellView.findViewById(R.id.publish_date);
        cell.imagelayout = (LinearLayout) cellView.findViewById(R.id.imagelayout);
        return cell;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        TEACHERCOMMENTS teacherComments = teacherCommentsArrayList.get(position);
        String teacher_name_tmp = teacherComments.teacher_name;
        String teacher_comments_tmp = teacherComments.teacher_comments;
        String publish_time_tmp = teacherComments.publish_time;
        String teacher_img_tmp = teacherComments.teacher_img_small;

        CommentsCellHolder holder = (CommentsCellHolder) h;
        holder.teacher_name.setText(teacher_name_tmp);
        holder.comments.setText(teacher_comments_tmp);
        holder.publish_time.setText(stampToDate(publish_time_tmp));
        mImageLoader.displayImage(teacher_img_tmp, holder.teacherImg);
        int size = teacherComments.photoArray.size();
        initImageLayout(teacherComments, holder, size);
        return cellView;

    }

    /**
     * 初始化装载图片的layout
     *
     * @param teacherComments json数据
     * @param holder          holder
     * @param size            图片数量
     */
    private void initImageLayout(final TEACHERCOMMENTS teacherComments, CommentsCellHolder holder, int size) {
        holder.imagelayout.removeAllViews();
        for (int i = 0; i < size; i++) {
            float y = size > 1 ? 3 : 2;//根据图片数量平分屏幕
            ImageView imageView = creatImageview(holder, size, y);
            mImageLoader.displayImage(teacherComments.photoArray.get(i).img, imageView, EcmobileApp.options);
            imageView.setTag(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, BigImageViewAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BigImageViewAct.IMAGE_ARRAY, (Serializable) teacherComments.photoArray);
                    intent.putExtras(bundle);
                    intent.putExtra("position", (int) view.getTag());
                    mContext.startActivity(intent);
                }
            });
            holder.imagelayout.addView(imageView);
        }
    }

    //动态创建imageview
    private ImageView creatImageview(CommentsCellHolder holder, int size, float y) {
        ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.imagelayout.getLayoutParams();
        int leftMargin = layoutParams.leftMargin;
        int rightMargin = layoutParams.rightMargin;
        int spaceW = 20;
        int imageW = (int) ((ScreenUtils.getScreenSize(mContext).x - leftMargin - rightMargin - size * spaceW) / y);
        LinearLayout.LayoutParams   params;
        if (size > 1) {
            params  = new LinearLayout.LayoutParams(imageW,
                    imageW);
        } else {
            params = new LinearLayout.LayoutParams(imageW,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setAdjustViewBounds(true);
            imageView.setMaxHeight(3*imageW);
        }
        params.setMargins(0, 0, spaceW, 0);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.z0_single_publish_item, null);
    }

    /*
    将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }
}
