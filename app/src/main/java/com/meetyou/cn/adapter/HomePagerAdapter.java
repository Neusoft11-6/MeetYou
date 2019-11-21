package com.meetyou.cn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.meetyou.cn.base.LazyFragmentPagerAdapter;
import com.meetyou.cn.mvp.ui.fragment.CoversFragment;
import com.meetyou.cn.vo.entity.Category;

import java.util.List;

public class HomePagerAdapter extends LazyFragmentPagerAdapter {

    private List<Category.DataBean> mDataList ;

    public HomePagerAdapter(FragmentManager fm,List<Category.DataBean> mDataList) {
        super(fm);
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList!=null?mDataList.size():0;
    }

    @Override
    public Fragment getItem(int position) {
        final Category.DataBean m_y_category = mDataList.get(position);
        return CoversFragment.newInstance(m_y_category);
    }


}
