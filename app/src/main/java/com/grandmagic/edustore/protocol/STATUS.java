
package com.grandmagic.edustore.protocol;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "STATUS")
public class STATUS  extends Model
{

     @Column(name = "succeed")
     public int succeed;

     @Column(name = "error_code")
     public int error_code;

     @Column(name = "error_desc")
     public String error_desc;
public String error_message;
 public  void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }

     JSONArray subItemArray;

     this.succeed = jsonObject.optInt("succeed");

     this.error_code = jsonObject.optInt("error_code");

     this.error_desc = jsonObject.optString("error_desc");
     if (jsonObject.optJSONArray("error_message") != null) {
         this.error_message =  jsonObject.optJSONArray("error_message").get(0)+"";
     }
     return ;

     
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("succeed", succeed);
     localItemObject.put("error_code", error_code);
     localItemObject.put("error_desc", error_desc);
     localItemObject.put("error_message", error_message);
     return localItemObject;
 }

}
