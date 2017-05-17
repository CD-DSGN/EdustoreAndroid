package com.grandmagic.edustore.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhangmengqi on 2017/5/11.
 */

public class StudentPointsResponse {
    public STATUS   status;
    public ArrayList<STU_POINT_LIST> data = new ArrayList<STU_POINT_LIST>();
    public PAGINATED   paginated;

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
                STU_POINT_LIST subItem = new STU_POINT_LIST();
                subItem.fromJson(subItemObject);
                this.data.add(subItem);
            }
        }

        PAGINATED  paginated = new PAGINATED();
        paginated.fromJson(jsonObject.optJSONObject("paginated"));
        this.paginated = paginated;
        return ;
    }
}
