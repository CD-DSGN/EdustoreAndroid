package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenggaoyuan on 2016/12/6.
 */
public class TEACHERCOMMENTS extends Model implements Serializable{

    public String teacher_img_small;

    public String teacher_name;

    public String teacher_comments;

    public String publish_time;
    public String publish_uid;
    public List<Img> photoArray   =new ArrayList<>();

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        JSONObject teacher_info = jsonObject.optJSONObject("teacher_info");
        JSONObject publish_info = jsonObject.optJSONObject("publish_info");


        this.teacher_name = teacher_info.optString("real_name");

        this.teacher_comments = publish_info.optString("news_content");

        this.publish_time = publish_info.optString("publish_time");
        this.publish_uid = publish_info.optString("user_id");

        this.teacher_img_small = teacher_info.optString("avatar");
        JSONArray photo_Array = publish_info.optJSONArray("photo_array");
        if (photo_Array != null && photo_Array.length() > 0) {

            for (int i = 0; i < photo_Array.length(); i++) {
                Img image = new Img();
                image.img = photo_Array.optJSONObject(i).optString("img");
                image.img_thumb = photo_Array.optJSONObject(i).optString("img_thumb");
                photoArray.add(image);
            }
        }
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localObj_teacher_info = new JSONObject();
        JSONObject localObj_publish_info = new JSONObject();
        JSONObject localItemObject = new JSONObject();

        localObj_teacher_info.put("real_name", teacher_name);
        localObj_teacher_info.put("avatar", teacher_img_small);
        localObj_publish_info.put("news_content", teacher_comments);
        localObj_publish_info.put("publish_time", publish_time);
        localObj_publish_info.put("publish_time", publish_uid);

        localItemObject.put("teacher_info", localObj_teacher_info);
        localItemObject.put("publish_info", localObj_publish_info);

        return localItemObject;
    }

   public class Img implements Serializable {
        public String img;
        public String img_thumb;
    }
}
