package me.newtrekwang.lib_base.utils;

import android.util.Log;

/**
 * @author newtrekWang
 * @fileName L
 * @createDate 2018/11/2 16:40
 * @email 408030208@qq.com
 * @desc Log工具
 */
public final class L {
    public static final int RELEASE = 1;
    public static final int DEBUG = 0;

    /**
     * 默认的TAG，建议后面加下划线
     */
    private static String DEFAULT_TAG = "flyAudio_";

    /**
     * 日志模式，默认为debug
     */
    private static int mode = DEBUG;

    /**
     * 防止实例
     */
    private L() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 设置log的模式
     * @param logMode 只能为L.RELEASE或L.DEBUG
     */
    public static void setMode(final int logMode){
        L.mode = logMode;
    }

    /**
     * 设置全局默认的tag前缀，不设置的话默认为flyAudio_
     * @param tag tag
     */
    public static void setDefaultTag(String tag){
        L.DEFAULT_TAG = tag;
    }

    /**
     * 生成TAG
     * @param o 对象object
     * @return tag名，比如flyAudio_MainActivity
     */
    public static String createTag(Object o) {
        return o == null ? createTag() : DEFAULT_TAG + o.getClass().getSimpleName();
    }

    /**
     * 生成默认TAG
     * @return
     */
    public static String createTag() {
        return DEFAULT_TAG;
    }

    /**
     * verbose 级别打印
     * @param msg log信息
     */
    public static void v(String msg) {
        if (mode == DEBUG){
            Log.v(DEFAULT_TAG, msg);
        }
    }

    /**
     * debug 级别打印
     * @param msg log信息
     */
    public static void d(String msg) {
        if (mode == DEBUG){
            Log.d(DEFAULT_TAG, msg);
        }
    }

    /**
     * info 级别打印
     * @param msg log信息
     */
    public static void i(String msg) {
        if (mode == DEBUG){
            Log.i(DEFAULT_TAG, msg);
        }
    }

    /**
     * warn 级别打印
     * @param msg log信息
     */
    public static void w(String msg) {
        if (mode == DEBUG){
            Log.w(DEFAULT_TAG, msg);
        }
    }

    /**
     * error 级别打印
     * @param msg log信息
     */
    public static void e(String msg) {
        if (mode == DEBUG){
            Log.e(DEFAULT_TAG, msg);
        }
    }

    /**
     * verbose 级别打印
     * @param tag log 标签
     * @param msg log信息
     */
    public static void v(String tag, String msg) {
        if (mode == DEBUG){
            Log.v(tag, msg);
        }
    }

    /**
     * debug 级别打印
     * @param tag log 标签
     * @param msg log信息
     */
    public static void d(String tag, String msg) {
        if (mode == DEBUG){
            Log.d(tag, msg);
        }
    }

    /**
     * info 级别打印
     * @param tag log 标签
     * @param msg log信息
     */
    public static void i(String tag, String msg) {
        if (mode == DEBUG){
            Log.i(tag, msg);
        }
    }
    /**
     * warn 级别打印
     * @param tag log 标签
     * @param msg log信息
     */
    public static void w(String tag, String msg) {
        if (mode == DEBUG){
            Log.w(tag, msg);
        }
    }
    /**
     * error 级别打印
     * @param tag log 标签
     * @param msg log信息
     */
    public static void e(String tag, String msg) {
        if (mode == DEBUG){
            StackTraceElement targetStackTraceElement = getTargetStackTraceElement();
            Log.e(tag, "(" + targetStackTraceElement.getFileName() + ":" + targetStackTraceElement.getLineNumber() + ")");
            Log.e(tag, msg);
        }
    }

    /**
     * 用于打印可以忽略的信息，比如说被忽视的catch
     *
     * @param tag log 标签
     * @param msg log信息
     */
    public static void ignore(String tag, String msg) {
        if (mode == DEBUG){
            Log.v(tag, "ignore_" + msg);
        }
    }

    /**
     * 找到调用者信息
     * @return 调用者信息
     */
    private static StackTraceElement getTargetStackTraceElement() {
        // find the target invoked method
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            boolean isLogMethod = stackTraceElement.getClassName().equals(L.class.getName())||stackTraceElement.getClassName().startsWith("java.lang");
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }
}
