package com.grandmagic.edustore.protocol;

import com.external.activeandroid.annotation.Column;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/12/6.
 */
public class fetchCommentsRequest {

    public PAGINATION   pagination;

    public SESSION session;



    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        PAGINATION  pagination = new PAGINATION();
        pagination.fromJson(jsonObject.optJSONObject("pagination"));
        this.pagination = pagination;

        SESSION session = new SESSION();
        session.fromJson(jsonObject.optJSONObject("session"));
        this.session = session;

        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        if(null != pagination)
        {
            localItemObject.put("pagination", pagination.toJson());
        }

        if(null != session){
            localItemObject.put("session", session.toJson());
        }

        return localItemObject;
    }

}
