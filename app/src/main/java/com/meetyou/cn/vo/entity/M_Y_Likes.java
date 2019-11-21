package com.meetyou.cn.vo.entity;

import cn.bmob.v3.BmobObject;

public class M_Y_Likes extends BmobObject {
    private String user_objectids;
    private String covers_ids;
    private String uniq;

//    public M_Y_Likes(String user_objectids, String covers_ids) {
//        super();
//        this.user_objectids = user_objectids;
//        this.covers_ids = covers_ids;
//        this.uniq = user_objectids + "&" + covers_ids;
//    }


    public String getUser_objectids() {
        return user_objectids;
    }

    public void setUser_objectids(String user_objectids) {
        this.user_objectids = user_objectids;
    }

    public String getCovers_ids() {
        return covers_ids;
    }

    public void setCovers_ids(String covers_ids) {
        this.covers_ids = covers_ids;
    }

    public String getUniq() {
        return uniq;
    }

    public void setUniq(String uniq) {
        this.uniq = uniq;
    }
}
