package com.grandmagic.edustore.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.model.TeacherInfoModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.Integral;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangmengqi on 2016/9/10.
 */
public class UserIntegralActivity extends BaseActivity implements View.OnClickListener, BusinessResponse {
    private TextView tv_hui_points;
    private TextView tv_buy_points;
    private TextView tv_student_num;
    private TextView tv_teacher_num;
    private TextView tv_points_from_affiliate;
    private TextView tv_points_from_subscription;


    private TeacherInfoModel teacherInfoModel;
    private Integral integral;

    private Resources resources;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_integral);
        resources = getBaseContext().getResources();
        initViews();
        loadData();

    }

    private void loadData() {
        teacherInfoModel = new TeacherInfoModel(this);
        teacherInfoModel.addResponseListener(this);
        teacherInfoModel.getTeacherInfo();
    }

    private void initViews() {
        tv_hui_points = (TextView) findViewById(R.id.tv_user_intergral_hui_points);
        tv_buy_points = (TextView) findViewById(R.id.tv_user_intergral_buy_points);
        tv_student_num = (TextView) findViewById(R.id.tv_user_integral_student_num);
        tv_teacher_num = (TextView) findViewById(R.id.tv_user_integral_teacher_num);
        tv_points_from_affiliate = (TextView) findViewById(R.id.tv_user_integral_teacher_recommand);
        tv_points_from_subscription = (TextView) findViewById(R.id.tv_user_integral_student_buy);

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.TEACHER_INFO)) {
            integral = teacherInfoModel.integral;
            setContent();

        }
    }

    private void setContent() {
        tv_hui_points.setText(integral.teacher_integral);
        tv_buy_points.setText(integral.pay_points);
        String str_student_num = resources.getString(R.string.student_subscription_num);
        String str_teacher_num = resources.getString(R.string.recommand_num);

        Pattern pattern = Pattern.compile("0");
        if (!TextUtils.isEmpty(integral.subscription_student_num)) {
            Matcher matcher = pattern.matcher(str_student_num);
            String student_num = matcher.replaceFirst(integral.subscription_student_num);
            tv_student_num.setText(student_num);
        }

        if (!TextUtils.isEmpty(integral.recommanded_teacher_num)) {
            Matcher matcher1 = pattern.matcher(str_teacher_num);
            String teacher_num = matcher1.replaceFirst(integral.recommanded_teacher_num);
            tv_teacher_num.setText(teacher_num);
        }

        tv_points_from_affiliate.setText(integral.points_from_affiliate);
        tv_points_from_subscription.setText(integral.points_from_subscription);
    }

    @Override
    public void onClick(View view) {

    }
}