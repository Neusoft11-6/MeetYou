package com.meetyou.cn.adapter.item.mine;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.meetyou.cn.base.adapter.item.DelegateAdapterItem;
import com.meetyou.cn.mvp.ui.activity.LoginActivity;
import com.meetyou.cn.utils.MyStringUtils;
import com.meetyou.cn.vo.entity.MeetYUser;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.meetyou.com.meetyou.R;

/**
 * Created by admin on 2018/3/16.
 */

public class UserInfoItem implements DelegateAdapterItem<Object> {


    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.layout_login)
    QMUIRoundRelativeLayout layoutLogin;
    @BindView(R.id.layout_nologin)
    QMUIRoundRelativeLayout layoutNologin;
    @BindView(R.id.btnLeve)
    QMUIAlphaImageButton btnLeve;
    @BindView(R.id.memberDescription)
    TextView memberDescription;
    @BindView(R.id.memberTime)
    TextView memberTime;

    @Override
    public int getLayoutResId() {
        return R.layout.adapter_mine_userinfo;
    }

    @Override
    public void bindViews(View root) {
        ButterKnife.bind(this, root);
    }

    @Override
    public void setViews() {
        layoutNologin.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), LoginActivity.class)));
    }

    @Override
    public void handleData(RecyclerView.ViewHolder holder, Object o, int position) {
        MeetYUser currentUser = BmobUser.getCurrentUser(MeetYUser.class);
        layoutLogin.setVisibility(currentUser == null ? View.GONE : View.VISIBLE);
        layoutNologin.setVisibility(currentUser == null ? View.VISIBLE : View.GONE);

        try {
            if (currentUser != null) {
                nickname.setText(MyStringUtils.checkNull(currentUser.getNickname()));
                final String leve = currentUser.getLeve();
                switch (leve){
                    case "1"://等级
                        btnLeve.setBackgroundResource(R.mipmap.ic_vip_01);
                        memberDescription.setText("普通会员");
                        memberTime.setText(memberTime.getResources().getString(R.string.member_time,"无"));
                        break;
                    case "2":
                    case "3":
                        btnLeve.setBackgroundResource(R.mipmap.ic_vip_02);
                        memberDescription.setText("赞助会员");
                        memberTime.setText(memberTime.getResources().getString(R.string.member_time,currentUser.getValidtime()!=null?currentUser.getValidtime().getDate():"未知"));
                        break;
                    case "66":
                        btnLeve.setBackgroundResource(R.mipmap.ic_vip_03);
                        memberTime.setText(memberTime.getResources().getString(R.string.member_time,"长期"));
                        memberDescription.setText("永久会员");
                        break;
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleDataWithOffset(Object o, int position, int offsetTotal) {

    }


}
