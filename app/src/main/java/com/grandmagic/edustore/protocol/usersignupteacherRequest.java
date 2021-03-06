
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.name;

@Table(name = "usersignupteacherRequest")
public class usersignupteacherRequest extends Model
{

     public ArrayList<FIELD>   field = new ArrayList<FIELD>();



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

     @Column(name = "invite_code")
     public String invite_code;
public String teacher_grade;
     public String teacher_class;
     public void fromJson(JSONObject jsonObject) throws JSONException {

          if (null == jsonObject) {
               return;
          }

          JSONArray subItemArray;

          subItemArray = jsonObject.optJSONArray("field");
          if (null != subItemArray) {
               for (int i = 0; i < subItemArray.length(); i++) {
                    JSONObject subItemObject = subItemArray.getJSONObject(i);
                    FIELD subItem = new FIELD();
                    subItem.fromJson(subItemObject);
                    this.field.add(subItem);
               }
          }



          this.password = jsonObject.optString("password");

          this.mobile_phone = jsonObject.optString("mobile_phone");
          this.real_name = jsonObject.optString("real_name");
          this.school = jsonObject.optString("school");
          this.course = jsonObject.optString("course");
          this.country = jsonObject.optString("country");
          this.province = jsonObject.optString("province");
          this.city = jsonObject.optString("city");
          this.district = jsonObject.optString("district");
          this.invite_code = jsonObject.optString("invite_code");

          return;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();

          localItemObject.put("password", password);
          localItemObject.put("real_name", real_name);
          localItemObject.put("teacher_school", school);
          localItemObject.put("course", course);
          localItemObject.put("mobile_phone", mobile_phone);
          localItemObject.put("country", country);
          localItemObject.put("province", province);
          localItemObject.put("city", city);
          localItemObject.put("district", district);
          localItemObject.put("invite_code", invite_code);
          localItemObject.put("teacher_class", teacher_class);
          localItemObject.put("teacher_grade", teacher_grade);

          return localItemObject;
     }

}
