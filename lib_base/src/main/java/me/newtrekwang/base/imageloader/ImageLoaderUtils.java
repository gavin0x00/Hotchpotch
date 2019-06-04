package me.newtrekwang.base.imageloader;

import android.content.Context;

import javax.annotation.Nonnull;

/**
 * @className ImageLoaderUtils
 * @createDate 2019/6/4 1:29
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc ImageLoaderUtils 图片加载工具类
 *
 */
public class ImageLoaderUtils {
    /**
     * 图片加载策略
     */
    private IImageLoaderStrategy imageLoaderStrategy;

    /**
     * 构造私有
     */
    private ImageLoaderUtils() {
        this.imageLoaderStrategy = new GlideImageLoaderProvider();
    }

    /**
     * 获取单例
     */
    public static ImageLoaderUtils getInstance() {
        return ImageLoaderUtilHolder.INSTANCE;
    }

    /**
     * 提供单例
     */
    private static class ImageLoaderUtilHolder {
        private static ImageLoaderUtils INSTANCE = new ImageLoaderUtils();
    }

    /**
     * 设置全局图片加载策略
     */
    public void setLoadImgStrategy(@Nonnull  IImageLoaderStrategy imageLoaderStrategy) {
        this.imageLoaderStrategy = imageLoaderStrategy;
    }

    /**
     * 加载图片
     * @param context
     * @param imageLoader
     */
    public void loadImage(@Nonnull Context context,@Nonnull ImageLoader imageLoader){
        if (this.imageLoaderStrategy == null){
            return;
        }
        this.imageLoaderStrategy.loadImage(context,imageLoader);
    }
}
