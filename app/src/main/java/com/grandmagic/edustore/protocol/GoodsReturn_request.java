package com.grandmagic.edustore.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lps on 2017/3/6.
 */

public class GoodsReturn_request {
    private SESSION mSESSION;
    private String rec_id;
    private String refund_reason;
    private String refund_desc;

    public SESSION getSESSION() {
        return mSESSION;
    }

    public GoodsReturn_request setSESSION(SESSION mSESSION) {
        this.mSESSION = mSESSION;
        return this;
    }

    public String getRec_id() {
        return rec_id;
    }

    public GoodsReturn_request setRec_id(String mRec_id) {
        rec_id = mRec_id;
        return this;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public GoodsReturn_request setRefund_reason(String mRefund_reason) {
        refund_reason = mRefund_reason;
        return this;
    }

    public String getRefund_desc() {
        return refund_desc;
    }

    public GoodsReturn_request setRefund_desc(String mRefund_desc) {
        refund_desc = mRefund_desc;
        return this;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject mJSONObject = new JSONObject();
        mJSONObject.put("session", this.mSESSION.toJson());
        mJSONObject.put("rec_id", this.rec_id);
        mJSONObject.put("refund_reason", this.refund_reason);
        mJSONObject.put("refund_desc", this.refund_desc);
        return mJSONObject;
    }
}
