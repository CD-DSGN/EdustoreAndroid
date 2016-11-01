
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "AlixSignResponse")
public class AlixSignResponse extends Model
{

     @Column(name = "status")
     public STATUS   status;

     @Column(name = "sign")
     public String sign;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          STATUS  status = new STATUS();
          sign  = null;
          status.fromJson(jsonObject.optJSONObject("status"));
          this.status = status;

          sign = jsonObject.getJSONObject("data").optString("sign");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          if(null != status)
          {
            localItemObject.put("status", status.toJson());
          }
          if(null != sign)
          {
            localItemObject.put("sign", sign);
          }
          return localItemObject;
     }

}
