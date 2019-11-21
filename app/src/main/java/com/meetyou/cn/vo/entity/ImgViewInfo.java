package com.meetyou.cn.vo.entity;

import android.graphics.Rect;
import android.os.Parcel;

import com.meetyou.enitity.IThumbViewInfo;


/**
 * Created by admin on 2018/4/26.
 */

public class ImgViewInfo implements IThumbViewInfo {
    private String imgUrl;  //图片地址
    private String referer;  //图片地址
    private Rect mBounds; // 记录坐标

    public ImgViewInfo(String imgUrl,String referer) {
        this.imgUrl = imgUrl;
        this.referer = referer;
    }

    @Override
    public String getUrl() {
        return imgUrl;
    }

    public void setUrl(String url) {
        this.imgUrl = imgUrl;
    }

    @Override
    public Rect getBounds() {
        return mBounds;
    }

    @Override
    public String getReferer() {
        return referer;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgUrl);
        dest.writeString(this.referer);
        dest.writeParcelable(this.mBounds, 0);
    }

    protected ImgViewInfo(Parcel in) {
        this.imgUrl = in.readString();
        this.referer = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
    }

    public static final Creator<ImgViewInfo> CREATOR = new Creator<ImgViewInfo>() {
        public ImgViewInfo createFromParcel(Parcel source) {
            return new ImgViewInfo(source);
        }

        public ImgViewInfo[] newArray(int size) {
            return new ImgViewInfo[size];
        }
    };
}
