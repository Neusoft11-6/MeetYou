package com.meetyou.library.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.di.component.DaggerAppComponent;
import com.meetyou.library.di.module.AppModule;
import com.meetyou.library.di.module.GlobalConfigModule;
import com.meetyou.library.integration.lifecycle.ActivityLifecycleForRxLifecycle;

/**
 * Created by admin on 2018/4/24.
 */

public class AppDelegate implements App,AppLifecycles {

    private Application mApplication;
    private AppComponent mAppComponent;
    private ActivityLifecycleForRxLifecycle mActivityLifecycleForRxLifecycle = new ActivityLifecycleForRxLifecycle();


    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void attachBaseContext(Context base) {

    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(mApplication))//提供application
                .globalConfigModule(new GlobalConfigModule(mApplication))//全局配置
                .build();
        mAppComponent.inject(this);
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle);
    }

    @Override
    public void onTerminate(Application application) {
        if (mActivityLifecycleForRxLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle);
        }

        this.mAppComponent = null;
        this.mActivityLifecycleForRxLifecycle = null;
        this.mApplication = null;
    }
}
