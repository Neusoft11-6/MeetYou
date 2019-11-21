package com.meetyou.cn.vo.entity;

import java.io.Serializable;
import java.util.List;

public class Category {

    public int code;
    public List<DataBean> data;

    public static class DataBean implements Serializable {
        public String updatedAt;
        public String objectId;
        public String ids;
        public String createdAt;
        public String name;
        public String leve;
        public String auth;


    }

    @Override
    public String toString() {
        return "Category{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
