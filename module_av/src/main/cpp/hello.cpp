//
// Created by Administrator on 2019/5/31.
//

#include <jni.h>
#include <opencv2/opencv.hpp>
#include <iostream>

using namespace cv;
using namespace std;


extern "C"
JNIEXPORT jintArray JNICALL
Java_me_newtrekwang_av_nativeutils_JNIUtils_bitmap2Grey(JNIEnv *env, jclass type, jintArray buf,
                                                        jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if(cbuf == NULL){
        return 0;
    }

    const Mat imgData(h, w, CV_8UC4, (unsigned char*)cbuf);
    // 注意，Android的Bitmap是ARGB四通道,而不是RGB三通道
    cvtColor(imgData,imgData,COLOR_BGRA2GRAY,0);
    cvtColor(imgData,imgData,COLOR_GRAY2BGRA,0);

    int size=w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint*)imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;

}