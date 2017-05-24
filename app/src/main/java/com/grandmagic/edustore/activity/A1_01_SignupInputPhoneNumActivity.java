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
import android.widget.LinearLayout;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenggaoyuan on 2016/8/22. 注册时候输入手机号
 */
public class A1_01_SignupInputPhoneNumActivity extends BaseActivity implements BusinessResponse{

    private ImageView backB;

    private EditText EditTextPhoneNum;

    private Button getVerification;

    private String mobilePhone;

    RegisterPhoneNumCheckModel mRegisterPNCModel;

    Resources resource;

    private boolean is_teacher;

    private View dividor;

    private LinearLayout ll_invitation_code;

    ActivityStackManager mActivityStackManager;

    private String invitation_code_str; //邀请码字符串

    private EditText et_invitation_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_01_signup_input_phone_number);

        Intent mIntent = this.getIntent();
        is_teacher = mIntent.getBooleanExtra("is_teacher",false);

        dividor = findViewById(R.id.view_dividor);

        ll_invitation_code = (LinearLayout) findViewById(R.id.ll_invitation_code);

        et_invitation_code = (EditText) findViewById(R.id.et_invitation_code);

        if (is_teacher) {
            //显示邀请码项
            showOrHideInvitationCode(true);
        }else{
            //隐藏注册码项
            showOrHideInvitationCode(false);
        }

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
                if(!judgePhoneNums(mobilePhone)){
                    String pnff = resource.getString(R.string.phone_number_format_false);
                    showToast(pnff);
                }

                invitation_code_str = et_invitation_code.getText().toString();
                if (!TextUtils.isEmpty(invitation_code_str)) {
                    //将小写全部替换为大写
                    invitation_code_str = invitation_code_str.toUpperCase();
                    //判断字符串是否包含非法字符
                    if (invitationCodeContainsInvalidChars(invitation_code_str)) {
                        showToast(getResources().getString(R.string.invalid_invitation_code));
                    }
                }

                mRegisterPNCModel.sendPhoneNumToServer(mobilePhone);

            }
        });

        mActivityStackManager = ActivityStackManager.getInstance();
        mActivityStackManager.pushOneActivity(this);
    }

    private void showToast(String pnff) {
        ToastView toast = new ToastView(A1_01_SignupInputPhoneNumActivity.this, pnff);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showOrHideInvitationCode(boolean b) {
        if (b) {
            dividor.setVisibility(View.VISIBLE);
            ll_invitation_code.setVisibility(View.VISIBLE);
        }else{
            dividor.setVisibility(View.GONE);
        }
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
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
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
                mIntent.putExtra("invitation_code", invitation_code_str);
                startActivity(mIntent);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityStackManager.popOneActivity(this);
    }


    //本地检查邀请码是否包含非法字符
    private boolean invitationCodeContainsInvalidChars(String str) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
        Matcher matcher = pattern.matcher(str);
        return !(matcher.matches());
    }
}
