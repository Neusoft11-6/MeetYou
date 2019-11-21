package com.meetyou.cn.utils;

import android.widget.ImageView;

import com.meetyou.library.http.imageloader.ImageLoader;
import com.meetyou.library.http.imageloader.glide.ImageConfigImpl;
import com.meetyou.library.utils.MeetYouUtils;

import cn.meetyou.com.meetyou.R;


/**
 * Created by admin on 2018/3/21.
 */

public class ImageUtils {

    /**
     * 加载图片
     * @param img
     * @param url
     */
    public static void loadImg(ImageView img,String url){
        loadImg(img, url,null);
    }
    public static void loadImg(ImageView img,String url,String referer){
        final ImageLoader imageLoader = MeetYouUtils.obtainAppComponentFromContext(img.getContext()).imageLoader();
        imageLoader.loadImage(img.getContext(),
                ImageConfigImpl
                        .builder()
                        .url(MyStringUtils.getRealUrl(url))
                        .referer(referer)
                        .errorPic(R.mipmap.ic_placeholder_400)
                        .placeholder(R.mipmap.ic_placeholder_400)
                        .imageView(img)
                        .build());
    }
    /**
     * 加载图片 可传占位图
     * @param img
     * @param url
     * @param placeholder
     */
    public static void loadImg(ImageView img,String url,int placeholder){
        loadImg(img, url,null, placeholder);
    }

    public static void loadImg(ImageView img,String url,String referer,int placeholder){
        final ImageLoader imageLoader = MeetYouUtils.obtainAppComponentFromContext(img.getContext()).imageLoader();
        imageLoader.loadImage(img.getContext(),
                ImageConfigImpl
                        .builder()
                        .url(MyStringUtils.getRealUrl(url))
                        .referer(referer)
                        .errorPic(placeholder)
                        .placeholder(placeholder)
                        .imageView(img)
                        .build());
    }




}
