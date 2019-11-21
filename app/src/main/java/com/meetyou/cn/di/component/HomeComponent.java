package com.meetyou.cn.di.component;

import com.meetyou.cn.di.module.HomeModule;
import com.meetyou.cn.di.scope.ActivityScope;
import com.meetyou.cn.mvp.ui.fragment.HomeFragment;
import com.meetyou.library.di.component.AppComponent;

import dagger.Component;

/**
 * Created by admin on 2018/3/15.
 */
@ActivityScope
@Component(modules = {HomeModule.class},dependencies = AppComponent.class)
public interface HomeComponent {
//    void inject(HomeActivity activity);
    void inject(HomeFragment fragment);
}
