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
//注册

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.ActivityStackManager;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.model.LoginModel;
import com.grandmagic.edustore.protocol.ApiInterface;

public class A0_SigninActivity extends BaseActivity implements OnClickListener, BusinessResponse {
	
	private ImageView back;
	private Button login;
	
	private EditText userName;
	private EditText password;
	private TextView register;
	
	//zhangmengqi end
	private TextView register_teacher;
	private TextView register_student;
	//zhangmengqi begin
	private String name;
	private String psd;
	
	private LoginModel loginModel;
    //private final static int REQUEST_SIGN_UP = 1;
	//private final static int REQUEST_SIGN_UP_TEACHER = 2;
	//private final static int REQUEST_SIGN_UP_STUDENT = 3;

	private ActivityStackManager mActivityStackManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a0_signin);
		
		back = (ImageView) findViewById(R.id.login_back);
		login = (Button) findViewById(R.id.login_login);
		userName = (EditText) findViewById(R.id.login_name);
		password = (EditText) findViewById(R.id.login_password);
//		register = (TextView) findViewById(R.id.login_register);
		register_teacher = (TextView) findViewById(R.id.login_register_teacher);
		register_student = (TextView) findViewById(R.id.login_register_student);
//        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        register_student.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        register_teacher.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		back.setOnClickListener(this);
		login.setOnClickListener(this);
//		register.setOnClickListener(this);
		register_teacher.setOnClickListener(this);
		register_student.setOnClickListener(this);

		mActivityStackManager = ActivityStackManager.getInstance();
		mActivityStackManager.pushOneActivity(this);
	}

	@Override
	public void onClick(View v) {		
        Resources resource = (Resources) getBaseContext().getResources();
        String usern=resource.getString(R.string.user_name_cannot_be_empty);
        String pass=resource.getString(R.string.password_cannot_be_empty);
		Intent intent;
		switch(v.getId()) {
		case R.id.login_back:
			mActivityStackManager.popOneActivity(this);
			//finish();
			CloseKeyBoard();
			overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
			break;
		case R.id.login_login:
			name = userName.getText().toString();
			psd = password.getText().toString();
            if(name.length()<2){
                ToastView toast = new ToastView(this, resource.getString(R.string.username_too_short));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            if(name.length()>20){
                ToastView toast = new ToastView(this, resource.getString(R.string.username_too_long));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            if(psd.length()<6){
                ToastView toast = new ToastView(this, resource.getString(R.string.password_too_short));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            if(psd.length()>20){
                ToastView toast = new ToastView(this, resource.getString(R.string.password_too_long));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
			if("".equals(name)) {				
				ToastView toast = new ToastView(this, usern);
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.show();
			} else if("".equals(psd)) {				
				ToastView toast = new ToastView(this, pass);
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.show();
			} else {
				loginModel = new LoginModel(A0_SigninActivity.this);
				loginModel.addResponseListener(this);
				loginModel.login(name, psd);
				CloseKeyBoard();
				
			}
			break;
		/*case R.id.login_register:
			intent = new Intent(this, A1_SignupActivity_student.class);
			startActivityForResult(intent, REQUEST_SIGN_UP);
			break;*/
		//学生注册
		case R.id.login_register_student:
			//intent = new Intent(this, A1_SignupActivity_student.class);
			//startActivityForResult(intent, REQUEST_SIGN_UP_STUDENT);
			//intent = new Intent(this, A1_SignupActivity_student.class);
			//startActivityForResult(intent, REQUEST_SIGN_UP);
			intent = new Intent(this, A1_01_SignupInputPhoneNumActivity.class);
			intent.putExtra("is_teacher",false);
			startActivity(intent);
			break;
		//教师注册
		case R.id.login_register_teacher:
			//intent = new Intent(this, A1_SignupActivity_teacher_step_one.class);
			intent = new Intent(this, A1_01_SignupInputPhoneNumActivity.class);
			intent.putExtra("is_teacher",true);
			startActivity(intent);
			break;
		}
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if(loginModel.responseStatus.succeed == 1) {
			if(url.endsWith(ApiInterface.USER_SIGNIN)) {
//				Intent intent = new Intent();
//				intent.putExtra("login", true);
//				setResult(Activity.RESULT_OK, intent);
//	            finish();
				mActivityStackManager.popOneActivity(this);
	            overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
			}
		}
		
		
	}

	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_SIGN_UP_TEACHER || requestCode == REQUEST_SIGN_UP_TEACHER) {
			if(data!=null) {
				*//*deleted,其他地方没有startActivityForResult来启动A0_SigninActivity
				Intent intent = new Intent();
				intent.putExtra("login", true);
				setResult(Activity.RESULT_OK, intent);*//*
	            finish();
                E0_ProfileFragment.isRefresh=true;
	            overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
    		}
		}

	}*/

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
		}
		return true;
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
