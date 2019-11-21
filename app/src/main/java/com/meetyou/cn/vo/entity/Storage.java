package com.meetyou.cn.vo.entity;

import java.util.List;

/**
 * Created by admin on 2018/4/20.
 */

public class Storage   {


    public int code;
    public List<DataBean> data;

    public static class DataBean {

        public String updatedAt;
        public String imgUrl;
        public boolean vip;
        public String covers_ids;
        public String objectId;
        public String createdAt;
        public String referer;
        public String auth;

        @Override
        public String toString() {
            return "DataBean{" +
                    "updatedAt='" + updatedAt + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", vip=" + vip +
                    ", covers_ids='" + covers_ids + '\'' +
                    ", objectId='" + objectId + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", referer='" + referer + '\'' +
                    ", auth='" + auth + '\'' +
                    '}';
        }
    }
}
