package com.meetyou.cn.mvp.interfaces;

import com.meetyou.cn.mvp.base.IModel;
import com.meetyou.cn.mvp.base.IView;
import com.meetyou.cn.vo.entity.Category;

import java.util.List;

import rx.Observable;

public interface IHome {
    interface View extends IView {
       void categoryCallback( List<Category.DataBean> list);
    }

    interface Model extends IModel {
        Observable<Object> findCategory() throws Exception;
    }
}
