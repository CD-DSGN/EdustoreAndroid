package com.grandmagic.edustore.protocol;

import android.content.Intent;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chenggaoyuan on 2016/12/6.
 */
public class fetchCommentsResponse extends Model {

    @Column(name = "status")
    public STATUS   status;

    public ArrayList<TEACHERCOMMENTS> data = new ArrayList<TEACHERCOMMENTS>();

    @Column(name = "paginated")
    public PAGINATED   paginated;

    public static int IDLE = 0;   //作为学生没有关注教师,或者作为老师没有发表
    public static int FOLLOW = 1; //作为学生关注至少一位教师，或者作为老师至少有一次发表
    public int follow = IDLE;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }


        STATUS  status = new STATUS();
        status.fromJson(jsonObject.optJSONObject("status"));
        this.status = status;

        JSONArray subItemArray;
        JSONObject info = jsonObject.optJSONObject("data");
        if(info != null) {
            subItemArray = info.optJSONArray("info");
            if(subItemArray != null){
                for(int i = 0;i < subItemArray.length();i++)
                {
                    JSONObject subItemObject = subItemArray.getJSONObject(i);
                    TEACHERCOMMENTS subItem = new TEACHERCOMMENTS();
                    subItem.fromJson(subItemObject);
                    this.data.add(subItem);
                }
            }
            if(1 == info.optInt("no_follow")){
                this.follow = FOLLOW;
            }
        }

        PAGINATED  paginated = new PAGINATED();
        paginated.fromJson(jsonObject.optJSONObject("paginated"));
        this.paginated = paginated;
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        if(null != status)
        {
            localItemObject.put("status", status.toJson());
        }

        for(int i =0; i< data.size(); i++)
        {
            TEACHERCOMMENTS itemData =data.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        JSONObject localItemObject_info = new JSONObject();
        localItemObject_info.put("info",itemJSONArray);
        localItemObject_info.put("no_follow",this.follow);

        localItemObject.put("data", localItemObject_info);
        if(null != paginated)
        {
            localItemObject.put("paginated", paginated.toJson());
        }
        return localItemObject;
    }

}
