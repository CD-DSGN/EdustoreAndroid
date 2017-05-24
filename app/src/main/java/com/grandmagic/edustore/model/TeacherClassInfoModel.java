package com.grandmagic.edustore.model;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.ClassInfo;
import com.grandmagic.edustore.protocol.ClassInfoResponse;
import com.grandmagic.edustore.protocol.SESSION;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangmengqi on 2017/5/18.
 */

public class TeacherClassInfoModel extends BaseModel {
    public ArrayList<ClassInfo> mClassInfos = new ArrayList<>();


    public TeacherClassInfoModel(Context context) {
        super(context);
    }

    public void getClassInfo() {
        TeacherClassInfoRequest request = new TeacherClassInfoRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                TeacherClassInfoModel.this.callback(url, jo, status);

                try {
                    ClassInfoResponse response = new ClassInfoResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            ArrayList<ClassInfo> data = response.data;
                            mClassInfos.clear();
                            if (null != data && data.size() > 0) {
                                mClassInfos.addAll(data);

                            }
                            TeacherClassInfoModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };

        SESSION session = SESSION.getInstance();

        request.session = session;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.TEACHER_CLASS_INFO).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    public static class TeacherClassInfoRequest {
        public SESSION   session;

        public JSONObject toJson() throws JSONException
        {
            JSONObject localItemObject = new JSONObject();
            if(null != session)
            {
                localItemObject.put("session", session.toJson());
            }
            return localItemObject;
        }
    }

}
