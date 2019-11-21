package com.meetyou.library.utils;

import android.content.Context;

import com.meetyou.library.base.App;
import com.meetyou.library.di.component.AppComponent;

/**
 * Created by admin on 2018/4/24.
 */

public class MeetYouUtils {

    public static AppComponent obtainAppComponentFromContext(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        Preconditions.checkState(context.getApplicationContext() instanceof App, "Application does not implements App");
        return ((App) context.getApplicationContext()).getAppComponent();
    }
}
