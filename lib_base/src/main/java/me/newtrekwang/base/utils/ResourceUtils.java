package me.newtrekwang.base.utils;

import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;
import me.newtrekwang.base.common.BaseApplication;

/**
 * @author newtrekWang
 * @fileName ResourceUtils
 * @createDate 2018/8/20 14:56
 * @email 408030208@qq.com
 * @desc 资源工具类
 */
public final class ResourceUtils {
    private ResourceUtils(){}

    /**
     * 获取字符串数组
     * @param id id
     * @return 字符串数组
     */
    public static String[] getStringList(@ArrayRes int id){
       return BaseApplication.getBaseApplication().getResources().getStringArray(id);
    }

    /**
     * 获取字符串
     * @param id id
     * @return 字符串
     */
    public static String getResString(@StringRes int id){
        return BaseApplication.getBaseApplication().getResources().getString(id);
    }
}
