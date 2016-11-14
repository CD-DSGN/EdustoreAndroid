package com.grandmagic.edustore.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.grandmagic.grandMagicManager.GrandMagicManager;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.grandmagic.BeeFramework.Utils.JsonParser;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.MyViewGroup;
import com.grandmagic.BeeFramework.view.ToastView;

import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.SearchTeacherAdapter;
import com.grandmagic.edustore.model.SearchModel;
import com.grandmagic.edustore.model.TeacherListModel;
import com.grandmagic.edustore.model.TeacherModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.PAGINATED;
import com.grandmagic.edustore.protocol.SimpleTeacherInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangmengqi on 2016/9/5.
 */
public class SearchSubscriptionTeacher extends BaseActivity implements View.OnClickListener, BusinessResponse, XListView.IXListViewListener {
    private View view;
    private ImageView search;
    private EditText input;
    private ImageButton voice;
    private FrameLayout fl;

    private SearchModel searchModel;

    private Button btn[];
    private MyViewGroup layout;

    private XListView xlist_search_body;

    private TeacherListModel teacherListModel;
    private SearchTeacherAdapter searchTeacherAdapter;
    private TeacherModel teacherModel;
    private String course_id;

    ArrayList<SimpleTeacherInfo> teachers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_subscription_teacher);
        if (teacherListModel == null) {
            teacherListModel = new TeacherListModel(this);
        }
        if (teacherModel == null) {
            teacherModel = new TeacherModel(this);
        }
        initView();
        setListener();
        course_id = getIntent().getStringExtra("course_id");
    }


    private void setListener() {
        //为模型增加监听
        teacherListModel.addResponseListener(this);

        //为输入框添加监听
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    try {
                        //                        Intent intent = new Intent(this, B1_ProductListActivity.class);
                        //                        FILTER filter = new FILTER();
                        //                        filter.keywords = input.getText().toString().toString();
                        String keywords = input.getText().toString().trim();
                        if (TextUtils.isEmpty(keywords)) {
                            return false;
                        }
                        //检测是否输入条件少于两个汉字
                        Pattern p = Pattern.compile("[\u4e00-\u9fa5]{2,}");
                        Matcher m = p.matcher(keywords);
                        if (!m.matches()) {
                            Resources resource = SearchSubscriptionTeacher.this.getResources();
                            String registration_closed = resource.getString(R.string.search_teacher_full_name);
                            ToastView toast = new ToastView(SearchSubscriptionTeacher.this, registration_closed);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return false;
                        }

                        teacherListModel.fetchPreSearch(keywords,course_id);
                        //                        intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                        //                        startActivity(intent);
                        return true;

                    } catch (Exception e) {

                    }
                }
                return false;
            }
        });

        voice.setOnClickListener(this);

    }

    private void initView() {
        input = (EditText) findViewById(R.id.search_input);
        search = (ImageView) findViewById(R.id.search_search);
        voice = (ImageButton) findViewById(R.id.search_voice);
        layout = (MyViewGroup) findViewById(R.id.search_layout);
        xlist_search_body = (XListView) findViewById(R.id.xlist_search_body);
        fl = (FrameLayout) findViewById(R.id.null_pager);

        xlist_search_body.setPullLoadEnable(false);
        xlist_search_body.setRefreshTime();
        xlist_search_body.setXListViewListener(this, 1);

//        View v = getLayoutInflater().inflate(R.layout.search_teacher_header, null);
//        xlist_search_body.addHeaderView(v);

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.SEARCH_TEACHER)) {
            //停止上拉加载，下拉刷新，设置刷新时间
            xlist_search_body.stopLoadMore();
            xlist_search_body.stopRefresh();
            xlist_search_body.setRefreshTime();

            setContent();
            PAGINATED paginated = new PAGINATED();
            paginated.fromJson(jo.optJSONObject("paginated"));
            if (0 == paginated.more) {
                xlist_search_body.setPullLoadEnable(false);
            } else {
                xlist_search_body.setPullLoadEnable(true);
            }
        }


        if (url.endsWith(ApiInterface.ADD_TEACHER)) {
            showToast(getString(R.string.subscription_sucess));
            SubscriptionActivity.isRefresh = true;
            this.finish();

        }

    }

    private void setContent() {
        teachers = teacherListModel.simpleTeachersList;
        if (teachers.size() == 0) {
            fl.setVisibility(View.VISIBLE);
            xlist_search_body.setVisibility(View.GONE);
        } else {
            fl.setVisibility(View.GONE);
            xlist_search_body.setVisibility(View.VISIBLE);
            if (searchTeacherAdapter == null) {
                searchTeacherAdapter = new SearchTeacherAdapter(this, teachers, teacherModel);
                teacherModel.addResponseListener(this);
                xlist_search_body.setAdapter(searchTeacherAdapter);
            }

            searchTeacherAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_voice: {
                showRecognizerDialog(); //弹出语音搜索框
                break;
            }

        }

    }

    public void showRecognizerDialog() {
        String appid = GrandMagicManager.getXunFeiCode(this);
        if (appid != null && !"".equals(appid)) {
            //用户登录
            SpeechUser.getUser().login(this, null, null
                    , "appid=" + GrandMagicManager.getXunFeiCode(this), listener);
            final RecognizerDialog recognizerDialog = new RecognizerDialog(this);
            //设置引擎为转写
            recognizerDialog.setParameter(SpeechConstant.DOMAIN, "iat");
            //设置识别语言为中文
            recognizerDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            //设置方言为普通话
            recognizerDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
            //设置录音采样率为
            recognizerDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
            //设置监听对象
            recognizerDialog.setListener(new RecognizerDialogListener() {
                @Override
                public void onResult(RecognizerResult results, boolean b) {
                    String text = JsonParser.parseIatResult(results.getResultString());
                    if (text.length() > 0) {
                        input.setText(text.toString());
                        input.setSelection(input.getText().length());
                        try {
                            recognizerDialog.setListener(null);
                            recognizerDialog.dismiss();
                            teacherListModel.fetchPreSearch(text.toString(), course_id);
                        } catch (Exception e) {
                        }
                    }
                }

                @Override
                public void onError(SpeechError speechError) {

                }
            });
            //开始识别
            recognizerDialog.show();
        }
    }

    private SpeechListener listener = new SpeechListener() {

        @Override
        public void onData(byte[] arg0) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error != null) {
                Toast.makeText(SearchSubscriptionTeacher.this, "登陆失败"
                        , Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onEvent(int arg0, Bundle arg1) {
        }
    };

    @Override
    public void onRefresh(int id) {
        String keywords = input.getText().toString().trim();
        if (!TextUtils.isEmpty(keywords)) {
            teacherListModel.fetchPreSearch(keywords, course_id);
        }
    }

    @Override
    public void onLoadMore(int id) {
        String keywords = input.getText().toString().trim();
        if (!TextUtils.isEmpty(keywords)) {
            teacherListModel.fetchPreSearchMore(keywords, course_id);
        }
    }

    private void showToast(String text) {
        ToastView toast = new ToastView(this, text);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}