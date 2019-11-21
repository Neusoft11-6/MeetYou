package com.meetyou.cn.adapter.item.mine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.mvp.ui.widget.OnCommitListener;
import com.meetyou.cn.vo.MineOptionInfo;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.meetyou.com.meetyou.R;

public class UserOptionItem implements DelegateAdapterItem<MineOptionInfo> {

    @BindView(R.id.optionName)
    TextView optionName;
    @BindView(R.id.layoutSettings)
    QMUIRoundRelativeLayout layoutSettings;
    private OnCommitListener onCommitListener;
    private MineOptionInfo mBeanInfo;

    public UserOptionItem(OnCommitListener onCommitListener) {
        this.onCommitListener = onCommitListener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.adapter_option_item;
    }

    @Override
    public void bindViews(View root) {
        ButterKnife.bind(this, root);
        layoutSettings.setOnClickListener(v -> {
            if (onCommitListener != null) {
                onCommitListener.onCommit(mBeanInfo.type, null);
            }
        });
    }

    @Override
    public void setViews() {

    }

    @Override
    public void handleData(RecyclerView.ViewHolder holder, MineOptionInfo bean, int position) {
        this.mBeanInfo = bean;
        optionName.setText(bean.optionName);
        optionName.setCompoundDrawablesWithIntrinsicBounds(mBeanInfo.imgIds,0,0,0);
    }

    @Override
    public void handleDataWithOffset(MineOptionInfo bean, int position, int offsetTotal) {

    }
}
