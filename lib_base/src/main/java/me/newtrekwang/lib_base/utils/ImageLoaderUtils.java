package me.newtrekwang.lib_base.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @className ImageLoaderUtils
 * @createDate 2018/7/15 23:39
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc Glide封装
 *
 */
public final class ImageLoaderUtils {
    private ImageLoaderUtils(){}

    /**
     * 加载图片到ImageView控件(暂时用Glide加载图片)
     * @param context 上下文
     * @param url 图片Url 支持网址，文件目录
     * @param placeHolderDrawableID 占位图资源ID
     * @param imageView ImageView控件
     */
    public static void loadImage(Context context, String url, int placeHolderDrawableID,ImageView imageView){
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeHolderDrawableID)
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 加载图片到ImageView控件(暂时用Glide加载图片)
     * @param context 上下文
     * @param url 图片Url 支持网址,文件目录
     * @param placeHolderDrawableID 占位图资源ID
     * @param imageView ImageView控件
     */
    public static void loadImageFitCenter(Context context,String  url,int placeHolderDrawableID,ImageView imageView){
        Glide.with(context)
                .load(url)
                .fitCenter()
                .placeholder(placeHolderDrawableID)
                .dontAnimate()
                .into(imageView);
    }
}
