package com.meetyou.cn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.meetyou.cn.base.BaseLazyFragment;
import com.meetyou.cn.base.adapter.DelegateRecAdapter;
import com.meetyou.cn.di.component.DaggerCoversComponent;
import com.meetyou.cn.di.module.CoversModule;
import com.meetyou.cn.mvp.interfaces.ICovers;
import com.meetyou.cn.mvp.presenter.CoversPresenterImpl;
import com.meetyou.cn.mvp.ui.helper.CoversHelper;
import com.meetyou.cn.mvp.ui.helper.RecyclerViewHelper;
import com.meetyou.cn.mvp.ui.widget.EmptyRecyclerView;
import com.meetyou.cn.mvp.ui.widget.SharpWrapper;
import com.meetyou.cn.utils.CommonUtils;
import com.meetyou.cn.vo.entity.Category;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.widget.pulltorefresh.MeetYouPtrFrameLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.meetyou.com.meetyou.R;

public class CoversFragment extends BaseLazyFragment<CoversPresenterImpl> implements ICovers.View<M_Y_Covers> {

    public static Fragment newInstance(Serializable serializable) {
        Fragment fragment = new CoversFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable("category", serializable);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.meetYou_head_frame)
    MeetYouPtrFrameLayout meetYouHeadFrame;
    private List<M_Y_Covers> mDataList = new ArrayList<>();
    private DelegateRecAdapter listAdapter;
    private Category.DataBean mYCategory;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerCoversComponent.builder()
                .coversModule(new CoversModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
        final Bundle arguments = getArguments();
        if (arguments != null) {
            mYCategory = (Category.DataBean) arguments.getSerializable("category");
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_common_refresh_list;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mRecyclerView.setPadding(QMUIDisplayHelper.dpToPx(5),QMUIDisplayHelper.dpToPx(5),QMUIDisplayHelper.dpToPx(5),0);
        final DelegateAdapter delegateAdapter = RecyclerViewHelper.initVirtualLayoutManager(getActivity(), mRecyclerView, false);
        delegateAdapter.addAdapter(listAdapter = CoversHelper.createCoversItem(mDataList,null));
        SharpWrapper mLoadMoreWrapper = new SharpWrapper(getActivity(), delegateAdapter);
        mRecyclerView.setAdapter(mLoadMoreWrapper, EmptyRecyclerView.MODE_AUTO);
        mLoadMoreWrapper.disableLoadMoreIfNotFullPage();
        mRecyclerView.setOnLoadingPageListener(new EmptyRecyclerView.OnLoadingPageListener() {
            @Override
            public void onRefresh(int page) {
                if (mYCategory != null) {
                    mPresenter.findCovers(mYCategory, 0, false);
                }
            }

            @Override
            public void onLoadMore(int page) {
                if (mYCategory != null) {
                    mPresenter.findCovers(mYCategory, (page - 1) * 20, false);
                }
            }
        });
        meetYouHeadFrame.setPtrHandler(mRecyclerView);
        meetYouHeadFrame.autoRefreshDelay(200);
    }


    @Override
    public void dataCallback(List<M_Y_Covers> list) {
        try {
            final List<M_Y_Covers> transform = CommonUtils.transform(list);
            mDataList.clear();
            mDataList.addAll(transform);
            if (transform.size() > 0) getSharpWrapper().setEnableLoadMore(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyDo(false,list);
        }
    }

    @Override
    public void nextDataCallback(List<M_Y_Covers> list) {
        try {
            final List<M_Y_Covers> transform = CommonUtils.transform(list);
            if (transform.size() > 0) {
                mDataList.addAll(transform);
            }else{
                getSharpWrapper().loadMoreEnd();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyDo(true,list);
        }
    }

    @Override
    public void likeCallback(int position, String coversId) {

    }

    private void finallyDo(boolean isNext,List<M_Y_Covers> list){
        final SharpWrapper originAdapter = getSharpWrapper();
        if (originAdapter != null) {
            if (originAdapter.isLoadMoreEnable() && (originAdapter.getLoadMoreStatus() == LoadMoreView.STATUS_DEFAULT || originAdapter.getLoadMoreStatus() == LoadMoreView.STATUS_LOADING)) {
                originAdapter.loadMoreComplete();
            }
            if (isNext) {
                if (list == null) originAdapter.loadMoreFail();
            }
        }
        listAdapter.notifyDataSetChanged();
        originAdapter.notifyDataSetChanged();
        meetYouHeadFrame.refreshComplete();
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

    private SharpWrapper getSharpWrapper() {
        return (SharpWrapper) mRecyclerView.getAdapter();
    }

}
