
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "Integral")
public class Integral extends Model {


    @Column(name = "rank_points")
    public String rank_points;

    @Column(name = "pay_points")
    public String pay_points;

    @Column(name = "teacher_integral")
    public String teacher_integral;

    @Column(name = "subscription_student_num")
    public String subscription_student_num;

    @Column(name = "recommanded_teacher_num")
    public String recommanded_teacher_num;

    @Column(name = "points_from_affiliate")
    public String points_from_affiliate;

    @Column(name = "points_from_subscription")
    public String points_from_subscription;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;
        this.teacher_integral = jsonObject.optString("teacher_integral");
        this.pay_points = jsonObject.optString("pay_points");
        this.rank_points = jsonObject.optString("rank_points");
        this.subscription_student_num = jsonObject.optString("subscription_student_num");
        this.recommanded_teacher_num = jsonObject.optString("recommanded_teacher_num");
        this.points_from_affiliate = jsonObject.optString("points_from_affiliate");
        this.points_from_subscription = jsonObject.optString("points_from_subscription");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("pay_points", pay_points); //added by zhangmengqi
        localItemObject.put("rank_points", rank_points); //added by zhangmengqi
        localItemObject.put("teacher_integral", teacher_integral); //added by zhangmengqi
        localItemObject.put("subscription_student_num", subscription_student_num); //added by zhangmengqi
        localItemObject.put("recommanded_teacher_num", recommanded_teacher_num); //added by zhangmengqi
        localItemObject.put("points_from_affiliate", points_from_affiliate); //added by zhangmengqi
        localItemObject.put("points_from_subscription", points_from_subscription); //added by zhangmengqi
        return localItemObject;
    }

}
