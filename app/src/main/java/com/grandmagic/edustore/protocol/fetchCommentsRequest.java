package com.grandmagic.edustore.protocol;

import com.external.activeandroid.annotation.Column;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/12/6.
 */
public class fetchCommentsRequest {

        @Column(name = "pagination")
        public PAGINATION   pagination;



    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        PAGINATION  pagination = new PAGINATION();
        pagination.fromJson(jsonObject.optJSONObject("pagination"));
        this.pagination = pagination;

        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        if(null != pagination)
        {
            localItemObject.put("pagination", pagination.toJson());
        }

        return localItemObject;
    }

}
