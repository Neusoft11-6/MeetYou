package com.meetyou.cn.mvp.presenter;

import com.meetyou.cn.di.scope.ActivityScope;
import com.meetyou.cn.http.rx.MeetYHttpObserver;
import com.meetyou.cn.mvp.base.BasePresenter;
import com.meetyou.cn.mvp.interfaces.IHome;
import com.meetyou.cn.utils.CommonUtils;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.cn.vo.entity.Category;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ActivityScope
public class HomePresenterImpl extends BasePresenter<IHome.Model, IHome.View>{

    @Inject
    public HomePresenterImpl(IHome.Model model, IHome.View rootView) {
        super(model, rootView);
    }


    public void findCategory(final boolean showLoding){
        try {
            mModel.findCategory()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> {
                        if(showLoding)mRootView.showLoading();
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//主线程执行
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(o -> {
                        if(showLoding)mRootView.hideLoading();
                    })
                    .doOnError(throwable -> {
                        if(showLoding)mRootView.hideLoading();
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new MeetYHttpObserver<Category>() {
                        @Override
                        protected void onSucceed(Category category) {
                            mRootView.categoryCallback(CommonUtils.transform(category.data));
                        }

                        @Override
                        protected void onError(int code) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
