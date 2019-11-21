package com.meetyou.cn.mvp.ui.widget;

import android.view.View;

/**
 * Created by admin on 2018/3/23.
 */

public interface OnItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
