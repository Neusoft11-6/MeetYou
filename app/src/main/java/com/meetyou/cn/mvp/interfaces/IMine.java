package com.meetyou.cn.mvp.interfaces;

import com.meetyou.cn.mvp.base.IModel;
import com.meetyou.cn.mvp.base.IView;

public interface IMine {
    interface View extends IView {
        void upgradeSucceed();
    }

    interface Model extends IModel {

    }
}
