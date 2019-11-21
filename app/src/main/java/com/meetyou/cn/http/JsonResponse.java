package com.meetyou.cn.http;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.meetyou.cn.app.MeetYouApp;
import com.meetyou.cn.utils.MyStringUtils;
import com.meetyou.library.utils.AppManager;
import com.meetyou.library.utils.MeetYouUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonResponse {
    private int code;
    private String data;
    private String fullData;

    public static JsonResponse create(String json) {

        JsonResponse mResponse = new JsonResponse();
        int code = -1;
        String msg = "";
        String data = "";
        String fullData = json;

        try {
            JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("code")) {
                    code = jsonObject.getInt("code");
                }
                if (code != 200) {
                    Activity content = AppManager.getAppManager().currentActivity();
                    Toast.makeText(content, MyStringUtils.checkNull(jsonObject.optString("error"),"请求异常，请稍后重试"), Toast.LENGTH_SHORT).show();
                }else{
                    if(jsonObject.has("data")){
                        data = jsonObject.optString("data");
                    }else{
                        Activity content = AppManager.getAppManager().currentActivity();
                        Toast.makeText(content, MyStringUtils.checkNull(jsonObject.optString("msg"),"请求异常，请稍后重试"), Toast.LENGTH_SHORT).show();
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mResponse.setCode(code);
        mResponse.setData(data);
        mResponse.setFullData(fullData);
        return mResponse;
    }


    public <T> T getBean(Class<T> clazz,boolean isFull) throws IllegalArgumentException, JSONException {
        if (isFull&&TextUtils.isEmpty(getFullData())){
            throw new IllegalArgumentException(
                    "In the JsonResponse, full data can't be empty");
        }else if(TextUtils.isEmpty(getData())){
            throw new IllegalArgumentException(
                    "In the JsonResponse, data can't be empty");
        }

        T object = null;
        if(isFull&&TextUtils.isEmpty(getData())){
            try {
                return (T) Class.forName(clazz.getName()).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            final Gson gson = MeetYouUtils.obtainAppComponentFromContext(MeetYouApp.getInstance()).gson();
            object = gson.fromJson(isFull?getFullData():getData(), clazz);
        }
        return object;
    }

    public String getFullData() {
        return fullData;
    }

    public void setFullData(String fullData) {
        this.fullData = fullData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
