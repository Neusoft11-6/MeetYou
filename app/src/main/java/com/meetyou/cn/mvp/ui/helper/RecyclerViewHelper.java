package com.meetyou.cn.mvp.ui.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutViewFactory;
import com.alibaba.android.vlayout.VirtualLayoutManager;

/**
 * Created by admin on 2018/3/28.
 */

public class RecyclerViewHelper {

    /**
     * 初始化使用Vlayout的RecyclerView
     * @param mContext
     * @param mRecyclerView
     * @param useBackground
     * @return 返回Vlayout自带的adapter
     */
    public static DelegateAdapter initVirtualLayoutManager(Context mContext, RecyclerView mRecyclerView, boolean useBackground){
        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);

        if(useBackground){
            layoutManager.setLayoutViewFactory(new LayoutViewFactory() {
                @Override
                public View generateLayoutView(@NonNull Context context) {
                    return new AppCompatImageView(context);
                }
            });
        }

        mRecyclerView.setLayoutManager(layoutManager);
        return new DelegateAdapter(layoutManager, true);
    }

}
