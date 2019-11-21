package com.meetyou.cn.mvp.model;

import com.meetyou.cn.mvp.interfaces.ICovers;
import com.meetyou.cn.utils.DeviceUtils;
import com.meetyou.cn.utils.MyStringUtils;
import com.meetyou.cn.vo.entity.Category;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.meetyou.cn.vo.entity.M_Y_Storage;
import com.meetyou.cn.vo.entity.MeetYUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import rx.Observable;

/**
 * Created by admin on 2018/4/24.
 */

public class CoversModel implements ICovers.Model {

    @Inject
    public CoversModel() {
    }

    @Override
    public <T extends BmobObject> Observable<List<M_Y_Covers>> findCovers(int skip, Category.DataBean category) {
        BmobQuery<M_Y_Covers> query = new BmobQuery<M_Y_Covers>();
        query.addWhereEqualTo("category", category.objectId);
//        if (!TextUtils.isEmpty(category.ids)) {
//            query.addWhereEqualTo("ids", category.ids);
//        }
        query.order("-updatedAt");
        query.setSkip(skip);
        query.setLimit(20);
        return query.findObjectsObservable(M_Y_Covers.class);
    }

    @Override
    public <T extends BmobObject> Observable<List<M_Y_Storage>> findImgs(int skip, M_Y_Covers covers) {
        BmobQuery<M_Y_Storage> query = new BmobQuery<M_Y_Storage>();
        query.addWhereEqualTo("covers_ids", MyStringUtils.checkNull(covers.getIds(), "private"));
//        query.order("-updatedAt");
        query.setSkip(skip);
        query.setLimit(10);
        return query.findObjectsObservable(M_Y_Storage.class);
    }

    @Override
    public <T extends BmobObject> Observable<Object> findImgByMethod(int skip, String coversIds) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("objectId", MeetYUser.getCurrentUser()!=null?MeetYUser.getCurrentUser().getObjectId():"");
        params.put("covers_ids",MyStringUtils.checkNull(coversIds, ""));
        params.put("deviceIds", DeviceUtils.getUniqueId());
        params.put("skip",skip);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        return   ace.callEndpointObservable("imgsBridge",params);
    }

    @Override
    public void onDestroy() {

    }
}
