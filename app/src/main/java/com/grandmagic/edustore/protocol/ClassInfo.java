package com.grandmagic.edustore.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangmengqi on 2017/5/18.
 */

public class ClassInfo {
    public String info_id;
    public String school_name;
    public String grade;
    public String class_no;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        this.info_id = jsonObject.optString("info_id");
        this.school_name = jsonObject.optString("school_name");
        this.grade = jsonObject.optString("grade");
        this.class_no = jsonObject.optString("class_no");

        return;
    }


}


