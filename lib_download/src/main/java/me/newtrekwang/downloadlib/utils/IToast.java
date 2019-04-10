package me.newtrekwang.downloadlib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author newtrekWang
 * @fileName IToast
 * @createDate 2018/9/26 11:57
 * @email 408030208@qq.com
 * @desc 自定义Toast
 */
public class IToast {
    /**
     * 显示Toast
     * @param msg
     */
    public static void showToast(String msg, Context context){
        if (msg == null){
            return;
        }
        Toast toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        toast.show();
    }
}
