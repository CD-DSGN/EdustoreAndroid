package com.grandmagic.edustore.model;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;
import com.external.activeandroid.query.Select;
import com.grandmagic.BeeFramework.BeeFrameworkConst;
import com.grandmagic.BeeFramework.Utils.Utils;
import com.grandmagic.edustore.ECMobileAppConst;
import com.insthub.ecmobile.EcmobileMessage;
import com.insthub.ecmobile.EcmobileMessage.MessageContResponse;
import com.insthub.ecmobile.EcmobileMessage.MessageListResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;

public class MsgModel extends BaseModel implements MessageContResponse, MessageListResponse {

	public PAGINATED paginated;
	
	private static OnMessageContResponse messageContResponse;
	private static OnMessageListResponse messageListResponse;
	
	public ArrayList<MESSAGE> msg_list = new ArrayList<MESSAGE>();
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public int unreadCount = 0;
    public int total = 0;
    public int requestCount = 10;

    public static MsgModel Instance;

    public static MsgModel getInstance()
    {
        return Instance;
    }
	public MsgModel(Context context)
    {
		super(context);
        Instance = this;
        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
	}

    public void loadCache()
    {
        int lastMessageId = 0;
        List<MESSAGE> messageList = null;
        if (msg_list.size() > 0)
        {
            MESSAGE message = msg_list.get(0);
            lastMessageId = message.id;
            messageList = new Select().from(MESSAGE.class).where("message_id < ?",lastMessageId).orderBy("message_id DESC").limit(10).execute();
        }
        else
        {
            messageList = new Select().from(MESSAGE.class).orderBy("message_id DESC").limit(10).execute();
        }

        if (messageList.size() > 0)
        {
            msg_list.addAll(messageList);
        }
    }

    public int getLatestMessageId()
    {
        int lastMessageId = 0;
        List<MESSAGE> messageList = null;
        messageList = new Select().from(MESSAGE.class).orderBy("message_id DESC").limit(1).execute();
        if (messageList.size() > 0)
        {
            MESSAGE message = messageList.get(0);
            return message.id;
        }
        return 0;
    }

    //获取消息数量
    public void getMessageCont() {
    	int lastMessageId = getLatestMessageId();
    	EcmobileMessage.getMessageCont(mContext, lastMessageId, ECMobileAppConst.AppId, ECMobileAppConst.AppKey);
    	EcmobileMessage.messageContCallBack(this);
    }
    
	@Override
	public void onMessageContResponse(JSONObject response) {
		 
		try {
			MESSAGE_COUNT message_count = new MESSAGE_COUNT();
            message_count.fromJson(response);
            if(null != message_count && message_count.succeed == 1) {
                unreadCount = message_count.unread;
                messageContResponse.onMessageContResponse(response);
            }
        } catch (Exception e) {
             
            e.printStackTrace();
        }
	}
	
	public void getMessageList() {
		EcmobileMessage.getMessageList(mContext, 0, requestCount, ECMobileAppConst.AppId, ECMobileAppConst.AppKey);
		EcmobileMessage.messageListCallBack(this);
	}
	
	public void getMessageListMore() {
		int lastMessageId = msg_list.get(msg_list.size()-1).id;
		EcmobileMessage.getMessageList(mContext, lastMessageId, requestCount, ECMobileAppConst.AppId, ECMobileAppConst.AppKey);
		EcmobileMessage.messageListCallBack(this);
	}
	
	@Override
	public void onMessageListResponse(boolean isIndex, final JSONObject response) {
		 
		if(isIndex) {
			try {
				final MESSAGE_RESPONSE messageResponse =new MESSAGE_RESPONSE();
                messageResponse.fromJson(response);
				if(null != messageResponse && messageResponse.succeed == 1) {
	                total = messageResponse.total;
	                msg_list.clear();
	                msg_list.addAll(messageResponse.messages);
	                messageListResponse.onMessageListResponse(response);
                    new Thread() {
                        public void run() {
                            super.run();
                            for (int i = 0; i <messageResponse.messages.size(); i++) {
                                MESSAGE message1 = messageResponse.messages.get(i);
                                message1.save();
                            }
                        };
                    }.start();
				}
			} catch (JSONException e) {
				 
				e.printStackTrace();
			}
		} else {
			try {
				final MESSAGE_RESPONSE messageResponse = new MESSAGE_RESPONSE();
                messageResponse.fromJson(response);
				if(null != messageResponse && messageResponse.succeed == 1) {
	                total = messageResponse.total;
	                msg_list.addAll(messageResponse.messages);
	                messageListResponse.onMessageListResponse(response);
                    new Thread() {
                        public void run() {
                            super.run();
                            for (int i = 0; i <messageResponse.messages.size(); i++) {
                                MESSAGE message1 = messageResponse.messages.get(i);
                                message1.save();
                            }
                        };
                    }.start();
				}
            } catch (JSONException e) {
                 
                e.printStackTrace();
            }
		}
	}
	
	public void messageContCallBack(OnMessageContResponse messageCont) {
		messageContResponse = messageCont;
	}
	
	public interface OnMessageContResponse {
		public void onMessageContResponse(JSONObject response);
	}
	
	public void messageListCallBack(OnMessageListResponse messageList) {
		messageListResponse = messageList;
	}
	
	public interface OnMessageListResponse {
		public void onMessageListResponse(JSONObject response);
	}
	
}
