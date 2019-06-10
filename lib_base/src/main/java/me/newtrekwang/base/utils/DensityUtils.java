package me.newtrekwang.base.utils;

import android.content.Context;

/**
 * @author newtrekWang
 * @fileName DensityUtils
 * @createDate 2018/7/16 11:01
 * @email 408030208@qq.com
 * @desc 密度转换器
 */
public final class DensityUtils {
    private DensityUtils(){}

    public static int dip2px(Context paramContext, float paramFloat)
    {
        return (int)(paramFloat * paramContext.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int px2dip(Context paramContext, float paramFloat)
    {
        return (int)(paramFloat / paramContext.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int px2sp(Context paramContext, float paramFloat)
    {
        return (int)(paramFloat / paramContext.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int sp2px(Context paramContext, float paramFloat)
    {
        return (int)(paramFloat * paramContext.getResources().getDisplayMetrics().density + 0.5F);
    }
}
