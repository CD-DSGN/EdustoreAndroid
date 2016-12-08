package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/12/6.
 */
public class TEACHERCOMMENTS extends Model {

    public String teacher_img_small;

    public String teacher_name;

    public String teacher_comments;

    public String publish_time;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }
        JSONObject teacher_info = jsonObject.optJSONObject("teacher_info");
        JSONObject publish_info = jsonObject.optJSONObject("publish_info");


        this.teacher_name = teacher_info.optString("real_name");

        this.teacher_comments = publish_info.optString("news_content");

        this.publish_time = publish_info.optString("publish_time");

        this.teacher_img_small = teacher_info.optString("avatar");

        return;
    }

    public JSONObject toJson() throws JSONException{
        JSONObject localObj_teacher_info = new JSONObject();
        JSONObject localObj_publish_info = new JSONObject();
        JSONObject localItemObject = new JSONObject();

        localObj_teacher_info.put("real_name",teacher_name);
        localObj_teacher_info.put("avatar",teacher_img_small);
        localObj_publish_info.put("news_content",teacher_comments);
        localObj_publish_info.put("publish_time",publish_time);

        localItemObject.put("teacher_info",localObj_teacher_info);
        localItemObject.put("publish_info",localObj_publish_info);

        return localItemObject;
    }
}
