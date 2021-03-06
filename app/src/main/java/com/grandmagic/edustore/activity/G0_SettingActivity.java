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

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.activity.WebViewActivity;
import com.grandmagic.BeeFramework.view.MyDialog;
import com.grandmagic.edustore.ECMobileAppConst;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.fragment.B0_IndexFragment;
import com.grandmagic.edustore.model.ConfigModel;
import com.grandmagic.edustore.protocol.SESSION;
import com.grandmagic.grandMagicManager.GrandMagicManager;
import com.insthub.ecmobile.EcmobilePush;
import com.insthub.ecmobile.EcmobilePush.PushResponse;

public class G0_SettingActivity extends BaseActivity implements OnClickListener, PushResponse {
	
	private TextView title;
	private ImageView back;
	
	private ImageView settingPush;
	
	private LinearLayout picture_auto; //智能模式
	private LinearLayout picture_high_quality; //高质量模式
	private LinearLayout picture_low_quality; //普通模式
	
	private ImageView picture_auto_arrow;
	private ImageView picture_high_quality_arrow;
	private ImageView picture_low_quality_arrow;
	


	private LinearLayout aboutApp;

    private LinearLayout about;
	private LinearLayout settingSupport;


	private Button exitLogin;
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	private MyDialog mDialog;
	
	private boolean pushSwitch = true;

	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g0_setting);
		
		shared = getSharedPreferences("userInfo", 0); 
		editor = shared.edit();

        if (null == ConfigModel.getInstance())
        {
            ConfigModel configModel = new ConfigModel(this);
            ConfigModel.getInstance().getConfig();
        }
        else if (null == ConfigModel.getInstance().config)
        {
            ConfigModel.getInstance().getConfig();
        }

		
		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String set=resource.getString(R.string.setting);
		title.setText(set);
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(this);
		
		settingPush = (ImageView) findViewById(R.id.setting_push);
		
		picture_auto = (LinearLayout) findViewById(R.id.setting_picture_auto);
		picture_high_quality = (LinearLayout) findViewById(R.id.setting_picture_high_quality);
		picture_low_quality = (LinearLayout) findViewById(R.id.setting_picture_low_quality);
		
		picture_auto_arrow = (ImageView) findViewById(R.id.setting_picture_auto_arrow);
		picture_high_quality_arrow = (ImageView) findViewById(R.id.setting_picture_high_quality_arrow);
		picture_low_quality_arrow = (ImageView) findViewById(R.id.setting_picture_low_quality_arrow);

        settingSupport = (LinearLayout) findViewById(R.id.setting_support);
        settingSupport.setOnClickListener(this);

		aboutApp = (LinearLayout) findViewById(R.id.setting_aboutApp);
		about = (LinearLayout) findViewById(R.id.setting_about);
		exitLogin = (Button) findViewById(R.id.setting_exitLogin);
		
		picture_auto.setOnClickListener(this);
		picture_high_quality.setOnClickListener(this);
		picture_low_quality.setOnClickListener(this);

		aboutApp.setOnClickListener(this);
		about.setOnClickListener(this);
		exitLogin.setOnClickListener(this);
		
		String imageType = shared.getString("imageType", "mind");
		if(imageType.equals("high")) {
			picture_auto_arrow.setVisibility(View.GONE);
			picture_high_quality_arrow.setVisibility(View.VISIBLE);
			picture_low_quality_arrow.setVisibility(View.GONE);
		} else if(imageType.equals("low")) {
			picture_auto_arrow.setVisibility(View.GONE);
			picture_high_quality_arrow.setVisibility(View.GONE);
			picture_low_quality_arrow.setVisibility(View.VISIBLE);
		} else {
			picture_auto_arrow.setVisibility(View.VISIBLE);
			picture_high_quality_arrow.setVisibility(View.GONE);
			picture_low_quality_arrow.setVisibility(View.GONE);
		}
		
		String uid = shared.getString("uid", "");
		if (uid.equals("")) {
			exitLogin.setVisibility(View.GONE);
		} else {
			exitLogin.setVisibility(View.VISIBLE);
		}
		
		if(EcmobilePush.isPush(this)) {
			settingPush.setImageResource(R.drawable.on);
			pushSwitch = true;
		} else {
			settingPush.setImageResource(R.drawable.off);
			pushSwitch = false;
		}
		settingPush.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EcmobilePush.switchPush(G0_SettingActivity.this, ECMobileAppConst.AppId, ECMobileAppConst.AppKey);
                EcmobilePush.pushCallBack(G0_SettingActivity.this);
                if(pushSwitch) {
                	pushSwitch = false;
                	settingPush.setImageResource(R.drawable.off);
                } else {
                	pushSwitch = true;
                	settingPush.setImageResource(R.drawable.on);
                }
            }
        });
		
	}

	@Override
	public void onClick(View v) {		
		switch(v.getId()) {
		case R.id.top_view_back:
			finish();
			break;
		case R.id.setting_picture_auto:
			picture_auto_arrow.setVisibility(View.VISIBLE);
			picture_high_quality_arrow.setVisibility(View.GONE);
			picture_low_quality_arrow.setVisibility(View.GONE);
			editor.putString("imageType", "mind");
			editor.commit();
			break;
		case R.id.setting_picture_high_quality:
			picture_auto_arrow.setVisibility(View.GONE);
			picture_high_quality_arrow.setVisibility(View.VISIBLE);
			picture_low_quality_arrow.setVisibility(View.GONE);
			editor.putString("imageType", "high");
			editor.commit();
			break;
		case R.id.setting_picture_low_quality:
			picture_auto_arrow.setVisibility(View.GONE);
			picture_high_quality_arrow.setVisibility(View.GONE);
			picture_low_quality_arrow.setVisibility(View.VISIBLE);
			editor.putString("imageType", "low");
			editor.commit();
			break;

		case R.id.setting_aboutApp:
        {
            Intent it = new Intent(G0_SettingActivity.this, WebViewActivity.class);
            it.putExtra(WebViewActivity.WEBURL, ConfigModel.getInstance().config.site_url);
            Resources resource = (Resources) getBaseContext().getResources();
            String tech=resource.getString(R.string.setting_tech);
            it.putExtra(WebViewActivity.WEBTITLE, tech);
            startActivity(it);
            break;
        }

        case R.id.setting_about:
        	Intent it = new Intent(G0_SettingActivity.this, WebViewActivity.class);
            it.putExtra(WebViewActivity.WEBURL, ConfigModel.getInstance().config.site_url);
            it.putExtra(WebViewActivity.WEBTITLE, "Geek-Zoo介绍");
            startActivity(it);
        	break;
        case R.id.setting_support:
//        	Intent intent = new Intent(G0_SettingActivity.this, WebViewActivity.class);
//			intent.putExtra(WebViewActivity.WEBURL, "http://ecmobile.me/copyright.html");
//			intent.putExtra(WebViewActivity.WEBTITLE, "版权声明");
//            startActivity(intent);
			//todo zhangmengqi

			Intent intent = new Intent(G0_SettingActivity.this, CopyRightActivity.class);
			startActivity(intent);


        	break;
		case R.id.setting_exitLogin:

            Resources resource = (Resources) getBaseContext().getResources();
			String exit=resource.getString(R.string.exit);
            String exiten=resource.getString(R.string.ensure_exit);
			mDialog = new MyDialog(G0_SettingActivity.this, exit, exiten);
            mDialog.show();
            mDialog.positive.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {					
					mDialog.dismiss();
					editor.putString("uid", "");
		            editor.putString("sid", "");
					B0_IndexFragment.login_is_teacher = -1;
					editor.commit();
		            SESSION.getInstance().uid = shared.getString("uid", "");
		            SESSION.getInstance().sid = shared.getString("sid", "");
		           
		            Intent intent = new Intent(G0_SettingActivity.this, EcmobileMainActivity.class);
		            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
		            startActivity(intent);
		            finish();
				}
			});
            mDialog.negative.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {					
					mDialog.dismiss();
				}
			});
			
			break;
		}
		
	}



	@Override
	public void onPushResponse(boolean success, boolean push_switch) {		
		if(success) {
			if(push_switch) {
				pushSwitch = true;
				settingPush.setImageResource(R.drawable.on);
				PushManager.activityStarted(this);
				PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY, GrandMagicManager.getPushKey(this));
			} else {
				pushSwitch = false;
				settingPush.setImageResource(R.drawable.off);
				PushManager.activityStoped(this);
				PushManager.stopWork(this);
			}
		}
	}
}
