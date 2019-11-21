package com.meetyou.cn.di.component;

import com.meetyou.cn.di.module.CoversModule;
import com.meetyou.cn.di.scope.ActivityScope;
import com.meetyou.cn.mvp.ui.activity.MyLikesActivity;
import com.meetyou.cn.mvp.ui.activity.PhotosActivity;
import com.meetyou.cn.mvp.ui.fragment.CoversFragment;
import com.meetyou.library.di.component.AppComponent;

import dagger.Component;

/**
 * Created by admin on 2018/3/15.
 */
@ActivityScope
@Component(modules = {CoversModule.class},dependencies = AppComponent.class)
public interface CoversComponent {
//    void inject(HomeActivity activity);
    void inject(CoversFragment fragment);
    void inject(PhotosActivity activity);
    void inject(MyLikesActivity activity);
}
