package com.grandmagic.edustore.model;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.IRequest;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.PAYMENT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dangxiaohui on 2017/1/5.
 */

public class PaymentListModel extends BaseModel {
    public PaymentListModel(Context context) {
        super(context);
    }

    /**
     * 获取支付类型列表
     * @param iRequest  回调解析后的json
     */
    public void getPaymentList(final IRequest<ArrayList<PAYMENT>> iRequest) {

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                PaymentListModel.this.callback(url, jo, status);
                ArrayList<PAYMENT> payments = new ArrayList<>();
                try {
                    JSONArray subItemArray = jo.getJSONObject("data").optJSONArray("payment_list");
                    if (null != subItemArray) {
                        for (int i = 0; i < subItemArray.length(); i++) {
                            JSONObject subItemObject = subItemArray.getJSONObject(i);
                            PAYMENT subItem = new PAYMENT();
                            subItem.fromJson(subItemObject);
                            payments.add(subItem);
                        }
                    }
                    iRequest.request(payments);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        cb.url(ApiInterface.PAYMENT_LIST).type(JSONObject.class);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
}
