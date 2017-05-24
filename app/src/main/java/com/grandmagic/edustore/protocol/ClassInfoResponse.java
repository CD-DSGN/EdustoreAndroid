package com.grandmagic.edustore.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhangmengqi on 2017/5/18.
 */

public class ClassInfoResponse {
    public STATUS   status;
    public ArrayList<ClassInfo> data = new ArrayList<ClassInfo>();

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;
        STATUS  status = new STATUS();
        status.fromJson(jsonObject.optJSONObject("status"));
        this.status = status;

        subItemArray = jsonObject.optJSONArray("data");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                ClassInfo subItem = new ClassInfo();
                subItem.fromJson(subItemObject);
                this.data.add(subItem);
            }
        }

        return ;
    }
}
