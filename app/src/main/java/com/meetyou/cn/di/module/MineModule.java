package com.meetyou.cn.di.module;

import com.meetyou.cn.di.scope.ActivityScope;
import com.meetyou.cn.mvp.interfaces.IHome;
import com.meetyou.cn.mvp.interfaces.IMine;
import com.meetyou.cn.mvp.model.HomeModel;
import com.meetyou.cn.mvp.model.MineModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2018/3/14.
 */
@Module
public class MineModule {
    private IMine.View view;
    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public MineModule(IMine.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    IMine.View provideUserView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IMine.Model provideUserModel(MineModel model) {
        return model;
    }

}
