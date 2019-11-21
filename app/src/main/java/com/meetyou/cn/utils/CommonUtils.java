package com.meetyou.cn.utils;


import android.app.Activity;
import android.content.Intent;

import com.meetyou.cn.app.MeetYouApp;
import com.meetyou.cn.mvp.ui.activity.LoginActivity;
import com.meetyou.cn.mvp.ui.widget.OnCommitListener;
import com.meetyou.cn.vo.entity.MeetYUser;
import com.meetyou.library.utils.AppManager;
import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.layout.QMUILayoutHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2018/3/16.
 */

public class CommonUtils {
    public static Random random = new Random();
    public static final String SAVE_SETTINGS_NAME = "settings";



    public static <T> List<T> transform(List<T> list) {
        if (list != null && list.size() > 0) {
            return list;
        }
        return Collections.<T>emptyList();
    }

    /**
     * @param target
     * @param minimum
     * @return
     */
    public static int sub(int target, int minimum) {
        return target > minimum ? target - 1 : minimum;
    }


    public static void adjustRadiusLayout(IQMUILayout layoutMyLikes,int mRadius) {
        layoutMyLikes.setRadius(QMUIDisplayHelper.dp2px(MeetYouApp.getInstance(), mRadius), QMUILayoutHelper.HIDE_RADIUS_SIDE_NONE);
        layoutMyLikes.setRadiusAndShadow(mRadius, QMUIDisplayHelper.dp2px(MeetYouApp.getInstance(), 14), 0.25f);
    }


    public static void checkLoginIntent(Intent intent){
        final BmobUser currentUser = MeetYUser.getCurrentUser();
        if(currentUser == null){
            MeetYDialogUtils.createMessageNegativeDialog("提示", "登录了才能继续操作哦", "去登录", new OnCommitListener() {
                @Override
                public void onCommit(int type, Object value) {
                    final Activity activity = AppManager.getAppManager().currentActivity();
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
            }).show();
        }else{
            final Activity activity = AppManager.getAppManager().currentActivity();
            activity.startActivity(intent);
        }
    }

    public static boolean checkLoginDialog(){
        final BmobUser currentUser = MeetYUser.getCurrentUser();
        if(currentUser == null){
            MeetYDialogUtils.createMessageNegativeDialog("提示", "登录了才能继续操作哦", "去登录", new OnCommitListener() {
                @Override
                public void onCommit(int type, Object value) {
                    final Activity activity = AppManager.getAppManager().currentActivity();
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
            }).show();
            return false;
        }else{
            return true;
        }
    }
}
