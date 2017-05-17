package com.grandmagic.edustore.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangmengqi on 2017/5/11.
 */

public class StudentPointsRequest {
    public SESSION   session;
    public PAGINATION   pagination;

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        if(null != session)
        {
            localItemObject.put("session", session.toJson());
        }
        if(null != pagination)
        {
            localItemObject.put("pagination", pagination.toJson());
        }
        return localItemObject;
    }
}
