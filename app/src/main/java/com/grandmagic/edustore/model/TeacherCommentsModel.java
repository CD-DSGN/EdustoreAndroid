package com.grandmagic.edustore.model;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.ErrorCodeConst;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.CommentResponse;
import com.grandmagic.edustore.protocol.DeleteCommentsRequest;
import com.grandmagic.edustore.protocol.NewCommentRequest;
import com.grandmagic.edustore.protocol.PAGINATED;
import com.grandmagic.edustore.protocol.PAGINATION;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.STATUS;
import com.grandmagic.edustore.protocol.TEACHERCOMMENTS;
import com.grandmagic.edustore.protocol.fetchCommentsRequest;
import com.grandmagic.edustore.protocol.fetchCommentsResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenggaoyuan on 2016/12/6.
 */
public class TeacherCommentsModel extends BaseModel {

    public ArrayList<TEACHERCOMMENTS> singleTeacherCommentList = new ArrayList<TEACHERCOMMENTS>();

    public STATUS responseStatus;

    public int follow_or_not;

    public static final int PAGE_COUNT = 10;

    public TeacherCommentsModel(Context context) {
        super(context);
    }

    private void setFollow(int tmp) {
        this.follow_or_not = tmp;
    }

    public void fetchComments() {
        fetchCommentsRequest request = new fetchCommentsRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                TeacherCommentsModel.this.callback(url, jo, status);
                try {
                    fetchCommentsResponse response = new fetchCommentsResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        PAGINATED paginated = response.paginated;
                        if (response.status.succeed == 1) {
                            ArrayList<TEACHERCOMMENTS> data = response.data;

                            singleTeacherCommentList.clear();
                            if (null != data && data.size() > 0) {
                                singleTeacherCommentList.clear();
                                singleTeacherCommentList.addAll(data);

                            }
                            setFollow(response.follow);

                            TeacherCommentsModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };

        PAGINATION pagination = new PAGINATION();
        pagination.page = 1;
        pagination.count = PAGE_COUNT;

        SESSION session = SESSION.getInstance();

        request.session = session;
        request.pagination = pagination;


        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.FETCH_COMMENTS).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void fetchCommentsMore() {
        fetchCommentsRequest request = new fetchCommentsRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                TeacherCommentsModel.this.callback(url, jo, status);
                try {
                    fetchCommentsResponse response = new fetchCommentsResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        PAGINATED paginated = response.paginated;
                        if (response.status.succeed == 1) {
                            ArrayList<TEACHERCOMMENTS> data = response.data;

                            if (null != data && data.size() > 0) {
                                singleTeacherCommentList.addAll(data);
                            }

                            TeacherCommentsModel.this.OnMessageResponse(url, jo, status);

                        }
                    }
                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };

        PAGINATION pagination = new PAGINATION();
        pagination.page = (int) Math.ceil((double) singleTeacherCommentList.size() * 1.0 / PAGE_COUNT) + 1;
        pagination.count = PAGE_COUNT;
        SESSION session = SESSION.getInstance();
        request.session = session;
        request.pagination = pagination;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.FETCH_COMMENTS).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    /**
     * 教师删除动态
     *  @param mTeacher_uid      id
     * @param mPublish_time_tmp 时间戳
     * @param mPosition
     */
    public void delete(String mTeacher_uid, String mPublish_time_tmp, final int mPosition) {
        DeleteCommentsRequest mRequest = new DeleteCommentsRequest();
        SESSION mInstance = SESSION.getInstance();
        mRequest.mSESSION = mInstance;
        mRequest.time = mPublish_time_tmp;
        mRequest.uid = mTeacher_uid;
        Map<String, String> params = new HashMap<String, String>();
        params.put("json", mRequest.toJson().toString());
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                TeacherCommentsModel.this.callback(url, jo, status);
                try {
                    JSONObject mStatus = jo.optJSONObject("status");
                    int mSucceed = mStatus.optInt("succeed");
                    if (mSucceed== ErrorCodeConst.ResponseSucceed){
                        singleTeacherCommentList.remove(mPosition);
                    }
                    TeacherCommentsModel.this.OnMessageResponse(url, jo, status);
                } catch (JSONException mE) {
                    mE.printStackTrace();
                }
            }
        };
        cb.url(ApiInterface.DELETE_ONE_COMMENT).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    /**
     * 评论动态
     * @param content
     * @param mNewsid
     */
    public void sendnewComment(String content, String mNewsid,int position) {
        commentPublish(content, mNewsid,null,position);
    }

    private void commentPublish(String content, String mNewsid, String mTargetcommentid, final int position) {
        NewCommentRequest mRequest = new NewCommentRequest();
        SESSION mInstance = SESSION.getInstance();
        mRequest.mSESSION = mInstance;
        mRequest.comment_content = content;
        mRequest.news_id = mNewsid;
        mRequest.target_comment_id=mTargetcommentid;
        Map<String, String> params = new HashMap<String, String>();
        params.put("json", mRequest.toJson().toString());
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                TeacherCommentsModel.this.callback(url, jo, status);
                try {
                    JSONObject mStatus = jo.optJSONObject("status");
                    int mSucceed = mStatus.optInt("succeed");
                    if (mSucceed== ErrorCodeConst.ResponseSucceed){
                        CommentResponse mCommentResponse = new Gson().fromJson(jo.toString(), CommentResponse.class);
                      if (mCommentResponse.getStatus().getSucceed()==1){
                          TEACHERCOMMENTS.CommentArray mCommentArray =new TEACHERCOMMENTS.CommentArray();
                          mCommentArray.comment_content=mCommentResponse.getData().getCommentInfo().getComment_content();
                          mCommentArray.target_username=mCommentResponse.getData().getCommentInfo().getTarget_username();
                          mCommentArray.username=mCommentResponse.getData().getCommentInfo().getUsername();
                          mCommentArray.comment_id=mCommentResponse.getData().getCommentInfo().getComment_id();
                          mCommentArray.show_target_name=mCommentResponse.getData().getCommentInfo().getShow_target_name();
                          mCommentArray.show_name=mCommentResponse.getData().getCommentInfo().getShow_name();
                          singleTeacherCommentList.get(position).mCommentArray.add(mCommentArray);
                      }
                    }
                    TeacherCommentsModel.this.OnMessageResponse(url, jo, status);
                } catch (JSONException mE) {
                    mE.printStackTrace();
                }
            }
        };
        cb.url(ApiInterface.COMMENT_PUBLISH).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    /**
     * 回复别人评论
     * @param content
     * @param mNewsid
     * @param mTargetcommentid
     */
    public void replyComment(String content, String mNewsid, String mTargetcommentid,int position) {
        commentPublish(content, mNewsid,mTargetcommentid,position);
    }
}
