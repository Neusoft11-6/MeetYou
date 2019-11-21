package com.meetyou.cn.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.meetyou.GPreviewBuilder;
import com.meetyou.cn.base.BaseActivity;
import com.meetyou.cn.base.adapter.DelegateRecAdapter;
import com.meetyou.cn.di.component.DaggerCoversComponent;
import com.meetyou.cn.di.module.CoversModule;
import com.meetyou.cn.mvp.base.IView;
import com.meetyou.cn.mvp.interfaces.ICovers;
import com.meetyou.cn.mvp.presenter.CoversPresenterImpl;
import com.meetyou.cn.mvp.ui.helper.PhotosHelper;
import com.meetyou.cn.mvp.ui.helper.RecyclerViewHelper;
import com.meetyou.cn.mvp.ui.widget.AbsDialogView;
import com.meetyou.cn.mvp.ui.widget.EmptyRecyclerView;
import com.meetyou.cn.mvp.ui.widget.MeetYLoadingDialog;
import com.meetyou.cn.mvp.ui.widget.OnItemClickListener;
import com.meetyou.cn.mvp.ui.widget.SharpWrapper;
import com.meetyou.cn.utils.CommonUtils;
import com.meetyou.cn.utils.RxLifecycleUtils;
import com.meetyou.cn.vo.entity.ImgViewInfo;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.meetyou.cn.vo.entity.Storage;
import com.meetyou.library.di.component.AppComponent;
import com.meetyou.library.widget.pulltorefresh.MeetYouPtrFrameLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.meetyou.com.meetyou.R;
import rx.Observable;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_INFO;

public class PhotosActivity extends BaseActivity<CoversPresenterImpl> implements ICovers.View<Storage.DataBean>, OnItemClickListener {

    public static void start(Context mtx, Serializable serializable) {
        Intent intent = new Intent(mtx, PhotosActivity.class);
        intent.putExtra("cover", serializable);
        mtx.startActivity(intent);
    }


    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.meetYou_head_frame)
    MeetYouPtrFrameLayout meetYouHeadFrame;
    private AbsDialogView mAbsDialogView;
    private List<Storage.DataBean> mDataList = new ArrayList<>();
    private List<ImgViewInfo> mImgUrlList = new ArrayList<>();//图片地址
    private DelegateRecAdapter listAdapter, detailsAdapter;
    private M_Y_Covers mYCovers;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        mYCovers = (M_Y_Covers) getIntent().getSerializableExtra("cover");
        mAbsDialogView = new MeetYLoadingDialog();
        DaggerCoversComponent.builder()
                .coversModule(new CoversModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.layout_common_topbar_refresh_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTopBar(mTopbar, "图组");
        mTopbar.addRightImageButton(R.mipmap.ic_more_n,R.id.META).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QMUIBottomSheet.BottomListSheetBuilder(getActivity())
                        .addItem("收藏")
                        .addItem("取消")
                        .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                            @Override
                            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                                dialog.dismiss();
                                if(CommonUtils.checkLoginDialog()){
                                    if(position == 0){
                                        mPresenter.likes(mYCovers,0,true,true);
                                    }
                                }
                            }
                        })
                        .build()
                        .show();
            }
        });
        final DelegateAdapter delegateAdapter = RecyclerViewHelper.initVirtualLayoutManager(this, mRecyclerView, false);
        delegateAdapter.addAdapter(detailsAdapter = PhotosHelper.createDetails(mYCovers));
        delegateAdapter.addAdapter(listAdapter = PhotosHelper.createImgItem(mDataList, this));
//        delegateAdapter.addAdapter(PhotosHelper.createBottomPanel(null));
        SharpWrapper mLoadMoreWrapper = new SharpWrapper(this, delegateAdapter);
        mRecyclerView.setAdapter(mLoadMoreWrapper, EmptyRecyclerView.MODE_AUTO);
        mRecyclerView.setOffset(20, 1);
        mLoadMoreWrapper.disableLoadMoreIfNotFullPage();
        mRecyclerView.setOnLoadingPageListener(new EmptyRecyclerView.OnLoadingPageListener() {
            @Override
            public void onRefresh(int page) {
                if (mYCovers != null) {
                    mPresenter.findImgByMethod(mYCovers.getIds(), 0, false);
                }
            }

            @Override
            public void onLoadMore(int page) {
                if (mYCovers != null) {
                    mPresenter.findImgByMethod(mYCovers.getIds(), (page - 1) * 20, false);
                }
            }
        });
        meetYouHeadFrame.setPtrHandler(mRecyclerView);
        meetYouHeadFrame.autoRefreshDelay(200);
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
        QMUITipDialog dialog = new QMUITipDialog.Builder(this)
                .setIconType(ICON_TYPE_INFO)
                .setTipWord(message)
                .create();
        dialog.show();
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .compose(RxLifecycleUtils.bindToLifecycle((IView) this))
                .subscribe(aLong -> {
                    dialog.dismiss();
                });
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
    public void dataCallback(List<Storage.DataBean> list) {
        try {
            final List<Storage.DataBean> transform = CommonUtils.transform(list);
            mDataList.clear();
            mImgUrlList.clear();
            mDataList.addAll(transform);
            for (Storage.DataBean m_y_storage : transform) {
                mImgUrlList.add(new ImgViewInfo(m_y_storage.imgUrl,m_y_storage.referer));
            }
            if (transform.size() > 0) getSharpWrapper().setEnableLoadMore(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyDo(false, list);
        }
    }

    @Override
    public void nextDataCallback(List<Storage.DataBean> list) {
        try {
            final List<Storage.DataBean> transform = CommonUtils.transform(list);
            if (transform.size() > 0) {
                mDataList.addAll(transform);
                for (Storage.DataBean m_y_storage : transform) {
                    mImgUrlList.add(new ImgViewInfo(m_y_storage.imgUrl,m_y_storage.referer));
                }
            } else {
                getSharpWrapper().loadMoreEnd();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyDo(true, list);
        }
    }

    @Override
    public void likeCallback(int position, String coversId) {

    }

    private void finallyDo(boolean isNext, List<Storage.DataBean> list) {
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

    private SharpWrapper getSharpWrapper() {
        return (SharpWrapper) mRecyclerView.getAdapter();
    }

    @Override
    public void onItemClick(View view, int position) {
        GPreviewBuilder.from(this)
                .setData(mImgUrlList)
                .setCurrentIndex(position)
                .setSingleFling(true)
                .setType(GPreviewBuilder.IndicatorType.Number)
                .start();
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

}
