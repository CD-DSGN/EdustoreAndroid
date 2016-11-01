
package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "AlixSignRequest")
public class AlixSignRequest extends Model {

    @Column(name = "session")
    public SESSION session;

    @Column(name = "content")
    public String content;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        SESSION session = new SESSION();
        session.fromJson(jsonObject.optJSONObject("session"));
        this.session = session;
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        if (null != session) {
            localItemObject.put("session", session.toJson());
        }

        if (null != content) {
            localItemObject.put("content", content);
        }
        return localItemObject;
    }

}
