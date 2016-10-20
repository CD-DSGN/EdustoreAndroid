package com.grandmagic.edustore.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.SubscriptionAdapter;
import com.grandmagic.edustore.model.TeacherModel;
import com.grandmagic.edustore.protocol.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangmengqi on 2016/9/2.
 */
public class SubscriptionActivity extends BaseActivity implements XListView.IXListViewListener, BusinessResponse {
    private XListView xListView_subscription;
    private TeacherModel teacherModel;
    private SubscriptionAdapter adapter;

    public static boolean isRefresh = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        initView();
        setXlistView();
        teacherModel = new TeacherModel(this);
        teacherModel.getSubscrption();
        teacherModel.addResponseListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isRefresh) {
            isRefresh = false;
            teacherModel.getSubscrption();
        }
    }


    private void setXlistView() {
        xListView_subscription.setPullLoadEnable(false);
        xListView_subscription.setPullRefreshEnable(true);
        LinearLayout ll = (LinearLayout) getLayoutInflater().inflate(R.layout.subscription_header, null);
        xListView_subscription.addHeaderView(ll);
        xListView_subscription.setXListViewListener(this, 1);

    }

    private void initView() {
        xListView_subscription = (XListView) findViewById(R.id.xlistview_subscription);
    }

    @Override
    public void onRefresh(int id) {
        teacherModel.getSubscrption();
    }

    @Override
    public void onLoadMore(int id) {

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        xListView_subscription.stopRefresh();
        xListView_subscription.stopLoadMore();
        xListView_subscription.setRefreshTime();

        if (url.endsWith(ApiInterface.USER_SUBSCRIPTION) ) {
            setContent();
        }

        if (url.endsWith(ApiInterface.DELETE_TEACHER)) {
            teacherModel.getSubscrption();
        }

    }


    private void setContent() {
        if (teacherModel.course_teachers.size() > 0) {
            if (adapter == null) {
                adapter = new SubscriptionAdapter(this, teacherModel.course_teachers, teacherModel);
                xListView_subscription.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }
}