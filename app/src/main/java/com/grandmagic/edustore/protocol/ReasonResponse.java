package com.grandmagic.edustore.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lps on 2017/3/6.
 */

public class ReasonResponse {


    /**
     * data : ["无理由退货","质量问题","与描述不符"]
     * status : {"succeed":1}
     */

    private StatusBean status;
    private List<String> data;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public static class StatusBean {
        /**
         * succeed : 1
         */

        private int succeed;

        public int getSucceed() {
            return succeed;
        }

        public void setSucceed(int succeed) {
            this.succeed = succeed;
        }
    }

    public void fromJson(JSONObject jo) throws JSONException{
        StatusBean mStatusBean=new StatusBean();
        mStatusBean.setSucceed(jo.optJSONObject("status").optInt("succeed"));
        this.setStatus(mStatusBean);
        JSONArray mData = jo.optJSONArray("data");
        List<String> mStrings=new ArrayList<>();
        for (int i = 0; i < mData.length(); i++) {
            mStrings.add(mData.optString(i));
        }
        this.setData(mStrings);
    }
}
