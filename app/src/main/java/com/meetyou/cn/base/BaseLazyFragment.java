package com.meetyou.cn.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewStubCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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

public abstract class BaseLazyFragment<P extends IPresenter> extends Fragment implements FragmentLifecycleable,IFragment {

    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Unbinder mUnbinder;
    @Inject
    protected P mPresenter;
    // 检测声明周期中，是否已经构建视图
    private boolean mViewCreated = false;

    // 占位图
    private ViewStubCompat mViewStub;

    @NonNull
    @Override
    public Subject<FragmentEvent, FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentComponent(MeetYouUtils.obtainAppComponentFromContext(getActivity()));
    }

    protected abstract void setupFragmentComponent(AppComponent appComponent);

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = inflater.getContext();
        FrameLayout root = new FrameLayout(context);
        mViewStub = new ViewStubCompat(context, null);
        mViewStub.setLayoutResource(getLayoutRes());
        root.addView(mViewStub, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        root.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));

        mViewCreated = true;
        if (mUserVisible) {
            realLoad();
        }
        return root;
    }


    private boolean mUserVisible = false;


    @Override
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mUserVisible = isVisibleToUser;
        if (mUserVisible && mViewCreated) {
            realLoad();
        }
    }

    // 判断是否已经加载
    private boolean mLoaded = false;


    /**
     * 控制只允许加载一次
     */
    @SuppressLint("RestrictedApi")
    private void realLoad() {
        if (mLoaded) {
            return;
        }
        mLoaded = true;
        View createView = null;
        createView = mViewStub.inflate();

        if(createView !=null){
            mUnbinder = ButterKnife.bind(this, createView);
        }

        initData(null);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onDestroyView() {
        mViewCreated = false;
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
