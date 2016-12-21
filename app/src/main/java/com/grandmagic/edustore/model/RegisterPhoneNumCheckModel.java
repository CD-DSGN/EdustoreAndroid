package com.grandmagic.edustore.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.Gravity;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.ErrorCodeConst;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.STATUS;
import com.grandmagic.edustore.protocol.VerificationCode;
import com.grandmagic.edustore.protocol.usersignupPhoneCheckRequest;
import com.grandmagic.edustore.protocol.usersignupPhoneCheckResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenggaoyuan on 2016/8/23.
 */
public class RegisterPhoneNumCheckModel extends BaseModel {
    public STATUS responseStatus;

    public VerificationCode mVerificationCode;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    public RegisterPhoneNumCheckModel(Context context){

        super(context);
        shared= context.getSharedPreferences("veri_code", Activity.MODE_PRIVATE);
        editor = shared.edit();
    }

    public void sendPhoneNumToServer(String phonenum)
    {
        usersignupPhoneCheckRequest musersignupPCRequest = new usersignupPhoneCheckRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                RegisterPhoneNumCheckModel.this.callback(url, jo, status);
                usersignupPhoneCheckResponse musersingupPCResponse = new usersignupPhoneCheckResponse();
                try {
                    musersingupPCResponse.fromJson(jo);
                    if(jo != null){
                        responseStatus=musersingupPCResponse.status;
                        mVerificationCode=musersingupPCResponse.mVCodeCls;
                        if(responseStatus.succeed == 1){
                            //手机号符合规则处理逻辑
                            Resources resource = mContext.getResources();
                            String verification_code_sended = resource.getString(R.string.verification_code_sended);
                            showToast(verification_code_sended);
                            String code = mVerificationCode.veri_code;
                            editor.putString("code", code);
                            editor.commit();

                        }else if (responseStatus.error_code == ErrorCodeConst.PhoneNumberExist){
                            //手机号码已经被注册
                            Resources resource = mContext.getResources();
                            String phone_number_exists = resource.getString(R.string.phone_number_exists);
                            showToast(phone_number_exists);
                        } else if (responseStatus.error_code == ErrorCodeConst.InvalidInvitation_CODE) {
                            Resources resource = mContext.getResources();
                            String phone_number_exists = resource.getString(R.string.invitation_code_invalid);
                            showToast(phone_number_exists);
                        }
                        //使A1_SignupInputPhoneNumActivity中的回调函数OnMessageResponse发生作用
                        RegisterPhoneNumCheckModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        musersignupPCRequest.phonenum = phonenum;

        Map<String, String> params =new HashMap<String, String>();
        try {
            params.put("json", musersignupPCRequest.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.USER_SINGUP_CHECK_PHONE).type(JSONObject.class).params(params);
        //MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        //aq.progress(pd.mDialog).ajax(cb);
        aq.ajax(cb);

    }

    private void showToast(String msg) {
        ToastView toast = new ToastView(mContext, msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
