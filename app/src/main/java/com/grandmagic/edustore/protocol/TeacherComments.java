package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/12/6.
 */
public class TeacherComments extends Model {

    public PHOTO teacher_img;

    public String teacher_name;

    public String teacher_comments;

    public String publish_time;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.teacher_name = jsonObject.optString("teacher_name");

        this.teacher_comments = jsonObject.optString("teacher_comments");

        this.publish_time = jsonObject.optString("publish_time");

        PHOTO img = new PHOTO();
        img.fromJson(jsonObject.optJSONObject("teacher_img"));
        this.teacher_img = img;

        return;
    }

    public JSONObject toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("teacher_name",teacher_name);
        localItemObject.put("teacher_comments",teacher_comments);
        localItemObject.put("publish_time",publish_time);
        if(null!=teacher_img){
            localItemObject.put("teacher_img",teacher_img.toJson());
        }
        return localItemObject;
    }
}
