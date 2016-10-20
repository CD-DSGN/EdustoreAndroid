
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "TeacherRequest ")
public class TeacherRequest extends Model
{

     @Column(name = "simpleTeacherInfo")
     public SimpleTeacherInfo simpleTeacherInfo;

     @Column(name = "session")
     public SESSION session;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();

          if(null != simpleTeacherInfo)
          {
            localItemObject.put("simpleTeacherInfo", simpleTeacherInfo.toJson());
          }
          if(null != session)
          {
            localItemObject.put("session", session.toJson());
          }

          return localItemObject;
     }

}
