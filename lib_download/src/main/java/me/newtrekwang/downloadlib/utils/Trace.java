package me.newtrekwang.downloadlib.utils;

import android.util.Log;
/**
 * @className Trace
 * @createDate 2018/8/1 9:51
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc log打印工具类
 *
 */
public class Trace {
    private static final String TAG = "Trace";

    private static final boolean DEBUG = true;

    public static void d(String msg){
        if (DEBUG){
            Log.d(TAG,msg);
        }
    }

    public static void e(String msg){
        if (DEBUG){
            Log.e(TAG, msg );
        }
    }
}
