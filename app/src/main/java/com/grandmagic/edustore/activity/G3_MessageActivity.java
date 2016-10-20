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
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.G3_MessageAdapter;
import com.grandmagic.edustore.model.MsgModel;
import com.grandmagic.edustore.model.MsgModel.OnMessageListResponse;
import com.grandmagic.edustore.model.ProtocolConst;
import com.grandmagic.edustore.protocol.FILTER;
import com.grandmagic.edustore.protocol.MESSAGE;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

public class G3_MessageActivity extends BaseActivity implements IXListViewListener,BusinessResponse, OnMessageListResponse {

	private TextView title;
	private ImageView back;
	
	private XListView xlistView;
	private G3_MessageAdapter shopNotifyAdapter;
	
	private MsgModel dataModel;
    private View null_pager;
    private MyProgressDialog pd;
    @Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g3_message);

        Resources resource = (Resources) getBaseContext().getResources();
        String mes=resource.getString(R.string.profile_message);
		title = (TextView) findViewById(R.id.top_view_text);
		title.setText(mes);
		
		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        null_pager = findViewById(R.id.null_pager);
		xlistView = (XListView) findViewById(R.id.shop_notify_list);
		xlistView.setPullLoadEnable(true);
        xlistView.setPullRefreshEnable(true);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
        xlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	if(position >= 1) {
            		MESSAGE message = dataModel.msg_list.get(position - 1);
                    String action = message.custom_data;
                    if(action != null) {
                    	try {
                            JSONObject jsonObject = new JSONObject(action);
                            String actionString = jsonObject.optString("a");
                            if (0 == actionString.compareTo("s")) {
                                String parameter = jsonObject.optString("k");
                                if (null != parameter && parameter.length() > 0) {
                                	try {
            							parameter = URLDecoder.decode(parameter,"UTF-8");
            						} catch (UnsupportedEncodingException e1) {            							
            							e1.printStackTrace();
            						}
                                    Intent it = new Intent(G3_MessageActivity.this, B1_ProductListActivity.class);
                                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    FILTER filter = new FILTER();
                                    filter.keywords =parameter;
                                    try {
                                        it.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                                    } catch (JSONException e) {
                                    	e.printStackTrace();
                                    }
                                    startActivity(it);
                                }
                            } else if(0 == actionString.compareTo("w")) {
                            	String parameter = jsonObject.optString("u");
                            	if (null != parameter && parameter.length() > 0) {
                            		try {
            							parameter = URLDecoder.decode(parameter,"UTF-8");
            						} catch (UnsupportedEncodingException e1) {            							
            							e1.printStackTrace();
            						}
                                    Intent it = new Intent(G3_MessageActivity.this, BannerWebActivity.class);
                                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    it.putExtra("url",parameter);
                                    startActivity(it);
                                }
                            }
                        } catch (JSONException e) {

                        }
                    }
            	}
            }
        });
        dataModel = MsgModel.getInstance();
        dataModel.messageListCallBack(this);
        dataModel.getMessageList();
        pd = new MyProgressDialog(this,this.getResources().getString(R.string.hold_on));
        pd.show();
		
	}

	@Override
	public void onRefresh(int id) {		
		dataModel.getMessageList();
	}

	@Override
	public void onLoadMore(int id) {		
		dataModel.getMessageListMore();
		
	}
	
	public void setCont() {
		if(dataModel.msg_list.size() > 0 ) {
			xlistView.setVisibility(View.VISIBLE);
			if(shopNotifyAdapter == null) {
				shopNotifyAdapter = new G3_MessageAdapter(this, dataModel.msg_list);
				xlistView.setAdapter(shopNotifyAdapter);
			} else {
				shopNotifyAdapter.list = dataModel.msg_list;
				shopNotifyAdapter.notifyDataSetChanged();
			}
		} else {
			xlistView.setVisibility(View.GONE);
            null_pager.setVisibility(View.VISIBLE);
		}
	}

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataModel.removeResponseListener(this);
    }

	@Override
	public void onMessageListResponse(JSONObject response) {		
		xlistView.setRefreshTime();
		xlistView.stopLoadMore();
		xlistView.stopRefresh();
		
		if(dataModel.requestCount >= dataModel.total) {
			xlistView.setPullLoadEnable(false);
		} else {
			xlistView.setPullLoadEnable(true);
		}
        if(pd!=null&&pd.isShowing()){
                pd.dismiss();
        }
		setCont();
	}
}
