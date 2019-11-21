package com.meetyou.cn.di.component;

import com.meetyou.cn.di.module.MineModule;
import com.meetyou.cn.di.scope.ActivityScope;
import com.meetyou.cn.mvp.ui.activity.UpgradeActivity;
import com.meetyou.cn.mvp.ui.fragment.MineFragment;
import com.meetyou.library.di.component.AppComponent;

import dagger.Component;

/**
 * Created by admin on 2018/3/15.
 */
@ActivityScope
@Component(modules = {MineModule.class},dependencies = AppComponent.class)
public interface MineComponent {
//    void inject(HomeActivity activity);
    void inject(MineFragment fragment);
    void inject(UpgradeActivity fragment);
}
