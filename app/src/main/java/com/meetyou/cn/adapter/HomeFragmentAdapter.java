package com.meetyou.cn.adapter;

import android.support.v4.app.Fragment;

import com.meetyou.cn.mvp.ui.fragment.HomeFragment;
import com.meetyou.cn.mvp.ui.fragment.MineFragment;
import com.meetyou.library.fragmentnavigator.FragmentNavigatorAdapter;

/**
 * Created by admin on 2018/4/23.
 */

public class HomeFragmentAdapter  implements FragmentNavigatorAdapter {

    private static final String TABS[] = {"home", "mine"};

    @Override
    public Fragment onCreateFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            default:
                return new MineFragment();
        }
    }

    @Override
    public String getTag(int position) {
        return TABS[position];
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}
