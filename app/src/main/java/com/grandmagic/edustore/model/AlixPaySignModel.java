package com.grandmagic.edustore.model;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.edustore.protocol.AlixSignRequest;
import com.grandmagic.edustore.protocol.AlixSignResponse;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.SESSION;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangmengqi on 2016/10/24.
 */
public class AlixPaySignModel extends BaseModel {
    public AlixPaySignModel(Context context) {
       super(context);
    }



    public void getSign(String content) {
        final AlixSignRequest request = new AlixSignRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    AlixPaySignModel.this.callback(url, jo, status);
                    if (jo != null) {
                        AlixSignResponse response = new AlixSignResponse();
                        response.fromJson(jo);
                        AlixPaySignModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };
        request.session= SESSION.getInstance();
        request.content = content;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.ALIXPAY_SIGN).type(JSONObject.class).params(params);
        aq.ajax(cb);
//        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
//        aq.progress(pd.mDialog).ajax(cb);

    }
}
