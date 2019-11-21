package com.meetyou.cn.vo.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2018/4/20.
 */

public class M_Y_Category extends BmobObject {
    private String ids;
    private String name;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
