package com.grandmagic.edustore.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.CourseAdapter;
import com.grandmagic.edustore.model.RegisterModel_teacher;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.SIGNUPCOURSES;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectCourseActivity extends Activity implements BusinessResponse {
	public static final String COURSE_TAG="course_tag";
	int tag=-1;
	private TextView title;
	private ListView listView;
	private CourseAdapter courseAdapter;
	private RegisterModel_teacher register_model_teacher;
	private ArrayList<String> course_strs = new ArrayList<String>();
	private HashMap<String, String> course_id_name = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_course);

		initViews();
        Resources resource = (Resources) getBaseContext().getResources();
        String select_course =resource.getString(R.string.please_select_course);
        title.setText(select_course);
		
		register_model_teacher = new RegisterModel_teacher(this);
		register_model_teacher.addResponseListener(this);
		register_model_teacher.signupCourses();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				String course_name = course_strs.get(position);
				String course_id = course_id_name.get(course_name);
				Intent intent = new Intent();
				intent.putExtra("course_name", course_name);
				intent.putExtra("course_id", course_id);
				intent.putExtra(COURSE_TAG, tag);
				setResult(Activity.RESULT_OK, intent);
				finish();

            }
        });
	}

	private void initViews() {
		title = (TextView) findViewById(R.id.tv_select_course);
		listView = (ListView) findViewById(R.id.lv_select_course);
		tag=getIntent().getIntExtra(COURSE_TAG,-1);
	}

	public void setCourses() {
		courseAdapter = new CourseAdapter(this,course_strs);
		listView.setAdapter(courseAdapter);
	}
	
	
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		// TODO Auto-generated method stub

		if (register_model_teacher.responseStatus.succeed == 1) {   //请求网络数据成功
			if(url.endsWith(ApiInterface.USER_SIGNUPCOURSES)) {
				for(int i = 0; i < register_model_teacher.signupcourseslist.size(); i++) {
					SIGNUPCOURSES course_item = register_model_teacher.signupcourseslist.get(i);
					course_strs.add(course_item.course_name);
					course_id_name.put(course_item.course_name, course_item.course_id);
				}
                setCourses();
            }
		}
	}

	private String getCourseIDbyName(String name) {
		if (name == null) {
			return null;
		}
		String course_id = course_id_name.get(name);
		return course_id;
	}

}
