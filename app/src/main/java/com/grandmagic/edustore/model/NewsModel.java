package com.grandmagic.edustore.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.model.BeeQuery;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.GOODORDER;
import com.grandmagic.edustore.protocol.PAGINATION;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.edustore.protocol.orderlistResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lps on 2017/5/5.
 *
 * @version 1
 * @see
 * @since 2017/5/5 11:16
 */


public class NewsModel extends BaseModel {
    public NewsModel(Context context) {
        super(context);

    }

    public void getNewsData(int cpage) {
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    NewsModel.this.OnMessageResponse(url, jo, status);
                } catch (JSONException mE) {
                    mE.printStackTrace();
                }
            }

        };

        Map<String, String> params = new HashMap<String, String>();
        params.put("pagesize", 20 + "");
        params.put("cpage", cpage + "");
        cb.url(ApiInterface.GET_NEWS).method(1).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    //    检查版本
    public void checkversion() {
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    NewsModel.this.OnMessageResponse(url, jo, status);
                } catch (JSONException mE) {
                    mE.printStackTrace();
                }
            }

        };
        String mVersionName="1.0.0";

        PackageManager mPackageManager = mContext.getPackageManager();
        try {
            PackageInfo mPackageInfo = mPackageManager.getPackageInfo(mContext.getPackageName()
                    , PackageManager.GET_CONFIGURATIONS);
            mVersionName = mPackageInfo.versionName;
        } catch (Exception mE) {
            mE.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("version", mVersionName + "");
        params.put("client_type", "2");
        cb.url(ApiInterface.CHECK_VERSION).method(1).type(JSONObject.class).params(params);
        aq.ajax(cb);
    }
}
