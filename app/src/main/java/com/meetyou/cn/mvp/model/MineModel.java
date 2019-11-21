package com.meetyou.cn.mvp.model;

import com.meetyou.cn.mvp.interfaces.IMine;

import javax.inject.Inject;

public class MineModel implements IMine.Model {

    @Inject
    public MineModel() {
    }

    @Override
    public void onDestroy() {

    }
}
