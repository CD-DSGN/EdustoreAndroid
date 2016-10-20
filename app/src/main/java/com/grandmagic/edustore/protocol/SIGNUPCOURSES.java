
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "SIGNUPCOURSES")
public class SIGNUPCOURSES extends Model
{

     @Column(name = "course_id")
     public String course_id;

     @Column(name = "course_name")
     public String course_name;

 public void  fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }

     JSONArray subItemArray;

     this.course_id = jsonObject.optString("course_id");

     this.course_name = jsonObject.optString("course_name");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("course_id", course_id);
     localItemObject.put("course_name", course_name);
     return localItemObject;
 }

}
