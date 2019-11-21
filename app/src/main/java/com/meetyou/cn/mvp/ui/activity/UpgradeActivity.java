package com.meetyou.cn.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.meetyou.cn.base.BaseActivity;
import com.meetyou.cn.base.BaseEvent;
import com.meetyou.cn.di.component.DaggerMineComponent;
import com.meetyou.cn.di.module.MineModule;
import com.meetyou.cn.mvp.base.IView;
import com.meetyou.cn.mvp.interfaces.IMine;
import com.meetyou.cn.mvp.presenter.MinePresenterImpl;
import com.meetyou.cn.mvp.ui.widget.AbsDialogView;
import com.meetyou.cn.mvp.ui.widget.MeetYLoadingDialog;
import com.meetyou.cn.utils.EventBusUtils;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.library.di.component.AppComponent;
import com.qmuiteam.qmui.alpha.QMUIAlphaTextView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.meetyou.com.meetyou.R;
import rx.Observable;

/**
 * Created by admin on 2018/5/7.
 */

public class UpgradeActivity extends BaseActivity<MinePresenterImpl> implements IMine.View {
    @BindView(R.id.topbar)
    QMUITopBar topbar;
    @BindView(R.id.editCode)
    EditText editCode;
    @BindView(R.id.confirm)
    QMUIAlphaTextView confirm;
    private AbsDialogView mAbsDialogView;
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        mAbsDialogView = new MeetYLoadingDialog();
        DaggerMineComponent.builder()
                .mineModule(new MineModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_upgrade;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTopBar(topbar, "成为赞助会员");
        addRightTextButton(topbar, "赞助说明");
        addRxClick(confirm);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAbsDialogView = null;
    }

    @Override
    public void onTitleRigthClick(View v) {
        startActivity(new Intent(this,UpgradeDetailsActivity.class));
    }

    @Override
    public void onRxClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                final String code = editCode.getText().toString();
                if(TextUtils.isEmpty(code)){
                    showTipDialog("还没输入兑换码呢~",QMUITipDialog.Builder.ICON_TYPE_FAIL,false);
                    return;
                }
                mPresenter.upgrade(code);
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

    @Override
    public void upgradeSucceed() {
        EventBusUtils.post(BaseEvent.CommonEvent.LOGIN,"refresh_login_status");
        showTipDialog("兑换成功，正在刷新信息中..",QMUITipDialog.Builder.ICON_TYPE_SUCCESS,true);
    }
}
