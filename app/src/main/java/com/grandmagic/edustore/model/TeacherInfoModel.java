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
import com.grandmagic.edustore.protocol.Integral;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.TeacherInfoResponse;
import com.grandmagic.edustore.protocol.userinfoRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TeacherInfoModel extends BaseModel {
    public Integral integral;


    public TeacherInfoModel(Context context) {
        super(context);
    }

    public void getTeacherInfo() {

        userinfoRequest request = new userinfoRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                TeacherInfoModel.this.callback(url, jo, status);

                try {

                    TeacherInfoResponse response = new TeacherInfoResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            integral = response.data;
                            TeacherInfoModel.this.OnMessageResponse(url, jo, status);
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
        cb.url(ApiInterface.TEACHER_INFO).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

}
