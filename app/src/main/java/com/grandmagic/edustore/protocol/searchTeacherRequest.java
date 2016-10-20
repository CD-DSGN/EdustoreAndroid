
package com.grandmagic.edustore.protocol;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "searchTeacherRequest")
public class searchTeacherRequest extends Model
{

     @Column(name = "pagination")
     public PAGINATION   pagination;

     @Column(name = "keywords")
     public String   keywords;

     @Column(name = "course_id")
     public String course_id;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          PAGINATION  pagination = new PAGINATION();
          pagination.fromJson(jsonObject.optJSONObject("pagination"));
          this.pagination = pagination;
          this.keywords = jsonObject.optString("keywords");
          this.course_id = jsonObject.optString("course_id");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          if(null != pagination)
          {
            localItemObject.put("pagination", pagination.toJson());
          }
          if(null != keywords)
          {
               localItemObject.put("keywords", keywords);
          }
          if (null != course_id) {
               localItemObject.put("course_id", course_id);
          }

          return localItemObject;
     }

}
