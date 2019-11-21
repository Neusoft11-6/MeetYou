package com.meetyou.cn.mvp.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.meetyou.cn.base.BaseActivity;
import com.meetyou.cn.base.adapter.DelegateRecAdapter;
import com.meetyou.cn.di.component.DaggerCoversComponent;
import com.meetyou.cn.di.module.CoversModule;
import com.meetyou.cn.mvp.interfaces.ICovers;
import com.meetyou.cn.mvp.presenter.CoversPresenterImpl;
import com.meetyou.cn.mvp.ui.helper.CoversHelper;
import com.meetyou.cn.mvp.ui.helper.RecyclerViewHelper;
import com.meetyou.cn.mvp.ui.widget.EmptyRecyclerView;
import com.meetyou.cn.mvp.ui.widget.OnItemClickListener;
import com.meetyou.cn.mvp.ui.widget.SharpWrapper;
import com.meetyou.cn.utils.CommonUtils;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.widget.pulltorefresh.MeetYouPtrFrameLayout;
import com.qmuiteam.qmui.alpha.QMUIAlphaRelativeLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.meetyou.com.meetyou.R;

public class MyLikesActivity extends BaseActivity<CoversPresenterImpl> implements ICovers.View<M_Y_Covers>,OnItemClickListener {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.meetYou_head_frame)
    MeetYouPtrFrameLayout meetYouHeadFrame;
    private List<M_Y_Covers> mDataList = new ArrayList<>();
    private DelegateRecAdapter listAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCoversComponent.builder()
                .coversModule(new CoversModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return  R.layout.layout_common_topbar_refresh_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTopBar(mTopbar,"收藏");
        mRecyclerView.setPadding(QMUIDisplayHelper.dpToPx(5),QMUIDisplayHelper.dpToPx(5),QMUIDisplayHelper.dpToPx(5),0);
        final DelegateAdapter delegateAdapter = RecyclerViewHelper.initVirtualLayoutManager(getActivity(), mRecyclerView, false);
        delegateAdapter.addAdapter(listAdapter = CoversHelper.createCoversItem(mDataList,this));
        SharpWrapper mLoadMoreWrapper = new SharpWrapper(getActivity(), delegateAdapter);
        mRecyclerView.setAdapter(mLoadMoreWrapper, EmptyRecyclerView.MODE_AUTO);
        mLoadMoreWrapper.disableLoadMoreIfNotFullPage();
        mRecyclerView.setOnLoadingPageListener(new EmptyRecyclerView.OnLoadingPageListener() {
            @Override
            public void onRefresh(int page) {
                    mPresenter.findLikes(0);
            }

            @Override
            public void onLoadMore(int page) {
                    mPresenter.findLikes((page - 1) * 20);
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
        mDataList.remove(position);
        listAdapter.notifyDataSetChanged();
        getSharpWrapper().notifyDataSetChanged();
    }

    private SharpWrapper getSharpWrapper() {
        return (SharpWrapper) mRecyclerView.getAdapter();
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

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {
        ((QMUIAlphaRelativeLayout)view).setEnabled(false);
        new QMUIBottomSheet.BottomListSheetBuilder(getActivity())
                .addItem("取消收藏")
                .addItem("取消")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int itemPosition, String tag) {
                        dialog.dismiss();
                        if(itemPosition == 0){
                            mPresenter.likes(mDataList.get(position),position,false,true);
                        }
                    }
                })
                .setOnBottomDialogDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        ((QMUIAlphaRelativeLayout)view).setEnabled(true);
                    }
                })
                .build()
                .show();
    }
}
