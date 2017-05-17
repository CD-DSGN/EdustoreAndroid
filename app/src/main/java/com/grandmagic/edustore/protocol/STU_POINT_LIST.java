package com.grandmagic.edustore.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangmengqi on 2017/5/11.
 */

public class STU_POINT_LIST {
    public String student_name;
    public String student_points;
    public String avatar;
    public void fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.student_name = jsonObject.optString("student_name");
        this.student_points = jsonObject.optString("student_points");
        this.avatar = jsonObject.optString("avatar");

        return ;
    }



}
