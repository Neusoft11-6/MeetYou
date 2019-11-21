package com.meetyou.cn.di.module;

import com.meetyou.cn.di.scope.ActivityScope;
import com.meetyou.cn.mvp.interfaces.IHome;
import com.meetyou.cn.mvp.model.HomeModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2018/3/14.
 */
@Module
public class HomeModule {
    private IHome.View view;
    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public HomeModule(IHome.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    IHome.View provideUserView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IHome.Model provideUserModel(HomeModel model) {
        return model;
    }

}
