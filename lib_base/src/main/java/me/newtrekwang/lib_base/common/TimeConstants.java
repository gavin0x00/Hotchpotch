package me.newtrekwang.lib_base.common;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * @className TimeConstants
 * @createDate 2018/11/1 18:16
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 时间符号常量，可以看作是枚举，这里应用到了注解上
 *
 */
public final class TimeConstants {
    /**
     * 毫秒
     */
    public static final int MSEC = 1;
    /**
     * 秒
     */
    public static final int SEC  = 1000;
    /**
     * 分钟
     */
    public static final int MIN  = 60000;
    /**
     * 时
     */
    public static final int HOUR = 3600000;
    /**
     * 天
     */
    public static final int DAY  = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
