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
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.SIGNIN_DATA;
import com.grandmagic.edustore.protocol.STATUS;
import com.grandmagic.edustore.protocol.USER;
import com.grandmagic.edustore.protocol.userImgRequest;
import com.grandmagic.edustore.protocol.userImgResponse;
import com.grandmagic.edustore.protocol.usersigninResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenggaoyuan on 2016/11/29.
 */
public class UserImgModel extends BaseModel {

    public STATUS responseStatus;

    public UserImgModel(Context context){
        super(context);
    }

    public void uploadUserImg(String user_img_str){
        userImgRequest request = new userImgRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                UserImgModel.this.callback(url, jo, status);
                try {
                    userImgResponse response = new userImgResponse();
                    response.fromJson(jo);
                    responseStatus=response.status;
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            Resources resource = mContext.getResources();
                            String upload_img_success = resource.getString(R.string.upload_img_success);
                            ToastView toast = new ToastView(mContext,upload_img_success);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        UserImgModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        SESSION session = SESSION.getInstance();
        request.session = session;
        request.user_img = user_img_str;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.USER_IMG_UPLOAD).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
}
