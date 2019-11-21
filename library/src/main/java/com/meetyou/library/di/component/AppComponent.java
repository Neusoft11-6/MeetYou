package com.meetyou.library.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.meetyou.library.base.AppDelegate;
import com.meetyou.library.di.module.AppModule;
import com.meetyou.library.di.module.GlobalConfigModule;
import com.meetyou.library.http.imageloader.ImageLoader;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by admin on 2018/4/24.
 */
@Singleton
@Component(modules = {AppModule.class, GlobalConfigModule.class})
public interface AppComponent {
    Application application();

    ImageLoader imageLoader();

    OkHttpClient okHttpClient();

    File cacheFile();

    Gson gson();

    void inject(AppDelegate delegate);
}
