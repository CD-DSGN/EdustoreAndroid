package com.grandmagic.edustore.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.F3_RegionPickAdapter;
import com.grandmagic.edustore.adapter.GradePickAdapter;
import com.grandmagic.edustore.adapter.SchoolPickAdapter;
import com.grandmagic.edustore.model.SchoolModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.SchoolResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class GradePickActicity extends Activity implements BusinessResponse {
    public static final String SCHOOL_REGION="region_id";
    public static final String TYPE="type";
    public static final String SCHOOL="school";
    public static final String SCHOOL_ID="school_id";
    public static final String GRADE="grade";
    public static final String GRADE_ID="grade_id";
    String region;
    String school;
    String grade;
TextView tv_title;
    ListView mListView;
    SchoolModel mModel;
    int i;//标志位，默认进入activity为0 ，请求到学校为1，请求到年级为2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_pick_acticity);
        initview();
        initdata();
    }

    private void initdata() {
        i=getIntent().getIntExtra("type",0);
        region=getIntent().getStringExtra(SCHOOL_REGION);
        mModel=new SchoolModel(this);
        if (i==0) {
            mModel.getSchool(region);
        }else if (i==1){
            mModel.getgrade();
        }
        mModel.addResponseListener(this);
    }

    private void initview() {
        tv_title= (TextView) findViewById(R.id.address_title);
        mListView= (ListView) findViewById(R.id.school_list);
mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
if (i==0){//请求学校
    school=mModel.mList.get(position).getSchool_name();
   String school_id=mModel.mList.get(position).getSchool_id();
    Intent mIntent=new Intent();
    mIntent.putExtra(SCHOOL,school);
    mIntent.putExtra(SCHOOL_ID,school_id);
    setResult(RESULT_OK,mIntent);
    finish();
}else if (i==1){
    grade=mModel.mGradeList.get(position).getGrade_name();
    String grade_id=mModel.mGradeList.get(position).getGrade_id();
    Intent mIntent=new Intent();
    mIntent.putExtra(GRADE,grade);
    mIntent.putExtra(GRADE_ID,grade_id);
    setResult(RESULT_OK,mIntent);
    finish();
}
    }
});
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
if (!url.isEmpty()&&url.endsWith(ApiInterface.SCHOOL_LIST)){
setSchollList();
}else if (!url.isEmpty()&&url.endsWith(ApiInterface.GRADE_LIST)){
setGradeLIst();
}
    }

    private void setGradeLIst() {
      mListView.setAdapter(new GradePickAdapter(this,mModel.mGradeList));
    }
    SchoolPickAdapter mSchoolPickAdapter;

    private void setSchollList() {
        mSchoolPickAdapter = new SchoolPickAdapter(this, mModel.mList);
        mListView.setAdapter(mSchoolPickAdapter);
    }
}
