package me.newtrekwang.base.imageloader;

import android.content.Context;
/**
 * @className IImageLoaderStrategy
 * @createDate 2019/6/4 1:05
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 图片加载策略
 *
 */
public interface IImageLoaderStrategy {
    /**
     * 加载图片
     * @param context
     * @param imageLoader
     */
    void loadImage(Context context, ImageLoader imageLoader);
}
