package com.meetyou.cn.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.meetyou.cn.base.BaseActivity;
import com.meetyou.cn.mvp.base.IView;
import com.meetyou.cn.mvp.ui.widget.AbsDialogView;
import com.meetyou.cn.mvp.ui.widget.MeetYLoadingDialog;
import com.meetyou.cn.utils.DeviceUtils;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.library.di.component.AppComponent;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.alpha.QMUIAlphaTextView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.meetyou.com.meetyou.R;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_FAIL;
import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_SUCCESS;

public class RegisterActivity extends BaseActivity implements IView {


    @BindView(R.id.topbar)
    QMUITopBar topbar;
    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.psw)
    EditText psw;
    @BindView(R.id.againPsw)
    EditText againPsw;
    @BindView(R.id.register)
    QMUIAlphaTextView register;
    private AbsDialogView mAbsDialogView;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        mAbsDialogView = new MeetYLoadingDialog();
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTopBar(topbar,"注册");
        addRxClick(register);
    }


    @Override
    public void onRxClick(View view) {
        switch (view.getId()){
            case R.id.register:
               String nickStr =  nickname.getText().toString().trim();
               String userStr =  userName.getText().toString().trim();
               String pswStr =  psw.getText().toString().trim();
               String apswStr =  againPsw.getText().toString().trim();

               if(checkNull(nickStr,userStr,pswStr,apswStr)){
                   showTipDialog("内容不能为空",ICON_TYPE_FAIL,false);
                   return;
               }

               if(!pswStr.equals(apswStr)){
                   showTipDialog("两次密码不匹配",ICON_TYPE_FAIL,false);
                   return;
               }

                try {
                    JSONObject params = new JSONObject();
                    params.put("nickname",nickStr);
                    params.put("username",userStr);
                    params.put("password",pswStr);
                    params.put("deviceIds",DeviceUtils.getUniqueId());
                    register(params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private boolean checkNull(String... str){
        for (String s : str) {
            if(TextUtils.isEmpty(s)){
                return true;
            }
        }
        return false;
    }


    private void register(JSONObject params){
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        ace.callEndpointObservable("register",params)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(this::showLoading)
                .subscribeOn(AndroidSchedulers.mainThread())//主线程执行
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(this::hideLoading)
                .compose(RxLifecycleUtils.bindToLifecycle((IView) this))
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        hideLoading();
                        Logger.e("onError",throwable.getMessage());
                    }

                    @Override
                    public void onNext(Object object) {
                        hideLoading();
                        if (object != null) {
                            Logger.e(object.toString());
                            JSONObject json = null;
                            try {
                                json = new JSONObject(object.toString());
                                final int code = json.getInt("code");
                                if(code == 200){
                                    showTipDialog("注册成功",ICON_TYPE_SUCCESS,true);
                                }else{
                                    showTipDialog(json.getString("error"),ICON_TYPE_FAIL,false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });
    }

    private void showTipDialog(String message, @QMUITipDialog.Builder.IconType int iconType, boolean close){
        QMUITipDialog dialog = new QMUITipDialog.Builder(this)
                .setIconType(iconType)
                .setTipWord(message)
                .create();
        dialog.show();
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .compose(RxLifecycleUtils.bindToLifecycle((IView) this))
                .subscribe(aLong -> {
                    dialog.dismiss();
                    if(close){
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
    protected void onDestroy() {
        super.onDestroy();
        mAbsDialogView = null;
    }
}
