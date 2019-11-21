package com.meetyou.library.utils;

/**
 * Created by admin on 2018/3/16.
 */

public class SignatureUtil {
    public static final int TYPE_BMOB_OPEN_ID = 0;

    static {
        System.loadLibrary("signature");
    }

    public static native String getSignatureParam(int type);
}
