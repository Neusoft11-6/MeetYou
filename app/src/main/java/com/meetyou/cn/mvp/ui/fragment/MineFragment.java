package com.meetyou.cn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.meetyou.cn.base.BaseEvent;
import com.meetyou.cn.base.BaseFragment;
import com.meetyou.cn.base.adapter.DelegateRecAdapter;
import com.meetyou.cn.di.component.DaggerMineComponent;
import com.meetyou.cn.di.module.MineModule;
import com.meetyou.cn.mvp.interfaces.IMine;
import com.meetyou.cn.mvp.presenter.MinePresenterImpl;
import com.meetyou.cn.mvp.ui.activity.AboutUsActivity;
import com.meetyou.cn.mvp.ui.activity.MyLikesActivity;
import com.meetyou.cn.mvp.ui.activity.SettingsActivity;
import com.meetyou.cn.mvp.ui.activity.UpgradeActivity;
import com.meetyou.cn.mvp.ui.helper.MineHelper;
import com.meetyou.cn.mvp.ui.helper.RecyclerViewHelper;
import com.meetyou.cn.mvp.ui.widget.EmptyRecyclerView;
import com.meetyou.cn.mvp.ui.widget.OnCommitListener;
import com.meetyou.cn.utils.CommonUtils;
import com.meetyou.cn.vo.MineOptionInfo;
import com.meetyou.library.di.component.AppComponent;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBar;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.Tempest;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.meetyou.com.meetyou.R;

import static com.meetyou.cn.mvp.ui.helper.MineHelper.OPTION_ABOUT;
import static com.meetyou.cn.mvp.ui.helper.MineHelper.OPTION_LIKES;
import static com.meetyou.cn.mvp.ui.helper.MineHelper.OPTION_SETTINGS;
import static com.meetyou.cn.mvp.ui.helper.MineHelper.OPTION_TRANSFORM;

public class MineFragment extends BaseFragment<MinePresenterImpl> implements IMine.View, OnCommitListener {


    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;
    private DelegateAdapter mAdapter;
    private DelegateRecAdapter infoAdapter;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMineComponent.builder()
                .mineModule(new MineModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Subscriber(tag = "refresh_login_status")
    private void event(BaseEvent.CommonEvent event) {
        if (event == BaseEvent.CommonEvent.LOGIN || event == BaseEvent.CommonEvent.LOGOUT) {
            infoAdapter.notifyDataSetChanged();
            fetchUserInfo();
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTopbar.setTitle("我的");
        initAdapter();
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = RecyclerViewHelper.initVirtualLayoutManager(getActivity(), mRecyclerView, false);
        }
        mAdapter.clear();
        mAdapter.addAdapter(infoAdapter = MineHelper.createUserInfo());
        mAdapter.addAdapter(MineHelper.createOption(new MineOptionInfo("收藏", OPTION_LIKES, R.mipmap.ic_my_likes), this));
        mAdapter.addAdapter(MineHelper.createOption(new MineOptionInfo("成为赞助会员", OPTION_TRANSFORM, R.mipmap.ic_transform), this));
        mAdapter.addAdapter(MineHelper.createOption(new MineOptionInfo("关于", OPTION_ABOUT, R.mipmap.ic_about_us), this));
        mAdapter.addAdapter(MineHelper.createOption(new MineOptionInfo("设置", OPTION_SETTINGS, R.mipmap.ic_settings), this));
        mRecyclerView.setAdapter(mAdapter, EmptyRecyclerView.MODE_AUTO);
        fetchUserInfo();
    }

    private void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    new Tempest().Code("user",s);
                    Logger.e("Newest UserInfo is " + s);
                    infoAdapter.notifyDataSetChanged();
                } else {
                    Logger.e(e.getMessage());
                }
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
    public void onCommit(int type, Object value) {
        switch (type) {
            case OPTION_LIKES:
                CommonUtils.checkLoginIntent(new Intent(getActivity(), MyLikesActivity.class));
                break;
            case OPTION_TRANSFORM:
                CommonUtils.checkLoginIntent(new Intent(getActivity(), UpgradeActivity.class));
                break;
            case OPTION_SETTINGS:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case OPTION_ABOUT:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
        }
    }

    @Override
    public void upgradeSucceed() {

    }
}
