package com.grandmagic.edustore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.MyListView;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.StudentPointAdapter;
import com.grandmagic.edustore.model.StudentPointModel;
import com.grandmagic.edustore.protocol.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class StudentPointsActivity extends BaseActivity implements IXListViewListener, BusinessResponse {
    //头部相关
    TextView mTVTitle;
    ImageView mIVBack;

    MyListView mMyListView;

    StudentPointModel mStudentPointModel ;
    StudentPointAdapter mStudentPointAdapter;

    private View null_paView;
    private String info_id_str;
    public final static String INFO_ID = "INFO_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_points);
        info_id_str = getIntent().getStringExtra(INFO_ID);
        initView();
        loadData();
    }


    //加载数据
    private void loadData() {
        mStudentPointModel.getStudentPoints(info_id_str);
    }

    private void initView() {
        mMyListView = (MyListView) findViewById(R.id.lv_student_points);
        mTVTitle = (TextView) findViewById(R.id.top_view_text);
        mTVTitle.setText(R.string.query_points);
        mIVBack = (ImageView) findViewById(R.id.top_view_back);
        mIVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        null_paView = findViewById(R.id.null_pager);
        mMyListView.setPullRefreshEnable(true);
        mMyListView.setRefreshTime();
        mStudentPointModel = new StudentPointModel(this);
        mStudentPointModel.addResponseListener(this);
        mMyListView.setXListViewListener(this,1);
        mMyListView.setPullLoadEnable(false);
        mMyListView.setPullRefreshEnable(true);
    }

    @Override
    public void onRefresh(int id) {
        mStudentPointModel.getStudentPoints(info_id_str);
    }

    @Override
    public void onLoadMore(int id) {

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.STUDENT_POINTS)) {
            mMyListView.setRefreshTime();
            mMyListView.stopRefresh();
            mMyListView.stopLoadMore();
            setContent();
        }
    }

    private void setContent() {
        if(mStudentPointModel.stu_points_list.size() > 0) {
            mMyListView.setVisibility(View.VISIBLE);
            mMyListView.setVisibility(View.VISIBLE);
            if(mStudentPointAdapter == null) {
                mStudentPointAdapter = new StudentPointAdapter(this, mStudentPointModel.stu_points_list);
                mMyListView.setAdapter(mStudentPointAdapter);
            } else {
                mStudentPointAdapter.dataList = mStudentPointModel.stu_points_list;
                mStudentPointAdapter.notifyDataSetChanged();
            }
        } else {
            null_paView.setVisibility(View.VISIBLE);
            mMyListView.setVisibility(View.GONE);
        }
    }
}
