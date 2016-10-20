
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Table(name = "usersignupteacherRequest")
public class usersignupteacherRequest extends Model
{

     public ArrayList<FIELD>   field = new ArrayList<FIELD>();

     @Column(name = "name")
     public String   name;

     @Column(name = "password")
     public String   password;

     @Column(name = "mobile_phone")
     public String  mobile_phone;

     @Column(name = "real_name")
     public String real_name;

     @Column(name = "school")
     public String school;

     @Column(name = "course")
     public String course;

     @Column(name = "country")
     public String country;

     @Column(name = "province")
     public String province;

     @Column(name = "city")
     public String city;

     @Column(name = "district")
     public String district;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          subItemArray = jsonObject.optJSONArray("field");
          if(null != subItemArray)
           {
              for(int i = 0;i < subItemArray.length();i++)
               {
                  JSONObject subItemObject = subItemArray.getJSONObject(i);
                  FIELD subItem = new FIELD();
                  subItem.fromJson(subItemObject);
                  this.field.add(subItem);
               }
           }


          this.name = jsonObject.optString("name");

          this.password = jsonObject.optString("password");

          this.mobile_phone = jsonObject.optString("mobile_phone");
          this.real_name = jsonObject.optString("real_name");
          this.school = jsonObject.optString("school");
          this.course = jsonObject.optString("course");
          this.country = jsonObject.optString("country");
          this.province = jsonObject.optString("province");
          this.city = jsonObject.optString("city");
          this.district = jsonObject.optString("district");

          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();

          localItemObject.put("name", name);
          localItemObject.put("password", password);
          localItemObject.put("real_name", real_name);
          localItemObject.put("school", school);
          localItemObject.put("course", course);
          localItemObject.put("mobile_phone", mobile_phone);
          localItemObject.put("country", country);
          localItemObject.put("province", province);
          localItemObject.put("city", city);
          localItemObject.put("district", district);

          return localItemObject;
     }

}
