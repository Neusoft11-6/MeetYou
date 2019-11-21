package com.meetyou.cn.mvp.ui.helper;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.meetyou.cn.adapter.item.mine.UserInfoItem;
import com.meetyou.cn.adapter.item.mine.UserOptionItem;
import com.meetyou.cn.base.adapter.DelegateRecAdapter;
import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.mvp.ui.widget.OnCommitListener;
import com.meetyou.cn.vo.MineOptionInfo;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * Created by admin on 2018/4/24.
 */

public class MineHelper {

    public final static int OPTION_LIKES = 0;
    public final static int OPTION_TRANSFORM = 1;
    public final static int OPTION_SETTINGS = 2;
    public final static int OPTION_ABOUT = 3;

    private final static int ITEM_TYPE_USER_INFO_ITEM = 0;
    private final static int ITEM_TYPE_LIST = 1;

    public static DelegateRecAdapter createUserInfo() {
        SingleLayoutHelper mLayoutHelper = new SingleLayoutHelper();
        int _20dp = QMUIDisplayHelper.dpToPx(20);
        int _15dp = QMUIDisplayHelper.dpToPx(15);
        mLayoutHelper.setMargin(_20dp,_15dp,_20dp,0);
        return new DelegateRecAdapter<M_Y_Covers>(Pair.create(1, null), mLayoutHelper) {
            @NonNull
            @Override
            public DelegateAdapterItem createItem(Object type) {
                return new UserInfoItem();
            }

            @Override
            public int getItemType(M_Y_Covers o) {
                return ITEM_TYPE_USER_INFO_ITEM;
            }
        };
    }

    public static DelegateRecAdapter createOption(MineOptionInfo mOption, OnCommitListener commitListener) {
        SingleLayoutHelper mLayoutHelper = new SingleLayoutHelper();
        int _20dp = QMUIDisplayHelper.dpToPx(20);
        int _15dp = QMUIDisplayHelper.dpToPx(15);
        mLayoutHelper.setMargin(_20dp,_15dp,_20dp,0);
        return new DelegateRecAdapter<MineOptionInfo>(Pair.create(1, mOption), mLayoutHelper) {
            @NonNull
            @Override
            public DelegateAdapterItem createItem(Object type) {
                return new UserOptionItem(commitListener);
            }

            @Override
            public int getItemType(MineOptionInfo o) {
                return ITEM_TYPE_LIST;
            }
        };
    }
}
