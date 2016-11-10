package com.grandmagic.edustore.model;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ADDRESS;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.addresslistResponse;
import com.grandmagic.edustore.protocol.teacherpublishRequest;
import com.grandmagic.edustore.protocol.teacherpublishResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenggaoyuan on 2016/10/26.
 */
public class TeacherPublishModel extends BaseModel{

    public TeacherPublishModel(Context context){
        super(context);
    }

    public void publish_teacher_message(String content){
        teacherpublishRequest request = new teacherpublishRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                TeacherPublishModel.this.callback(url, jo, status);
                try {
                    if (jo != null) {
                        teacherpublishResponse response = new teacherpublishResponse();
                        response.fromJson(jo);
                        if (response.status.succeed == 1) {
                            TeacherPublishModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            Resources resource = mContext.getResources();
                            String publish_failed = resource.getString(R.string.publish_failed);
                            ToastView toast = new ToastView(mContext, publish_failed);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SESSION session = SESSION.getInstance();
        request.session = session;
        request.publishContent = content;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception

        }

        cb.url(ApiInterface.TEACHER_PUBLISH).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }
}
