package com.grandmagic.edustore.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.grandmagic.BeeFramework.fragment.BaseFragment;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.A0_SigninActivity;
import com.grandmagic.edustore.activity.G0_SettingActivity;
import com.grandmagic.edustore.activity.Z1_TeacherPublishActivity;
import com.grandmagic.edustore.adapter.Z0_TeacherCommentsAdapter;
import com.grandmagic.edustore.model.TeacherCommentsModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.PAGINATED;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/10/21.
 */
public class Z0_InteractionFragment extends BaseFragment implements View.OnClickListener, XListView.IXListViewListener {
    private View view;

    protected Context mContext;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    TextView publish;

    private String uid;

    private View null_pager;

    private XListView commentsListView;

    private TeacherCommentsModel teacherCommentsModel;

    private Z0_TeacherCommentsAdapter teacherCommentsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared = getActivity().getSharedPreferences("userInfo", 0);
        editor = shared.edit();
        uid = shared.getString("uid", "");
        teacherCommentsModel = new TeacherCommentsModel(this.getActivity());
        teacherCommentsModel.addResponseListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.z0_interaction,null);

        //headView = LayoutInflater.from(getActivity()).inflate(R.layout., null);

        null_pager = view.findViewById(R.id.null_pager);
        if (!uid.equals("")){
            publish = (TextView) view.findViewById(R.id.teacher_publish);
            publish.setVisibility(View.VISIBLE);
            publish.setOnClickListener(this);
        }else{
            null_pager.setVisibility(View.VISIBLE);
        }

        commentsListView = (XListView) view.findViewById(R.id.interaction_list);
        commentsListView.setPullLoadEnable(true);
        commentsListView.setRefreshTime();
        commentsListView.setXListViewListener(this,1);

        teacherCommentsModel.fetchComments();

        return view;
    }

    private void setContent(){

        if(teacherCommentsAdapter == null){

            if(teacherCommentsModel.singleTeacherCommentList.size() == 0){
                null_pager.setVisibility(View.VISIBLE);
            } else{
                null_pager.setVisibility(View.GONE);
                teacherCommentsAdapter = new Z0_TeacherCommentsAdapter(this.getActivity(),teacherCommentsModel.singleTeacherCommentList);
                commentsListView.setAdapter(teacherCommentsAdapter);

            }
        } else{
            teacherCommentsAdapter.dataList = teacherCommentsModel.singleTeacherCommentList;
            teacherCommentsAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onClick(View v) {

        Intent intent;
        switch(v.getId()) {
            case R.id.teacher_publish:
                intent = new Intent(getActivity(), Z1_TeacherPublishActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    break;
            default:
                break;
        }

    }

    @Override
    public void onRefresh(int id) {
        teacherCommentsModel.fetchComments();
    }

    @Override
    public void onLoadMore(int id) {
        teacherCommentsModel.fetchCommentsMore();
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if(url.endsWith(ApiInterface.FETCH_COMMENTS)){
            commentsListView.stopLoadMore();
            commentsListView.stopRefresh();
            commentsListView.setRefreshTime();

            setContent();
            PAGINATED paginated = new PAGINATED();
            paginated.fromJson(jo.optJSONObject("paginated"));
            if (0 == paginated.more)
            {
                commentsListView.setPullLoadEnable(false);
            }
            else
            {
                commentsListView.setPullLoadEnable(true);
            }
        }
    }
}
