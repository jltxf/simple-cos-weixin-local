package com.example.sxb.mywechat.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class ActivityCollector {
    static List<Activity> activityList=new ArrayList();
    public static void add(Activity activity){
        activityList.add(activity);
    }
    public static void remove(Activity activity){
        activityList.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity:activityList){
            if(!activity.isFinishing())
                activity.finish();
        }
    }
}
