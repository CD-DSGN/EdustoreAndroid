package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/11/29.
 */
public class userImgRequest extends Model {

    public SESSION session;
    public String user_img;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }
        JSONArray subItemArray;
        this.user_img = jsonObject.optString("name");
        SESSION session = new SESSION();
        session.fromJson(jsonObject.optJSONObject("session"));
        this.session = session;
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        if(null != user_img){
            localItemObject.put("avatar", user_img);
        }
        if(null != session){
            localItemObject.put("session", session.toJson());
        }
        return localItemObject;
    }
}
