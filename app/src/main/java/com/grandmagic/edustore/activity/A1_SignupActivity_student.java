package com.grandmagic.edustore.activity;

//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//注册 -学生注册

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;
import android.text.InputType;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.edustore.ActivityStackManager;
import com.grandmagic.edustore.fragment.E0_ProfileFragment;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.FIELD;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.edustore.R;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.model.RegisterModel;

public class A1_SignupActivity_student extends BaseActivity implements OnClickListener, BusinessResponse {

    private ImageView back;
    private Button register;

    private EditText userName;
    private EditText email;
    private EditText password;
    private EditText passwordRepeat;

    private LinearLayout body;

    private String name;
    private String mail;
    private String passwordStr;
    private String passwordRepeatStr;

    private RegisterModel registerModel;

    private ArrayList<String> items = new ArrayList<String>();

    public static Map<Integer, EditText> edit;
    private ArrayList<FIELD> fields = new ArrayList<FIELD>();

    private boolean flag = true;

    private String phoneNumber;

    Resources resource;

    ActivityStackManager mActivityStackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_signup_student);

        Intent mIntent = this.getIntent();
        phoneNumber = mIntent.getStringExtra("phonenumber");

        resource = (Resources) getBaseContext().getResources();

        back = (ImageView) findViewById(R.id.register_back);
        register = (Button) findViewById(R.id.register_register);
        userName = (EditText) findViewById(R.id.register_name);
//        email = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password1);
        passwordRepeat = (EditText) findViewById(R.id.register_password2);

        body = (LinearLayout) findViewById(R.id.register_body);

        back.setOnClickListener(this);
        register.setOnClickListener(this);

        registerModel = new RegisterModel(this);
        registerModel.addResponseListener(this);
        registerModel.signupFields();

        mActivityStackManager = ActivityStackManager.getInstance();
        mActivityStackManager.pushOneActivity(this);

    }

    //动态添加输入框
    public void signupFields() {
        edit = new HashMap<Integer, EditText>();

        if (registerModel.signupfiledslist.size() > 0) {
            body.setVisibility(View.VISIBLE);
            for (int i = 0; i < registerModel.signupfiledslist.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.a1_register_item, null);
                EditText goods_name = (EditText) view.findViewById(R.id.register_item_edit);
                String nonull = resource.getString(R.string.not_null);

                if (registerModel.signupfiledslist.get(i).need.equals("1")) { //判断是否是必填
                    goods_name.setHint(registerModel.signupfiledslist.get(i).name + nonull);
                } else {
                    goods_name.setHint(registerModel.signupfiledslist.get(i).name);
                }
                if (registerModel.signupfiledslist.get(i).name.equals("MSN")) {
                    goods_name.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    goods_name.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                View line = view.findViewById(R.id.register_item_line);
                if (i == registerModel.signupfiledslist.size() - 1) {
                    line.setVisibility(View.GONE);
                }
                edit.put(i, goods_name);
                body.addView(view);
            }
        } else {
            body.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back:
                mActivityStackManager.popOneActivity(this);
                //finish();
                break;
            case R.id.register_register:
                name = userName.getText().toString();
//                mail = email.getText().toString();
                passwordStr = password.getText().toString();
                passwordRepeatStr = passwordRepeat.getText().toString();

                String user = resource.getString(R.string.user_name_cannot_be_empty);
//                String email = resource.getString(R.string.email_cannot_be_empty);
                String pass = resource.getString(R.string.password_cannot_be_empty);
                String fault = resource.getString(R.string.fault);
                String passw = resource.getString(R.string.password_not_match);
                String req = resource.getString(R.string.required_cannot_be_empty);

                if ("".equals(name)) {
                    ToastView toast = new ToastView(this, user);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (name.length() < 2) {
                    ToastView toast = new ToastView(this, resource.getString(R.string.username_too_short));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (name.length() > 20) {
                    ToastView toast = new ToastView(this, resource.getString(R.string.username_too_long));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
//                } else if ("".equals(mail)) {
//                    ToastView toast = new ToastView(this, email);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                }else if ("".equals(passwordStr)) {
                    ToastView toast = new ToastView(this, pass);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }  else if (passwordStr.length() < 6) {
                    ToastView toast = new ToastView(this, resource.getString(R.string.password_too_short));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (passwordStr.length() > 20) {
                    ToastView toast = new ToastView(this, resource.getString(R.string.password_too_long));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
//                } else if (!ReflectionUtils.isEmail(mail)) {
//                    ToastView toast = new ToastView(this, fault);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                } else if (!passwordStr.equals(passwordRepeatStr)) {
                    ToastView toast = new ToastView(this, passw);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    StringBuffer sbf = new StringBuffer();
                    for (int i = 0; i < registerModel.signupfiledslist.size(); ++i) {
                        if (registerModel.signupfiledslist.get(i).need.equals("1") && edit.get(i).getText().toString().equals("")) {
                            ToastView toast = new ToastView(this, req);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            flag = false;
                            break;
                        }else{
                            flag = true;
                        }
                        items.add(edit.get(i).getText().toString());
                        sbf.append(edit.get(i).getText().toString() + "/");

                        FIELD field = new FIELD();
                        field.id = Integer.parseInt(registerModel.signupfiledslist.get(i).id);
                        field.value = edit.get(i).getText().toString();
                        fields.add(field);
                    }

                    signup();

                }
                break;
        }

    }

    public void signup() {

        if (flag) {
            CloseKeyBoard(); //关闭键盘
            registerModel.signup(name, passwordStr, mail, fields, phoneNumber);

        }

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        if (registerModel.responseStatus.succeed == 1) {
            if (url.endsWith(ApiInterface.USER_SIGNUPFIELDS)) {
                signupFields();
            } else if (url.endsWith(ApiInterface.USER_SIGNUP)) {
//                Intent intent = new Intent();
//                intent.putExtra("login", true);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
                mActivityStackManager.finishAllActivity();
                E0_ProfileFragment.isRefresh=true;
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                String wel = resource.getString(R.string.welcome);
                ToastView toast = new ToastView(this, wel);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }

    }

    // 关闭键盘
    public void CloseKeyBoard() {
        userName.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityStackManager.popOneActivity(this);
    }
}
