package com.meetyou.cn.http.rx;

import android.text.TextUtils;

import com.meetyou.cn.http.JsonResponse;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import rx.Observer;

public abstract class MeetYHttpObserver<T> implements Observer<Object> {
    private Class<T> entityClass;

    public MeetYHttpObserver() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {
        Logger.e(throwable.getMessage());
        onError(-2);
    }

    @Override
    public void onNext(Object o) {
        if (o != null) {
            JsonResponse mJsonResponse = JsonResponse.create(o.toString());
            try {
                if(mJsonResponse.getCode() == 200){
                    onSucceed(!TextUtils.isEmpty(mJsonResponse.getData())?mJsonResponse.getBean(entityClass,true):null);
                }else{
                    onError(-1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                onError(-2);
            }
        } else {
            onError(-1);
        }
    }

    protected abstract void onSucceed(T t);

    protected abstract void onError(int code);

}
