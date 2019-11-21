package com.meetyou.cn.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import cn.meetyou.com.meetyou.R;


/**
 * Created by admin on 2018/3/16.
 */

public class MYBottomNavigationBar extends BottomNavigationBar {

    public static final int INDEX_HOME = 0;
    public static final int INDEX_TASK = 1;
    public static final int INDEX_LEARN = 2;
    public static final int INDEX_MINE = 3;

    private SparseArray<TextBadgeItem> mBadgeList = new SparseArray<>();

    public MYBottomNavigationBar(Context context) {
        this(context, null);
    }

    public MYBottomNavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MYBottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initialiseTHD(int index) {
        clearAll();
        mBadgeList.clear();
        setMode(BottomNavigationBar.MODE_FIXED);
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        setAutoHideEnabled(false);
        mBadgeList.append(INDEX_HOME, createBadgeItem());
        mBadgeList.append(INDEX_TASK, createBadgeItem());
        mBadgeList.append(INDEX_LEARN, createBadgeItem());
        mBadgeList.append(INDEX_MINE, createBadgeItem());
        addItem(new BottomNavigationItem(R.mipmap.ic_home_list_selet, "图库")
                .setInactiveIconResource(R.mipmap.ic_home_list_n)
                .setInActiveColorResource(R.color.gray)
                .setActiveColorResource(R.color.pink)//选中
                .setBadgeItem(mBadgeList.get(INDEX_HOME)))
       .addItem(new BottomNavigationItem(R.mipmap.ic_home_mine_select, "我的")
                        .setInactiveIconResource(R.mipmap.ic_home_mine_n)
                        .setInActiveColorResource(R.color.gray)
                        .setActiveColorResource(R.color.pink)//选中
                        .setBadgeItem(mBadgeList.get(INDEX_TASK)))
                .setFirstSelectedPosition(index)
                .initialise();
    }

    private TextBadgeItem createBadgeItem() {
        return new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.red_hint)
                .setText("")
                .hide();
    }


}