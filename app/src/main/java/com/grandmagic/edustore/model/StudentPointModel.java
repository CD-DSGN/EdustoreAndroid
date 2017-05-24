package com.grandmagic.edustore.model;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.PAGINATED;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.STU_POINT_LIST;
import com.grandmagic.edustore.protocol.StudentPointsRequest;
import com.grandmagic.edustore.protocol.StudentPointsResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangmengqi on 2017/5/11.
 */

public class StudentPointModel extends BaseModel{
    public PAGINATED paginated;
    public int PAGE_COUNT = 40;
    public ArrayList<STU_POINT_LIST> stu_points_list = new ArrayList<>();


    public StudentPointModel(Context context) {
        super(context);
    }

    public void getStudentPoints(String info_id) {
        StudentPointsRequest request = new StudentPointsRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                StudentPointModel.this.callback(url, jo, status);

                try {
                    StudentPointsResponse response = new StudentPointsResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            ArrayList<STU_POINT_LIST> data = response.data;
                            stu_points_list.clear();
                            if (null != data && data.size() > 0) {
                                stu_points_list.clear();
                                stu_points_list.addAll(data);

                            }
                            paginated = response.paginated;
                            StudentPointModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };

        SESSION session = SESSION.getInstance();


        request.session = session;
        request.info_id = info_id;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.STUDENT_POINTS).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

}
