package com.meetyou.cn.adapter.item.photos;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.utils.CommonUtils;
import com.meetyou.cn.utils.MyStringUtils;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.meetyou.com.meetyou.R;

/**
 * Created by admin on 2018/3/16.
 */

public class PhotosDetailsItem implements DelegateAdapterItem<M_Y_Covers> {


    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.normalTotals)
    TextView normalTotals;

    @Override
    public int getLayoutResId() {
        return R.layout.adapter_photo_description;
    }

    @Override
    public void bindViews(View root) {
        ButterKnife.bind(this, root);
    }

    @Override
    public void setViews() {
    }

    @Override
    public void handleData(RecyclerView.ViewHolder holder, M_Y_Covers o, int position) {
        title.setText(MyStringUtils.checkNull(o.getTitle()));
        normalTotals.setText(String.format("图片数量:%dP",o.getTotals() == null?0:o.getTotals()));
    }

    @Override
    public void handleDataWithOffset(M_Y_Covers o, int position, int offsetTotal) {

    }


}
