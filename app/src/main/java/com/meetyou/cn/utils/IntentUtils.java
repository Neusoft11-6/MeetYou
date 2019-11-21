package com.meetyou.cn.utils;

import android.app.Activity;
import android.content.Intent;

import com.meetyou.cn.app.MeetYouApp;
import com.meetyou.cn.base.BaseActivity;
import com.meetyou.cn.mvp.ui.activity.LoginActivity;
import com.meetyou.library.utils.AppManager;

import cn.bmob.v3.BmobUser;

public class IntentUtils {

    public static void start(Intent intent,boolean checkLogin){
        Activity activity = AppManager.getAppManager().currentActivity();
        if(checkLogin  && BmobUser.getCurrentUser() == null){
            activity.startActivity(new Intent(activity, LoginActivity.class));
            return;
        }
        activity.startActivity(intent);

    }
}
