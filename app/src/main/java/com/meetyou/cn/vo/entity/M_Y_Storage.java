package com.meetyou.cn.vo.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2018/4/20.
 */

public class M_Y_Storage extends BmobObject  {
    private String covers_ids;
    private String title;
    private String imgUrl;
    private String referer;
    private Boolean vip;
    private M_Y_Category category;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public M_Y_Category getCategory() {
        return category;
    }

    public void setCategory(M_Y_Category category) {
        this.category = category;
    }

    public String getCovers_ids() {
        return covers_ids;
    }

    public void setCovers_ids(String covers_ids) {
        this.covers_ids = covers_ids;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    @Override
    public String toString() {
        return "M_Y_Storage{" +
                "covers_ids='" + covers_ids + '\'' +
                ", title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", referer='" + referer + '\'' +
                ", vip=" + vip +
                ", category=" + category +
                '}';
    }
}
