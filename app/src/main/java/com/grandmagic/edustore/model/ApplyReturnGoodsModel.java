package com.grandmagic.edustore.model;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.IRequest;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.ApplyReturnGoodsActivity;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.GoodsReturn_request;
import com.grandmagic.edustore.protocol.ReasonResponse;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.addressaddResponse;
import com.grandmagic.edustore.protocol.addresslistRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lps on 2017/3/6.
 */

public class ApplyReturnGoodsModel extends BaseModel {

    public ApplyReturnGoodsModel(Context mContext) {
        super(mContext);
    }

    public void getReason(final IRequest<ReasonResponse> mIRequest) {
        addresslistRequest mRequest = new addresslistRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                ApplyReturnGoodsModel.this.callback(url, jo, status);
                ReasonResponse mResponse = new ReasonResponse();
                try {
                    mResponse.fromJson(jo);
                } catch (JSONException mE) {
                    mE.printStackTrace();
                }
                mIRequest.request(mResponse);
            }
        };
        mRequest.session = SESSION.getInstance();
        Map<String, String> params = new HashMap<>();
        try {
            params.put("json", mRequest.toJson().toString());
        } catch (JSONException mE) {
            mE.printStackTrace();
        }
        cb.url(ApiInterface.RETURN_REASON).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void Retuan_Goods(String id,String reason,String desc,final IRequest<addressaddResponse> mIRequest) {
        GoodsReturn_request mRequest = new GoodsReturn_request();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                ApplyReturnGoodsModel.this.callback(url, jo, status);
                addressaddResponse mResponse=new addressaddResponse();
                try {
                    mResponse.fromJson(jo);
                    mIRequest.request(mResponse);
                } catch (JSONException mE) {
                    mE.printStackTrace();
                }
            }
        };
        mRequest.setSESSION( SESSION.getInstance())
        .setRec_id(id)
        .setRefund_reason(reason)
        .setRefund_desc(desc);
        Map<String, String> params = new HashMap<>();
        try {
            params.put("json", mRequest.toJson().toString());
        } catch (JSONException mE) {
            mE.printStackTrace();
        }
        cb.url(ApiInterface.GOODS_RETURN).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
}
