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
//注册 -教师注册

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.ActivityStackManager;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.fragment.E0_ProfileFragment;
import com.grandmagic.edustore.model.RegisterModel;
import com.grandmagic.edustore.model.RegisterModel_teacher;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.FIELD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class A1_SignupActivity_teacher extends BaseActivity implements View.OnClickListener, BusinessResponse {
    private static final int REQUEST_GRADE = 0x110;
    private static final int REQUEST_SCHOOL = 0x111;
    private ImageView back;
    private Button register;

    private EditText password;
    private EditText passwordRepeat;

    private String name;
    private String mobile_phone;
    private String passwordStr;
    private String passwordRepeatStr;

    private String school_str; //学校
    private String course_str; //课程
    private String real_name_str; //真实姓名
    private String course_id;
    private String invitation_code_str; //邀请码字符串


    private HashMap<String, String> course_id_name = new HashMap<String, String>();

    private RegisterModel registerModel;
    private RegisterModel_teacher register_model_teacher;

    private ArrayList<String> items = new ArrayList<String>();

    public static Map<Integer, EditText> edit;
    private ArrayList<FIELD> fields = new ArrayList<FIELD>();

    private boolean flag = true;


    Resources resource;

    private EditText et_real_name;
    private EditText et_school;
    private TextView tv_courses;
    private int select_item = 0;
    private String[] course_strs;
    private LinearLayout ll_school_address;
    private LinearLayout ll_course;
    private TextView tv_shcool_address;
    private String country_id;
    private String province_id;
    private String city_id;
    private String county_id;

    private EditText et_invitation_code;

    private ActivityStackManager mActivityStackManager;

    //    lps
    TextView tv_addCourse;
    LinearLayout mrootlinearLayout;
    LinearLayout lin_grade;
    TextView tv_grade;
    LinearLayout lin_school;
TextView tv_school;
    String school_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_signup_teacher);

        resource = (Resources) getBaseContext().getResources();
        initView();

        initListener();

        initData();

        mActivityStackManager = ActivityStackManager.getInstance();
        mActivityStackManager.pushOneActivity(this);
    }


    //获得第一步注册时，填写的用户信息
    private void initData() {
        Intent intent = getIntent();
        //name = intent.getStringExtra("username");
        mobile_phone = intent.getStringExtra("phonenumber");
        invitation_code_str = intent.getStringExtra("invitation_code");
    }

    private void initListener() {
//        tv_courses.setClickable(true);
//        tv_courses.setOnClickListener(this);
        ll_course.setOnClickListener(this);
        register.setOnClickListener(this);
        back.setOnClickListener(this);
        tv_addCourse.setOnClickListener(this);

        register_model_teacher = new RegisterModel_teacher(this);
        register_model_teacher.addResponseListener(this);

        ll_school_address.setOnClickListener(this);
        lin_grade.setOnClickListener(this);
        lin_school.setOnClickListener(this);
    }

    private void initView() {

        et_real_name = (EditText) findViewById(R.id.et_signup_teacher_step_two_real_name); //真实姓名
        et_school = (EditText) findViewById(R.id.et_signup_teacher_step_two_school_name);  //学校姓名

        password = (EditText) findViewById(R.id.et_signup_teacher_step_two_password1);//密码
        passwordRepeat = (EditText) findViewById(R.id.et_signup_teacher_step_two_password2);  //确认密码

        ll_course = (LinearLayout) findViewById(R.id.ll_signup_teacher_step_two_school_course);
        tv_courses = (TextView) findViewById(R.id.tv_signup_teacher_step_two_courses); //课程

        register = (Button) findViewById(R.id.register_register_teacher);  //右上角的注册按钮
        back = (ImageView) findViewById(R.id.register_back_teacher);

        ll_school_address = (LinearLayout) findViewById(R.id.ll_signup_teacher_step_two_school_address); //学校地址的线性布局
        tv_shcool_address = (TextView) findViewById(R.id.tv_signup_teacher_step_two_address);

        tv_addCourse = (TextView) findViewById(R.id.btn_add_course);
        mrootlinearLayout = (LinearLayout) findViewById(R.id.root_course_linearlayout);
        lin_grade = (LinearLayout) findViewById(R.id.lin_grade);
        tv_grade = (TextView) findViewById(R.id.tv_grade);
        lin_school= (LinearLayout) findViewById(R.id.ll_school);
        tv_school= (TextView) findViewById(R.id.tv_school);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back_teacher:
                mActivityStackManager.popOneActivity(this);
                //finish();
                break;
            case R.id.register_register_teacher:
                btn_step_two_register_onClick();
                break;

            case R.id.ll_signup_teacher_step_two_school_course:         //请求课程数据
                Intent intent_select_course = new Intent(A1_SignupActivity_teacher.this,
                        SelectCourseActivity.class);
                startActivityForResult(intent_select_course, 3);
                break;

            case R.id.ll_signup_teacher_step_two_school_address:
                Intent intent = new Intent(A1_SignupActivity_teacher.this, F3_RegionPickActivity.class);
                startActivityForResult(intent, 2);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
                break;
            case R.id.btn_add_course:
                addCourse();
                break;
            case R.id.lin_grade:
                Intent mIntent = new Intent(A1_SignupActivity_teacher.this, GradePickActicity.class);
                mIntent.putExtra(GradePickActicity.TYPE, 1);
                startActivityForResult(mIntent, REQUEST_GRADE);
                break;
            case R.id.ll_school:
                Intent mIntent1= new Intent(A1_SignupActivity_teacher.this, GradePickActicity.class);
                mIntent1.putExtra(GradePickActicity.TYPE, 0);
                mIntent1.putExtra(GradePickActicity.SCHOOL_REGION, county_id);
                startActivityForResult(mIntent1, REQUEST_SCHOOL);
                break;
        }

    }

    int addCourseCount = 0;
    List<TextView> courseNameList = new ArrayList<>();
    List<EditText> classeNameList = new ArrayList<>();
    List<TextView> gradeNameList = new ArrayList<>();
    List<LinearLayout> linCourse = new ArrayList<>();
    List<LinearLayout> linGrade = new ArrayList<>();

    private void addCourse() {
        if (addCourseCount < 4) {
            View mInflate = View.inflate(this, R.layout.view_add_course, null);
            TextView mTextViewcourse = (TextView) mInflate.findViewById(R.id.tv_signup_teacher_step_two_courses);
            TextView mTextViewgrade = (TextView) mInflate.findViewById(R.id.tv_grade);
            EditText mTextViewclass = (EditText) mInflate.findViewById(R.id.et_class);
            LinearLayout lincourse = (LinearLayout) mInflate.findViewById(R.id.ll_signup_teacher_step_two_school_course);
            LinearLayout lingrade = (LinearLayout) mInflate.findViewById(R.id.lin_grade);
            courseNameList.add(mTextViewcourse);
            gradeNameList.add(mTextViewgrade);
            classeNameList.add(mTextViewclass);
            mrootlinearLayout.addView(mInflate);
            addCourseCount++;
            lincourse.setTag(addCourseCount - 1);
            lincourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mTag = (int) v.getTag();
                    Intent intent_select_course = new Intent(A1_SignupActivity_teacher.this,
                            SelectCourseActivity.class);
                    intent_select_course.putExtra(SelectCourseActivity.COURSE_TAG, mTag);
                    startActivityForResult(intent_select_course, 3);
                }
            });
            lingrade.setTag(addCourseCount - 1);
            lingrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mTag = (int) v.getTag();
                    Intent mIntent = new Intent(A1_SignupActivity_teacher.this, GradePickActicity.class);
                    mIntent.putExtra(GradePickActicity.TYPE, 1);
                    mIntent.putExtra(SelectCourseActivity.COURSE_TAG, mTag);
                    startActivityForResult(mIntent, REQUEST_GRADE);
                }
            });
        } else {
            Toast.makeText(this, "最多添加到五门课程", Toast.LENGTH_SHORT).show();
        }
    }

    private void btn_step_two_register_onClick() {
        passwordStr = password.getText().toString();
        passwordRepeatStr = passwordRepeat.getText().toString();

        school_str = et_school.getText().toString();
        course_str = tv_courses.getText().toString();
        real_name_str = et_real_name.getText().toString();


        String user = resource.getString(R.string.user_name_cannot_be_empty);
        String pass = resource.getString(R.string.password_cannot_be_empty);
        String fault = resource.getString(R.string.fault);
        String passw = resource.getString(R.string.password_not_match);
        String req = resource.getString(R.string.required_cannot_be_empty);

        String select_course = resource.getString(R.string.please_select_course);

        if ("".equals(passwordStr)) {
            showToast(pass);
        } else if (passwordStr.length() < 6) {
            showToast(resource.getString(R.string.password_too_short));
        } else if (passwordStr.length() > 20) {
            showToast(resource.getString(R.string.password_too_long));
        } else if (!passwordStr.equals(passwordRepeatStr)) {
            showToast(passw);
        } else if ("".equals(real_name_str)) {
            showToast(getString(R.string.real_name_not_empty));
        } else if (country_id == null || province_id == null || city_id == null || county_id == null) {
            showToast(getString(R.string.school_address_empty));
        } else if ("".equals(school_str)) {
            showToast(getString(R.string.shcool_name_cannot_be_empty));
        } else if (select_course.equals(course_str) || "".equals(course_str)) {
            showToast(select_course);
        } else {
            signup();
        }
    }

    private void showToast(String user) {
        ToastView toast = new ToastView(this, user);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void signup() {
        CloseKeyBoard(); //关闭键盘
        register_model_teacher.signup(passwordStr, mobile_phone, course_id, real_name_str,
                school_str, country_id, province_id, city_id, county_id, invitation_code_str);
    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.USER_SIGNUP_TEACHER)) {
//            Intent intent = new Intent();
//            intent.putExtra("login", true);
//            setResult(Activity.RESULT_OK, intent);
//            finish();
            mActivityStackManager.finishAllActivity();
            E0_ProfileFragment.isRefresh = true;
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            String wel = resource.getString(R.string.welcome);
            showToast(wel);
        }
    }


    //     关闭键盘
    public void CloseKeyBoard() {
        et_real_name.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_real_name.getWindowToken(), 0);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
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
                    tv_school.setText("学校");
                    school_id="";
                }
            }
        }else if (requestCode==REQUEST_SCHOOL&&resultCode==RESULT_OK){
            school_id=data.getStringExtra(GradePickActicity.SCHOOL_ID);
            tv_school.setText(data.getStringExtra(GradePickActicity.SCHOOL));
        }

        else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                switch (data.getIntExtra(SelectCourseActivity.COURSE_TAG, -1)) {
                    case -1:
                        course_str = data.getStringExtra("course_name");
                        course_id = data.getStringExtra("course_id");
                        tv_courses.setText(course_str);
                        break;
                    case 0:
                        courseNameList.get(0).setText(data.getStringExtra("course_name"));
                        break;
                    case 1:
                        courseNameList.get(1).setText(data.getStringExtra("course_name"));
                        break;
                    case 2:
                        courseNameList.get(2).setText(data.getStringExtra("course_name"));
                        break;
                    case 3:
                        courseNameList.get(3).setText(data.getStringExtra("course_name"));
                        break;
                }

            }
        } else if (requestCode == REQUEST_GRADE && resultCode == RESULT_OK) {
            switch (data.getIntExtra(GradePickActicity.COURSE_TAG, -1)) {
                case -1:
                    tv_grade.setText(data.getStringExtra(GradePickActicity.GRADE));
                    break;
                case 0:
                    gradeNameList.get(0).setText(data.getStringExtra(GradePickActicity.GRADE));
                    break;
                case 1:
                    gradeNameList.get(1).setText(data.getStringExtra(GradePickActicity.GRADE));
                    break;
                case 2:
                    gradeNameList.get(2).setText(data.getStringExtra(GradePickActicity.GRADE));
                    break;
                case 3:
                    gradeNameList.get(3).setText(data.getStringExtra(GradePickActicity.GRADE));
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityStackManager.popOneActivity(this);
    }
}
