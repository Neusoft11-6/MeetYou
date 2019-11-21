package com.meetyou.cn.vo.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2018/4/20.
 */

public class M_Y_Covers extends BmobObject {

    private M_Y_Category category;
    private String ids;
    private String title;
    private String auth;
    private String imgUrl;
    private String description;
    private String referer;
    private Boolean vip;
    private Integer heat;
    private Integer totals;

    public M_Y_Category getCategory() {
        return category;
    }

    public void setCategory(M_Y_Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public Integer getHeat() {
        return heat;
    }

    public void setHeat(Integer heat) {
        this.heat = heat;
    }

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }
}
