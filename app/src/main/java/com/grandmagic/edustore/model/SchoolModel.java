package com.grandmagic.edustore.model;

import android.content.Context;
import android.util.Log;

import com.external.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.GradeResponse;
import com.grandmagic.edustore.protocol.SchoolResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lps on 2017/5/18.
 *
 * @version 1
 * @see
 * @since 2017/5/18 14:17
 */


public class SchoolModel  extends BaseModel{
//    学校相关数据

    public List<SchoolResponse.DataBean> mList;
//    年级相关数据
    public List<GradeResponse.DataBean> mGradeList;

    public SchoolModel(Context context) {
        super(context);
    }
    public void getSchool(String regionid){
        BeeCallback<JSONObject> cb=new BeeCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    SchoolModel.this.callback(url, jo, status);
                    SchoolResponse   mSchoolResponse=new Gson().fromJson(jo.toString(),SchoolResponse.class);
                    if (mSchoolResponse.getStatus().getSucceed()==1) {
                    if (null!=mSchoolResponse.getData()&&!mSchoolResponse.getData().isEmpty()){
                        mList=new ArrayList<>();
                        mList.addAll(mSchoolResponse.getData());
                    }
                    }
                    SchoolModel.this.OnMessageResponse(url,jo,status);
                } catch (Exception mE) {
                    Log.e("jsonerr",mE.getMessage());
                }
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        JSONObject mJSONObject=new JSONObject();
        try {
            mJSONObject.put("school_region",regionid);
            params.put("json", mJSONObject.toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.SCHOOL_LIST).type(JSONObject.class).params(params);
        aq.ajax(cb);
    }

    public void getgrade() {
        BeeCallback<JSONObject> cb=new BeeCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    SchoolModel.this.callback(url, jo, status);
                    GradeResponse mGradeResponse=new Gson().fromJson(jo.toString(),GradeResponse.class);
                    if (mGradeResponse.getStatus().getSucceed()==1){
                        if (null!=mGradeResponse.getData()&&!mGradeResponse.getData().isEmpty()){
                            mGradeList=new ArrayList<>();
                            mGradeList.addAll(mGradeResponse.getData());
                        }
                    }
                    SchoolModel.this.OnMessageResponse(url,jo,status);
                } catch (Exception mE) {
                    Log.e("jsonerr",mE.getMessage());
                }
            }
        };

        cb.url(ApiInterface.GRADE_LIST).type(JSONObject.class).params(null);
        aq.ajax(cb);
    }
}
