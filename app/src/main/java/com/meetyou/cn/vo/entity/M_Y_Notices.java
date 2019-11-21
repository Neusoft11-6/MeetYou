package com.meetyou.cn.vo.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2018/4/20.
 */

public class M_Y_Notices extends BmobObject  {
    private String type;
    private String title;
    private String notice;
    private Integer itemType;
    private Integer sequence;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
