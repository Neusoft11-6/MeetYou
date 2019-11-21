package com.meetyou.cn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.meetyou.cn.adapter.HomePagerAdapter;
import com.meetyou.cn.base.BaseEvent;
import com.meetyou.cn.base.BaseFragment;
import com.meetyou.cn.di.component.DaggerHomeComponent;
import com.meetyou.cn.di.module.HomeModule;
import com.meetyou.cn.mvp.interfaces.IHome;
import com.meetyou.cn.mvp.presenter.HomePresenterImpl;
import com.meetyou.cn.mvp.ui.helper.GalleryHelper;
import com.meetyou.cn.mvp.ui.widget.AbsDialogView;
import com.meetyou.cn.mvp.ui.widget.MeetYLoadingDialog;
import com.meetyou.cn.vo.entity.Category;
import com.meetyou.cn.vo.entity.MeetYUser;
import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.utils.ReflectUtils;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.meetyou.com.meetyou.R;

/**
 * Created by admin on 2018/4/23.
 */

public class HomeFragment extends BaseFragment<HomePresenterImpl> implements IHome.View {
    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.tabSegment)
    QMUITabSegment mTabSegment;
    @BindView(R.id.viewPager)
    QMUIViewPager viewPager;

    private AbsDialogView mAbsDialogView;

    private GalleryHelper mCoversHepler;
    private HomePagerAdapter homePagerAdapter;
    private List<Category.DataBean> mCategoryData = new ArrayList<>();

    @Subscriber(tag = "refresh_login_status")
    private void event(BaseEvent.CommonEvent event) {
        if (event == BaseEvent.CommonEvent.LOGIN || event == BaseEvent.CommonEvent.LOGOUT) {
            mPresenter.findCategory(false);
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTopbar.setTitle(" ").setBackgroundResource(R.mipmap.ic_meetyou);
        mPresenter.findCategory(true);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        mCoversHepler = new GalleryHelper(getActivity());
        mAbsDialogView = new MeetYLoadingDialog();
        DaggerHomeComponent.builder()
                .homeModule(new HomeModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void categoryCallback(List<Category.DataBean> list) {
        if (list == null || list.size() == 0) return;

        if (mCategoryData.size() > 0) {
            adjustCategory(list);
            return;
        }
        mCategoryData.addAll(list);
        mCoversHepler.initQMUITabSegment(mTabSegment, mCategoryData, new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {

            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {

            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
        viewPager.setAdapter(homePagerAdapter = new HomePagerAdapter(getFragmentManager(), mCategoryData));
        mTabSegment.setupWithViewPager(viewPager, false);
//        mTabSegment.setupWithViewPager(viewPager);
    }

    @Override
    public void showLoading() {
        mAbsDialogView.showDialog();
    }

    private void adjustCategory(List<Category.DataBean> list){
        BmobUser currentUser = MeetYUser.getCurrentUser();
        if(currentUser !=null){
            List<Category.DataBean> newCategorys = new ArrayList<>();
            for (Category.DataBean newCategory : list) {
                boolean has = false;
                for (Category.DataBean currentCategory : mCategoryData) {
                    if(currentCategory.objectId.equals(newCategory.objectId)){
                        has = true;
                        break;
                    }
                }
                if(!has){
                    newCategorys.add(newCategory);
                }
            }
            mCategoryData.addAll(newCategorys);
            mCoversHepler.addTabItem(mTabSegment,newCategorys);
            homePagerAdapter.notifyDataSetChanged();
        }else{
            mCategoryData.clear();
            mCategoryData.addAll(list);
            mTabSegment.reset();
            mCoversHepler.addTabItem(mTabSegment,mCategoryData);
            ReflectUtils reflect = ReflectUtils.reflect(mTabSegment);
            reflect.field("mSelectedIndex",Integer.MIN_VALUE);
            reflect.field("mPendingSelectedIndex",Integer.MIN_VALUE);
            homePagerAdapter.notifyDataSetChanged();
            mTabSegment.selectTab(0);
        }


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
}
