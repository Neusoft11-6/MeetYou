package com.meetyou.cn.di.module;

import com.meetyou.cn.di.scope.ActivityScope;
import com.meetyou.cn.mvp.interfaces.ICovers;
import com.meetyou.cn.mvp.model.CoversModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2018/3/14.
 */
@Module
public class CoversModule {
    private ICovers.View view;
    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public CoversModule(ICovers.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ICovers.View provideUserView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ICovers.Model provideUserModel(CoversModel model) {
        return model;
    }

}
