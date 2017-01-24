
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



@Table(name = "userinfoResponse")
public class SimpleuserinfoResponse extends Model
{

     @Column(name = "status")
     public STATUS   status;

     @Column(name = "is_teacher")
     public int    is_teacher;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          STATUS  status = new STATUS();
          status.fromJson(jsonObject.optJSONObject("status"));
          this.status = status;
          JSONObject obj = jsonObject.optJSONObject("data");
          if (obj != null) {
               this.is_teacher = obj.optInt("is_teacher", -1);
          }
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          if(null != status)
          {
            localItemObject.put("status", status.toJson());
          }

          localItemObject.put("is_teacher", is_teacher);
          return localItemObject;
     }

}
