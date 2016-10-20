package com.grandmagic.edustore.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/8/23.
 */
@Table(name = "usersignupPhoneCheckResponse")
public class usersignupPhoneCheckResponse extends Model{
    @Column(name = "status")
    public STATUS status;

    public VerificationCode mVCodeCls;
    public void fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        STATUS  status = new STATUS();
        status.fromJson(jsonObject.optJSONObject("status"));
        this.status = status;
        VerificationCode mVCodeCls= new VerificationCode();
        mVCodeCls.fromJson(jsonObject.optJSONObject("data"));
        this.mVCodeCls = mVCodeCls;
        return ;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        if(null != status)
        {
            localItemObject.put("status", status.toJson());
        }
        if(null != mVCodeCls)
        {
            localItemObject.put("data", mVCodeCls.toJson());
        }
        return localItemObject;
    }
}
