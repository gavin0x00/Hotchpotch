package me.newtrekwang.base.utils;

import java.util.Locale;


/**
 * @author newtrekWang
 * @fileName SystemUtils
 * @createDate 2018/9/25 16:06
 * @email 408030208@qq.com
 * @desc 系统工具
 */
public final class SystemUtils {
    private SystemUtils(){}

    public static String getCurrentLauguageAndCountry(){
        // 获取系统当前使用的语言和国家 例：zh_CN
        String mCurrentLanguage = Locale.getDefault().getLanguage();
        String mCurrentCountry = Locale.getDefault().getCountry();
        return mCurrentLanguage+"_"+mCurrentCountry;
    }

    /**
     * 当前语言是否为英文
     * @return
     */
    public static boolean isEnglishLanguage(){
        String mCurrentLanguage = Locale.getDefault().getLanguage();
        return "en".equals(mCurrentLanguage);
    }
}
