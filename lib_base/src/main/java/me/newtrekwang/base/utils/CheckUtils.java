package me.newtrekwang.base.utils;

import androidx.annotation.Nullable;

/**
 * @className CheckUtils
 * @createDate 2019/6/10 0:34
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 做一些检查的工具类
 *
 */
public final class CheckUtils {

    private CheckUtils(){}

    /**
     * 检查空指针
     * @param object
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(@Nullable T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
