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
import com.grandmagic.edustore.protocol.CourseTeacherResponse;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.SimpleTeacherInfo;
import com.grandmagic.edustore.protocol.TeacherRequest;
import com.grandmagic.edustore.protocol.TeacherResponse;
import com.grandmagic.edustore.protocol.userinfoRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherModel extends BaseModel {

    public ArrayList<SimpleTeacherInfo> course_teachers = new ArrayList<SimpleTeacherInfo>();

    public TeacherModel(Context context) {
        super(context);
    }

    //添加关注教师
    public void addTeacher(SimpleTeacherInfo simpleTeacherInfo) {
        TeacherRequest request = new TeacherRequest();
        request.simpleTeacherInfo = simpleTeacherInfo;
        request.session = SESSION.getInstance();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                TeacherModel.this.callback(url, jo, status);
                try {
                    TeacherResponse response = new TeacherResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            TeacherModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.ADD_TEACHER).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    //取消对教师的关注
    public void deleteTeacher(SimpleTeacherInfo simpleTeacherInfo) {
        TeacherRequest request = new TeacherRequest();
        request.simpleTeacherInfo = simpleTeacherInfo;
        request.session = SESSION.getInstance();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                TeacherModel.this.callback(url, jo, status);
                try {
                    TeacherResponse response = new TeacherResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            TeacherModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.DELETE_TEACHER).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    //获取关注列表
    public void getSubscrption() {

        userinfoRequest request = new userinfoRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                TeacherModel.this.callback(url, jo, status);

                try {

                    CourseTeacherResponse response = new CourseTeacherResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            ArrayList<SimpleTeacherInfo> list_course_teacher = response.data;
                            if (list_course_teacher != null && list_course_teacher.size() > 0) {
                                course_teachers.clear();
                                course_teachers.addAll(list_course_teacher);
                            }
                            TeacherModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        request.session = SESSION.getInstance();

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }
        cb.url(ApiInterface.USER_SUBSCRIPTION).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

}
