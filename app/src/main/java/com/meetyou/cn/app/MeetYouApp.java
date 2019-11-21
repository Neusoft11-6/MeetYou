package com.meetyou.cn.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.meetyou.ZoomMediaLoader;
import com.meetyou.cn.base.FullImageLoader;
import com.meetyou.library.base.App;
import com.meetyou.library.base.AppDelegate;
import com.meetyou.library.base.AppLifecycles;
import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.utils.Preconditions;
import com.meetyou.library.utils.SignatureUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

import static com.meetyou.library.utils.SignatureUtil.TYPE_BMOB_OPEN_ID;

public class MeetYouApp extends Application implements App{

    private AppLifecycles mAppDelegate;
    private static MeetYouApp mInstance;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate();
        this.mAppDelegate.attachBaseContext(base);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);
        final String signatureParam = SignatureUtil.getSignatureParam(TYPE_BMOB_OPEN_ID);
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId(signatureParam)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)

                .build();
        Bmob.initialize(config);
        Logger.addLogAdapter(new AndroidLogAdapter());

        ZoomMediaLoader.getInstance().init(new FullImageLoader());
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", AppDelegate.class.getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    public static MeetYouApp getInstance() {
        return mInstance;
    }
}
