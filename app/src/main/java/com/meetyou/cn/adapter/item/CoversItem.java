package com.meetyou.cn.adapter.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.mvp.ui.activity.PhotosActivity;
import com.meetyou.cn.mvp.ui.widget.OnItemClickListener;
import com.meetyou.cn.utils.ImageUtils;
import com.meetyou.cn.utils.MyStringUtils;
import com.meetyou.cn.vo.entity.M_Y_Covers;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.meetyou.com.meetyou.R;

/**
 * Created by admin on 2018/3/16.
 */

public class CoversItem implements DelegateAdapterItem<M_Y_Covers> {


    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.galleryNum)
    TextView galleryNum;
    private  M_Y_Covers mBean;
    private OnItemClickListener onItemClickListener;
    private int mPosition;

    public CoversItem(){

    }

    public CoversItem(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.adapter_covers;
    }

    @Override
    public void bindViews(View root) {
        ButterKnife.bind(this, root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotosActivity.start(v.getContext(),mBean);
            }
        });
        root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemLongClick(v,mPosition);
                }
                return false;
            }
        });
    }

    @Override
    public void setViews() {

    }

    @Override
    public void handleData(RecyclerView.ViewHolder holder, M_Y_Covers bean, int position) {
        try {
            this.mPosition = position;
            this.mBean = bean;
            ImageUtils.loadImg(picture, bean.getImgUrl(),bean.getReferer(),R.mipmap.ic_placeholder_200);
            title.setText(MyStringUtils.checkNull(bean.getTitle()));
            galleryNum.setText(MyStringUtils.checkNull(bean.getTotals()!=null?bean.getTotals().toString():"?"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void handleDataWithOffset(M_Y_Covers o, int position, int offsetTotal) {

    }


}
