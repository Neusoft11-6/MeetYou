package com.meetyou.cn.mvp.ui.helper;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.meetyou.cn.adapter.item.upgrade.ContentItem;
import com.meetyou.cn.adapter.item.upgrade.WXItem;
import com.meetyou.cn.base.adapter.DelegateRecAdapter;
import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.vo.MineOptionInfo;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.meetyou.cn.vo.entity.M_Y_Notices;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * Created by admin on 2018/4/24.
 */

public class UpgradeHelper {

    public final static int OPTION_CONTENT = 0;
    public final static int OPTION_BUTTON = 1;

    private final static int ITEM_TYPE_CONTENT = 0;
    private final static int ITEM_TYPE_BUTTON = 1;

    public static DelegateRecAdapter createOption(M_Y_Notices notices) {
        SingleLayoutHelper mLayoutHelper = new SingleLayoutHelper();
        int _20dp = QMUIDisplayHelper.dpToPx(15);
        int _15dp = QMUIDisplayHelper.dpToPx(15);
        mLayoutHelper.setMargin(_15dp,_15dp,_15dp,0);
        return new DelegateRecAdapter<M_Y_Covers>(Pair.create(1, notices), mLayoutHelper) {
            @NonNull
            @Override
            public DelegateAdapterItem createItem(Object type) {
                return new ContentItem();
            }

            @Override
            public int getItemType(M_Y_Covers o) {
                return ITEM_TYPE_CONTENT;
            }
        };
    }

    public static DelegateRecAdapter createWXOption(M_Y_Notices notices) {
        SingleLayoutHelper mLayoutHelper = new SingleLayoutHelper();
        int _15dp = QMUIDisplayHelper.dpToPx(15);
        mLayoutHelper.setMargin(_15dp,_15dp,_15dp,0);
        return new DelegateRecAdapter<MineOptionInfo>(Pair.create(1, notices), mLayoutHelper) {
            @NonNull
            @Override
            public DelegateAdapterItem createItem(Object type) {
                return new WXItem();
            }

            @Override
            public int getItemType(MineOptionInfo o) {
                return ITEM_TYPE_BUTTON;
            }
        };
    }
}
