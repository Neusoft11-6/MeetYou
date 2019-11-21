package com.meetyou.cn.mvp.presenter;

import com.meetyou.cn.http.rx.MeetYHttpObserver;
import com.meetyou.cn.mvp.base.BasePresenter;
import com.meetyou.cn.mvp.interfaces.ICovers;
import com.meetyou.cn.utils.CommonUtils;
import com.meetyou.cn.utils.MyStringUtils;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.cn.vo.entity.Category;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.meetyou.cn.vo.entity.M_Y_Likes;
import com.meetyou.cn.vo.entity.MeetYUser;
import com.meetyou.cn.vo.entity.Storage;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobQuery;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2018/4/24.
 */

public class CoversPresenterImpl extends BasePresenter<ICovers.Model, ICovers.View> {

    @Inject
    public CoversPresenterImpl(ICovers.Model model, ICovers.View rootView) {
        super(model, rootView);
    }


    public void findCovers(Category.DataBean category, int skip, boolean showLoding) {
        mModel.findCovers(skip, category)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    if (showLoding) mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())//主线程执行
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (showLoding) mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.<List<M_Y_Covers>>bindToLifecycle(mRootView))
                .subscribe(new Observer<List<M_Y_Covers>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e("onError", throwable.getMessage());
                        if (skip > 0) {
                            mRootView.nextDataCallback(null);
                        } else {
                            mRootView.dataCallback(null);
                        }
                    }

                    @Override
                    public void onNext(List<M_Y_Covers> m_y_categories) {
                        if (skip > 0) {
                            mRootView.nextDataCallback(m_y_categories);
                        } else {
                            mRootView.dataCallback(m_y_categories);
                        }
                    }

                });
    }


    public void likes(M_Y_Covers mYCovers, int position,boolean like, boolean showLoding) {
        try {
            JSONObject params = null;
            params = new JSONObject();
            params.put("objectId", MeetYUser.getCurrentUser() != null ? MeetYUser.getCurrentUser().getObjectId() : "1");
            params.put("coversId", MyStringUtils.checkNull(mYCovers.getIds(), "private"));
            params.put("like", like);
            AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
            ace.callEndpointObservable("toggleLike", params)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> {
                        if (showLoding) mRootView.showLoading();
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//主线程执行
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(o -> {
                        if (showLoding) mRootView.hideLoading();
                    })
                    .doOnError(throwable -> {
                        if (showLoding) mRootView.hideLoading();
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new MeetYHttpObserver<Object>() {
                        @Override
                        protected void onSucceed(Object o) {
                            mRootView.likeCallback(position,mYCovers.getIds());
                        }

                        @Override
                        protected void onError(int code) {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void findLikes(int skip) {
        BmobQuery<M_Y_Likes> query = new BmobQuery<M_Y_Likes>();
        query.addWhereEqualTo("user_objectids", MeetYUser.getCurrentUser().getObjectId());
        query.order("-createdAt");
        query.setSkip(skip);
        query.setLimit(20);
        query.findObjectsObservable(M_Y_Likes.class)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())//主线程执行
                .observeOn(Schedulers.io())
                .flatMap(new Func1<List<M_Y_Likes>, Observable<List<M_Y_Covers>>>() {
                    @Override
                    public Observable<List<M_Y_Covers>> call(List<M_Y_Likes> m_y_likes) {
                        BmobQuery<M_Y_Covers> query = new BmobQuery<M_Y_Covers>();
                        List<String> idsList = new ArrayList<>();
                        for (M_Y_Likes m_y_like : m_y_likes) {
                            idsList.add(m_y_like.getCovers_ids());
                        }
                        query.addWhereContainedIn("ids", idsList);
                        return query.findObjectsObservable(M_Y_Covers.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<List<M_Y_Covers>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e("onError", throwable.getMessage());
                        if (skip > 0) {
                            mRootView.nextDataCallback(null);
                        } else {
                            mRootView.dataCallback(null);
                        }
                    }

                    @Override
                    public void onNext(List<M_Y_Covers> m_y_categories) {
                        if (skip > 0) {
                            mRootView.nextDataCallback(m_y_categories);
                        } else {
                            mRootView.dataCallback(m_y_categories);
                        }
                    }
                });
    }

    public void findImgByMethod(String coversIds, int skip, boolean showLoding) {
        try {
            mModel.findImgByMethod(skip, coversIds)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> {
                        if (showLoding) mRootView.showLoading();
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//主线程执行
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(o -> {
                        if (showLoding) mRootView.hideLoading();
                    })
                    .doOnError(throwable -> {
                        if (showLoding) mRootView.hideLoading();
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new MeetYHttpObserver<Storage>() {
                        @Override
                        protected void onSucceed(Storage category) {
                            if (skip > 0) {
                                mRootView.nextDataCallback(CommonUtils.transform(category.data));
                            } else {
                                mRootView.dataCallback(CommonUtils.transform(category.data));
                            }
                        }

                        @Override
                        protected void onError(int code) {
                            if (skip > 0) {
                                mRootView.nextDataCallback(null);
                            } else {
                                mRootView.dataCallback(null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
