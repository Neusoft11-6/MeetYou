package com.meetyou.cn.mvp.ui.helper;

import android.support.annotation.NonNull;

import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.meetyou.cn.adapter.item.CoversItem;
import com.meetyou.cn.base.adapter.DelegateRecAdapter;
import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.mvp.ui.widget.OnItemClickListener;
import com.meetyou.cn.vo.entity.M_Y_Covers;

import java.util.List;

/**
 * Created by admin on 2018/4/24.
 */

public class CoversHelper  {

    private final static  int ITEM_TYPE_LIST = 1;

    public static DelegateRecAdapter createCoversItem(List<M_Y_Covers> mData, OnItemClickListener listener){
        GridLayoutHelper mLayoutHelper = new GridLayoutHelper(2);
//        mLayoutHelper.setGap(QMUIDisplayHelper.dpToPx(10));
//        mLayoutHelper.setHGap(QMUIDisplayHelper.dpToPx(10));
//        mLayoutHelper.setMargin(20, 10, 10, 10);
        mLayoutHelper.setAutoExpand(false);
        return new DelegateRecAdapter<M_Y_Covers>(mData, mLayoutHelper) {
            @NonNull
            @Override
            public DelegateAdapterItem createItem(Object type) {
                return new CoversItem(listener);
            }

            @Override
            public int getItemType(M_Y_Covers o) {
                return ITEM_TYPE_LIST;
            }
        };
    }
}
