package com.meetyou.cn.base;

/**
 * Created by admin on 2018/3/23.
 */

public interface BaseEvent {
    void setObject(Object obj);

    Object getObject();

    //事件定义
    enum CommonEvent implements BaseEvent {
        LOGIN, //登录
        LOGOUT, //登出
        TOKEN_TIMEOUT, //TOKEN失效
        REFRESH, //刷新
        CHECK_AUTH; //检查身份
        private Object obj;

        @Override
        public void setObject(Object obj) {
            this.obj = obj;
        }

        @Override
        public Object getObject() {
            return obj;
        }
    }

    enum ActionEvent implements BaseEvent {

        UPDATE_TASK_TOTAL; //更新
        private Object obj;

        @Override
        public void setObject(Object obj) {
            this.obj = obj;
        }

        @Override
        public Object getObject() {
            return obj;
        }
    }
}
