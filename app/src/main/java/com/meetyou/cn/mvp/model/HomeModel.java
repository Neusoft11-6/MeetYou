package com.meetyou.cn.mvp.model;

import com.meetyou.cn.mvp.interfaces.IHome;
import com.meetyou.cn.vo.entity.MeetYUser;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import cn.bmob.v3.AsyncCustomEndpoints;
import rx.Observable;

public class HomeModel implements IHome.Model {

    @Inject
    public HomeModel() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<Object> findCategory() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("objectId", MeetYUser.getCurrentUser()!=null?MeetYUser.getCurrentUser().getObjectId():"1");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        return   ace.callEndpointObservable("findCategory",params);
    }
}
