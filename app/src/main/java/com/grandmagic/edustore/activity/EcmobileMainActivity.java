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
//

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import com.external.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.fragment.D0_CategoryFragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.grandmagic.BeeFramework.BeeFrameworkApp;
import com.grandmagic.BeeFramework.model.BeeQuery;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.model.NewsModel;
import com.grandmagic.edustore.protocol.APKVersion;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.grandMagicManager.GrandMagicManager;

import com.insthub.ecmobile.EcmobilePush;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.FILTER;
import com.tencent.bugly.crashreport.CrashReport;

public class EcmobileMainActivity extends FragmentActivity {

    public static final String RESPONSE_METHOD = "method";
    public static final String RESPONSE_CONTENT = "content";
    public static final String RESPONSE_ERRCODE = "errcode";
    protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
    public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
    public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
    public static final String ACTION_PUSHCLICK = "bccsclient.action.PUSHCLICK";
    public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
    protected static final String EXTRA_ACCESS_TOKEN = "access_token";
    public static final String EXTRA_MESSAGE = "message";
    public static final String CUSTOM_CONTENT = "CustomContent";

    // 在百度开发者中心查询应用的API Key
    public static String API_KEY;
    private AlertDialog mAlertDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = new Intent();
        intent.setAction("com.BeeFramework.NetworkStateService");
        startService(intent);

        if (getIntent().getStringExtra(CUSTOM_CONTENT) != null) {
            pushIntent(getIntent().getStringExtra(CUSTOM_CONTENT));
        }

        checkversion();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (BeeQuery.environment() == BeeQuery.ENVIROMENT_DEVELOPMENT) {
            BeeFrameworkApp.getInstance().showBug(EcmobileMainActivity.this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 如果要统计Push引起的用户使用应用情况，请实现本方法，且加上这一个语句
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();

        if (ACTION_RESPONSE.equals(action)) {

        } else if (ACTION_LOGIN.equals(action)) {

        } else if (ACTION_MESSAGE.equals(action)) {

        } else if (ACTION_PUSHCLICK.equals(action)) {
            String message = intent.getStringExtra(CUSTOM_CONTENT);
            pushIntent(message);
        }
    }

    public void pushIntent(String message) {
        if (message != null) {
            try {
                JSONObject jsonObject = new JSONObject(message);
                String actionString = jsonObject.optString("a");
                if (0 == actionString.compareTo("s")) {
                    String parameter = jsonObject.optString("k");
                    if (null != parameter && parameter.length() > 0) {
                        try {
                            parameter = URLDecoder.decode(parameter, "UTF-8");
                        } catch (UnsupportedEncodingException e1) {

                            e1.printStackTrace();
                        }
                        Intent it = new Intent(this, B1_ProductListActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        FILTER filter = new FILTER();
                        filter.keywords = parameter;
                        try {
                            it.putExtra(B1_ProductListActivity.FILTER, filter.toJson().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(it);
                    }
                } else if (0 == actionString.compareTo("w")) {
                    String parameter = jsonObject.optString("u");
                    if (null != parameter && parameter.length() > 0) {
                        try {
                            parameter = URLDecoder.decode(parameter, "UTF-8");
                        } catch (UnsupportedEncodingException e1) {

                            e1.printStackTrace();
                        }
                        Intent it = new Intent(this, BannerWebActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        it.putExtra("url", parameter);
                        startActivity(it);
                    }
                }
            } catch (JSONException e) {

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (EcmobilePush.isPush(this)) {
            API_KEY = GrandMagicManager.getPushKey(this);
            PushManager.activityStarted(this);
            PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, API_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mPreferences = this.getSharedPreferences("userInfo", 0);
        CrashReport.setUserId(mPreferences.getString("uid", ""));
    }

    private boolean isExit = false;

    //退出操作
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                Resources resource = (Resources) getBaseContext().getResources();
                String exit = resource.getString(R.string.again_exit);
                ToastView toast = new ToastView(getApplicationContext(), exit);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                handler.sendEmptyMessageDelayed(0, 3000);


                return true;
            } else {
                Intent intent = new Intent();
                intent.setAction("com.BeeFramework.NetworkStateService");
                stopService(intent);
                finish();
                ToastView.cancel();
                return false;
            }
        }
        return true;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        PushManager.activityStoped(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void checkversion() {
        NewsModel mNewsModel = new NewsModel(this);
        mNewsModel.checkversion();
        mNewsModel.addResponseListener(new BusinessResponse() {
            @Override
            public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
                if (ApiInterface.CHECK_VERSION.equals(url)) {
                    Gson mGson = new Gson();
                    try {
                        APKVersion mAPKVersion = mGson.fromJson(jo.toString(), APKVersion.class);
                        if (mAPKVersion.getData().is_update()) {
                            showUpdateDialog(mAPKVersion);
                        }
                    } catch (Exception mE) {
                        mE.printStackTrace();
                    }
                }
            }
        });
    }

    private void showUpdateDialog(APKVersion mAPKVersion) {
//设置主题，否则会报异常
// java.lang.IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activity.
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAlertDialog.dismiss();
            }
        }).setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openApplicationMarket();
                mAlertDialog.dismiss();
            }
        });
        mBuilder.setTitle("版本升级");
        mBuilder.setMessage("更新信息：\n" + mAPKVersion.getData().getUpdate_note().replace("\\n","\n"));
        mAlertDialog = mBuilder.create();
        mAlertDialog.show();
    }

    private void openApplicationMarket() {
        StringBuilder mBuilder = new StringBuilder("market://details?id=");
        String pkn = getPackageName();
        mBuilder.append(pkn);
        Intent mIntent = new Intent("android.intent.action.VIEW", Uri.parse(mBuilder.toString()));
        List<ResolveInfo> mResolveInfos = getPackageManager().queryIntentActivities(mIntent, PackageManager.GET_INTENT_FILTERS);
        if (mResolveInfos != null && !mResolveInfos.isEmpty()) {
            startActivity(mIntent);
        } else {
            Toast.makeText(this, "你的手机没有应用市场", Toast.LENGTH_SHORT).show();
        }
    }
}
