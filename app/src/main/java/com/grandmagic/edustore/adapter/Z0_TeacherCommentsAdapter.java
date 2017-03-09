package com.grandmagic.edustore.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
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
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

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

    public boolean isSelf(String publisherId) {
        SharedPreferences mUserinfo = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String mUid = mUserinfo.getString("uid", "");
        return mUid.equals(publisherId);
    }

    public class CommentsCellHolder extends BeeCellHolder {
        ImageView teacherImg;
        TextView teacher_name;
        TextView comments;
        TextView publish_time;
        TextView delete, comment;
        LinearLayout imagelayout, commentlayout;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        CommentsCellHolder cell = new CommentsCellHolder();
        cell.teacherImg = (ImageView) cellView.findViewById(R.id.teacher_img);
        cell.teacher_name = (TextView) cellView.findViewById(R.id.teacher_name);
        cell.comments = (TextView) cellView.findViewById(R.id.comments);
        cell.delete = (TextView) cellView.findViewById(R.id.delete);
        cell.publish_time = (TextView) cellView.findViewById(R.id.publish_date);
        cell.comment = (TextView) cellView.findViewById(R.id.comment);
        cell.imagelayout = (LinearLayout) cellView.findViewById(R.id.imagelayout);
        cell.commentlayout = (LinearLayout) cellView.findViewById(R.id.commentlayout);
        return cell;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    protected View bindData(final int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        TEACHERCOMMENTS teacherComments = teacherCommentsArrayList.get(position);
        String teacher_name_tmp = teacherComments.teacher_name;
        String teacher_comments_tmp = teacherComments.teacher_comments;
        final String publish_time_tmp = teacherComments.publish_time;
        String teacher_img_tmp = teacherComments.teacher_img_small;
        final String teacher_uid = teacherComments.publish_uid;
        final String newsid = teacherComments.news_id;

        CommentsCellHolder holder = (CommentsCellHolder) h;
        holder.teacher_name.setText(teacher_name_tmp);
        holder.comments.setText(teacher_comments_tmp);
        holder.publish_time.setText(stampToDate(publish_time_tmp));
        mImageLoader.displayImage(teacher_img_tmp, holder.teacherImg);
        int size = teacherComments.photoArray.size();
        initImageLayout(teacherComments, holder, size);//加载图片
        initCommentLayout(teacherComments, holder, position);//加载评论
        holder.delete.setVisibility(isSelf(teacherComments.publish_uid) ? View.VISIBLE : View.GONE);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if (mDeleteListener != null) {
                    mDeleteListener.delete(teacher_uid, publish_time_tmp, position);
                }
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if (mDeleteListener != null) {
                    mDeleteListener.commentnews(newsid, position);
                }
            }
        });
        return cellView;

    }

    //加载评论
    private void initCommentLayout(final TEACHERCOMMENTS mTeacherComments, CommentsCellHolder mHolder, final int position) {
        mHolder.commentlayout.removeAllViews();
        //如果有评论
        if (mTeacherComments.mCommentArray != null && mTeacherComments.mCommentArray.size() > 0) {
            for (int i = 0; i < mTeacherComments.mCommentArray.size(); i++) {//根据评论数量动态添加Textview
//                使用SpannableString是为了保持换行的时候依然对齐，如果用Linlearlayout往里面添加多个Texview不行的
                SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder();
                TextView mcommentView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.view_comment, null);
                final TEACHERCOMMENTS.CommentArray mCommentArray = mTeacherComments.mCommentArray.get(i);
                String targetuserbane = mCommentArray.target_username;
                String mUsername = mCommentArray.username;
                if (!TextUtils.isEmpty(targetuserbane) && !"null".equals(targetuserbane)) {//如果target为空则不需要添加被回复人和“回复”
                    mSpannableStringBuilder.append(targetuserbane).append("回复");
                    mSpannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#516792")), 0, targetuserbane.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mSpannableStringBuilder.append(mUsername);
                    mSpannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#516792")), targetuserbane.length()+2, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    mSpannableStringBuilder.append(mUsername);
                    mSpannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#516792")), 0, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                mSpannableStringBuilder.append(": ");
                mSpannableStringBuilder.append(mCommentArray.comment_content);
                mcommentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View mView) {
                        if (mDeleteListener != null) {
                            mDeleteListener.replycomment(mTeacherComments.news_id, mCommentArray.comment_id,mCommentArray.username, position);
                        }
                    }
                });
                mcommentView.setText(mSpannableStringBuilder);
                mHolder.commentlayout.addView(mcommentView);

            }
        }
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
            mImageLoader.displayImage(teacherComments.photoArray.get(i).img, imageView, EcmobileApp.options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String mS, View mView, Bitmap mBitmap) {
                    if (mView.getLayoutParams().height == LinearLayout.LayoutParams.WRAP_CONTENT) {//发现一张图的时候小图放大，高度WRAP_CONTENT的时候太矮
                        float scale = mBitmap.getHeight() / mBitmap.getWidth();
                        mView.setLayoutParams(new LinearLayout.LayoutParams(mView.getLayoutParams().width, (int) (scale * mView.getLayoutParams().width)));
                    }
                }
            });
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
        LinearLayout.LayoutParams params;
        if (size > 1) {
            params = new LinearLayout.LayoutParams(imageW,
                    imageW);
        } else {
            params = new LinearLayout.LayoutParams(imageW,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setAdjustViewBounds(true);
            imageView.setMaxHeight(3 * imageW);
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

    DeleteListener mDeleteListener;

    public void setDeleteListener(DeleteListener mDeleteListener) {
        this.mDeleteListener = mDeleteListener;
    }

    public interface DeleteListener {
        void delete(String mTeacher_uid, String mPublish_time_tmp, int mPosition);

        void commentnews(String newsid, int mPosition);

        void replycomment(String newsid, String targetcommentid, String mTarget_username, int position);
    }
}
