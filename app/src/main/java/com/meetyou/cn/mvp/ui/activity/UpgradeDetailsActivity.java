package com.meetyou.cn.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.meetyou.cn.base.BaseActivity;
import com.meetyou.cn.mvp.base.IView;
import com.meetyou.cn.mvp.interfaces.IMine;
import com.meetyou.cn.mvp.ui.helper.RecyclerViewHelper;
import com.meetyou.cn.mvp.ui.helper.UpgradeHelper;
import com.meetyou.cn.mvp.ui.widget.AbsDialogView;
import com.meetyou.cn.mvp.ui.widget.EmptyRecyclerView;
import com.meetyou.cn.mvp.ui.widget.MeetYLoadingDialog;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.cn.vo.entity.M_Y_Notices;
import com.meetyou.library.di.component.AppComponent;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.meetyou.com.meetyou.R;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2018/5/7.
 */

public class UpgradeDetailsActivity extends BaseActivity implements IMine.View {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;
    private DelegateAdapter mAdapter;
    private AbsDialogView mAbsDialogView;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        mAbsDialogView = new MeetYLoadingDialog();
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_upgrade_details;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAbsDialogView = null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTopBar(mTopbar, "赞助说明");
        mAdapter = RecyclerViewHelper.initVirtualLayoutManager(getActivity(), mRecyclerView, false);
        findDetails();
    }

    private void findDetails() {
        BmobQuery<M_Y_Notices> query = new BmobQuery<M_Y_Notices>();
        query.addWhereEqualTo("type", "upgradeDetails");
        query.order("sequence");
        query.findObjectsObservable(M_Y_Notices.class)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())//主线程执行
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(o -> {
                    hideLoading();
                })
                .doOnError(throwable -> {
                    hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle((IView) this))
                .subscribe(new Observer<List<M_Y_Notices>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e("onError", throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<M_Y_Notices> m_y_categories) {
                        for (M_Y_Notices m_y_category : m_y_categories) {
                            mAdapter.addAdapter(m_y_category.getItemType() == 0 ? UpgradeHelper.createOption(m_y_category) : UpgradeHelper.createWXOption(m_y_category));
                        }
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
    }

    @Override
    public void upgradeSucceed() {

    }

    @Override
    public void showLoading() {
        mAbsDialogView.showDialog();
    }

    @Override
    public void hideLoading() {
        mAbsDialogView.dismiss();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }


}
