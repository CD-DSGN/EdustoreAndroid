package com.external.activeandroid.util;

/*
 * Copyright (C) 2010 Michael Pardo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.external.activeandroid.Model;
import com.external.activeandroid.serializer.TypeSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ReflectionUtils {
	//////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	//////////////////////////////////////////////////////////////////////////////////////

	public static boolean isModel(Class<?> type) {
		return isSubclassOf(type, Model.class);
	}

	public static boolean isTypeSerializer(Class<?> type) {
		return isSubclassOf(type, TypeSerializer.class);
	}

	// Meta-data

	@SuppressWarnings("unchecked")
	public static <T> T getMetaData(Context context, String name) {
		try {
			final ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);

			if (ai.metaData != null) {
				return (T) ai.metaData.get(name);
			}
		}
		catch (Exception e) {
			Log.w("Couldn't find meta-data: " + name);
		}

		return null;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	//////////////////////////////////////////////////////////////////////////////////////

	public static boolean isSubclassOf(Class<?> type, Class<?> superClass) {
		if (type.getSuperclass() != null) {
			if (type.getSuperclass().equals(superClass)) {
				return true;
			}

			return isSubclassOf(type.getSuperclass(), superClass);
		}

		return false;
	}
	
    // 验证邮箱的正则表达式
    public static boolean isEmail(String email){     
        //String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        //String str="^([a-z0-9A-Z]+[-|//.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?//.)+[a-zA-Z]{2,}$";  
        String str="[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(email);     
        return m.matches();     
    }

	// 验证手机的正则表达式
	public static boolean isMobile(String mobile){
		if (TextUtils.isEmpty(mobile)) {
			return false;
		}

		Pattern p = Pattern.compile("^13[\\d]{9}$|^14[5,7]{1}\\d{8}$|^15[^4]{1}\\d{8}$|^17[0,6,7,8]{1}\\d{8}$|^18[\\d]{9}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
}