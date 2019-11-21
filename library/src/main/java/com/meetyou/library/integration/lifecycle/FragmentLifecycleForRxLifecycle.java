/**
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.meetyou.library.integration.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.meetyou.library.base.IFragment;
import com.trello.rxlifecycle.android.FragmentEvent;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subjects.Subject;



public class FragmentLifecycleForRxLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    private Unbinder mUnbinder;

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.ATTACH);
        }

    }


    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.CREATE);
        }
        if(f instanceof IFragment){
            if (obtainIFragment(f).useEventBus())//如果要使用eventbus请将此方法返回true
                EventBus.getDefault().register(obtainIFragment(f));//注册到事件主线
        }
    }



    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.CREATE_VIEW);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.START);
        }
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.RESUME);
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.PAUSE);
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.STOP);
        }
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.DESTROY_VIEW);
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.DESTROY);
        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.DETACH);
        }
    }

    private Subject<FragmentEvent,FragmentEvent> obtainSubject(Fragment fragment) {
        return ((FragmentLifecycleable) fragment).provideLifecycleSubject();
    }

    private IFragment obtainIFragment(Fragment f){
        return (IFragment)f;
    }
}
