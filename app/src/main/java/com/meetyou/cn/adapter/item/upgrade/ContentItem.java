package com.meetyou.cn.adapter.item.upgrade;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.utils.MyStringUtils;
import com.meetyou.cn.vo.entity.M_Y_Notices;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.meetyou.com.meetyou.R;

/**
 * Created by admin on 2018/3/16.
 */

public class ContentItem implements DelegateAdapterItem<M_Y_Notices> {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;

    @Override
    public int getLayoutResId() {
        return R.layout.adapter_upgrade_content;
    }

    @Override
    public void bindViews(View root) {
        ButterKnife.bind(this, root);
    }

    @Override
    public void setViews() {
    }

    @Override
    public void handleData(RecyclerView.ViewHolder holder, M_Y_Notices o, int position) {
        try {
            title.setText(MyStringUtils.checkNull(o.getTitle()));
            content.setText(MyStringUtils.checkNull(o.getNotice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleDataWithOffset(M_Y_Notices o, int position, int offsetTotal) {

    }


}
