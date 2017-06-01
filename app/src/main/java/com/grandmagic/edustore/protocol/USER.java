
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "USER")
public class USER extends Model {

    @Column(name = "collection_num")
    public String collection_num;

    @Column(name = "USER_id", unique = true)
    public String id;

    @Column(name = "rank_level")
    public int rank_level;

    @Column(name = "order_num")
    public ORDER_NUM order_num;

    @Column(name = "name")
    public String name;

    @Column(name = "rank_name")
    public String rank_name;

    @Column(name = "email")
    public String email;

    @Column(name = "is_teacher")
    public String is_teacher; //added by zhangmengqi

    public String nickname;
    public String teacher_course;
//    @Column(name="teacher_integral")
//    public String teacher_integral;

//    @Column(name="rank_points")
//    public String rank_points;

//    @Column(name="pay_points")
//    public String pay_points;


    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.collection_num = jsonObject.optString("collection_num");

        this.id = jsonObject.optString("id");

        this.rank_level = jsonObject.optInt("rank_level");
        ORDER_NUM order_num = new ORDER_NUM();
        order_num.fromJson(jsonObject.optJSONObject("order_num"));
        this.order_num = order_num;

        this.name = jsonObject.optString("name");
        this.nickname = jsonObject.optString("show_name");
        this.rank_name = jsonObject.optString("rank_name");
        this.email = jsonObject.optString("email");
        this.teacher_course = jsonObject.optString("teacher_course");
        this.is_teacher = jsonObject.optString("is_teacher");
//        this.pay_points = jsonObject.optString("pay_points");
//        this.rank_points = jsonObject.optString("rank_points");
//        this.teacher_integral = jsonObject.optString("teacher_integral");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("collection_num", collection_num);
        localItemObject.put("id", id);
        localItemObject.put("rank_level", rank_level);
        if (null != order_num) {
            localItemObject.put("order_num", order_num.toJson());
        }
        localItemObject.put("name", name);
        localItemObject.put("rank_name", rank_name);
        localItemObject.put("email", email);
        localItemObject.put("is_teacher", is_teacher); //added by zhangmengqi
//        localItemObject.put("pay_points", pay_points); //added by zhangmengqi
//        localItemObject.put("rank_points", rank_points); //added by zhangmengqi
//        localItemObject.put("teacher_integral", teacher_integral); //added by zhangmengqi
        return localItemObject;
    }

}
