
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "SimpleTeacherInfo")
public class SimpleTeacherInfo extends Model {

    @Column(name = "teacher_id", unique = true)
    public String teacher_id;

    @Column(name = "teacher_name")
    public String teacher_name;

    @Column(name = "school")
    public String school;

    @Column(name = "course_id")
    public String course_id;

    @Column(name = "course_name")
    public String course_name;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }


        JSONArray subItemArray;

        this.teacher_id = jsonObject.optString("teacher_id");

        this.teacher_name = jsonObject.optString("teacher_name");

        this.school = jsonObject.optString("school");

        this.course_id = jsonObject.optString("course_id");

        this.course_name = jsonObject.optString("course_name");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("teacher_id", teacher_id);
        localItemObject.put("teacher_name", teacher_name);
        localItemObject.put("school", school);
        localItemObject.put("course", course_id);
        localItemObject.put("course_name", course_name);
        return localItemObject;
    }

}
