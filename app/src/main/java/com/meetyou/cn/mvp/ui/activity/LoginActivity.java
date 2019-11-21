package com.meetyou.cn.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.meetyou.cn.base.BaseActivity;
import com.meetyou.cn.base.BaseEvent;
import com.meetyou.cn.mvp.base.IView;
import com.meetyou.cn.mvp.ui.widget.AbsDialogView;
import com.meetyou.cn.mvp.ui.widget.MeetYLoadingDialog;
import com.meetyou.cn.utils.EventBusUtils;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.cn.vo.entity.MeetYUser;
import com.meetyou.library.di.component.AppComponent;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.alpha.QMUIAlphaTextView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.meetyou.com.meetyou.R;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_FAIL;
import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_SUCCESS;


public class LoginActivity extends BaseActivity implements IView {


    @BindView(R.id.topbar)
    QMUITopBar topbar;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.passWord)
    EditText passWord;
    @BindView(R.id.login)
    QMUIAlphaTextView login;
    private AbsDialogView mAbsDialogView;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        mAbsDialogView = new MeetYLoadingDialog();
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTopBar(topbar, "登录");
        addRightTextButton(topbar, "注册");
        addRxClick(login);
    }


    @Override
    public void onTitleRigthClick(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }


    @Override
    public void onRxClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                String userStr = userName.getText().toString().trim();
                String pswStr = passWord.getText().toString().trim();

                if (checkNull(userStr, pswStr)) {
                    showTipDialog("内容不能为空", ICON_TYPE_FAIL,false);
                    return;
                }
                MeetYUser user = new MeetYUser();
                user.setUsername(userStr);
                user.setPassword(pswStr);
                user.loginObservable(MeetYUser.class)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(this::showLoading)
                        .subscribeOn(AndroidSchedulers.mainThread())//主线程执行
                        .observeOn(AndroidSchedulers.mainThread())
                        .doAfterTerminate(this::hideLoading)
                        .compose(RxLifecycleUtils.bindToLifecycle((IView) this))
                        .subscribe(new Observer<MeetYUser>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                hideLoading();
                                Logger.e("onError", throwable.getMessage());
                                showTipDialog("登录失败,用户名或密码错误", ICON_TYPE_FAIL,false);
                            }

                            @Override
                            public void onNext(MeetYUser object) {
                                hideLoading();
                                if (object != null) {
                                    showTipDialog("登录成功",ICON_TYPE_SUCCESS, true);
                                    EventBusUtils.post(BaseEvent.CommonEvent.LOGIN,"refresh_login_status");
                                } else {
                                    showTipDialog("登录失败", ICON_TYPE_FAIL,false);
                                }
                            }

                        });
                break;
        }
    }

    private boolean checkNull(String... str) {
        for (String s : str) {
            if (TextUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
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
