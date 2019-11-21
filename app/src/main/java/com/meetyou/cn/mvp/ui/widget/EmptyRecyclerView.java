package com.meetyou.cn.mvp.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.meetyou.library.widget.pulltorefresh.PtrFrameLayout;
import com.meetyou.library.widget.pulltorefresh.PtrHandler;
import com.orhanobut.logger.Logger;


/**
 * Created by admin on 2018/3/16.
 */

public class EmptyRecyclerView extends RecyclerView implements PtrHandler {

    public static final int MODE_AUTO = 1 << 1;//自动加载
    public static final int MODE_PULL_UP = 1 << 2;//手动加载

    private int offsetItemCount = 0;//需要剔除的item数量；默认为0
    private int rowCount = 20;//一页的数量，默认为20
    private int oldItem = 0;

    private SharpWrapper mSharpWrapper;
    private OnLoadingPageListener mOnLoadingPageListener;

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(RecyclerView.Adapter adapter, int mode) {
        offsetItemCount = 0;

        if(adapter instanceof SharpWrapper){
            mSharpWrapper = (SharpWrapper) adapter;
            if (mode == MODE_AUTO) {
                mSharpWrapper.setOnLoadMoreListener(createSharpWrapperLoadMoreListener(), this);
                offsetItemCount++;//默认给显示更多view +1
            }
        }
        setAdapter(adapter);
    }

    public void setOnLoadingPageListener(OnLoadingPageListener mOnLoadingPageListener) {
        this.mOnLoadingPageListener = mOnLoadingPageListener;
    }

    public OnLoadingPageListener  getOnLoadingPageListener(){
        return  mOnLoadingPageListener;
    }

    /**
     * @param rowCount        一页的数量，默认为10
     * @param offsetItemCount 需要剔除的item数量；默认为0
     */
    public void setOffset(int rowCount, int offsetItemCount) {
        this.rowCount = rowCount;
        this.offsetItemCount += offsetItemCount;
    }

    private SharpWrapper.RequestLoadMoreListener createSharpWrapperLoadMoreListener() {
        SharpWrapper.RequestLoadMoreListener   mLoadMoreListener = new SharpWrapper.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (mSharpWrapper == null)
                        return;
                    final int itemCount = mSharpWrapper.getItemCount();
                    if (itemCount - offsetItemCount <= 0) {
                        mSharpWrapper.loadMoreEnd(false);
                        return;
                    } else if ((itemCount - offsetItemCount) <= rowCount) {
                        oldItem = 0;
                    }
                    final int newItem = itemCount - offsetItemCount - oldItem;
                    final int page = getPage(itemCount - offsetItemCount, rowCount);
                    Logger.e("itemCount:"+itemCount + "  offsetItemCount:"+offsetItemCount
                            + "  oldItem:"+oldItem + " newItem:"+newItem + " page:"+page
                    );
                    if (newItem > 0) {
                        if (newItem < rowCount) {//没有下一页
                            mSharpWrapper.loadMoreEnd(false);
                            Logger.w("没有下一页");
                        } else {
                            if(newItem%rowCount>0){
                                mSharpWrapper.loadMoreEnd(false);
                                Logger.w("没有下一页");
                            }else if (mOnLoadingPageListener != null) {
                                mOnLoadingPageListener.onLoadMore(page);
                                Logger.w("有下一页:" + page);
                            }
                        }
                        oldItem = itemCount - offsetItemCount;
                    }  else {
                        if (mOnLoadingPageListener != null) {
                            mOnLoadingPageListener.onLoadMore(page);
                        }
                        Logger.w("第一页:" + page);
                    }
                }
            };
        return mLoadMoreListener;
    }

    private int getPage(int itemCount, int rowcount) {
        int page = 0;
        double temp = (double) itemCount / rowcount;
        if ((temp % 1) > 0) {
            page = (int) (temp + 2);
        } else {
            page = (int) (temp + 1);
        }
        return page;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        oldItem = 0;

        if (mSharpWrapper != null&&mSharpWrapper.isLoadMoreEnable()) {
            mSharpWrapper.setEnableLoadMore(true);
            mSharpWrapper.loadMoreComplete();
        }

        if (mOnLoadingPageListener != null) {
            mOnLoadingPageListener.onRefresh(1);
        }
    }

    public interface OnLoadingPageListener {
        void onRefresh(int page);

        void onLoadMore(int page);
    }
}
