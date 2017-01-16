package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by chenggaoyuan on 2016/10/26.
 */
public class teacherpublishRequest {

    public String publishContent;
    public SESSION session;
public List<String> image;
    public void fromJson(JSONObject jsonObject) throws JSONException {

        if (null == jsonObject) {
            return;
        }

        publishContent = jsonObject.optString("publishcontent");
        SESSION session = new SESSION();
        session.fromJson(jsonObject.optJSONObject("session"));
        this.session = session;
        return;
    }

    public JSONObject toJson() throws JSONException {

        JSONObject localItemObject = new JSONObject();
        if (null != publishContent) {
            localItemObject.put("publishcontent", publishContent);
        }
        if (image!=null&&image.size()>0){
            JSONArray jsonArray=new JSONArray();
            for (int i = 0; i < image.size(); i++) {
                jsonArray.put(image.get(i));
            }
            localItemObject.put("publish_images", jsonArray);
        }
        if (null != session) {
            localItemObject.put("session", session.toJson());
        }
        return localItemObject;
    }

}
