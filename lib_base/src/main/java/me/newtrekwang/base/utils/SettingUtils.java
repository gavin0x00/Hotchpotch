package me.newtrekwang.base.utils;

public class SettingUtils {
    private static boolean onlyWifiLoadImg =false;

    public static boolean isOnlyWifiLoadImg() {
        return onlyWifiLoadImg;
    }

    public static void setOnlyWifiLoadImg(boolean onlyWifiLoadImg) {
        SettingUtils.onlyWifiLoadImg = onlyWifiLoadImg;
    }
}
