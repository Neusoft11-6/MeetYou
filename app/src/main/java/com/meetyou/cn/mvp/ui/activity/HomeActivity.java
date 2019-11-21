package com.meetyou.cn.mvp.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.meetyou.cn.adapter.HomeFragmentAdapter;
import com.meetyou.cn.base.BaseActivity;
import com.meetyou.cn.mvp.ui.widget.MYBottomNavigationBar;
import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.fragmentnavigator.FragmentNavigator;

import butterknife.BindView;
import cn.meetyou.com.meetyou.R;

public class HomeActivity extends BaseActivity {


    @BindView(R.id.bottomNavigatorView)
    MYBottomNavigationBar bottomNavigatorView;
    @BindView(R.id.container)
    FrameLayout container;
    private FragmentNavigator mNavigator;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
//        Logger.e(DeviceUtils.getUniqueId());
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mNavigator = new FragmentNavigator(getSupportFragmentManager(), new HomeFragmentAdapter(), R.id.container);
        mNavigator.setDefaultPosition(0);
        mNavigator.onCreate(savedInstanceState);
        bottomNavigatorView.initialiseTHD(0);
        bottomNavigatorView.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                setCurrentTab(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
        setCurrentTab(0);
    }
    private void setCurrentTab(int position) {
        mNavigator.showFragment(position);
    }



}
