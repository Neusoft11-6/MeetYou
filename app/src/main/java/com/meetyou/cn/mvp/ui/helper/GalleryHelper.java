package com.meetyou.cn.mvp.ui.helper;

import android.content.Context;

import com.meetyou.cn.vo.entity.Category;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.List;

import cn.meetyou.com.meetyou.R;

public class GalleryHelper {


    private Context mContext;

    public GalleryHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void addTabItem(QMUITabSegment mTHDTabSegment,List<Category.DataBean> mCategoryList){
        for (Category.DataBean m_y_category : mCategoryList) {
            mTHDTabSegment.addTab(createTab(m_y_category.name));
        }
        mTHDTabSegment.notifyDataChanged();
    }

    public void initQMUITabSegment(QMUITabSegment mTHDTabSegment, List<Category.DataBean> mCategoryList, QMUITabSegment.OnTabSelectedListener mListener){
        for (Category.DataBean m_y_category : mCategoryList) {
            mTHDTabSegment.addTab(createTab(m_y_category.name));
        }
        int space = QMUIDisplayHelper.dp2px(mContext, 16);
        mTHDTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);
        mTHDTabSegment.setHasIndicator(true);
        mTHDTabSegment.setItemSpaceInScrollMode(space);
//        mTHDTabSegment.setIndicatorPosition(true);
//        mTHDTabSegment.setIndicatorWidthAdjustContent(true);//
        mTHDTabSegment.addOnTabSelectedListener(mListener);
    }

    private QMUITabSegment.Tab createTab(String name){
        QMUITabSegment.Tab tab = new QMUITabSegment.Tab(name);
        tab.setTextColor(mContext.getResources().getColor(R.color.gray), mContext.getResources().getColor(R.color.pink));
        return tab;
    }
}
