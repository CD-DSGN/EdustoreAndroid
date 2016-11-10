package com.grandmagic.edustore.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/10/26.
 */
public class teacherpublishResponse {

    public STATUS status;

    public void fromJson(JSONObject jsonObject) throws JSONException{

        if(null == jsonObject){
            return;
        }

        STATUS status = new STATUS();
        status.fromJson(jsonObject.optJSONObject("status"));
        this.status = status;
        return;
    }

    public JSONObject toJson() throws JSONException{

        JSONObject localItemObject = new JSONObject();
        if(null != status)
        {
            localItemObject.put("status", status.toJson());
        }
        return localItemObject;
    }
}
