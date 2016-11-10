package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/10/26.
 */
public class teacherpublishRequest{

    public String publishContent;

    public SESSION session;

    public void fromJson(JSONObject jsonObject) throws JSONException{

        if (null == jsonObject){
            return;
        }

        publishContent = jsonObject.optString("publishcontent");
        SESSION session = new SESSION();
        session.fromJson(jsonObject.optJSONObject("session"));
        this.session = session;
        return;
    }

    public JSONObject toJson() throws JSONException{

        JSONObject localItemObject = new JSONObject();
        if(null != publishContent){
            localItemObject.put("publishcontent", publishContent);
        }
        if(null != session){
            localItemObject.put("session", session.toJson());
        }
        return localItemObject;
    }

}
