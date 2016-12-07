package com.grandmagic.edustore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandmagic.BeeFramework.adapter.BeeBaseAdapter;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.PHOTO;
import com.grandmagic.edustore.protocol.TEACHERCOMMENTS;
import com.grandmagic.edustore.view.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    public class CommentsCellHolder extends BeeCellHolder{
        ImageView teacherImg;
        TextView teacher_name;
        TextView comments;
        TextView publish_time;
    }
    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        CommentsCellHolder cell = new CommentsCellHolder();
        cell.teacherImg = (ImageView) cellView.findViewById(R.id.teacher_img);
        cell.teacher_name = (TextView) cellView.findViewById(R.id.teacher_name);
        cell.comments = (TextView) cellView.findViewById(R.id.comments);
        cell.publish_time = (TextView) cellView.findViewById(R.id.publish_date);
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



        return cellView;

    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.z0_single_publish_item, null);
    }

    /*
    将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt*1000);
        res = simpleDateFormat.format(date);
        return res;
    }
}
