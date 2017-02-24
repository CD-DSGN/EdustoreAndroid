package com.grandmagic.edustore.fragment;

import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.grandmagic.BeeFramework.Utils.ScreenUtils;
import com.grandmagic.BeeFramework.fragment.BaseFragment;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.Z1_TeacherPublishActivity;
import com.grandmagic.edustore.adapter.Z0_TeacherCommentsAdapter;
import com.grandmagic.edustore.model.TeacherCommentsModel;
import com.grandmagic.edustore.model.UserInfoModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.PAGINATED;
import com.grandmagic.edustore.protocol.USER;
import com.tencent.weibo.sdk.android.api.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/10/21. 汇师圈对应的fragment
 */
public class Z0_InteractionFragment extends BaseFragment implements View.OnClickListener, XListView.IXListViewListener, Z0_TeacherCommentsAdapter.DeleteListener {
    private static final int REQUESTCODE_PUBLISH = 1;
    private View view;

    protected Context mContext;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    TextView publish;

    private String uid;

    private View not_login;
    private View not_publish;

    private XListView commentsListView;

    private TeacherCommentsModel teacherCommentsModel;

    private Z0_TeacherCommentsAdapter teacherCommentsAdapter;

    private UserInfoModel userInfoModel;

    private USER _user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == teacherCommentsModel) {
            teacherCommentsModel = new TeacherCommentsModel(this.getActivity());
        }
        teacherCommentsModel.addResponseListener(this);

        if (null == userInfoModel) {
            userInfoModel = new UserInfoModel(getActivity());
        }
        userInfoModel.addResponseListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.z0_interaction, null);
        Log.d("chenggaoyuan", "oncreateview");
        shared = getActivity().getSharedPreferences("userInfo", 0);
        editor = shared.edit();
        uid = shared.getString("uid", "");

        not_login = view.findViewById(R.id.notlogin);
        not_publish = view.findViewById(R.id.notpublish);
        publish = (TextView) view.findViewById(R.id.teacher_publish);
        commentsListView = (XListView) view.findViewById(R.id.interaction_list);

        if (uid.equals("")) {
            not_login.setVisibility(View.VISIBLE);
            not_publish.setVisibility(View.GONE);
            commentsListView.setVisibility(View.GONE);
            publish.setVisibility(View.GONE);
        } else {
            userInfoModel.getUserInfo();
            teacherCommentsModel.fetchComments();
        }

        publish.setOnClickListener(this);

        commentsListView.setPullLoadEnable(true);
        commentsListView.setRefreshTime();
        commentsListView.setXListViewListener(this, 1);

        return view;
    }

    private void setContent() {

        if (teacherCommentsAdapter == null) {

            if (teacherCommentsModel.singleTeacherCommentList.size() == 0) {
                not_publish.setVisibility(View.VISIBLE);
                not_login.setVisibility(View.GONE);
                commentsListView.setVisibility(View.GONE);
            } else {
                not_publish.setVisibility(View.GONE);
                not_login.setVisibility(View.GONE);
                commentsListView.setVisibility(View.VISIBLE);
                teacherCommentsAdapter = new Z0_TeacherCommentsAdapter(this.getActivity(), teacherCommentsModel.singleTeacherCommentList);
                commentsListView.setAdapter(teacherCommentsAdapter);
                teacherCommentsAdapter.setDeleteListener(this);
            }
        } else {
            teacherCommentsAdapter.dataList = teacherCommentsModel.singleTeacherCommentList;
            teacherCommentsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_PUBLISH && resultCode == Activity.RESULT_OK) {
            onRefresh(-1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        userInfoModel.removeResponseListener(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.teacher_publish:
                intent = new Intent(getActivity(), Z1_TeacherPublishActivity.class);
                startActivityForResult(intent, REQUESTCODE_PUBLISH);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.cancle:
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onRefresh(int id) {
        if (!uid.equals("")) {
            teacherCommentsModel.fetchComments();
        }
    }

    @Override
    public void onLoadMore(int id) {
        teacherCommentsModel.fetchCommentsMore();
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.FETCH_COMMENTS)) {
            commentsListView.stopLoadMore();
            commentsListView.stopRefresh();
            commentsListView.setRefreshTime();

            setContent();
            PAGINATED paginated = new PAGINATED();
            paginated.fromJson(jo.optJSONObject("paginated"));
            if (0 == paginated.more) {
                commentsListView.setPullLoadEnable(false);
            } else {
                commentsListView.setPullLoadEnable(true);
            }
            Log.d("chenggaoyuan", String.valueOf(teacherCommentsModel.follow_or_not));
            if (0 == teacherCommentsModel.follow_or_not) {
                not_publish.setVisibility(View.VISIBLE);
                not_login.setVisibility(View.GONE);
                commentsListView.setVisibility(View.GONE);

            } else if (1 == teacherCommentsModel.follow_or_not) {
                not_publish.setVisibility(View.GONE);
                not_login.setVisibility(View.GONE);
                commentsListView.setVisibility(View.VISIBLE);
            }
        } else if (url.endsWith(ApiInterface.USER_INFO)) {
            _user = userInfoModel.user;
            if (_user.is_teacher.equals("0")) {
                publish.setVisibility(View.GONE);
            } else {
                publish.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 删除发表的动态
     */
    Dialog mDialog;//询问是否删除的弹窗

    @Override
    public void delete() {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_delete, null);
        mView.findViewById(R.id.cancle).setOnClickListener(this);
        mView.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                teacherCommentsModel.delete();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
        mDialog = new Dialog(getActivity(), R.style.dialog);
        mDialog.setContentView(mView);
        mDialog.show();
        WindowManager.LayoutParams mAttributes = mDialog.getWindow().getAttributes();
        mAttributes.width = ScreenUtils.getScreenSize(getActivity()).x * 2 / 3;
        mDialog.getWindow().setAttributes(mAttributes);
    }
}
