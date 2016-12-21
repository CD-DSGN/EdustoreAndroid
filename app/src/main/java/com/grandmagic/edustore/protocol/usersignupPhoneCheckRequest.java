package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/8/23.
 */
@Table(name = "usersignupPhoneCheckRequest")
public class usersignupPhoneCheckRequest extends Model {

    @Column(name = "phonenum")
    public String phonenum;

    @Column(name = "invitation_code")
    public String invitation_code;

    public void fromJson(JSONObject jsonObject) throws JSONException
    {
        if(null==jsonObject){
            return;
        }
        this.phonenum = jsonObject.optString("phonenum");
        this.invitation_code = jsonObject.optString("invitate_code");
        return;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("phonenum",phonenum);
        localItemObject.put("invitate_code", invitation_code);
        return localItemObject;
    }
}
