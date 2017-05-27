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

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grandmagic.BeeFramework.adapter.BeeBaseAdapter;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.model.TeacherModel;
import com.grandmagic.edustore.protocol.SimpleTeacherInfo;

import java.util.ArrayList;

public class SubscriptionAdapter extends BeeBaseAdapter {

    private ArrayList<SimpleTeacherInfo> teachers;
    private TeacherModel teacherModel;


    public SubscriptionAdapter(Context c, ArrayList teachers, TeacherModel teacherModel) {
        super(c, teachers);
        this.teachers = teachers;
        this.teacherModel = teacherModel;
    }

    public class SubscriptionCellHolder extends BeeCellHolder {
        TextView tv_course_name;   //课程名
        TextView tv_teacher_name;  //已关注教师名
//        Button btn_subscription;   //关注按钮
//        Button btn_unsubscription; //取消关注按钮
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        SubscriptionCellHolder cell = new SubscriptionCellHolder();
        cell.tv_course_name = (TextView) cellView.findViewById(R.id.tv_subscription_course_name);
        cell.tv_teacher_name = (TextView) cellView.findViewById(R.id.tv_subscription_teacher_name);
//        cell.btn_subscription = (Button) cellView.findViewById(R.id.btn_subscription);
//        cell.btn_unsubscription = (Button) cellView.findViewById(R.id.btn_unsubscription);
        return cell;
    }


    @Override
    protected View bindData(int position, View cellView, ViewGroup parent,
                            BeeCellHolder h) {
        final SimpleTeacherInfo course_teacher = teachers.get(position);
        String course_name = course_teacher.course_name;
        String teacher_name = course_teacher.teacher_name;

        SubscriptionCellHolder holder = (SubscriptionCellHolder) h;
        holder.tv_teacher_name.setText(teacher_name);
        holder.tv_course_name.setText(course_name);
//        holder.btn_subscription.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, SearchSubscriptionTeacher.class);
//                intent.putExtra("course_id", course_teacher.course_id);
//                mContext.startActivity(intent);
//                Activity activity = (Activity) mContext;
//                activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
//            }
//        });
//
//        holder.btn_unsubscription.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                teacherModel.deleteTeacher(course_teacher);
//                SubscriptionActivity.isRefresh = true;
//            }
//        });
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.subscription_cell, null);
    }

}
