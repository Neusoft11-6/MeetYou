package com.meetyou.library.di.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meetyou.library.http.imageloader.BaseImageLoaderStrategy;
import com.meetyou.library.http.imageloader.glide.GlideImageLoaderStrategy;
import com.meetyou.library.utils.DataHelper;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;


/**
 * Created by admin on 2018/4/24.
 */
@Module
public class GlobalConfigModule {
    private static final int TIME_OUT = 20;

    public GlobalConfigModule(Application mApplication) {

    }

    @Singleton
    @Provides
    Gson provideGson(){
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }
    /**
     * 提供缓存文件
     */
    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        return DataHelper.getCacheFile(application);
    }

    /**
     * 提供图片加载框架,默认使用
     *
     * @return
     */
    @Singleton
    @Provides
    BaseImageLoaderStrategy provideImageLoaderStrategy() {
        return new GlideImageLoaderStrategy();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    /**
     * 提供 {@link OkHttpClient}
     *
     * @param builder
     * @return
     */
    @Singleton
    @Provides
    OkHttpClient provideClient(Application application, OkHttpClient.Builder builder) {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.protocols(Arrays.asList(Protocol.HTTP_1_1));
        return builder.build();
    }

}
