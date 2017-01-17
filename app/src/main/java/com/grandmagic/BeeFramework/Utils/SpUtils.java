package com.grandmagic.BeeFramework.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lps on 2017/1/17.
 */

public class SpUtils {
    public static final String SHARE_USERINFO="userInfo";
    public static String getUid(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_USERINFO, Context.MODE_PRIVATE);
      return   sharedPreferences.getString("uid",null);
    }
}
