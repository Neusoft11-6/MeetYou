package com.meetyou.cn.adapter.item.photos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.mvp.ui.widget.OnItemClickListener;
import com.meetyou.cn.utils.ImageUtils;
import com.meetyou.cn.vo.entity.Storage;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.meetyou.com.meetyou.R;

/**
 * Created by admin on 2018/4/26.
 */

public class PhotosItem implements DelegateAdapterItem<Storage.DataBean> {


    @BindView(R.id.picture)
    ImageView picture;
    private OnItemClickListener mOnItemClickListener;
    private int mPosition;

    public PhotosItem(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.adapter_photo_item;
    }

    @Override
    public void bindViews(View root) {
        ButterKnife.bind(this, root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(mOnItemClickListener!=null){
                        mOnItemClickListener.onItemClick(v,mPosition);
                    }
            }
        });
    }

    @Override
    public void setViews() {
    }

    @Override
    public void handleData(RecyclerView.ViewHolder holder, Storage.DataBean bean, int position) {
        try {
            this.mPosition = position;
            ImageUtils.loadImg(picture, bean.imgUrl,bean.referer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleDataWithOffset(Storage.DataBean o, int position, int offsetTotal) {

    }

}
