package com.meetyou.cn.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meetyou.cn.mvp.base.IPresenter;
import com.meetyou.library.base.IFragment;
import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.integration.lifecycle.FragmentLifecycleable;
import com.meetyou.library.utils.MeetYouUtils;
import com.trello.rxlifecycle.android.FragmentEvent;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

public abstract class BaseFragment<P extends IPresenter> extends Fragment implements FragmentLifecycleable,IFragment{

    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Unbinder mUnbinder;
    @Inject
    protected P mPresenter;

    @NonNull
    @Override
    public Subject<FragmentEvent, FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册到事件主线
        setupFragmentComponent(MeetYouUtils.obtainAppComponentFromContext(getActivity()));
    }

    protected abstract void setupFragmentComponent(AppComponent appComponent);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
    }

    protected View createViewBefore(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View createView = null;
        createView = createViewBefore(inflater, container, savedInstanceState);
        if (createView == null) {
            createView = inflater.inflate(getLayoutRes(), container, false);
        }
        if(createView !=null){
            mUnbinder = ButterKnife.bind(this, createView);
        }
        return createView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null && mUnbinder != mUnbinder.EMPTY) {
            try {
                mUnbinder.unbind();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                //fix Bindings already cleared
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);//注册到事件主线
        this.mUnbinder = null;
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void setData(Object data) {

    }
}
