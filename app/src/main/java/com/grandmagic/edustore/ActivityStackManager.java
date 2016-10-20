package com.grandmagic.edustore;

import android.app.Activity;

import com.external.activeandroid.util.Log;

import java.util.Stack;

/**
 * Created by chenggaoyuan on 2016/9/1.
 */
public class ActivityStackManager {

    private static ActivityStackManager instance;
    private Stack<Activity> activityStack;//activity栈

    private ActivityStackManager() {
    }

    //单例模式
    public static ActivityStackManager getInstance() {
        if (instance == null) {
            instance = new ActivityStackManager();
        }
        return instance;
    }
    //把一个activity压入栈中
    public void pushOneActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
        Log.d("ActivityStackManager ", "size = " + activityStack.size());
    }

    //获取栈顶的activity，先进后出原则
    public Activity getLastActivity() {
        return activityStack.lastElement();
    }

    //移除一个activity
    public void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }

    //退出所有activity
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null) break;
                popOneActivity(activity);
            }
        }
    }
}