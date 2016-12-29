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
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.SimpleuserinfoResponse;
import com.grandmagic.edustore.protocol.USER;
import com.grandmagic.edustore.protocol.userinfoRequest;
import com.grandmagic.edustore.protocol.userinfoResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.util.Locale.US;

public class SimpleUserInfoModel extends BaseModel {

    public int is_teacher = -1;
    public SimpleUserInfoModel(Context context) {
        super(context);
    }

    public void getSimpleUserInfo() {

        userinfoRequest request = new userinfoRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SimpleUserInfoModel.this.callback(url, jo, status);
                try {

                    SimpleuserinfoResponse response = new SimpleuserinfoResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            is_teacher = response.is_teacher;
                            SimpleUserInfoModel.this.OnMessageResponse(url, jo, status);
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
        cb.url(ApiInterface.SIMPLE_USER_INFO).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }

}
