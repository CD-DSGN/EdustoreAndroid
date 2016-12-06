package com.grandmagic.edustore.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.PAGINATED;
import com.grandmagic.edustore.protocol.PAGINATION;
import com.grandmagic.edustore.protocol.SIMPLEGOODS;
import com.grandmagic.edustore.protocol.STATUS;
import com.grandmagic.edustore.protocol.TeacherComments;
import com.grandmagic.edustore.protocol.fetchCommentsRequest;
import com.grandmagic.edustore.protocol.fetchCommentsResponse;
import com.grandmagic.edustore.protocol.searchResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenggaoyuan on 2016/12/6.
 */
public class TeacherCommentsModel extends BaseModel{


    public ArrayList<TeacherComments> singleTeacherCommentList = new ArrayList<TeacherComments>();
    public STATUS responseStatus;

    public static final int PAGE_COUNT = 6;
    public TeacherCommentsModel(Context context) {
        super(context);
    }

    public void fetchComments(){
        fetchCommentsRequest request = new fetchCommentsRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                TeacherCommentsModel.this.callback(url, jo, status);
                try {
                    fetchCommentsResponse response = new fetchCommentsResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        PAGINATED paginated = response.paginated;
                        if (response.status.succeed == 1) {
                            ArrayList<TeacherComments> data = response.data;

                            singleTeacherCommentList.clear();
                            if (null != data && data.size() > 0) {
                                singleTeacherCommentList.clear();
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
        pagination.page = 1;
        pagination.count = PAGE_COUNT;
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
}
