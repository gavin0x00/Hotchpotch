package me.newtrekwang.lib_base.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @className MemoryConstants
 * @createDate 2018/11/1 18:18
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 字节大小符号常量，可以看作是枚举，这里应用到了注解上
 *
 */
public final class MemoryConstants {
    /**
     * 字节
     */
    public static final int BYTE = 1;
    /**
     * KB
     */
    public static final int KB   = 1024;
    /**
     * MB
     */
    public static final int MB   = 1048576;
    /**
     * GB
     */
    public static final int GB   = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
