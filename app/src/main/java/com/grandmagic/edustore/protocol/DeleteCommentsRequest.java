package com.grandmagic.edustore.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lps on 2017/2/27.
 * 删除汇师圈动态的request
 *
 */

public class DeleteCommentsRequest {
    public SESSION mSESSION;
    public String uid;
    public String time;

    public JSONObject toJson(){
        JSONObject result=new JSONObject();
        if (null!=mSESSION){
            try {
                result.put("session",mSESSION.toJson());
                result.put("publish_uid",uid);
                result.put("publish_time",time);
            } catch (JSONException mE) {
                mE.printStackTrace();
            }
        }
        return result;
    }
}
