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
import android.content.SharedPreferences;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.ErrorCodeConst;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.fragment.B0_IndexFragment;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.SIGNUPCOURSES;
import com.grandmagic.edustore.protocol.SIGNUP_DATA;
import com.grandmagic.edustore.protocol.STATUS;
import com.grandmagic.edustore.protocol.USER;
import com.grandmagic.edustore.protocol.usersignupCoursesRequest;
import com.grandmagic.edustore.protocol.usersignupCoursesResponse;
import com.grandmagic.edustore.protocol.usersignupResponse;
import com.grandmagic.edustore.protocol.usersignupteacherRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterModel_teacher extends BaseModel {

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public ArrayList<SIGNUPCOURSES> signupcourseslist = new ArrayList<SIGNUPCOURSES>();
    public STATUS responseStatus;

    public RegisterModel_teacher(Context context) {
        super(context);

        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
    }

    public void signupCourses() {
        usersignupCoursesRequest request = new usersignupCoursesRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                RegisterModel_teacher.this.callback(url, jo, status);
                try {
                    usersignupCoursesResponse response = new usersignupCoursesResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        responseStatus = response.status;
                        if (responseStatus.succeed == ErrorCodeConst.ResponseSucceed) {
                            ArrayList<SIGNUPCOURSES> data = response.data;
                            if (null != data && data.size() > 0) {
                                signupcourseslist.clear();
                                signupcourseslist.addAll(data);
                            }
                        }

                        RegisterModel_teacher.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        cb.url(ApiInterface.USER_SIGNUPCOURSES).type(JSONObject.class);
//        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.ajax(cb);

    }

    public void signup(String password, String mobile_phone, String course,
                       String teacher_real_name, String school, String country, String province, String city, String district, String invite_code
    ,String teach_grade,String teach_class) {
        usersignupteacherRequest request = new usersignupteacherRequest( );

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                RegisterModel_teacher.this.callback(url, jo, status);
                try {
                    usersignupResponse response = new usersignupResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            SIGNUP_DATA data = response.data;
                            SESSION session = data.session;
                            SESSION.getInstance().uid=session.uid;
                            SESSION.getInstance().sid = session.sid;
                            USER user = data.user;
                            user.save();
                            editor.putString("uid", session.uid);
                            editor.putString("sid", session.sid);
                            B0_IndexFragment.login_is_teacher = 1;
                            editor.commit();
                            RegisterModel_teacher.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        request.password = password;
//        request.field = fields;
        request.mobile_phone = mobile_phone;
        request.course = course;
        request.real_name = teacher_real_name;
        request.school = school;
        request.country = country;
        request.province = province;
        request.city = city;
        request.district = district;
        request.invite_code = invite_code;
        request.teacher_grade = teach_grade;
        request.teacher_class = teach_class;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.USER_SIGNUP_TEACHER).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

}
