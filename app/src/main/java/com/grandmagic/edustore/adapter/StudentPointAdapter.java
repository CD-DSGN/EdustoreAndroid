package com.grandmagic.edustore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandmagic.BeeFramework.adapter.BeeBaseAdapter;
import com.grandmagic.edustore.EcmobileApp;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.STU_POINT_LIST;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by zhangmengqi on 2017/5/11.
 */

public class StudentPointAdapter extends BeeBaseAdapter {
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    public StudentPointAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    public class StudentPointHolder extends BeeCellHolder {
        public ImageView mIvHead;
        public TextView mTVName;
        public TextView mTVPoints;
        public TextView mTVNO;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        StudentPointHolder studentPointHolder = new StudentPointHolder();
        studentPointHolder.mTVName = (TextView) cellView.findViewById(R.id.tv_student_name);
        studentPointHolder.mTVPoints = (TextView) cellView.findViewById(R.id.tv_points);
        studentPointHolder.mIvHead = (ImageView) cellView.findViewById(R.id.iv_head);
        studentPointHolder.mTVNO = (TextView) cellView.findViewById(R.id.tv_no);
        return studentPointHolder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        StudentPointHolder studentPointHolder = (StudentPointHolder) h;
        STU_POINT_LIST data = (STU_POINT_LIST) dataList.get(position);
        studentPointHolder.mTVName.setText(data.student_name);
        studentPointHolder.mTVPoints.setText(data.student_points);
        imageLoader.displayImage(data.avatar, studentPointHolder.mIvHead
                , EcmobileApp.options_head_circle);
        studentPointHolder.mTVNO.setText((position + 1) + "");
        return null;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_student_point, null, false);
    }
}
