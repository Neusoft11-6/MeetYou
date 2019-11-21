package com.meetyou.cn.utils;

import android.text.TextUtils;


/**
 * Created by admin on 2018/3/16.
 */

public class MyStringUtils {

    public static String getRealUrl(String url){
        if (TextUtils.isEmpty(url)) {
            return "";
        }
//        if (url.startsWith("http")) {
//            return url;
//        }
        return url;
    }

    public static String checkNull(String content) {
        if (content == null || content.length() == 0) {
            return "";
        }
        return content.trim();
    }
    public static String checkNull(String content,String defaultValue) {
        if (content == null || content.length() == 0) {
            return defaultValue;
        }
        return content.trim();
    }
    /**
     * @param args
     * @return true为都不为空 false 为有其中一个为空
     */
    public static boolean checkArgs(String... args){
       if(args!=null&&args.length>0){
           boolean noEmpty = true;
           for (String arg : args) {
               if(TextUtils.isEmpty(arg)){
                   noEmpty = false;
                   break;
               }
           }
           return noEmpty;
       }

       return false;

    }

    public static String adjustGender(String sex){
        return "0".equals(sex.trim())?"女":"男";
    }
}
