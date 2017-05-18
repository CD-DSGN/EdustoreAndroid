package com.grandmagic.edustore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.model.TeacherClassInfoModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.ClassInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class TeacherClassActivity extends BaseActivity implements BusinessResponse {
    LinearLayout mll;
    TeacherClassInfoModel mTeacherClassInfoModel;

    LayoutInflater mLayoutInflater;
    View null_page;
    View back;
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class);
        initView();
        loadData();
    }

    private void loadData() {
        if (mTeacherClassInfoModel == null) {
            mTeacherClassInfoModel = new TeacherClassInfoModel(this);
            mTeacherClassInfoModel.addResponseListener(this);
        }
        mTeacherClassInfoModel.getClassInfo();
    }

    private void initView() {
        mll = (LinearLayout) findViewById(R.id.ll_class_info);
        mLayoutInflater = LayoutInflater.from(this);
        null_page = findViewById(R.id.null_pager);
        back = findViewById(R.id.top_view_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title = (TextView) findViewById(R.id.top_view_text);
        tv_title.setText(R.string.query_points);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.TEACHER_CLASS_INFO)) {
            setContent();
        }
    }

    private void setContent() {
        mll.removeAllViews();

        if (mTeacherClassInfoModel.mClassInfos != null && mTeacherClassInfoModel.mClassInfos.size() > 0) {
            for (int i = 0; i < mTeacherClassInfoModel.mClassInfos.size(); i++) {
                final int pos = i;
                mll.setVisibility(View.VISIBLE);
                View v = mLayoutInflater.inflate(R.layout.item_class_info, mll, false);
                TextView tv = (TextView) v.findViewById(R.id.tv_class_info);
                ClassInfo info = mTeacherClassInfoModel.mClassInfos.get(pos);
                tv.setText(info.school_name + " " +  info.grade + " " + info.class_no + "班");
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClassInfo info = mTeacherClassInfoModel.mClassInfos.get(pos);
                        if (info == null) {
                            return;
                        }
                        Intent intent = new Intent(TeacherClassActivity.this, StudentPointsActivity.class);
                        intent.putExtra(StudentPointsActivity.INFO_ID, info.info_id);
                        startActivity(intent);
                    }
                });
                mll.addView(v);
            }
            mll.setVisibility(View.VISIBLE);
            null_page.setVisibility(View.INVISIBLE);
        } else {
            null_page.setVisibility(View.VISIBLE);
            mll.setVisibility(View.INVISIBLE);
        }
    }
}
