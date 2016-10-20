package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/8/29.
 */
public class VerificationCode extends Model {

    public String veri_code;

    public  void fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.veri_code = jsonObject.optString("veri_code");

        return;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("veri_code", veri_code);
        return localItemObject;
    }
}
