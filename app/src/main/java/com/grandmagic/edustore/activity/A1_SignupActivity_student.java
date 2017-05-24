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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.ActivityStackManager;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.fragment.E0_ProfileFragment;
import com.grandmagic.edustore.model.RegisterModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.FIELD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class A1_SignupActivity_student extends BaseActivity implements OnClickListener, BusinessResponse {
    public static final int REQUEST_ADDRES = 0x100;
    public static final int REQUEST_GRADE = 0x101;
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

    // create by lps
    private TextView tv_shcool_address, tv_stu_school, tv_grade;
    private LinearLayout lin_stu_address, lin_stu_school, lin_grade;
    private EditText et_class;
    private String country_id;
    private String province_id;
    private String city_id;
    private String county_id;
    private String school_id;
    private String grade_id;
    int type;
private String student_school;
private String student_grade;
private String student_class;
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
        lin_stu_address = (LinearLayout) findViewById(R.id.ll_signup_student_step_two_school_address);
        lin_stu_school = (LinearLayout) findViewById(R.id.ll_signup_student_step_two_school_grade);
        body = (LinearLayout) findViewById(R.id.register_body);
        tv_shcool_address = (TextView) findViewById(R.id.tv_signup_teacher_step_two_address);
        tv_stu_school = (TextView) findViewById(R.id.tv_signup_student_step_two_grade);
        tv_grade = (TextView) findViewById(R.id.tv_grade);
        lin_grade = (LinearLayout) findViewById(R.id.lin_grade);
        et_class= (EditText) findViewById(R.id.et_class);
        lin_grade.setOnClickListener(this);
        back.setOnClickListener(this);
        register.setOnClickListener(this);
        lin_stu_school.setOnClickListener(this);
        registerModel = new RegisterModel(this);
        registerModel.addResponseListener(this);
        lin_stu_address.setOnClickListener(this);
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
                    ToastView toast = new ToastView(this, resource.getString(R.string.nickname_too_short));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (name.length() > 20) {
                    ToastView toast = new ToastView(this, resource.getString(R.string.nickname_too_long));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
//                } else if ("".equals(mail)) {
//                    ToastView toast = new ToastView(this, email);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                } else if ("".equals(passwordStr)) {
                    ToastView toast = new ToastView(this, pass);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (passwordStr.length() < 6) {
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
//                    新加的判断，学生新增了选择学校年级班级的参数
                }else if (TextUtils.isEmpty(et_class.getText())){
                    ToastView toast = new ToastView(this, "请输入所在的班级");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();}
                else if (TextUtils.isEmpty(grade_id)){
                    ToastView toast = new ToastView(this, "请选择年级");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (TextUtils.isEmpty(school_id)) {
                    ToastView toast = new ToastView(this, "请选择学校");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }   else if (!passwordStr.equals(passwordRepeatStr)) {
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
                        } else {
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
            case R.id.ll_signup_student_step_two_school_address:
                Intent intent = new Intent(A1_SignupActivity_student.this, F3_RegionPickActivity.class);
                startActivityForResult(intent, REQUEST_ADDRES);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
                break;
            case R.id.ll_signup_student_step_two_school_grade:
                if (TextUtils.isEmpty(county_id)) {
                    new ToastView(A1_SignupActivity_student.this, "请先选择地区再选择学校");
                    return;
                }
                Intent mIntent = new Intent(A1_SignupActivity_student.this, GradePickActicity.class);
                mIntent.putExtra(GradePickActicity.SCHOOL_REGION, county_id);
                mIntent.putExtra(GradePickActicity.TYPE, 0);
                type = 0;
                startActivityForResult(mIntent, REQUEST_GRADE);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
                break;
            case R.id.lin_grade:
                Intent mIntent1 = new Intent(A1_SignupActivity_student.this, GradePickActicity.class);
                mIntent1.putExtra(GradePickActicity.SCHOOL_REGION, county_id);
                mIntent1.putExtra(GradePickActicity.TYPE, 1);
                type = 1;
                startActivityForResult(mIntent1, REQUEST_GRADE);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
                break;
        }

    }

    public void signup() {

        if (flag) {
            CloseKeyBoard(); //关闭键盘
            registerModel.signup(name, passwordStr, mail, fields, phoneNumber,school_id,grade_id,et_class.getText().toString());

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
                E0_ProfileFragment.isRefresh = true;
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADDRES && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                country_id = data.getStringExtra("country_id");
                province_id = data.getStringExtra("province_id");
                city_id = data.getStringExtra("city_id");
                county_id = data.getStringExtra("county_id");

                StringBuffer sbf = new StringBuffer();
                sbf.append(data.getStringExtra("country_name") + " ");
                sbf.append(data.getStringExtra("province_name") + " ");
                sbf.append(data.getStringExtra("city_name") + " ");
                sbf.append(data.getStringExtra("county_name"));
                tv_shcool_address.setText(sbf.toString());
                if (!TextUtils.isEmpty(school_id)) {//选过学校之后再次选择地区的时候重置学校信息
                    tv_stu_school.setText("学校");
                    school_id="";
                }
            }
        } else if (requestCode == REQUEST_GRADE && resultCode == RESULT_OK) {
            if (type == 0) {
                StringBuffer sbf = new StringBuffer();
                sbf.append(data.getStringExtra(GradePickActicity.SCHOOL) + " ");
                school_id=data.getStringExtra(GradePickActicity.SCHOOL_ID);
                tv_stu_school.setText(sbf);
            } else if (type == 1) {
                tv_grade.setText(data.getStringExtra(GradePickActicity.GRADE) + " ");
                grade_id=data.getStringExtra(GradePickActicity.GRADE_ID);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
