package com.grandmagic.edustore.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static u.aly.av.T;

/**
 * Created by chenggaoyuan on 2016/8/25. 注册时提交验证码
 */
public class A1_02_SignupCheckCodeActivity extends BaseActivity implements BusinessResponse {

    ImageView backB;

    EditText mEditTextVeriCode;

    Button reSendSms;

    Button nextStep;

    Resources resource;

    TimeCount mTimeCount;

    RegisterPhoneNumCheckModel mRegisterPNCModel;

    String phoneNumber;

    Boolean is_teacher;

    String CodeText;

    SmsObserver mSmsObserver;

    SharedPreferences mSharedPreferences;

    String code;

    ActivityStackManager mActivityStackManager;

    private String invitation_code;

    Handler obHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityStackManager = ActivityStackManager.getInstance();
        mActivityStackManager.pushOneActivity(this);

        setContentView(R.layout.a1_02_singup_input_vercification_code);

        backB = (ImageView) findViewById(R.id.register_back);

        mEditTextVeriCode = (EditText) findViewById(R.id.verification_number);

        reSendSms = (Button) findViewById(R.id.get_verification_code_again);

        nextStep = (Button) findViewById(R.id.next_step);

        resource = getBaseContext().getResources();

        Intent mIntent = this.getIntent();
        phoneNumber = mIntent.getStringExtra("phonenumber");
        is_teacher = mIntent.getBooleanExtra("is_teacher",false);
        invitation_code = mIntent.getStringExtra("invitation_code");

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityStackManager.popOneActivity(A1_02_SignupCheckCodeActivity.this);
            }
        });

        reSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterPNCModel = new RegisterPhoneNumCheckModel(A1_02_SignupCheckCodeActivity.this);
                mRegisterPNCModel.addResponseListener(A1_02_SignupCheckCodeActivity.this);
                mRegisterPNCModel.sendPhoneNumToServer(phoneNumber);
                mTimeCount.start();
            }
        });
        /**
         * 第一个参数表示总时间，第二个参数表示间隔时间
         * 单位：millisecond
         */
        mTimeCount = new TimeCount(60000,1000);
        mTimeCount.start();

        mSmsObserver = new SmsObserver(obHandler);
        getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, mSmsObserver);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharedPreferences = getSharedPreferences("veri_code", Activity.MODE_PRIVATE);
                code = mSharedPreferences.getString("code", "");
                if(code != null && phoneNumber!=null){
                    if(mEditTextVeriCode.getText().toString().equals(code)){
                        if(is_teacher==false){
                            Intent mIntent = new Intent(A1_02_SignupCheckCodeActivity.this, A1_SignupActivity_student.class);
                            mIntent.putExtra("phonenumber",phoneNumber);
                            startActivity(mIntent);
                        }else if(is_teacher==true){
                            Intent mIntent = new Intent(A1_02_SignupCheckCodeActivity.this, A1_SignupActivity_teacher.class);
                            mIntent.putExtra("phonenumber",phoneNumber);
                            if (invitation_code != null) {
                                mIntent.putExtra("invitation_code", invitation_code);
                            }
                            startActivity(mIntent);
                        }
                    }else{
                        String vw = resource.getString(R.string.verification_wrong);
                        ToastView toast = new ToastView(A1_02_SignupCheckCodeActivity.this, vw);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }

    class TimeCount extends CountDownTimer {

        String sr = resource.getString(R.string.seconds_resend);
        String gvca = resource.getString(R.string.get_verification_code_again);

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            reSendSms.setBackgroundColor(resource.getColor(R.color.default_register_button_color));
            reSendSms.setClickable(false);
            reSendSms.setText(millisUntilFinished / 1000 + sr);
        }

        @Override
        public void onFinish() {

            reSendSms.setText(gvca);
            reSendSms.setClickable(true);
            reSendSms.setBackgroundColor(resource.getColor(R.color.default_register_button_color));

        }
    }

    private class SmsObserver extends ContentObserver {

        public SmsObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }
        /**
         *Uri.parse("content://sms/inbox")表示对收到的短信的一个监听的uri.
         */
        @Override
        public void onChange(boolean selfChange) {
            // TODO Auto-generated method stub
            StringBuilder sb = new StringBuilder();
            Cursor cursor = null;

            try {
                cursor = getContentResolver().query(
                        Uri.parse("content://sms/inbox"), null, null, null, null);
                //这里不要使用while循环.我们只需要获取当前发送过来的短信数据就可以了.
                if (cursor != null) {
                    cursor.moveToNext();
                    int columnIndex = cursor.getColumnIndex("body");
                    if (columnIndex >= 0) {
                        sb.append("body=" + cursor.getString(columnIndex)); //获取短信内容的实体数据.
                        //Pattern pattern = Pattern.compile("[^0-9]"); //正则表达式.
                        //Matcher matcher = pattern.matcher(sb.toString());
                        //CodeText = matcher.replaceAll("");
                        CodeText=getVeriCodeFromSms(sb,4);
                        if (!TextUtils.isEmpty(CodeText)) {
                            mEditTextVeriCode.setText(CodeText); //将输入验证码的控件内容进行改变.
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close(); //关闭游标指针.
                }
            }
            super.onChange(selfChange);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mSmsObserver);
        mActivityStackManager.popOneActivity(A1_02_SignupCheckCodeActivity.this);
    };

    /**
     * 从短信字符窜提取验证码
     */
    public static String getVeriCodeFromSms(StringBuilder body, int code_length) {
        // 首先([a-zA-Z0-9]{YZMLENGTH})是得到一个连续的六位数字字母组合
        // (?<![a-zA-Z0-9])负向断言([0-9]{YZMLENGTH})前面不能有数字
        // (?![a-zA-Z0-9])断言([0-9]{YZMLENGTH})后面不能有数字出现

//  获得数字字母组合
//    Pattern p = Pattern   .compile("(?<![a-zA-Z0-9])([a-zA-Z0-9]{" + YZMLENGTH + "})(?![a-zA-Z0-9])");

//  获得纯数字
        Pattern p = Pattern.compile("(?<![0-9])([0-9]{" + code_length+ "})(?![0-9])");

        Matcher m = p.matcher(body);
        if (m.find()) {
            //Log.d("chenggaoyuan",m.group());
            //System.out.println(m.group());
            return m.group(0);
        }
        return null;
    }

}
