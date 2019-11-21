package com.meetyou.cn.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.meetyou.cn.mvp.base.IPresenter;
import com.meetyou.cn.utils.MyStringUtils;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.integration.lifecycle.ActivityLifecycleable;
import com.meetyou.library.utils.MeetYouUtils;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.trello.rxlifecycle.android.ActivityEvent;

import org.simple.eventbus.EventBus;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

import static com.meetyou.cn.utils.ThirdViewUtil.convertAutoView;

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements ActivityLifecycleable {

    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Unbinder mUnbinder;
    @Inject
    protected P mPresenter;

    @NonNull
    @Override
    public Subject<ActivityEvent, ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
            if (useEventBus())//如果要使用eventbus请将此方法返回true
                EventBus.getDefault().register(this);//注册到事件主线
            setupActivityComponent(MeetYouUtils.obtainAppComponentFromContext(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        if (useEventBus())
            EventBus.getDefault().unregister(this);
    }

    protected boolean useEventBus(){
        return true;
    }

    protected abstract int initView(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);

    public void initTopBar(QMUITopBar topBar, String title) {
        initTopBar(topBar, title, null);
    }

    public void initTopBar(QMUITopBar topBar, String title, String suString) {
        if (topBar != null) {
            topBar.setTitle(MyStringUtils.checkNull(title));
            if (!TextUtils.isEmpty(suString)) {
                topBar.setSubTitle(MyStringUtils.checkNull(suString));
            }else{
                topBar.setTitleGravity(Gravity.CENTER);
            }
            topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTitleFinishClick(v);
                    finish();
                }
            });
        }
    }

    public void addRightTextButton(QMUITopBar topBar, String text) {
        topBar.addRightTextButton(text, QMUIViewHelper.generateViewId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleRigthClick(v);
            }
        });
    }

    public void addRightSingleImageButton(QMUITopBar topBar, int drawableResId) {
        topBar.addRightImageButton(drawableResId, QMUIViewHelper.generateViewId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleRigthClick(v);
            }
        });
    }

    public void onTitleRigthClick(View v) {

    }

    public void onTitleFinishClick(View v) {

    }

    public void addRxClick(final View view){
        RxView.clicks(view)
                .throttleFirst(1, TimeUnit.SECONDS)
                .map(new Func1<Void, View>() {
                    @Override
                    public View call(Void aVoid) {
                        return view;
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(this))
                .subscribe(new Observer<View>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(View view) {
                        onRxClick(view);
                    }
                });
    }

    public void onRxClick(View view) {

    }
}
