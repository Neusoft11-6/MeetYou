//
// Created by admin on 2017/10/17.
//
#include <jni.h>
#include <string>

#define TYPE_BMOB_OPEN_ID 0

static const char *T_BMOB_OPEN_ID = "36cf26558fac7b019f69ed52203ed841";
extern "C"

JNIEXPORT jstring JNICALL
Java_com_meetyou_library_utils_SignatureUtil_getSignatureParam(
        JNIEnv *env,
        jobject instance,
        jint type) {
    std::string sign;
    switch (type){
        case TYPE_BMOB_OPEN_ID:
            sign = T_BMOB_OPEN_ID;
            break;
    }

    return env->NewStringUTF(sign.c_str());
}

