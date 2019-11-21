package com.meetyou.cn.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meetyou.cn.base.BaseActivity;
import com.meetyou.cn.base.BaseEvent;
import com.meetyou.cn.mvp.base.IView;
import com.meetyou.cn.utils.DataCleanManager;
import com.meetyou.cn.utils.EventBusUtils;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.cn.vo.entity.MeetYUser;
import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.integration.lifecycle.Lifecycleable;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundRelativeLayout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.meetyou.com.meetyou.R;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_SUCCESS;

public class SettingsActivity extends BaseActivity implements IView {

    @BindView(R.id.topbar)
    QMUITopBar topbar;
    @BindView(R.id.cacheCount)
    TextView cacheCount;
    @BindView(R.id.layoutClear)
    QMUIRoundRelativeLayout layoutClear;
    @BindView(R.id.layoutLogout)
    QMUIRoundRelativeLayout layoutLogout;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_settings;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTopBar(topbar, "设置");
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            cacheCount.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addRxClick(layoutClear);
        addRxClick(layoutLogout);
        layoutLogout.setVisibility(MeetYUser.getCurrentUser() == null?View.GONE:View.VISIBLE);
    }

    @Override
    public void onRxClick(View view) {
        switch (view.getId()) {
            case R.id.layoutClear:
                Observable.just(1)
                        .observeOn(Schedulers.io())
                        .map(integer -> DataCleanManager.clearAllCache(getActivity()))
                        .compose(RxLifecycleUtils.bindToLifecycle((Lifecycleable) this))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                try {
                                    cacheCount.setText(DataCleanManager.getTotalCacheSize(getActivity()));
                                    showTipDialog("清理缓存完成", ICON_TYPE_SUCCESS, false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                break;
            case R.id.layoutLogout:
                MeetYUser.getCurrentUser().logOut();
                EventBusUtils.post(BaseEvent.CommonEvent.LOGOUT,"refresh_login_status");
                showTipDialog("注销成功", ICON_TYPE_SUCCESS, true);
                break;
        }
    }

    private void showTipDialog(String message, @QMUITipDialog.Builder.IconType int iconType, boolean close) {
        QMUITipDialog dialog = new QMUITipDialog.Builder(this)
                .setIconType(iconType)
                .setTipWord(message)
                .create();
        dialog.show();
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .compose(RxLifecycleUtils.bindToLifecycle((IView) this))
                .subscribe(aLong -> {
                    dialog.dismiss();
                    if (close) {
                        finish();
                    }
                });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
