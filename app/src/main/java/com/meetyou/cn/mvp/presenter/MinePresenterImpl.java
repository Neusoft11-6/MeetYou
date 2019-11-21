package com.meetyou.cn.mvp.presenter;

import com.meetyou.cn.di.scope.ActivityScope;
import com.meetyou.cn.http.rx.MeetYHttpObserver;
import com.meetyou.cn.mvp.base.BasePresenter;
import com.meetyou.cn.mvp.interfaces.IMine;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.cn.vo.entity.MeetYUser;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import cn.bmob.v3.AsyncCustomEndpoints;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ActivityScope
public class MinePresenterImpl extends BasePresenter<IMine.Model, IMine.View>{

    @Inject
    public MinePresenterImpl(IMine.Model model, IMine.View rootView) {
        super(model, rootView);
    }


    public void upgrade(String code){
        try {
            JSONObject params = new JSONObject();
            params.put("objectId", MeetYUser.getCurrentUser()!=null?MeetYUser.getCurrentUser().getObjectId():"");
            params.put("CDkey", code);
            AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
            ace.callEndpointObservable("upgradeleve",params)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> {
                         mRootView.showLoading();
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//主线程执行
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(o -> {
                         mRootView.hideLoading();
                    })
                    .doOnError(throwable -> {
                        mRootView.hideLoading();
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new MeetYHttpObserver<Object>() {
                        @Override
                        protected void onSucceed(Object o) {
                            mRootView.upgradeSucceed();
                        }

                        @Override
                        protected void onError(int code) {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
