package com.meetyou.cn.mvp.interfaces;

import com.meetyou.cn.mvp.base.IModel;
import com.meetyou.cn.mvp.base.IView;
import com.meetyou.cn.vo.entity.Category;
import com.meetyou.cn.vo.entity.M_Y_Covers;
import com.meetyou.cn.vo.entity.M_Y_Storage;

import java.util.List;

import cn.bmob.v3.BmobObject;
import rx.Observable;

public interface ICovers {
    interface View<T> extends IView {
       void dataCallback(List<T> list);
       void nextDataCallback(List<T> list);
       void likeCallback(int position,String coversId);
    }

    interface Model extends IModel {
        <T extends BmobObject> Observable<List<M_Y_Covers>> findCovers(int skip,Category.DataBean category);
        <T extends BmobObject> Observable<List<M_Y_Storage>> findImgs(int skip, M_Y_Covers category);
        <T extends BmobObject> Observable<Object> findImgByMethod(int skip, String coversIds)throws Exception;
    }

}

