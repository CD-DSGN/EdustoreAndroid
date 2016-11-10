package com.grandmagic.edustore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.model.TeacherPublishModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.sina.weibo.sdk.api.share.Base;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenggaoyuan on 2016/10/25.
 */
public class Z1_TeacherPublishActivity extends BaseActivity implements OnClickListener,BusinessResponse {

    private ImageView publish_back;
    private EditText publish_content;
    private Button teacher_publish;
    private TeacherPublishModel teacherPublishModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z1_teacher_publish);
        initView();
    }

    private void initView(){
        publish_back = (ImageView) findViewById(R.id.publish_back);
        publish_content = (EditText) findViewById(R.id.teacher_publish_content);
        teacher_publish = (Button) findViewById(R.id.teacher_publish_button);

        publish_back.setOnClickListener(this);
        publish_content.setOnClickListener(this);
        teacher_publish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publish_back:
                finish();
                closeKeyBoard();
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                break;
            case R.id.teacher_publish_button:
                String content = publish_content.getText().toString();

                Resources resource = (Resources) getBaseContext().getResources();

                if("".equals(content)){
                    String con = resource.getString(R.string.publish_content_not_empty);
                    ToastView toast = new ToastView(this, con);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    publish_content.requestFocus();
                }
                teacherPublishModel = new TeacherPublishModel(this);
                teacherPublishModel.addResponseListener(this);
                teacherPublishModel.publish_teacher_message(content);
        }

    }

    public void closeKeyBoard() {
        publish_content.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(publish_content.getWindowToken(), 0);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if(url.endsWith(ApiInterface.TEACHER_PUBLISH)){
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return true;
    }
}
