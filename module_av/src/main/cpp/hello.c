//
// Created by Administrator on 2019/5/31.
//

#include <jni.h>

JNIEXPORT jstring JNICALL
Java_me_newtrekwang_av_nativeutils_JNIUtils_getStringFromC(JNIEnv *env, jclass type) {
    char* result="Hello from C!";
    return (*env)->NewStringUTF(env, result);
}