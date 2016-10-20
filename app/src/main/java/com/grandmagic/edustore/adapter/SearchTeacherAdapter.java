package com.grandmagic.edustore.adapter;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.grandmagic.BeeFramework.adapter.BeeBaseAdapter;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.model.TeacherModel;
import com.grandmagic.edustore.protocol.SimpleTeacherInfo;

import java.util.ArrayList;

public class SearchTeacherAdapter extends BeeBaseAdapter {

    private ArrayList<SimpleTeacherInfo> teachers;
    Activity activity;
    TeacherModel teacherModel;


    public SearchTeacherAdapter(Context c, ArrayList teachers, TeacherModel teacherModel) {
        super(c, teachers);
        this.teachers = teachers;
        activity = (Activity) mContext;
        this.teacherModel = teacherModel;
    }


    public class TeacherInfoCellHolder extends BeeCellHolder {
        TextView tv_course_name;   //课程名
        TextView tv_teacher_name;  //教师名
        TextView tv_school_name;   //学校名
        Button btn_subscription;   //关注按钮
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        TeacherInfoCellHolder cell = new TeacherInfoCellHolder();
        cell.tv_course_name = (TextView) cellView.findViewById(R.id.tv_search_teacher_course_name);
        cell.tv_teacher_name = (TextView) cellView.findViewById(R.id.tv_search_teacher_teacher_name);
        cell.tv_school_name = (TextView) cellView.findViewById(R.id.tv_search_teacher_school);
        cell.btn_subscription = (Button) cellView.findViewById(R.id.btn_subscription);
        return cell;
    }


    @Override
    protected View bindData(int position, View cellView, ViewGroup parent,
                            BeeCellHolder h) {
        SimpleTeacherInfo teacherInfo = teachers.get(position);
        final int position_temp = position;
        TeacherInfoCellHolder holder = (TeacherInfoCellHolder) h;
        holder.tv_course_name.setText(teacherInfo.course_name);
        holder.tv_teacher_name.setText(teacherInfo.teacher_name);
        holder.tv_school_name.setText(teacherInfo.school);
        holder.btn_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleTeacherInfo simpleTeacherInfo = teachers.get(position_temp);
                teacherModel.addTeacher(simpleTeacherInfo);
            }
        });
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.search_teacher_cell, null);
    }

}
