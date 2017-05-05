package com.grandmagic.BeeFramework.model;

import android.content.Context;
import com.external.androidquery.AQuery;
import com.external.androidquery.callback.AjaxCallback;
import com.grandmagic.edustore.ECMobileAppConst;

import java.util.Map;


public class BeeQuery<T> extends AQuery {
	public BeeQuery(Context context) {
		super(context);
		 
	}

	public static final int ENVIRONMENT_PRODUCTION = 1;
	public static final int ENVIROMENT_DEVELOPMENT = 2;
	public static final int ENVIROMENT_MOCKSERVER = 3;
	
	public static int environment() 
	{
		return ENVIRONMENT_PRODUCTION;
//		return ENVIROMENT_DEVELOPMENT;
	}
	
	public static String serviceUrl()
	{
		
		if (ENVIRONMENT_PRODUCTION == BeeQuery.environment()) {
			return ECMobileAppConst.SERVER_PRODUCTION;
		}
		else 
		{
            return ECMobileAppConst.SERVER_DEVELOPMENT;
		}
	}
    public static  String wapCallBackUrl(){
        if (ENVIRONMENT_PRODUCTION == BeeQuery.environment())
        {
            return ECMobileAppConst.WAP_PAY_CALLBCK_PRODUCTION;
        }
        else
        {
            return ECMobileAppConst.WAP_PAY_CALLBCK_DEVELOPMENT;
        }
    }
	public <K> AQuery ajax(AjaxCallback<K> callback){

		if (BeeQuery.environment() == BeeQuery.ENVIROMENT_MOCKSERVER)
		{
			MockServer.ajax(callback);
			return null;
		}
        else
        {
            String url = callback.getUrl();
            String absoluteUrl = getAbsoluteUrl(url);

            callback.url(absoluteUrl);

        }

        if(BeeQuery.environment() == BeeQuery.ENVIROMENT_DEVELOPMENT)
        {
            DebugMessageModel.addMessage((BeeCallback)callback);
        }

		return (BeeQuery)super.ajax(callback);
	}

    public <K> AQuery ajaxAbsolute(AjaxCallback<K> callback){

        return (BeeQuery)super.ajax(callback);
    }

	public <K> AQuery ajax(String url, Map<String, ?> params, Class<K> type, BeeCallback<K> callback){
						
		callback.type(type).url(url).params(params);
		
		if (BeeQuery.environment() == BeeQuery.ENVIROMENT_MOCKSERVER)
		{
			MockServer.ajax(callback);
			return null;
		}
        else
        {
            String absoluteUrl = getAbsoluteUrl(url);
            callback.url(absoluteUrl);
        }
		return ajax(callback);
	}


    private static String getAbsoluteUrl(String relativeUrl) {
		if (relativeUrl.startsWith("http://")){//如果给的是完整路径。就不去拼接
			return relativeUrl;
		}
			return  BeeQuery.serviceUrl() + relativeUrl;
    }
}