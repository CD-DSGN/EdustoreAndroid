package com.grandmagic.edustore.model;
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

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.PAGINATED;
import com.grandmagic.edustore.protocol.PAGINATION;
import com.grandmagic.edustore.protocol.SimpleTeacherInfo;
import com.grandmagic.edustore.protocol.searchTeacherRequest;
import com.grandmagic.edustore.protocol.searchTeacherResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherListModel extends BaseModel {

    public ArrayList<SimpleTeacherInfo> simpleTeachersList = new ArrayList<SimpleTeacherInfo>();

    public static final int PAGE_COUNT = 10;

    public TeacherListModel(Context context) {
        super(context);
    }

    public void fetchPreSearch(String keywords, String course_id) {
        searchTeacherRequest request = new searchTeacherRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                TeacherListModel.this.callback(url, jo, status);
                try {
                    searchTeacherResponse response = new searchTeacherResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        PAGINATED paginated = response.paginated;
                        if (response.status.succeed == 1) {
                            ArrayList<SimpleTeacherInfo> data = response.data;

                            simpleTeachersList.clear();
                            if (null != data && data.size() > 0) {
                                simpleTeachersList.clear();
                                simpleTeachersList.addAll(data);

                            }

                            TeacherListModel.this.OnMessageResponse(url, jo, status);
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

        request.keywords = keywords;
        request.pagination = pagination;
        request.course_id = course_id;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.SEARCH_TEACHER).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    public void fetchPreSearchMore(String keywords, String course_id) {
        searchTeacherRequest request = new searchTeacherRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                TeacherListModel.this.callback(url, jo, status);
                try {
                    searchTeacherResponse response = new searchTeacherResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        PAGINATED paginated = response.paginated;
                        if (response.status.succeed == 1) {
                            ArrayList<SimpleTeacherInfo> data = response.data;

                            if (null != data && data.size() > 0) {
                                simpleTeachersList.addAll(data);
                            }

                            TeacherListModel.this.OnMessageResponse(url, jo, status);

                        }
                    }
                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };

        PAGINATION pagination = new PAGINATION();
        pagination.page = (int) Math.ceil((double) simpleTeachersList.size() * 1.0 / PAGE_COUNT) + 1;
        pagination.count = PAGE_COUNT;

        request.keywords = keywords;
        request.pagination = pagination;
        request.course_id = course_id;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.SEARCH_TEACHER).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

}
