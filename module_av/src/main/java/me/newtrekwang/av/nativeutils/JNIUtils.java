package me.newtrekwang.av.nativeutils;
/**
 * @className JNIUtils
 * @createDate 2019/5/31 1:17
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc JNI 工具类
 *
 */
public class JNIUtils {
    static {
        System.loadLibrary("hello");
    }
    public static native String getStringFromC();
}
