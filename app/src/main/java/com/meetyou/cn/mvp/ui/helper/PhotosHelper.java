package com.meetyou.cn.mvp.ui.helper;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.meetyou.cn.adapter.item.photos.PhotosDetailsItem;
import com.meetyou.cn.adapter.item.photos.PhotosItem;
import com.meetyou.cn.base.adapter.DelegateRecAdapter;
import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.mvp.ui.widget.OnItemClickListener;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.meetyou.cn.vo.entity.Storage;

import java.util.List;

/**
 * Created by admin on 2018/4/24.
 */

public class PhotosHelper {

    private final static  int ITEM_TYPE_DETAILS = 1;
    private final static  int ITEM_TYPE_LIST = 2;
    private final static  int ITEM_TYPE_LIKES = 3;

    public static DelegateRecAdapter createImgItem(List<Storage.DataBean> mData, OnItemClickListener onItemClickListener){
        GridLayoutHelper mLayoutHelper = new GridLayoutHelper(2);
        mLayoutHelper.setAutoExpand(false);
        return new DelegateRecAdapter<Storage.DataBean>(mData, mLayoutHelper) {
            @NonNull
            @Override
            public DelegateAdapterItem createItem(Object type) {
                return new PhotosItem(onItemClickListener);
            }

            @Override
            public int getItemType(Storage.DataBean o) {
                return ITEM_TYPE_LIST;
            }
        };
    }

    public static DelegateRecAdapter createDetails(M_Y_Covers mData){
        SingleLayoutHelper mLayoutHelper = new SingleLayoutHelper();
        return new DelegateRecAdapter<M_Y_Covers>(Pair.create(1, mData), mLayoutHelper) {
            @NonNull
            @Override
            public DelegateAdapterItem createItem(Object type) {
                return new PhotosDetailsItem();
            }

            @Override
            public int getItemType(M_Y_Covers o) {
                return ITEM_TYPE_DETAILS;
            }
        };
    }


}
