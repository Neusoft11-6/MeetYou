package com.meetyou.cn.adapter.item.upgrade;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.utils.ClipboardUtils;
import com.meetyou.cn.utils.MyStringUtils;
import com.meetyou.cn.vo.entity.M_Y_Notices;
import com.qmuiteam.qmui.alpha.QMUIAlphaTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.meetyou.com.meetyou.R;

/**
 * Created by admin on 2018/3/16.
 */

public class WXItem implements DelegateAdapterItem<M_Y_Notices> {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.wx)
    TextView wx;
    @BindView(R.id.copy)
    QMUIAlphaTextView copy;
    private M_Y_Notices mBean;

    @Override
    public int getLayoutResId() {
        return R.layout.adapter_upgrade_wx;
    }

    @Override
    public void bindViews(View root) {
        ButterKnife.bind(this, root);
    }

    @Override
    public void setViews() {
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardUtils.copyText(MyStringUtils.checkNull(mBean.getNotice()));
                Toast.makeText(v.getContext(),"复制成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void handleData(RecyclerView.ViewHolder holder, M_Y_Notices o, int position) {
        try {
            this.mBean = o;
            title.setText(MyStringUtils.checkNull(o.getTitle()));
            wx.setText(MyStringUtils.checkNull(o.getNotice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleDataWithOffset(M_Y_Notices o, int position, int offsetTotal) {

    }


}
