package com.grandmagic.edustore.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.ActivityStackManager;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.model.RegisterPhoneNumCheckModel;
import com.grandmagic.edustore.protocol.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/8/22.
 */
public class A1_01_SignupInputPhoneNumActivity extends BaseActivity implements BusinessResponse{

    private ImageView backB;

    private EditText EditTextPhoneNum;

    private Button getVerification;

    private String mobilePhone;

    RegisterPhoneNumCheckModel mRegisterPNCModel;

    Resources resource;

    private boolean is_teacher;

    ActivityStackManager mActivityStackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_01_signup_input_phone_number);

        Intent mIntent = this.getIntent();
        is_teacher = mIntent.getBooleanExtra("is_teacher",false);



        Log.d("chenggaoyuan", String.valueOf(is_teacher));
        resource = getBaseContext().getResources();
        backB = (ImageView) findViewById(R.id.register_back);
        EditTextPhoneNum=(EditText) findViewById(R.id.mobilephone_number);
        getVerification=(Button) findViewById(R.id.get_verification_code);

        mRegisterPNCModel = new RegisterPhoneNumCheckModel(this);
        mRegisterPNCModel.addResponseListener(this);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityStackManager.popOneActivity(A1_01_SignupInputPhoneNumActivity.this);
            }
        });

        getVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobilePhone = EditTextPhoneNum.getText().toString();
                if(judgePhoneNums(mobilePhone) == true){
                //将手机号发送到服务器校验
                    mRegisterPNCModel.sendPhoneNumToServer(mobilePhone);
                }else{
                    String pnff = resource.getString(R.string.phone_number_format_false);
                    ToastView toast = new ToastView(A1_01_SignupInputPhoneNumActivity.this, pnff);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        mActivityStackManager = ActivityStackManager.getInstance();
        mActivityStackManager.pushOneActivity(this);
    }

    /**
     * 本想用TextWatcher监听EditText输入，未果
     * @param phoneNums
     * @return
     */
    /*private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.d("TAG","afterTextChanged--------------->");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
            Log.d("TAG","beforeTextChanged--------------->");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("TAG","onTextChanged--------------->");
            mobilePhone = EditTextphoneNum.getText().toString();
            if(judgePhoneNums(mobilePhone) == true){
                getVerification.setEnabled(true);
            }

        }
    };*/

    // 判断手机号码是否合理
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        return false;
    }

    //判断一个字符串的位数
    public static boolean isMatchLength(String str, int length) {
        if (str.length() <= 0) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    //验证手机格式
    public static boolean isMobileNO(String mobileNums) {
    /*
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums)) {
            return false;
        }
        else {
            return mobileNums.matches(telRegex);
        }
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (mRegisterPNCModel.responseStatus.succeed == 1) {
            if (url.endsWith(ApiInterface.USER_SINGUP_CHECK_PHONE)) {
                Intent mIntent = new Intent(A1_01_SignupInputPhoneNumActivity.this, A1_02_SignupCheckCodeActivity.class);
                mobilePhone = EditTextPhoneNum.getText().toString();
                mIntent.putExtra("phonenumber",mobilePhone);
                mIntent.putExtra("is_teacher",is_teacher);
                startActivity(mIntent);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityStackManager.popOneActivity(this);
    }
}
